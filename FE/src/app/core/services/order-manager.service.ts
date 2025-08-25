import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject, of } from 'rxjs';
import { DonHang } from '../models/donhang.model';
import { ChiTietDonHang } from '../models/chitietdonhang.model';
import { ChiTietSanPham } from '../models/chitietsanpham.model';

export interface CreateOrderRequest {
  khachhangid?: number;
  diachinhan: string;
  phuongthucthanhtoan: string;
  ghichu?: string;
  customerName: string;
  customerPhone: string;
  chitietdonhang: {
    chitietsanphamid: number;
    soluong: number;
    dongia: number;
    productDetail: ChiTietSanPham;
  }[];
}

@Injectable({
  providedIn: 'root',
})
export class OrderManagerService {
  private readonly STORAGE_KEY = 'dtn_orders';
  private ordersSubject = new BehaviorSubject<DonHang[]>([]);
  public orders$ = this.ordersSubject.asObservable();

  constructor() {
    this.loadOrdersFromStorage();
    // Create sample order for testing if no orders exist
    if (this.ordersSubject.value.length === 0) {
      this.createSampleOrder();
    }
  }

  private loadOrdersFromStorage(): void {
    const stored = localStorage.getItem(this.STORAGE_KEY);
    console.log('🔍 Loading orders from localStorage:', stored);
    if (stored) {
      try {
        const orders = JSON.parse(stored);
        console.log('📦 Parsed orders:', orders);
        this.ordersSubject.next(orders);
      } catch (error) {
        console.error('Error loading orders from storage:', error);
        this.ordersSubject.next([]);
      }
    } else {
      console.log('📝 No orders found in localStorage');
      this.ordersSubject.next([]);
    }
  }

  private saveOrdersToStorage(orders: DonHang[]): void {
    console.log('💾 Saving orders to localStorage:', orders);
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(orders));
    this.ordersSubject.next(orders);
  }

  createOrder(orderRequest: CreateOrderRequest): Observable<DonHang> {
    const orders = this.ordersSubject.value;
    const newOrderId =
      orders.length > 0 ? Math.max(...orders.map((o) => o.id)) + 1 : 1;

    // Calculate total amount
    const tongtien = orderRequest.chitietdonhang.reduce((total, item) => {
      return total + item.dongia * item.soluong;
    }, 0);

    // Create order details
    const chitietdonhang: ChiTietDonHang[] = orderRequest.chitietdonhang.map(
      (item, index) => ({
        id: newOrderId * 100 + index + 1,
        donhangid: newOrderId,
        chitietsanphamid: item.chitietsanphamid,
        soluong: item.soluong,
        dongia: item.dongia,
        chitietsanpham: {
          ...item.productDetail,
          tensanpham: item.productDetail.tensanpham,
          hinhchinh: item.productDetail.hinhchinh,
        },
      })
    );

    // Create new order
    const newOrder: DonHang = {
      id: newOrderId,
      khachhangid: orderRequest.khachhangid || 0,
      ngaydat: new Date().toISOString(),
      diachinhan: orderRequest.diachinhan,
      trangthaidonhang: 'CHO_XAC_NHAN',
      tongtien: tongtien,
      phuongthucthanhtoan: orderRequest.phuongthucthanhtoan,
      trangthaithanhtoan:
        orderRequest.phuongthucthanhtoan === 'COD' ? false : true,
      chitietdonhang: chitietdonhang,
      khachhang: {
        id: orderRequest.khachhangid || newOrderId,
        taikhoanid: orderRequest.khachhangid || newOrderId,
        hoten: orderRequest.customerName,
        taikhoan: {
          id: orderRequest.khachhangid || newOrderId,
          sodienthoai: orderRequest.customerPhone,
          tendangnhap: orderRequest.customerName
            .toLowerCase()
            .replace(/\s+/g, ''),
          trangthai: true,
          ngaytao: new Date(),
          quyenid: 2,
          loaidangnhap: 'LOCAL',
        },
      },
    };

    const updatedOrders = [newOrder, ...orders];
    this.saveOrdersToStorage(updatedOrders);

    return of(newOrder);
  }

  getAllOrders(): Observable<DonHang[]> {
    return this.orders$;
  }

  getOrderById(id: number): Observable<DonHang | null> {
    const orders = this.ordersSubject.value;
    const order = orders.find((o) => o.id === id);
    return of(order || null);
  }

  updateOrderStatus(id: number, status: string): Observable<DonHang | null> {
    const orders = this.ordersSubject.value;
    const orderIndex = orders.findIndex((o) => o.id === id);

    if (orderIndex !== -1) {
      orders[orderIndex].trangthaidonhang = status;
      this.saveOrdersToStorage(orders);
      return of(orders[orderIndex]);
    }

    return of(null);
  }

  getOrderCode(orderId: number): string {
    return `DH${orderId.toString().padStart(3, '0')}`;
  }

  getStatusLabel(status: string): string {
    const statusMap: { [key: string]: string } = {
      CHO_XAC_NHAN: 'Chờ xác nhận',
      DA_XAC_NHAN: 'Đã xác nhận',
      DANG_GIAO: 'Đang giao hàng',
      DA_GIAO: 'Đã giao hàng',
      DA_HUY: 'Đã hủy',
    };
    return statusMap[status] || status;
  }

  getStatusClass(status: string): string {
    const statusClassMap: { [key: string]: string } = {
      CHO_XAC_NHAN: 'status-pending',
      DA_XAC_NHAN: 'status-processing',
      DANG_GIAO: 'status-shipping',
      DA_GIAO: 'status-delivered',
      DA_HUY: 'status-cancelled',
    };
    return statusClassMap[status] || '';
  }

  // Simulate order status progression for demo
  simulateOrderProgress(orderId: number): void {
    setTimeout(() => {
      this.updateOrderStatus(orderId, 'DA_XAC_NHAN').subscribe();
    }, 2000);

    setTimeout(() => {
      this.updateOrderStatus(orderId, 'DANG_GIAO').subscribe();
    }, 10000);
  }

  // Create sample order for testing
  private createSampleOrder(): void {
    console.log('🧪 Creating sample order for testing...');
    const sampleOrder: CreateOrderRequest = {
      diachinhan: '123 Đường ABC, Quận 1, TP.HCM',
      phuongthucthanhtoan: 'COD',
      customerName: 'Khách hàng test',
      customerPhone: '0123456789',
      ghichu: 'Đơn hàng mẫu để test',
      chitietdonhang: [
        {
          chitietsanphamid: 1,
          soluong: 1,
          dongia: 500000,
          productDetail: {
            id: 1,
            sanphamId: 1,
            tensanpham: 'Sản phẩm test',
            tenmau: 'Đỏ',
            soluong: 1,
            hinhchinh: 'test.jpg',
            hinhphu: undefined,
            mamau: undefined,
            chatlieu: undefined,
            kichthuoc: undefined,
            trongluong: undefined,
          },
        },
      ],
    };

    this.createOrder(sampleOrder).subscribe({
      next: (order) => {
        console.log('✅ Sample order created:', order);
      },
      error: (err) => {
        console.error('❌ Error creating sample order:', err);
      },
    });
  }
}
