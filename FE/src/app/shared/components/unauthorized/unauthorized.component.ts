import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-unauthorized',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="unauthorized-container">
      <div class="unauthorized-content">
        <div class="error-icon">
          <i class="fas fa-lock fa-5x"></i>
        </div>
        <h1>403 - Không có quyền truy cập</h1>
        <p>Bạn không có quyền truy cập vào trang này.</p>
        <div class="actions">
          <button class="btn btn-primary" (click)="goHome()">
            <i class="fas fa-home"></i> Về trang chủ
          </button>
          <button class="btn btn-secondary" (click)="goBack()">
            <i class="fas fa-arrow-left"></i> Quay lại
          </button>
        </div>
      </div>
    </div>
  `,
  styles: [
    `
      .unauthorized-container {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        background-color: #f8f9fa;
        padding: 20px;
      }

      .unauthorized-content {
        text-align: center;
        background: white;
        padding: 40px;
        border-radius: 10px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        max-width: 500px;
        width: 100%;
      }

      .error-icon {
        color: #dc3545;
        margin-bottom: 20px;
      }

      h1 {
        color: #333;
        margin-bottom: 15px;
        font-size: 2rem;
      }

      p {
        color: #666;
        margin-bottom: 30px;
        font-size: 1.1rem;
      }

      .actions {
        display: flex;
        gap: 15px;
        justify-content: center;
        flex-wrap: wrap;
      }

      .btn {
        padding: 12px 24px;
        border: none;
        border-radius: 5px;
        font-size: 1rem;
        cursor: pointer;
        text-decoration: none;
        transition: all 0.3s;
        display: inline-flex;
        align-items: center;
        gap: 8px;
      }

      .btn-primary {
        background-color: #007bff;
        color: white;
      }

      .btn-primary:hover {
        background-color: #0056b3;
      }

      .btn-secondary {
        background-color: #6c757d;
        color: white;
      }

      .btn-secondary:hover {
        background-color: #545b62;
      }

      @media (max-width: 768px) {
        .unauthorized-content {
          padding: 20px;
        }

        .actions {
          flex-direction: column;
        }

        .btn {
          width: 100%;
          justify-content: center;
        }
      }
    `,
  ],
})
export class UnauthorizedComponent {
  constructor(private router: Router) {}

  goHome() {
    this.router.navigate(['/']);
  }

  goBack() {
    window.history.back();
  }
}
