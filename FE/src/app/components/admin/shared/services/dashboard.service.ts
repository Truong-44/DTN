import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../../../../environments/environment';

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp: string;
  path?: string;
}

export interface DashboardStats {
  totalOrders: number;
  totalRevenue: number;
  totalProducts: number;
  totalCustomers: number;
  ordersPaid: number;
  pendingOrders: number;
}

export interface ChartData {
  labels: string[];
  datasets: {
    label: string;
    data: number[];
    backgroundColor?: string[];
    borderColor?: string[];
    borderWidth?: number;
  }[];
}

export interface TopProduct {
  id: number;
  name: string;
  hinhchinh: string; // Thay đổi từ image sang hinhchinh
  quantity: number;
  revenue: number;
}

export interface RecentOrder {
  id: number;
  customerName: string;
  total: number;
  status: string;
  createdDate: string;
}

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private apiUrl = environment.apiUrl || 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getDashboardStats(): Observable<DashboardStats> {
    return this.http.get<ApiResponse<DashboardStats>>(`${this.apiUrl}/api/admin/dashboard/stats`)
      .pipe(map(response => response.data));
  }

  getRevenueChart(period: string = 'month'): Observable<ChartData> {
    return this.http.get<ApiResponse<ChartData>>(`${this.apiUrl}/api/admin/dashboard/revenue-chart?period=${period}`)
      .pipe(map(response => response.data));
  }

  getOrdersChart(period: string = 'month'): Observable<ChartData> {
    return this.http.get<ApiResponse<ChartData>>(`${this.apiUrl}/api/admin/dashboard/orders-chart?period=${period}`)
      .pipe(map(response => response.data));
  }

  getTopProducts(limit: number = 5): Observable<TopProduct[]> {
    return this.http.get<ApiResponse<TopProduct[]>>(`${this.apiUrl}/api/admin/dashboard/top-products?limit=${limit}`)
      .pipe(map(response => response.data));
  }

  getRecentOrders(limit: number = 10): Observable<RecentOrder[]> {
    return this.http.get<ApiResponse<RecentOrder[]>>(`${this.apiUrl}/api/admin/dashboard/recent-orders?limit=${limit}`)
      .pipe(map(response => response.data));
  }

  getCategoryChart(): Observable<ChartData> {
    return this.http.get<ApiResponse<ChartData>>(`${this.apiUrl}/api/admin/dashboard/category-chart`)
      .pipe(map(response => response.data));
  }
}
