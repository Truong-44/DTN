import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, catchError, map } from 'rxjs';
import { environment } from '../../../../../environments/environment';

// Interfaces
export interface DashboardStats {
  totalOrders: number;
  totalRevenue: number;
  totalProducts: number;
  totalCustomers: number;
  ordersPaid: number;
  pendingOrders: number;
}

export interface TopProduct {
  id: number;
  tensanpham: string;
  totalSold: number;
  revenue: number;
  hinhchinh?: string;
}

export interface RecentOrder {
  id: number;
  customerName: string;
  total: number;
  status: string;
  ngaytao: string;
}

export interface SanPham {
  id: number;
  tensanpham: string;
  mota?: string;
  trangthai: boolean;
  danhmucid?: number;
  tenloai?: string;
  giacu?: number;
  giamoi?: number;
  ngaytao?: string;
  chitietsanpham?: ChiTietSanPham[];
}

export interface ChiTietSanPham {
  id: number;
  sanphamid: number;
  tenmau?: string;
  mamau?: string;
  chatlieu?: string;
  kichthuoc?: string;
  trongluong?: number;
  soluong: number;
  hinhchinh?: string;
  hinhphu?: string;
}

export interface DanhMuc {
  id: number;
  tendanhmuc: string;
  mota?: string;
  danhmucchaid?: number;
}

export interface KhachHang {
  id: number;
  hoten: string;
  email?: string;
  sodienthoai?: string;
  diachi?: string;
  gioitinh?: string;
  ngaysinh?: string;
  taikhoanid?: number;
  taikhoan?: any;
}

export interface NhanVien {
  id: number;
  hoten: string;
  email?: string;
  sodienthoai?: string;
  chucvu?: string;
  taikhoanid?: number;
  ngayvaolam?: string;
  taikhoan?: any;
}

export interface DonHang {
  id: number;
  khachhangid: number;
  ngaydat: string;
  diachinhan?: string;
  trangthaidonhang: string;
  tongtien: number;
  phuongthucthanhtoan?: string;
  trangthaithanhtoan: boolean;
  ngaythanhtoan?: string;
  khachhang?: KhachHang;
  chitietdonhang?: ChiTietDonHang[];
}

export interface ChiTietDonHang {
  id: number;
  donhangid: number;
  chitietsanphamid: number;
  soluong: number;
  dongia: number;
  chitietsanpham?: ChiTietSanPham;
}

export interface HoaDon {
  id: number;
  donhangid: number;
  ngayxuathoadon: string;
  nguoixuathoadon?: string;
  tenkhachhang?: string;
  diachinguoinhan?: string;
  tongtien: number;
  ghichu?: string;
  donhang?: DonHang;
}

export interface KhoHang {
  id: number;
  chitietsanphamid: number;
  soluongton: number;
  ngaycapnhat: string;
  chitietsanpham?: ChiTietSanPham;
}

@Injectable({
  providedIn: 'root'
})
export class AdminDataService {
  private baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  // ==================== DASHBOARD ====================
  getDashboardStats(): Observable<DashboardStats> {
    return this.http.get<any>(`${this.baseUrl}/api/admin/dashboard/stats`).pipe(
      map((response) => {
        const d = response?.data || {};
        // Backend may return BigDecimal as number or string; coerce to number
        const toNum = (v: any) => {
          if (v == null) return 0;
          if (typeof v === 'number') return v;
          const n = Number(v);
          return isNaN(n) ? 0 : n;
        };
        const normalized: DashboardStats = {
          totalOrders: toNum(d.totalOrders),
          totalRevenue: toNum(d.totalRevenue),
          totalProducts: toNum(d.totalProducts),
          totalCustomers: toNum(d.totalCustomers),
          ordersPaid: toNum(d.ordersPaid),
          pendingOrders: toNum(d.pendingOrders),
        };
        return normalized;
      }),
      catchError(() => this.getMockDashboardStats())
    );
  }

