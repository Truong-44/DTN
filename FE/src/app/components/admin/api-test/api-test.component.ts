import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-api-test',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div style="padding: 20px; background: white; margin: 20px;">
      <h1 style="color: #FF9800;">🧪 API Connection Test</h1>
      
      <div *ngIf="loading" style="color: #2196F3;">
        Loading data from backend...
      </div>
      
      <div *ngIf="error" style="color: #f44336; background: #ffebee; padding: 10px; border-radius: 4px;">
        <h3>❌ API Error:</h3>
        <pre>{{ error | json }}</pre>
      </div>
      
      <div *ngIf="data && !loading" style="color: #4CAF50;">
        <h3>✅ API Connection Successful!</h3>
        <p><strong>Message:</strong> {{ data.message }}</p>
        <p><strong>Success:</strong> {{ data.success }}</p>
        <p><strong>Product Count:</strong> {{ data.data?.length || 0 }}</p>
        
        <div *ngIf="data.data?.length > 0">
          <h4>First Product:</h4>
          <pre style="background: #f5f5f5; padding: 10px; border-radius: 4px;">{{ data.data[0] | json }}</pre>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class ApiTestComponent implements OnInit {
  data: any = null;
  loading = true;
  error: any = null;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    console.log('🧪 Starting API test...');
    
    this.http.get('/api/sanpham').subscribe({
      next: (response) => {
        console.log('✅ API Response:', response);
        this.data = response;
        this.loading = false;
      },
      error: (error) => {
        console.error('❌ API Error:', error);
        this.error = error;
        this.loading = false;
      }
    });
  }
}
