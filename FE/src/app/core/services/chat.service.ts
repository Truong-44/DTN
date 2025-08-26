import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject, throwError, of } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';
import { HttpParams } from '@angular/common/http';
import { ApiService } from './api.service';
import { AuthService } from './auth.service';
import { SanPhamService } from './sanpham.service';
import {
  ChatSession,
  ChatMessage,
  BotSuggestion,
  ProductSuggestionResponse,
} from '../models/chat.model';
import { _ENDPOINTS } from '../constants/api.constants';

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  // ===================== SUBJECTS =====================
  private messagesSubject = new BehaviorSubject<ChatMessage[]>([]);
  private currentSessionSubject = new BehaviorSubject<ChatSession | null>(null);
  private isConnectedSubject = new BehaviorSubject<boolean>(false);

  // ===================== OBSERVABLES =====================
  public messages$ = this.messagesSubject.asObservable();
  public currentSession$ = this.currentSessionSubject.asObservable();
  public isConnected$ = this.isConnectedSubject.asObservable();

  constructor(
    private apiService: ApiService,
    private authService: AuthService,
    private sanPhamService: SanPhamService
  ) {
    this.initializeService();
  }

  private initializeService(): void {
    // Initialize với empty state
    this.messagesSubject.next([]);
    this.currentSessionSubject.next(null);
    this.isConnectedSubject.next(false);
  }

  // ===================== SESSION MANAGEMENT =====================
  createSession(sessionData?: Partial<ChatSession>): Observable<ChatSession> {
    const user = this.authService.getCurrentUser();

    const payload: Partial<ChatSession> = {
      sessionType: user ? 'USER' : 'GUEST',
      status: 'ACTIVE',
      taiKhoanId: user?.id || undefined,
      ...sessionData,
    };

    return this.apiService
      .post<ChatSession>(_ENDPOINTS.SPECIFIC.CREATE_SESSION, payload)
      .pipe(
        tap((session) => {
          this.currentSessionSubject.next(session);
          this.isConnectedSubject.next(true);
          console.log('✅ Chat session created:', session);
        }),
        catchError((error) => {
          console.error('❌ Failed to create chat session:', error);
          this.isConnectedSubject.next(false);
          return throwError(() => error);
        })
      );
  }

  getSession(sessionId: number): Observable<ChatSession> {
    const endpoint = _ENDPOINTS.SPECIFIC.GET_SESSION.replace(
      '{id}',
      sessionId.toString()
    );
    return this.apiService.get<ChatSession>(endpoint);
  }

  getAllSessions(): Observable<ChatSession[]> {
    return this.apiService.get<ChatSession[]>(
      _ENDPOINTS.SPECIFIC.GET_ALL_SESSIONS
    );
  }

  getSessionsByUser(taiKhoanId: number): Observable<ChatSession[]> {
    const endpoint = _ENDPOINTS.SPECIFIC.GET_SESSIONS_BY_USER.replace(
      '{taiKhoanId}',
      taiKhoanId.toString()
    );
    return this.apiService.get<ChatSession[]>(endpoint);
  }

  getLatestSessionByUser(taiKhoanId: number): Observable<ChatSession> {
    const endpoint = _ENDPOINTS.SPECIFIC.GET_LATEST_SESSION.replace(
      '{taiKhoanId}',
      taiKhoanId.toString()
    );
    return this.apiService.get<ChatSession>(endpoint);
  }

  getGuestSessions(): Observable<ChatSession[]> {
    return this.apiService.get<ChatSession[]>(
      _ENDPOINTS.SPECIFIC.GET_GUEST_SESSIONS
    );
  }

  endSession(sessionId: number): Observable<void> {
    const endpoint = _ENDPOINTS.SPECIFIC.END_SESSION.replace(
      '{id}',
      sessionId.toString()
    );
    return this.apiService.post<void>(endpoint, {}).pipe(
      tap(() => {
        this.currentSessionSubject.next(null);
        this.isConnectedSubject.next(false);
        console.log('✅ Chat session ended');
      }),
      catchError((error) => {
        console.error('❌ Failed to end session:', error);
        return throwError(() => error);
      })
    );
  }

  setCurrentSession(session: ChatSession): void {
    this.currentSessionSubject.next(session);
    this.isConnectedSubject.next(session?.status === 'ACTIVE');
  }

  getCurrentSession(): ChatSession | null {
    return this.currentSessionSubject.value;
  }

  isSessionActive(): boolean {
    const session = this.getCurrentSession();
    return session?.status === 'ACTIVE' && this.isConnectedSubject.value;
  }

  // ===================== MESSAGE MANAGEMENT =====================
  sendMessage(messageData: Partial<ChatMessage>): Observable<ChatMessage> {
    const payload = {
      sessionId: messageData.sessionId,
      content: messageData.content,
      messageType: messageData.messageType || 'USER',
      timestamp: new Date(),
      ...messageData,
    };

    return this.apiService
      .post<ChatMessage>(_ENDPOINTS.SPECIFIC.SEND_MESSAGE, payload)
      .pipe(
        tap((message) => {
          this.addMessage(message);
          console.log('✅ Message sent:', message);
        }),
        catchError((error) => {
          console.error('❌ Failed to send message:', error);
          return throwError(() => error);
        })
      );
  }

  sendBotResponse(botResponseData: any): Observable<ChatMessage> {
    return this.apiService
      .post<ChatMessage>(_ENDPOINTS.SPECIFIC.SEND_BOT_RESPONSE, botResponseData)
      .pipe(
        tap((message) => {
          this.addMessage(message);
          console.log('✅ Bot response received:', message);
        }),
        catchError((error) => {
          console.error('❌ Failed to get bot response:', error);
          return throwError(() => error);
        })
      );
  }

  getMessagesBySession(sessionId: number): Observable<ChatMessage[]> {
    const endpoint = _ENDPOINTS.SPECIFIC.GET_MESSAGES.replace(
      '{sessionId}',
      sessionId.toString()
    );
    return this.apiService.get<ChatMessage[]>(endpoint).pipe(
      tap((messages) => {
        this.messagesSubject.next(messages || []);
        console.log('✅ Messages loaded:', messages?.length || 0);
      }),
      catchError((error) => {
        console.error('❌ Failed to load messages:', error);
        return of([]);
      })
    );
  }

  getMessagesBySessionPaged(
    sessionId: number,
    page: number = 0,
    size: number = 50
  ): Observable<any> {
    const endpoint = _ENDPOINTS.SPECIFIC.GET_MESSAGES_PAGED.replace(
      '{sessionId}',
      sessionId.toString()
    );
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.apiService.get<any>(endpoint, params);
  }

  getMessageCount(sessionId: number): Observable<number> {
    const endpoint = _ENDPOINTS.SPECIFIC.GET_MESSAGE_COUNT.replace(
      '{sessionId}',
      sessionId.toString()
    );
    return this.apiService.get<number>(endpoint);
  }

  searchMessages(query: string): Observable<ChatMessage[]> {
    const params = new HttpParams().set('query', query);
    return this.apiService.get<ChatMessage[]>(
      _ENDPOINTS.SPECIFIC.SEARCH_MESSAGES,
      params
    );
  }

  addMessage(message: ChatMessage): void {
    const currentMessages = this.messagesSubject.value;
    const updatedMessages = [...currentMessages, message];
    this.messagesSubject.next(updatedMessages);
  }

  clearMessages(): void {
    this.messagesSubject.next([]);
  }

  // ===================== BOT SUGGESTIONS =====================
  getBotSuggestions(): Observable<BotSuggestion[]> {
    return this.apiService
      .get<BotSuggestion[]>(_ENDPOINTS.SPECIFIC.GET_BOT_SUGGESTIONS)
      .pipe(
        catchError((error) => {
          console.error('❌ Failed to get bot suggestions:', error);
          return of([]);
        })
      );
  }

  suggestProducts(
    sessionId: number,
    query: string
  ): Observable<ProductSuggestionResponse[]> {
    const endpoint = _ENDPOINTS.SPECIFIC.SUGGEST_PRODUCTS.replace(
      '{sessionId}',
      sessionId.toString()
    );
    const payload = { query, sessionId };

    return this.apiService
      .post<ProductSuggestionResponse[]>(endpoint, payload)
      .pipe(
        catchError((error) => {
          console.error('❌ Failed to get product suggestions:', error);
          return of([]);
        })
      );
  }

  // ===================== SEARCH INTEGRATION =====================
  searchProductsForChat(
    keyword: string
  ): Observable<ProductSuggestionResponse[]> {
    return this.sanPhamService.searchSanPham(keyword).pipe(
      map((products) => {
        return (products || []).slice(0, 3).map(
          (product) =>
            ({
              sanpham: product,
              reason: `Sản phẩm phù hợp với từ khóa "${keyword}"`,
              confidence: 0.8,
              relevanceScore: 0.9,
            } as ProductSuggestionResponse)
        );
      }),
      catchError((error) => {
        console.error('❌ Search products for chat failed:', error);
        return of([]);
      })
    );
  }

  // ===================== SESSION QUERIES =====================
  getSessionsInDateRange(
    startDate: Date,
    endDate: Date
  ): Observable<ChatSession[]> {
    const params = new HttpParams()
      .set('startDate', startDate.toISOString())
      .set('endDate', endDate.toISOString());

    return this.apiService.get<ChatSession[]>(
      _ENDPOINTS.SPECIFIC.GET_SESSIONS_DATE_RANGE,
      params
    );
  }

  // ===================== UTILITY METHODS =====================
  resetSession(): void {
    this.currentSessionSubject.next(null);
    this.clearMessages();
    this.isConnectedSubject.next(false);
  }

  // ===================== CONNECTION STATUS =====================
  checkConnection(): Observable<boolean> {
    return this.apiService.get<any>('/api/chat/sessions').pipe(
      map(() => {
        this.isConnectedSubject.next(true);
        return true;
      }),
      catchError(() => {
        this.isConnectedSubject.next(false);
        return of(false);
      })
    );
  }

  // ===================== ERROR RECOVERY =====================
  retryConnection(): Observable<boolean> {
    const currentSession = this.getCurrentSession();

    if (currentSession?.id) {
      return this.getSession(currentSession.id).pipe(
        map((session) => {
          this.setCurrentSession(session);
          return true;
        }),
        catchError(() => {
          this.resetSession();
          return of(false);
        })
      );
    }

    return of(false);
  }
}
