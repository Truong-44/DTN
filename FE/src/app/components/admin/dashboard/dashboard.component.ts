import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Subject, takeUntil, catchError, of } from 'rxjs';

interface DashboardStats {
  totalOrders: number;
  totalRevenue: number;
  totalProducts: number;
  totalCustomers: number;
  ordersPaid: number;
  pendingOrders: number;
}

interface TopProduct {
  id: number;
  tensanpham: string;
  totalSold: number;
  revenue: number;
  hinhchinh?: string;
}

interface RecentOrder {
  id: number;
  customerName: string;
  total: number;
  status: string;
  ngaytao: string;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();

  // Dashboard Stats
  stats: DashboardStats = {
    totalOrders: 0,
    totalRevenue: 0,
    totalProducts: 0,
    totalCustomers: 0,
    ordersPaid: 0,
    pendingOrders: 0
  };

  // Other Data
  topProducts: TopProduct[] = [];
  recentOrders: RecentOrder[] = [];
  isLoading = true;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadDashboardData();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadDashboardData(): void {
    this.isLoading = true;

    // Load stats from real backend
    this.http.get<any>('http://localhost:8080/api/admin/dashboard/stats')
      .pipe(
        takeUntil(this.destroy$),
        catchError((error) => {
          console.warn('Dashboard stats API not available, using mock data');
          return of({
            data: {
              totalOrders: 156,
              totalRevenue: 2450000000,
              totalProducts: 89,
              totalCustomers: 234,
              ordersPaid: 142,
              pendingOrders: 14
            }
          });
        })
      )
      .subscribe({
        next: (response) => {
          this.stats = response.data;
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error loading dashboard stats:', error);
          this.isLoading = false;
        }
      });

    // Load top products from real backend
    this.http.get<any>('http://localhost:8080/api/admin/dashboard/top-products')
      .pipe(
        takeUntil(this.destroy$),
        catchError((error) => {
          console.warn('Top products API not available, using mock data');
          return of({
            data: [
              {
                id: 1,
                tensanpham: 'Ghế Sofa MOHO GIORGIO',
                totalSold: 45,
                revenue: 580500000,
                hinhchinh: 'sanpham/ghe/sofa/giorgio_01.jpg'
              },
              {
                id: 2,
                tensanpham: 'Bàn Ăn Gỗ Tự Nhiên',
                totalSold: 32,
                revenue: 320000000,
                hinhchinh: 'sanpham/ban/an/ban_an_01.jpg'
              }
            ]
          });
        })
      )
      .subscribe({
        next: (response) => {
          this.topProducts = response.data;
        },
        error: (error) => console.error('Error loading top products:', error)
      });

    // Load recent orders from real backend
    this.http.get<any>('http://localhost:8080/api/admin/dashboard/recent-orders')
      .pipe(
        takeUntil(this.destroy$),
        catchError((error) => {
          console.warn('Recent orders API not available, using mock data');
          return of({
            data: [
              {
                id: 1001,
                customerName: 'Nguyễn Văn An',
                total: 15000000,
                status: 'Hoàn thành',
                ngaytao: '2025-08-26T10:30:00'
              },
              {
                id: 1002,
                customerName: 'Trần Thị Bình',
                total: 8500000,
                status: 'Chờ xử lý',
                ngaytao: '2025-08-26T09:15:00'
              }
            ]
          });
        })
      )
      .subscribe({
        next: (response) => {
          this.recentOrders = response.data;
        },
        error: (error) => console.error('Error loading recent orders:', error)
      });
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(amount);
  }

  formatNumber(num: number): string {
    return new Intl.NumberFormat('vi-VN').format(num);
  }

  getStatusClass(status: string): string {
    switch (status?.toLowerCase()) {
      case 'completed':
      case 'hoàn thành':
        return 'status-completed';
      case 'pending':
      case 'chờ xử lý':
        return 'status-pending';
      case 'cancelled':
      case 'đã hủy':
        return 'status-cancelled';
      default:
        return 'status-default';
    }
  }

  refreshData(): void {
    this.loadDashboardData();
  }

  trackByProductId(index: number, product: TopProduct): number {
    return product.id;
  }

  trackByOrderId(index: number, order: RecentOrder): number {
    return order.id;
  }

  onImageError(event: Event): void {
    const target = event.target as HTMLImageElement;
    if (target) {
      target.src = '/assets/img/placeholder/product.png';
    }
  }

  getProductImageUrl(hinhchinh: string): string {
    if (!hinhchinh) {
      return '/assets/img/placeholder/product.png';
    }
    return `/assets/img/${hinhchinh}`;
  }
}
