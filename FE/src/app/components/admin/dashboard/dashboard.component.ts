import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BaseChartDirective } from 'ng2-charts';
import { Chart, ChartConfiguration, ChartData, ChartEvent, ChartType, registerables } from 'chart.js';
import { Subject, takeUntil, catchError, of } from 'rxjs';
import { 
  DashboardService, 
  DashboardStats, 
  TopProduct, 
  RecentOrder,
  ChartData as ServiceChartData 
} from '../shared/services/dashboard.service';
import { MockDashboardService } from '../shared/services/mock-dashboard.service';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, BaseChartDirective],
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

  // Charts Data
  revenueChartData: ChartData<'line'> = {
    labels: [],
    datasets: []
  };

  ordersChartData: ChartData<'bar'> = {
    labels: [],
    datasets: []
  };

  categoryChartData: ChartData<'doughnut'> = {
    labels: [],
    datasets: []
  };

  // Chart Options
  revenueChartOptions: ChartConfiguration<'line'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'top'
      },
      title: {
        display: true,
        text: 'Doanh thu theo tháng'
      }
    },
    scales: {
      y: {
        beginAtZero: true,
        ticks: {
          callback: function(value) {
            return new Intl.NumberFormat('vi-VN', {
              style: 'currency',
              currency: 'VND'
            }).format(value as number);
          }
        }
      }
    }
  };

  ordersChartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'top'
      },
      title: {
        display: true,
        text: 'Số lượng đơn hàng'
      }
    },
    scales: {
      y: {
        beginAtZero: true
      }
    }
  };

  categoryChartOptions: ChartConfiguration<'doughnut'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: true,
        position: 'right'
      },
      title: {
        display: true,
        text: 'Sản phẩm theo danh mục'
      }
    }
  };

  // Other Data
  topProducts: TopProduct[] = [];
  recentOrders: RecentOrder[] = [];
  isLoading = true;

  constructor(
    private dashboardService: DashboardService,
    private mockDashboardService: MockDashboardService
  ) {}

  ngOnInit(): void {
    this.loadDashboardData();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadDashboardData(): void {
    this.isLoading = true;

    // Load stats - Try real API first, fallback to mock
    this.dashboardService.getDashboardStats()
      .pipe(
        takeUntil(this.destroy$),
        catchError((error) => {
          console.warn('Backend API not available, using mock data for dashboard stats');
          console.error('API Error:', error);
          return this.mockDashboardService.getDashboardStats();
        })
      )
      .subscribe({
        next: (stats) => {
          this.stats = stats;
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Error loading dashboard stats:', error);
          this.isLoading = false;
        }
      });

    // Load revenue chart
    this.dashboardService.getRevenueChart()
      .pipe(
        takeUntil(this.destroy$),
        catchError((error) => {
          console.warn('Backend API not available, using mock data for revenue chart');
          return this.mockDashboardService.getRevenueChart();
        })
      )
      .subscribe({
        next: (data) => {
          this.revenueChartData = {
            labels: data.labels,
            datasets: data.datasets.map(dataset => ({
              ...dataset,
              borderColor: '#FF9800',
              backgroundColor: 'rgba(255, 152, 0, 0.1)',
              borderWidth: 3,
              tension: 0.4
            }))
          };
        },
        error: (error) => console.error('Error loading revenue chart:', error)
      });

    // Load orders chart
    this.dashboardService.getOrdersChart()
      .pipe(
        takeUntil(this.destroy$),
        catchError((error) => {
          console.warn('Backend API not available, using mock data for orders chart');
          return this.mockDashboardService.getOrdersChart();
        })
      )
      .subscribe({
        next: (data) => {
          this.ordersChartData = {
            labels: data.labels,
            datasets: data.datasets.map(dataset => ({
              ...dataset,
              backgroundColor: ['#FF9800', '#FFA726', '#FFB74D', '#FFCC02'],
              borderColor: '#FF9800',
              borderWidth: 1
            }))
          };
        },
        error: (error) => console.error('Error loading orders chart:', error)
      });

    // Load category chart
    this.dashboardService.getCategoryChart()
      .pipe(
        takeUntil(this.destroy$),
        catchError((error) => {
          console.warn('Backend API not available, using mock data for category chart');
          return this.mockDashboardService.getCategoryChart();
        })
      )
      .subscribe({
        next: (data) => {
          this.categoryChartData = {
            labels: data.labels,
            datasets: data.datasets.map(dataset => ({
              ...dataset,
              backgroundColor: [
                '#FF9800', '#FFA726', '#FFB74D', '#FFCC02', 
                '#FF8F00', '#FF6F00', '#E65100', '#BF360C'
              ],
              borderWidth: 2,
              borderColor: '#FFFFFF'
            }))
          };
        },
        error: (error) => console.error('Error loading category chart:', error)
      });

    // Load top products
    this.dashboardService.getTopProducts()
      .pipe(
        takeUntil(this.destroy$),
        catchError((error) => {
          console.warn('Backend API not available, using mock data for top products');
          return this.mockDashboardService.getTopProducts();
        })
      )
      .subscribe({
        next: (products) => {
          this.topProducts = products;
        },
        error: (error) => console.error('Error loading top products:', error)
      });

    // Load recent orders
    this.dashboardService.getRecentOrders()
      .pipe(
        takeUntil(this.destroy$),
        catchError((error) => {
          console.warn('Backend API not available, using mock data for recent orders');
          return this.mockDashboardService.getRecentOrders();
        })
      )
      .subscribe({
        next: (orders) => {
          this.recentOrders = orders;
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
