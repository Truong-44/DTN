import { Injectable } from '@angular/core';
import { Observable, of, delay } from 'rxjs';
import { 
  DashboardStats, 
  ChartData, 
  TopProduct, 
  RecentOrder 
} from './dashboard.service';

@Injectable({
  providedIn: 'root'
})
export class MockDashboardService {

  getDashboardStats(): Observable<DashboardStats> {
    const mockStats: DashboardStats = {
      totalOrders: 1247,
      totalRevenue: 125690000,
      totalProducts: 189,
      totalCustomers: 856,
      ordersPaid: 1180,
      pendingOrders: 67
    };
    
    return of(mockStats).pipe(delay(500));
  }

  getRevenueChart(period: string = 'month'): Observable<ChartData> {
    const mockData: ChartData = {
      labels: ['T1', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'T8', 'T9', 'T10', 'T11', 'T12'],
      datasets: [{
        label: 'Doanh thu (VNĐ)',
        data: [8500000, 9200000, 10500000, 11800000, 13200000, 15600000, 14800000, 16200000, 17500000, 18900000, 20100000, 22300000],
        borderColor: ['#FF9800'],
        backgroundColor: ['rgba(255, 152, 0, 0.1)'],
        borderWidth: 3
      }]
    };
    
    return of(mockData).pipe(delay(300));
  }

  getOrdersChart(period: string = 'month'): Observable<ChartData> {
    const mockData: ChartData = {
      labels: ['T1', 'T2', 'T3', 'T4', 'T5', 'T6', 'T7', 'T8', 'T9', 'T10', 'T11', 'T12'],
      datasets: [{
        label: 'Số đơn hàng',
        data: [45, 52, 68, 75, 89, 95, 88, 102, 115, 125, 138, 156],
        backgroundColor: ['#FF9800', '#FFA726', '#FFB74D', '#FFCC02', '#FF8F00', '#FF6F00', '#E65100', '#BF360C', '#FF9800', '#FFA726', '#FFB74D', '#FFCC02']
      }]
    };
    
    return of(mockData).pipe(delay(300));
  }

  getTopProducts(limit: number = 5): Observable<TopProduct[]> {
    const mockProducts: TopProduct[] = [
      {
        id: 1,
        name: 'Ghế sofa cao cấp',
        hinhchinh: 'sofa1.jpg',
        quantity: 45,
        revenue: 15600000
      },
      {
        id: 2,
        name: 'Bàn ăn gỗ tự nhiên',
        hinhchinh: 'ban-an1.jpg',
        quantity: 38,
        revenue: 12800000
      },
      {
        id: 3,
        name: 'Tủ quần áo hiện đại',
        hinhchinh: 'tu-quan-ao1.jpg',
        quantity: 32,
        revenue: 18900000
      },
      {
        id: 4,
        name: 'Giường ngủ king size',
        hinhchinh: 'giuong1.jpg',
        quantity: 28,
        revenue: 14200000
      },
      {
        id: 5,
        name: 'Kệ tivi phòng khách',
        hinhchinh: 'ke-tivi1.jpg',
        quantity: 25,
        revenue: 8750000
      }
    ];
    
    return of(mockProducts.slice(0, limit)).pipe(delay(400));
  }

  getRecentOrders(limit: number = 10): Observable<RecentOrder[]> {
    const mockOrders: RecentOrder[] = [
      {
        id: 1001,
        customerName: 'Nguyễn Văn An',
        total: 2650000,
        status: 'Hoàn thành',
        createdDate: '2025-08-25T10:30:00'
      },
      {
        id: 1002,
        customerName: 'Trần Thị Bình',
        total: 1850000,
        status: 'Chờ xử lý',
        createdDate: '2025-08-25T09:15:00'
      },
      {
        id: 1003,
        customerName: 'Lê Minh Cường',
        total: 4200000,
        status: 'Hoàn thành',
        createdDate: '2025-08-24T16:45:00'
      },
      {
        id: 1004,
        customerName: 'Phạm Thị Dung',
        total: 3100000,
        status: 'Chờ xử lý',
        createdDate: '2025-08-24T14:20:00'
      },
      {
        id: 1005,
        customerName: 'Hoàng Văn Em',
        total: 950000,
        status: 'Đã hủy',
        createdDate: '2025-08-24T11:10:00'
      },
      {
        id: 1006,
        customerName: 'Vũ Thị Phương',
        total: 5800000,
        status: 'Hoàn thành',
        createdDate: '2025-08-23T15:30:00'
      },
      {
        id: 1007,
        customerName: 'Đỗ Minh Quang',
        total: 2200000,
        status: 'Chờ xử lý',
        createdDate: '2025-08-23T13:45:00'
      },
      {
        id: 1008,
        customerName: 'Bùi Thị Hương',
        total: 3750000,
        status: 'Hoàn thành',
        createdDate: '2025-08-23T10:20:00'
      }
    ];
    
    return of(mockOrders.slice(0, limit)).pipe(delay(350));
  }

  getCategoryChart(): Observable<ChartData> {
    const mockData: ChartData = {
      labels: ['Ghế sofa', 'Bàn ăn', 'Tủ quần áo', 'Giường ngủ', 'Kệ tivi', 'Bàn làm việc', 'Tủ bếp'],
      datasets: [{
        label: 'Số lượng sản phẩm',
        data: [25, 18, 32, 15, 22, 12, 28],
        backgroundColor: [
          '#FF9800', '#FFA726', '#FFB74D', '#FFCC02', 
          '#FF8F00', '#FF6F00', '#E65100'
        ]
      }]
    };
    
    return of(mockData).pipe(delay(300));
  }
}
