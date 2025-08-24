import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SanPhamService } from '../../../../core/services/sanpham.service';

@Component({
  selector: 'app-product-add',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-add.component.html',
  styleUrls: ['./product-add.component.scss'],
})
export class ProductAddComponent implements OnInit {
  @Output() productAdded = new EventEmitter<void>();

  // Cấu trúc thư mục mẫu (có thể mở rộng nhiều cấp)
  folderStructure: any = {
    sanpham: {
      ban: {},
      bep: {},
      ghe: {
        sofa: {},
        gaming: {},
      },
      giuong: {},
      tu: {},
    },
  };

  // Hình chính
  mainPath: string[] = [];
  mainFolders: string[] = [];
  subFolders: string[][] = [];
  selectedMainFile: File | null = null;
  selectedMainFileName: string = '';
  mainImagePreview: string | null = null;
  hinhchinh: string = '';

  // Hình phụ
  subPath: string[] = [];
  subFoldersForSub: string[][] = [];
  selectedSubFiles: File[] = [];
  selectedSubFileNames: string[] = [];
  subImagePreviews: string[] = [];
  hinhphu: string[] = [];

  // Thông tin sản phẩm
  tensanpham = '';
  giacu: number | null = null;
  giamoi: number | null = null;
  mota = '';
  danhmucId: number | null = null;
  tenmau = '';
  mamau = '';
  chatlieu = '';
  kichthuoc = '';
  trongluong: number | null = null;
  soluong: number | null = null;
  categories: any[] = [];
  loading = false;

  constructor(private sanPhamService: SanPhamService) {}

  ngOnInit() {
    this.mainFolders = Object.keys(this.folderStructure);
    this.subFolders = [Object.keys(this.folderStructure)];
    this.sanPhamService.getAllDanhMuc().subscribe({
      next: (res: any) => {
        this.categories = Array.isArray(res) ? res : res.data || [];
      },
      error: () => {
        this.categories = [];
      },
    });
  }

  // Xử lý select động cho hình chính
  onMainFolderChange(level: number, value: string) {
    this.mainPath = this.mainPath.slice(0, level);
    this.mainPath[level] = value;
    this.subFolders = this.subFolders.slice(0, level + 1);

    let obj = this.folderStructure;
    for (let i = 0; i <= level; i++) {
      if (this.mainPath[i]) obj = obj[this.mainPath[i]];
    }
    const nextFolders = obj ? Object.keys(obj) : [];
    if (nextFolders.length) {
      this.subFolders[level + 1] = nextFolders;
    }
  }

