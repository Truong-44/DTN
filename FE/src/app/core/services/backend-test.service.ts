// Simple Backend Connection Test
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class BackendTestService {
  private baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  // Test basic connection
  testConnection(): Observable<any> {
    console.log('Testing connection to:', this.baseUrl);
    return this.http.get(`${this.baseUrl}/health`);
  }

  // Test API endpoints
  testEndpoints(): void {
    const endpoints = [
      'api/sanpham',
      'api/danhmuc',
      'api/taikhoan',
      'api/giohang',
      'api/donhang',
    ];

    console.log('Testing endpoints:');
    endpoints.forEach((endpoint) => {
      this.http.get(`${this.baseUrl}/${endpoint}`).subscribe({
        next: (data) => {
          console.log(`✅ ${endpoint}: SUCCESS`, data);
        },
        error: (error) => {
          console.log(`❌ ${endpoint}: FAILED`, error);
        },
      });
    });
  }

  // Test authentication endpoints
  testAuthEndpoints(): void {
    const authEndpoints = ['api/taikhoan/validate-token'];

    console.log('Testing auth endpoints:');
    authEndpoints.forEach((endpoint) => {
      this.http.post(`${this.baseUrl}/${endpoint}`, {}).subscribe({
        next: (data) => {
          console.log(`✅ ${endpoint}: SUCCESS`, data);
        },
        error: (error) => {
          console.log(`❌ ${endpoint}: FAILED`, error);
        },
      });
    });
  }
}
