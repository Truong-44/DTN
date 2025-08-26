import { Injectable } from '@angular/core';
import { Observable, of, delay } from 'rxjs';
import { map } from 'rxjs/operators';
import { SanPham } from '../models/sanpham.model';
import { DanhMuc } from '../models/danhmuc.model';
import { PaginationResponse } from '../models/pagination.model';

@Injectable({
  providedIn: 'root',
})
export class MockDataService {
  constructor() {}

  // Mock danh mục
  getMockDanhMuc(): Observable<DanhMuc[]> {
    const mockDanhMuc: DanhMuc[] = [
      {
        id: 1,
        tendanhmuc: 'Bàn ghế',
        mota: 'Bàn ghế cao cấp',
      },
      {
        id: 2,
        tendanhmuc: 'Tủ kệ',
        mota: 'Tủ kệ đa dạng',
      },
      {
        id: 3,
        tendanhmuc: 'Giường nệm',
        mota: 'Giường nệm chất lượng',
      },
      {
        id: 4,
        tendanhmuc: 'Đèn trang trí',
        mota: 'Đèn trang trí hiện đại',
      },
    ];

    return of(mockDanhMuc).pipe(delay(500));
  }

  // Mock sản phẩm
  getMockSanPham(): Observable<PaginationResponse<SanPham>> {
    const mockSanPham: SanPham[] = [
      {
        id: 1,
        tensanpham: 'Bàn làm việc hiện đại',
        mota: 'Bàn làm việc phong cách hiện đại, chất lượng cao',
        danhmucId: 1,
        danhmucid: 1,
        giamoi: 2500000,
        ngaytao: new Date(),
        trangthai: true,
      },
      {
        id: 2,
        tensanpham: 'Ghế xoay văn phòng',
        mota: 'Ghế xoay ergonomic cho văn phòng',
        danhmucid: 1,
        giamoi: 1800000,
        ngaytao: new Date(),
        trangthai: true,
      },
      {
        id: 3,
        tensanpham: 'Tủ quần áo 3 cánh',
        mota: 'Tủ quần áo gỗ cao cấp 3 cánh',
        danhmucid: 2,
        giamoi: 4500000,
        ngaytao: new Date(),
        trangthai: true,
      },
      {
        id: 4,
        tensanpham: 'Giường ngủ Queen Size',
        mota: 'Giường ngủ cao cấp kích thước Queen',
        danhmucid: 3,
        giamoi: 6800000,
        ngaytao: new Date(),
        trangthai: true,
      },
      {
        id: 5,
        tensanpham: 'Đèn trần LED hiện đại',
        mota: 'Đèn LED trần nhà phong cách hiện đại',
        danhmucid: 4,
        giamoi: 1200000,
        ngaytao: new Date(),
        trangthai: true,
      },
      {
        id: 6,
        tensanpham: 'Sofa góc L',
        mota: 'Sofa góc chữ L phong cách châu Âu',
        danhmucid: 1,
        giamoi: 8500000,
        ngaytao: new Date(),
        trangthai: true,
      },
    ];

    const response: PaginationResponse<SanPham> = {
      content: mockSanPham,
      totalElements: mockSanPham.length,
      totalPages: 1,
      size: 10,
      number: 0,
      first: true,
      last: true,
    };

    return of(response).pipe(delay(500));
  }

  // Mock sản phẩm theo ID
  getMockSanPhamById(id: number): Observable<SanPham> {
    return this.getMockSanPham().pipe(
      delay(300),
      map(
        (response) =>
          response.content.find((p) => p.id === id) || response.content[0]
      )
    );
  }
}
