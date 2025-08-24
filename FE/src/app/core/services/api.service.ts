import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  get<T>(path: string, params?: HttpParams): Observable<T> {
    const url = path.startsWith('http') ? path : `${this.baseUrl}/${path}`;
    console.log('API GET Request:', { url, params: params?.toString() });
    return this.http
      .get<T>(url, { params })
      .pipe(retry(1), catchError(this.handleError));
  }

  post<T>(path: string, body: any): Observable<T> {
    const url = path.startsWith('http') ? path : `${this.baseUrl}/${path}`;
    console.log('🌐 API POST Request:', { url, body });

    const headers = {
      'Content-Type': 'application/json',
      Accept: 'application/json',
    };

    return this.http
      .post<T>(url, body, { headers })
      .pipe(catchError(this.handleError));
  }

  put<T>(path: string, body: any): Observable<T> {
    const url = path.startsWith('http') ? path : `${this.baseUrl}/${path}`;
    return this.http.put<T>(url, body).pipe(catchError(this.handleError));
  }

  patch<T>(path: string, body: any): Observable<T> {
    const url = path.startsWith('http') ? path : `${this.baseUrl}/${path}`;
    return this.http.patch<T>(url, body).pipe(catchError(this.handleError));
  }

  delete<T>(path: string): Observable<T> {
    const url = path.startsWith('http') ? path : `${this.baseUrl}/${path}`;
    return this.http.delete<T>(url).pipe(catchError(this.handleError));
  }

  uploadFile<T>(path: string, file: File, additionalData?: any): Observable<T> {
    const formData = new FormData();
    formData.append('file', file);

    if (additionalData) {
      Object.keys(additionalData).forEach((key) => {
        formData.append(key, additionalData[key]);
      });
    }

    const url = path.startsWith('http') ? path : `${this.baseUrl}${path}`;
    return this.http.post<T>(url, formData).pipe(catchError(this.handleError));
  }

  downloadFile(path: string): Observable<Blob> {
    const url = path.startsWith('http') ? path : `${this.baseUrl}${path}`;
    return this.http
      .get(url, {
        responseType: 'blob',
      })
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Đã xảy ra lỗi không xác định';

    if (error.error instanceof ErrorEvent) {
      errorMessage = `Lỗi client: ${error.error.message}`;
    } else {
      switch (error.status) {
        case 400:
          errorMessage = 'Yêu cầu không hợp lệ (400)';
          break;
        case 401:
          errorMessage = 'Không có quyền truy cập (401)';
          break;
        case 403:
          errorMessage = 'Bị từ chối truy cập (403)';
          break;
        case 404:
          errorMessage = 'Không tìm thấy tài nguyên (404)';
          break;
        case 500:
          errorMessage = 'Lỗi máy chủ nội bộ (500)';
          break;
        case 503:
          errorMessage = 'Dịch vụ không khả dụng (503)';
          break;
        default:
          errorMessage = `Lỗi: ${error.status} - ${error.message}`;
      }
    }

    console.error('API Error Details:', {
      status: error.status,
      statusText: error.statusText,
      message: error.message,
      url: error.url,
      error: error.error,
      timestamp: new Date().toISOString(),
    });

    return throwError(() => new Error(errorMessage));
  }
}
