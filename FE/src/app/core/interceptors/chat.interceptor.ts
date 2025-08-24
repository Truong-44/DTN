import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable()
export class ChatInterceptor implements HttpInterceptor {
  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    // Chỉ áp dụng cho chat endpoints
    if (request.url.includes('/api/chat/')) {
      // Add chat-specific headers if needed
      const chatRequest = request.clone({
        setHeaders: {
          'Content-Type': 'application/json',
          'X-Chat-Client': 'angular-web',
        },
      });

      return next.handle(chatRequest).pipe(
        retry(1), // Retry chat requests once if failed
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Lỗi kết nối chat';

          if (error.status === 0) {
            errorMessage = 'Không thể kết nối đến server chat';
          } else if (error.status === 429) {
            errorMessage = 'Quá nhiều tin nhắn, vui lòng chờ một chút';
          } else if (error.status >= 500) {
            errorMessage = 'Server chat đang gặp sự cố';
          } else if (error.error?.message) {
            errorMessage = error.error.message;
          }

          console.error('Chat Error:', errorMessage, error);
          return throwError(() => new Error(errorMessage));
        })
      );
    }

    return next.handle(request);
  }
}