  getTopProducts(): Observable<TopProduct[]> {
    return this.http.get<any>(`${this.baseUrl}/api/admin/dashboard/top-products`).pipe(
      map((response) => {
        const list = Array.isArray(response?.data) ? response.data : [];
        const cleanPath = (image?: string) => {
          if (!image) return '';
          // Backend returns '/assets/img/<path>'; keep relative path only
          return image.replace(/^\/?assets\/?img\//i, '').replace(/^\//, '');
        };
        const mapped: TopProduct[] = list.map((item: any) => ({
          id: Number(item.id),
          tensanpham: item.name ?? item.tensanpham ?? 'Sản phẩm',
          totalSold: Number(item.quantity ?? item.totalSold ?? 0),
          revenue: Number(item.revenue ?? 0),
          hinhchinh: cleanPath(item.image ?? item.hinhchinh),
        }));
        return mapped;
      }),
      catchError(() => this.getMockTopProducts())
    );
  }

  getRecentOrders(): Observable<RecentOrder[]> {
    return this.http.get<any>(`${this.baseUrl}/api/admin/dashboard/recent-orders`).pipe(
      map((response) => {
        const list = Array.isArray(response?.data) ? response.data : [];
        const mapped: RecentOrder[] = list.map((item: any) => ({
          id: Number(item.id),
          customerName: item.customerName ?? item.khachhang?.hoten ?? 'Khách hàng',
          total: Number(item.total ?? 0),
          status: item.status ?? 'pending',
          ngaytao: (item.createdDate ?? item.ngaytao ?? '').toString(),
        }));
        return mapped;
      }),
      catchError(() => this.getMockRecentOrders())
    );
  }

  // ==================== SẢN PHẨM ====================
  getAllProducts(): Observable<SanPham[]> {
    return this.http.get<any>(`${this.baseUrl}/api/sanpham`).pipe(
      map(response => response.data || response),
      catchError(() => this.getMockProducts())
    );
  }

  getProductById(id: number): Observable<SanPham> {
    return this.http.get<any>(`${this.baseUrl}/api/sanpham/${id}`).pipe(
      map(response => response.data)
    );
  }

  createProduct(product: Partial<SanPham>): Observable<SanPham> {
    return this.http.post<any>(`${this.baseUrl}/api/sanpham`, product).pipe(
      map(response => response.data)
    );
  }

  updateProduct(id: number, product: Partial<SanPham>): Observable<SanPham> {
    return this.http.put<any>(`${this.baseUrl}/api/sanpham/${id}`, product).pipe(
      map(response => response.data)
    );
  }

  deleteProduct(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/api/sanpham/${id}`);
  }

  updateProductStatus(id: number, status: boolean): Observable<any> {
    return this.http.patch(`${this.baseUrl}/api/sanpham/${id}/status`, { trangthai: status });
  }

  // ==================== DANH MỤC ====================
  getAllCategories(): Observable<DanhMuc[]> {
    return this.http.get<any>(`${this.baseUrl}/api/danhmuc`).pipe(
      map(response => response.data || response),
      catchError(() => this.getMockCategories())
    );
  }

  createCategory(category: Partial<DanhMuc>): Observable<DanhMuc> {
    return this.http.post<any>(`${this.baseUrl}/api/danhmuc`, category).pipe(
      map(response => response.data)
    );
  }

  updateCategory(id: number, category: Partial<DanhMuc>): Observable<DanhMuc> {
    return this.http.put<any>(`${this.baseUrl}/api/danhmuc/${id}`, category).pipe(
      map(response => response.data)
    );
  }

  deleteCategory(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/api/danhmuc/${id}`);
  }

  // ==================== KHÁCH HÀNG ====================
  getAllCustomers(): Observable<KhachHang[]> {
    return this.http.get<any>(`${this.baseUrl}/api/khachhang`).pipe(
      map(response => response.data || response),
      catchError(() => this.getMockCustomers())
    );
  }

  createCustomer(customer: Partial<KhachHang>): Observable<KhachHang> {
    return this.http.post<any>(`${this.baseUrl}/api/khachhang`, customer).pipe(
      map(response => response.data)
    );
  }

  updateCustomer(id: number, customer: Partial<KhachHang>): Observable<KhachHang> {
    return this.http.put<any>(`${this.baseUrl}/api/khachhang/${id}`, customer).pipe(
      map(response => response.data)
    );
  }