  // Khi chọn file hình chính
  onMainFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedMainFile = input.files[0];
      this.selectedMainFileName = this.selectedMainFile.name;
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.mainImagePreview = e.target.result;
      };
      reader.readAsDataURL(this.selectedMainFile);

      // Ghép đường dẫn đúng yêu cầu
      if (this.mainPath.length && this.selectedMainFileName) {
        this.hinhchinh =
          this.mainPath.join('/') + '/' + this.selectedMainFileName;
      } else {
        this.hinhchinh = this.selectedMainFileName;
      }
    } else {
      this.selectedMainFile = null;
      this.selectedMainFileName = '';
      this.mainImagePreview = null;
      this.hinhchinh = '';
    }
  }

  // Xử lý select động cho hình phụ (chỉ 1 bộ select, áp dụng cho tất cả file phụ)
  onSubFolderChange(level: number, value: string) {
    this.subPath = this.subPath.slice(0, level);
    this.subPath[level] = value;
    this.subFoldersForSub = this.subFoldersForSub.slice(0, level + 1);

    let obj = this.folderStructure;
    for (let i = 0; i <= level; i++) {
      if (this.subPath[i]) obj = obj[this.subPath[i]];
    }
    const nextFolders = obj ? Object.keys(obj) : [];
    if (nextFolders.length) {
      this.subFoldersForSub[level + 1] = nextFolders;
    }
  }

  // Khi chọn nhiều file hình phụ
  onSubFilesSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedSubFiles = Array.from(input.files);
      this.selectedSubFileNames = this.selectedSubFiles.map((f) => f.name);
      this.subImagePreviews = [];
      this.hinhphu = [];
      this.selectedSubFiles.forEach((file, idx) => {
        const reader = new FileReader();
        reader.onload = (e: any) => {
          this.subImagePreviews[idx] = e.target.result;
        };
        reader.readAsDataURL(file);

        // Ghép đường dẫn đúng yêu cầu cho từng hình phụ
        if (this.subPath.length && file.name) {
          const imgPath = this.subPath.join('/') + '/' + file.name;
          this.hinhphu.push(imgPath);
        } else {
          this.hinhphu.push(file.name);
        }
      });
    } else {
      this.selectedSubFiles = [];
      this.selectedSubFileNames = [];
      this.subImagePreviews = [];
      this.hinhphu = [];
    }
  }

  addProduct() {
    // Validate required fields
    if (!this.tensanpham || !this.giacu || !this.danhmucId) {
      alert('Vui lòng nhập đầy đủ tên sản phẩm, giá và danh mục!');
      return;
    }

    // Step 1: Create SanPham first (basic info only)
    const sanPhamData: any = {
      tensanpham: this.tensanpham,
      giacu: this.giacu,
      giamoi: this.giamoi,
      mota: this.mota,
      danhmucId: this.danhmucId, // Use correct field name
      trangthai: true,
    };

    // Remove null/empty values
    Object.keys(sanPhamData).forEach(
      (k) =>
        (sanPhamData[k] == null || sanPhamData[k] === '') &&
        delete sanPhamData[k]
    );

    console.log('Dữ liệu sản phẩm gửi lên:', sanPhamData);
    this.loading = true;

    this.sanPhamService.createSanPham(sanPhamData).subscribe({
      next: (sanPhamResponse: any) => {
        console.log('Tạo sản phẩm thành công:', sanPhamResponse);
        console.log(
          'Full response structure:',
          JSON.stringify(sanPhamResponse, null, 2)
        );

        // Step 2: Create ChiTietSanPham with the returned sanpham ID
        // Try different ways to extract the ID from response
        let sanPhamId = null;
        if (sanPhamResponse?.data?.id) {
          sanPhamId = sanPhamResponse.data.id;
        } else if (sanPhamResponse?.id) {
          sanPhamId = sanPhamResponse.id;
        } else if (typeof sanPhamResponse === 'number') {
          sanPhamId = sanPhamResponse;
        }

        console.log('Extracted sanPhamId:', sanPhamId);

        if (
          sanPhamId &&
          (this.tenmau ||
            this.mamau ||
            this.chatlieu ||
            this.kichthuoc ||
            this.trongluong ||
            this.soluong ||
            this.hinhchinh ||
            this.hinhphu.length)
        ) {
          this.createChiTietSanPham(sanPhamId);
        } else if (sanPhamId) {
          // Skip chi tiết creation for now, just success
          this.loading = false;
          alert('Thêm sản phẩm thành công! (Bỏ qua chi tiết tạm thời)');
          console.log('Sản phẩm được tạo với ID:', sanPhamId);
          this.resetForm();
          this.productAdded.emit(); // Notify parent to reload
        } else {
          // Could not extract ID
          this.loading = false;
          alert(
            'Thêm sản phẩm thành công nhưng không thể lấy ID để tạo chi tiết!'
          );
          console.error(
            'Could not extract sanPhamId from response:',
            sanPhamResponse
          );
          this.resetForm();
        }
      },
      error: (err) => {
        this.loading = false;
        console.error('Lỗi tạo sản phẩm:', err);
        alert(
          'Thêm sản phẩm thất bại: ' +
            (err?.error?.message || err?.message || 'Lỗi không xác định')
        );
      },
    });
  }

  createChiTietSanPham(sanPhamId: number) {
    // Validate sanPhamId
    if (!sanPhamId || sanPhamId <= 0) {
      this.loading = false;
      alert('ID sản phẩm không hợp lệ!');
      console.error('Invalid sanPhamId:', sanPhamId);
      return;
    }

    const hinhphuStr = this.hinhphu.length ? this.hinhphu.join(';') : undefined;

    // Create minimal request - only required fields
    const chiTietData: any = {
      sanphamId: sanPhamId,
      soluong: 0, // Set default quantity
    };

    console.log('=== MINIMAL CHI TIẾT SẢN PHẨM REQUEST ===');
    console.log('sanPhamId từ response:', sanPhamId);
    console.log('Minimal request data:', chiTietData);
    console.log('Request structure:', JSON.stringify(chiTietData, null, 2));

    this.sanPhamService.createChiTietSanPham(chiTietData).subscribe({
      next: (chiTietResponse: any) => {
        this.loading = false;
        console.log('Tạo chi tiết sản phẩm thành công:', chiTietResponse);
        alert('Thêm sản phẩm và chi tiết thành công!');
        this.resetForm();
        this.productAdded.emit(); // Notify parent to reload
      },
      error: (err) => {
        this.loading = false;
        console.error('Lỗi tạo chi tiết sản phẩm:', err);
        console.error('Error details:', JSON.stringify(err, null, 2));
        console.error('Request data was:', chiTietData);

        let errorMessage = 'Lỗi không xác định';
        if (err?.error?.message) {
          errorMessage = err.error.message;
        } else if (err?.message) {
          errorMessage = err.message;
        } else if (err?.status === 0) {
          errorMessage = 'Không thể kết nối tới server';
        } else if (err?.status) {
          errorMessage = `HTTP Error ${err.status}: ${
            err.statusText || 'Unknown error'
          }`;
        }

        alert(
          'Thêm sản phẩm thành công nhưng tạo chi tiết thất bại: ' +
            errorMessage
        );
        // Still reset form since main product was created
        this.resetForm();
      },
    });
  }

  resetForm() {
    // Reset basic product info
    this.tensanpham = '';
    this.giacu = null;
    this.giamoi = null;
    this.mota = '';
    this.danhmucId = null;

    // Reset detail product info
    this.tenmau = '';
    this.mamau = '';
    this.chatlieu = '';
    this.kichthuoc = '';
    this.trongluong = null;
    this.soluong = null;

    // Reset image paths
    this.hinhchinh = '';
    this.hinhphu = [];

    // Reset folder paths
    this.mainPath = [];
    this.subPath = [];

    // Reset selected files
    this.selectedMainFile = null;
    this.selectedMainFileName = '';
    this.mainImagePreview = null;
    this.selectedSubFiles = [];
    this.selectedSubFileNames = [];
    this.subImagePreviews = [];

    // Reset folder structures
    this.subFolders = [Object.keys(this.folderStructure)];
    this.subFoldersForSub = [];

    // Reset loading state
    this.loading = false;
  }
}
