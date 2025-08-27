import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { Chart, ChartConfiguration, registerables } from 'chart.js';
import { DashboardService, DashboardStats, ChartData } from '../shared/services/dashboard.service';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard-new',
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard-new.html',
  styleUrl: './dashboard-new.scss'
})
export class DashboardNew implements OnInit, OnDestroy, AfterViewInit {
  @ViewChild('revenueChart', { static: false }) revenueChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('ordersChart', { static: false }) ordersChartRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('categoryChart', { static: false }) categoryChartRef!: ElementRef<HTMLCanvasElement>;

  stats: DashboardStats = {
    totalOrders: 0,
    totalRevenue: 0,
    totalProducts: 0,
    totalCustomers: 0,
    ordersPaid: 0,
    pendingOrders: 0
  };

  isLoading = true;
  error: string | null = null;
  
  private subscriptions = new Subscription();
  private revenueChart?: Chart;
  private ordersChart?: Chart;
  private categoryChart?: Chart;

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.loadDashboardData();
  }

  ngAfterViewInit(): void {
    // Charts will be initialized after data is loaded
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
    this.destroyCharts();
  }

  private loadDashboardData(): void {
    this.isLoading = true;
    this.error = null;

    // Load dashboard stats
    const statsSubscription = this.dashboardService.getDashboardStats().subscribe({
      next: (data) => {
        this.stats = data;
        this.isLoading = false;
        // Initialize charts after stats are loaded
        setTimeout(() => this.initializeCharts(), 100);
      },
      error: (error) => {
        console.error('Error loading dashboard stats:', error);
        this.error = 'Không thể tải dữ liệu thống kê';
        this.isLoading = false;
      }
    });

    this.subscriptions.add(statsSubscription);
  }

  private initializeCharts(): void {
    this.loadRevenueChart();
    this.loadOrdersChart();
    this.loadCategoryChart();
  }

  private loadRevenueChart(): void {
    const chartSubscription = this.dashboardService.getRevenueChart('month').subscribe({
      next: (chartData) => {
        this.createRevenueChart(chartData);
      },
      error: (error) => {
        console.error('Error loading revenue chart:', error);
      }
    });
    this.subscriptions.add(chartSubscription);
  }

  private loadOrdersChart(): void {
    const chartSubscription = this.dashboardService.getOrdersChart('month').subscribe({
      next: (chartData) => {
        this.createOrdersChart(chartData);
      },
      error: (error) => {
        console.error('Error loading orders chart:', error);
      }
    });
    this.subscriptions.add(chartSubscription);
  }

  private loadCategoryChart(): void {
    const chartSubscription = this.dashboardService.getCategoryChart().subscribe({
      next: (chartData) => {
        this.createCategoryChart(chartData);
      },
      error: (error) => {
        console.error('Error loading category chart:', error);
      }
    });
    this.subscriptions.add(chartSubscription);
  }

  private createRevenueChart(chartData: ChartData): void {
    if (this.revenueChart) {
      this.revenueChart.destroy();
    }

    const ctx = this.revenueChartRef?.nativeElement;
    if (!ctx) return;

    const config: ChartConfiguration = {
      type: 'line',
      data: {
        labels: chartData.labels,
        datasets: chartData.datasets.map(dataset => ({
          ...dataset,
          borderColor: '#ff6600',
          backgroundColor: 'rgba(255, 102, 0, 0.1)',
          borderWidth: 3,
          tension: 0.4,
          fill: true
        }))
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: {
            display: true,
            text: 'Biểu đồ doanh thu 12 tháng gần nhất',
            color: '#ff6600',
            font: { size: 16, weight: 'bold' }
          },
          legend: {
            display: false
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            ticks: {
              callback: function(value) {
                return new Intl.NumberFormat('vi-VN').format(Number(value)) + ' VNĐ';
              }
            }
          }
        }
      }
    };

    this.revenueChart = new Chart(ctx, config);
  }

  private createOrdersChart(chartData: ChartData): void {
    if (this.ordersChart) {
      this.ordersChart.destroy();
    }

    const ctx = this.ordersChartRef?.nativeElement;
    if (!ctx) return;

    const config: ChartConfiguration = {
      type: 'bar',
      data: {
        labels: chartData.labels,
        datasets: chartData.datasets.map(dataset => ({
          ...dataset,
          backgroundColor: '#ff6600',
          borderColor: '#e55a00',
          borderWidth: 1
        }))
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: {
            display: true,
            text: 'Biểu đồ đơn hàng 12 tháng gần nhất',
            color: '#ff6600',
            font: { size: 16, weight: 'bold' }
          },
          legend: {
            display: false
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            ticks: {
              stepSize: 1
            }
          }
        }
      }
    };

    this.ordersChart = new Chart(ctx, config);
  }

  private createCategoryChart(chartData: ChartData): void {
    if (this.categoryChart) {
      this.categoryChart.destroy();
    }

    const ctx = this.categoryChartRef?.nativeElement;
    if (!ctx) return;

    const config: ChartConfiguration = {
      type: 'doughnut',
      data: {
        labels: chartData.labels,
        datasets: chartData.datasets.map(dataset => ({
          ...dataset,
          backgroundColor: [
            '#ff6600', '#ff8533', '#ffa366', '#ffcc99',
            '#e55a00', '#cc4d00', '#b34000', '#993300'
          ],
          borderWidth: 2,
          borderColor: '#fff'
        }))
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: {
            display: true,
            text: 'Phân bố sản phẩm theo danh mục',
            color: '#ff6600',
            font: { size: 16, weight: 'bold' }
          },
          legend: {
            position: 'bottom',
            labels: {
              padding: 20,
              usePointStyle: true
            }
          }
        }
      }
    };

    this.categoryChart = new Chart(ctx, config);
  }

  private destroyCharts(): void {
    if (this.revenueChart) {
      this.revenueChart.destroy();
    }
    if (this.ordersChart) {
      this.ordersChart.destroy();
    }
    if (this.categoryChart) {
      this.categoryChart.destroy();
    }
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND'
    }).format(amount);
  }

  refreshData(): void {
    this.loadDashboardData();
  }
}