  deleteCustomer(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/api/khachhang/${id}`);
  }

  // ==================== NHÂN VIÊN ====================
  getAllEmployees(): Observable<NhanVien[]> {
    return this.http.get<any>(`${this.baseUrl}/api/nhanvien`).pipe(
      map(response => response.data || response),
      catchError(() => this.getMockEmployees())
    );
  }

  createEmployee(employee: Partial<NhanVien>): Observable<NhanVien> {
    return this.http.post<any>(`${this.baseUrl}/api/nhanvien`, employee).pipe(
      map(response => response.data)
    );
  }

  updateEmployee(id: number, employee: Partial<NhanVien>): Observable<NhanVien> {
    return this.http.put<any>(`${this.baseUrl}/api/nhanvien/${id}`, employee).pipe(
      map(response => response.data)
    );
  }

  deleteEmployee(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/api/nhanvien/${id}`);
  }

  // ==================== ĐỐN HÀNG ====================
  getAllOrders(): Observable<DonHang[]> {
    return this.http.get<any>(`${this.baseUrl}/api/donhang`).pipe(
      map(response => response.data || response),
      catchError(() => this.getMockOrders())
    );
  }

  updateOrderStatus(id: number, status: string): Observable<any> {
    return this.http.patch(`${this.baseUrl}/api/donhang/${id}/status`, { trangthaidonhang: status });
  }

  deleteOrder(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/api/donhang/${id}`);
  }

  cancelOrder(id: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/donhang/${id}/cancel`, {});
  }

  // ==================== HÓA ĐƠN ====================
  getAllInvoices(): Observable<HoaDon[]> {
    return this.http.get<any>(`${this.baseUrl}/api/hoadon`).pipe(
      map(response => response.data || response),
      catchError(() => this.getMockInvoices())
    );
  }

  createInvoice(invoice: Partial<HoaDon>): Observable<HoaDon> {
    return this.http.post<any>(`${this.baseUrl}/api/hoadon`, invoice).pipe(
      map(response => response.data)
    );
  }

  // ==================== KHO HÀNG ====================
  getAllStock(): Observable<KhoHang[]> {
    return this.http.get<any>(`${this.baseUrl}/api/khohang`).pipe(
      map(response => response.data || response),
      catchError(() => this.getMockStock())
    );
  }

  updateStock(id: number, quantity: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/api/khohang/${id}/soluong`, { soluong: quantity });
  }

  // ==================== MOCK DATA ====================
  private getMockDashboardStats(): Observable<DashboardStats> {
    return of({
      totalOrders: 156,
      totalRevenue: 2450000000,
      totalProducts: 89,
      totalCustomers: 234,
      ordersPaid: 142,
      pendingOrders: 14
    });
  }

  private getMockTopProducts(): Observable<TopProduct[]> {
    return of([
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
    ]);
  }

  private getMockRecentOrders(): Observable<RecentOrder[]> {
    return of([
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
    ]);
  }

  private getMockProducts(): Observable<SanPham[]> {
    return of([
      {
        id: 1,
        tensanpham: 'Bàn ăn gỗ cao cấp Aurora',
        mota: 'Bàn ăn 6 chỗ làm từ gỗ tự nhiên cao cấp',
        trangthai: true,
        danhmucid: 1,
        tenloai: 'ban',
        giacu: 8000000,
        giamoi: 7200000,
        ngaytao: '2025-08-26T10:00:00'
      },
      {
        id: 2,
        tensanpham: 'Ghế sofa da thật 3 chỗ Premium',
        mota: 'Ghế sofa da thật cao cấp phong cách hiện đại',
        trangthai: true,
        danhmucid: 2,
        tenloai: 'ghe',
        giacu: 15000000,
        giamoi: 13500000,
        ngaytao: '2025-08-25T14:30:00'
      }
    ]);
  }

  private getMockCategories(): Observable<DanhMuc[]> {
    return of([
      { id: 1, tendanhmuc: 'Bàn' },
      { id: 2, tendanhmuc: 'Ghế' },
      { id: 3, tendanhmuc: 'Giường' },
      { id: 4, tendanhmuc: 'Tủ' }
    ]);
  }

  private getMockCustomers(): Observable<KhachHang[]> {
    return of([
      {
        id: 1,
        hoten: 'Nguyễn Văn An',
        email: 'nguyenvanan@email.com',
        sodienthoai: '0901234567',
        diachi: '123 Đường ABC, Quận 1, TP.HCM',
        gioitinh: 'Nam',
        ngaysinh: '1990-05-15'
      },
      {
        id: 2,
        hoten: 'Trần Thị Bình',
        email: 'tranthibinh@email.com',
        sodienthoai: '0902345678',
        diachi: '456 Đường XYZ, Quận 2, TP.HCM',
        gioitinh: 'Nữ',
        ngaysinh: '1985-12-20'
      }
    ]);
  }

  private getMockEmployees(): Observable<NhanVien[]> {
    return of([
      {
        id: 1,
        hoten: 'Nguyễn Nhân Viên',
        email: 'nhanvien@example.com',
        sodienthoai: '0922222222',
        chucvu: 'Nhân viên bán hàng',
        ngayvaolam: '2025-01-01T00:00:00'
      }
    ]);
  }

  private getMockOrders(): Observable<DonHang[]> {
    return of([
      {
        id: 1,
        khachhangid: 1,
        ngaydat: '2025-08-26T10:30:00',
        diachinhan: '123 Đường ABC, Quận 1, TP.HCM',
        trangthaidonhang: 'Đang xử lý',
        tongtien: 15000000,
        phuongthucthanhtoan: 'Chuyển khoản',
        trangthaithanhtoan: false
      }
    ]);
  }

  private getMockInvoices(): Observable<HoaDon[]> {
    return of([
      {
        id: 1,
        donhangid: 1,
        ngayxuathoadon: '2025-08-26T10:30:00',
        nguoixuathoadon: 'Admin',
        tenkhachhang: 'Nguyễn Văn An',
        diachinguoinhan: '123 Đường ABC, Quận 1, TP.HCM',
        tongtien: 15000000,
        ghichu: 'Hóa đơn VAT'
      }
    ]);
  }

  private getMockStock(): Observable<KhoHang[]> {
    return of([
      {
        id: 1,
        chitietsanphamid: 1,
        soluongton: 10,
        ngaycapnhat: '2025-08-26T10:30:00'
      }
    ]);
  }
}
