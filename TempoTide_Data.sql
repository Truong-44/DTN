-- Tạo cơ sở dữ liệu
CREATE DATABASE TempoTide;
DROP DATABASE TempoTide
GO
USE TempoTide;
GO

-- Bảng 1: NhanVien (Nhân viên) - Được tạo trước để tránh lỗi khóa ngoại
CREATE TABLE NhanVien (
    MaNhanVien INT PRIMARY KEY IDENTITY(1,1),
    Ho NVARCHAR(50) NOT NULL,
    Ten NVARCHAR(50) NOT NULL,
    Email NVARCHAR(100) NOT NULL,
    SoDienThoai NVARCHAR(15),
    NgayTuyenDung DATE NOT NULL,
    MaSoThue NVARCHAR(50),
    NgayNghiViec DATE,
    MatKhau NVARCHAR(255) NOT NULL,
    NgayTao DATETIME DEFAULT GETDATE(),
    NgayCapNhat DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    NguoiCapNhat INT,
    CONSTRAINT CHK_Email_NhanVien CHECK (Email LIKE '%_@__%.__%'),
    CONSTRAINT CHK_SoDienThoai_NhanVien CHECK (SoDienThoai IS NULL OR SoDienThoai LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    CONSTRAINT UQ_Email_NhanVien UNIQUE (Email),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (NguoiCapNhat) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 2: ThuongHieu (Thương hiệu)
CREATE TABLE ThuongHieu (
    MaThuongHieu INT PRIMARY KEY IDENTITY(1,1),
    TenThuongHieu NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(200),
    HinhAnh NVARCHAR(500),
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    NguoiCapNhat INT,
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (NguoiCapNhat) REFERENCES NhanVien(MaNhanVien),
    CONSTRAINT UQ_TenThuongHieu UNIQUE (TenThuongHieu)
);

-- Bảng 3: KhachHang (Khách hàng)
CREATE TABLE KhachHang (
    MaKhachHang INT PRIMARY KEY IDENTITY(1,1),
    Ho NVARCHAR(50) NOT NULL,
    Ten NVARCHAR(50) NOT NULL,
    Email NVARCHAR(100) NOT NULL,
    SoDienThoai NVARCHAR(15),
    NgaySinh DATE,
    MatKhau NVARCHAR(255) NOT NULL,
    LaKhachVangLai BIT DEFAULT 0,
    DiemTichLuy INT DEFAULT 0,
    MaCapBac INT NULL,
    NgayTao DATETIME DEFAULT GETDATE(),
    NgayCapNhat DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    NguoiCapNhat INT,
    CONSTRAINT CHK_Email_KhachHang CHECK (Email LIKE '%_@__%.__%'),
    CONSTRAINT CHK_SoDienThoai_KhachHang CHECK (SoDienThoai IS NULL OR SoDienThoai LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    CONSTRAINT UQ_Email_KhachHang UNIQUE (Email),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (NguoiCapNhat) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (MaCapBac) REFERENCES CapBacKhachHang(MaCapBac)
);

-- Bảng 4: CapBacKhachHang (Cấp bậc khách hàng)
CREATE TABLE CapBacKhachHang (
    MaCapBac INT PRIMARY KEY IDENTITY(1,1),
    TenCapBac NVARCHAR(50) NOT NULL,
    DiemToiThieu INT NOT NULL CHECK (DiemToiThieu >= 0),
    GiamGiaMacDinh DECIMAL(5,2) CHECK (GiamGiaMacDinh BETWEEN 0 AND 100),
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien),
    CONSTRAINT UQ_TenCapBac UNIQUE (TenCapBac)
);

-- Bảng 5: GiaoDichTichDiem (Giao dịch tích điểm)
CREATE TABLE GiaoDichTichDiem (
    MaGiaoDich INT PRIMARY KEY IDENTITY(1,1),
    MaKhachHang INT NOT NULL,
    SoDiem INT NOT NULL CHECK (SoDiem <> 0),
    LoaiGiaoDich NVARCHAR(50) NOT NULL CHECK (LoaiGiaoDich IN (N'Tich', N'Tieu')),
    MaDonHang INT NULL,
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang),
    FOREIGN KEY (MaDonHang) REFERENCES DonHang(MaDonHang),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 6: SanPham (Sản phẩm)
CREATE TABLE SanPham (
    MaSanPham INT PRIMARY KEY IDENTITY(1,1),
    TenSanPham NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(500),
    GiaNiemYet DECIMAL(18,2) NOT NULL CHECK (GiaNiemYet >= 0),
    GiaBanMacDinh DECIMAL(18,2) NOT NULL CHECK (GiaBanMacDinh >= 0),
    MaDanhMuc INT NOT NULL,
    MaNhaCungCap INT NOT NULL,
    MaThuongHieu INT NULL,
    HinhAnhMacDinh NVARCHAR(500),
    TrangThai BIT DEFAULT 1,
    TrangThaiBan BIT DEFAULT 1,
    NgayTao DATETIME DEFAULT GETDATE(),
    NgayCapNhat DATETIME DEFAULT GETDATE(),
    NguoiTao INT,
    NguoiCapNhat INT,
    NguoiDuyet INT,
    FOREIGN KEY (MaDanhMuc) REFERENCES DanhMuc(MaDanhMuc),
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap),
    FOREIGN KEY (MaThuongHieu) REFERENCES ThuongHieu(MaThuongHieu),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (NguoiCapNhat) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (NguoiDuyet) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 7: DanhMuc (Danh mục sản phẩm)
CREATE TABLE DanhMuc (
    MaDanhMuc INT PRIMARY KEY IDENTITY(1,1),
    TenDanhMuc NVARCHAR(50) NOT NULL,
    MoTa NVARCHAR(200),
    MaDanhMucCha INT NULL,
    ThuTuHienThi INT DEFAULT 0,
    HinhAnh NVARCHAR(500),
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (MaDanhMucCha) REFERENCES DanhMuc(MaDanhMuc),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 8: NhaCungCap (Nhà cung cấp)
CREATE TABLE NhaCungCap (
    MaNhaCungCap INT PRIMARY KEY IDENTITY(1,1),
    TenNhaCungCap NVARCHAR(100) NOT NULL,
    NguoiLienHe NVARCHAR(100),
    SoDienThoai NVARCHAR(15),
    Email NVARCHAR(100),
    DiaChi NVARCHAR(200),
    MaTaiKhoanNganHang NVARCHAR(50),
    TrangWeb NVARCHAR(200),
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    CONSTRAINT CHK_Email_NhaCungCap CHECK (Email IS NULL OR Email LIKE '%_@__%.__%'),
    CONSTRAINT CHK_SoDienThoai_NhaCungCap CHECK (SoDienThoai IS NULL OR SoDienThoai LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 9: DonHang (Đơn hàng)
CREATE TABLE DonHang (
    MaDonHang INT PRIMARY KEY IDENTITY(1,1),
    MaKhachHang INT NULL,
    MaNhanVienXuLy INT NULL,
    NgayDatHang DATETIME DEFAULT GETDATE(),
    TongTien DECIMAL(18,2) NOT NULL CHECK (TongTien >= 0),
    MaPhuongThucVanChuyen INT NOT NULL,
    MaDiaChiGiaoHang INT NOT NULL,
    TrangThaiDonHang NVARCHAR(50) NOT NULL CHECK (TrangThaiDonHang IN (N'Chờ xử lý', N'Đang xử lý', N'Đã giao', N'Hoàn thành', N'Đã hủy', N'Đặt trước')),
    MaKhuyenMai INT NULL,
    GhiChu NVARCHAR(500),
    LaDonHangVangLai BIT DEFAULT 0,
    NgayTao DATETIME DEFAULT GETDATE(),
    NgayCapNhat DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    NguoiCapNhat INT,
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang),
    FOREIGN KEY (MaPhuongThucVanChuyen) REFERENCES PhuongThucVanChuyen(MaPhuongThucVanChuyen),
    FOREIGN KEY (MaDiaChiGiaoHang) REFERENCES DiaChi(MaDiaChi),
    FOREIGN KEY (MaKhuyenMai) REFERENCES KhuyenMai(MaKhuyenMai),
    FOREIGN KEY (MaNhanVienXuLy) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (NguoiCapNhat) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 10: LichSuDonHang (Lịch sử đơn hàng) 
CREATE TABLE LichSuDonHang (
    MaLichSu INT PRIMARY KEY IDENTITY(1,1),
    MaDonHang INT NOT NULL,
    TrangThaiCu NVARCHAR(50),
    TrangThaiMoi NVARCHAR(50),
    LyDo NVARCHAR(500),
    NgayCapNhat DATETIME DEFAULT GETDATE(),
    NguoiCapNhat INT,
    FOREIGN KEY (MaDonHang) REFERENCES DonHang(MaDonHang),
    FOREIGN KEY (NguoiCapNhat) REFERENCES NhanVien(MaNhanVien),
    CONSTRAINT CHK_TrangThai_LichSu CHECK (TrangThaiCu IN (N'Chờ xử lý', N'Đang xử lý', N'Đã giao', N'Hoàn thành', N'Đã hủy', N'Đặt trước') 
        AND TrangThaiMoi IN (N'Chờ xử lý', N'Đang xử lý', N'Đã giao', N'Hoàn thành', N'Đã hủy', N'Đặt trước'))
);

-- Bảng 11: ChiTietDonHang (Chi tiết đơn hàng) 
CREATE TABLE ChiTietDonHang (
    MaChiTietDonHang INT PRIMARY KEY IDENTITY(1,1),
    MaDonHang INT NOT NULL,
    MaChiTietSanPham INT NOT NULL,
    SoLuong INT NOT NULL CHECK (SoLuong > 0),
    DonGia DECIMAL(18,2) NOT NULL CHECK (DonGia >= 0),
    GiamGia DECIMAL(18,2) DEFAULT 0 CHECK (GiamGia >= 0),
    LaDatTruoc BIT DEFAULT 0,
    NgayTao DATETIME DEFAULT GETDATE(),
    NgayCapNhat DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    NguoiCapNhat INT,
    FOREIGN KEY (MaDonHang) REFERENCES DonHang(MaDonHang),
    FOREIGN KEY (MaChiTietSanPham) REFERENCES ChiTietSanPham(MaChiTietSanPham),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (NguoiCapNhat) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 12: LienHeDatHang (Liên hệ đặt hàng từ khách vãng lai) check
CREATE TABLE LienHeDatHang (
    MaLienHe INT PRIMARY KEY IDENTITY(1,1),
    HoTen NVARCHAR(100) NOT NULL,
    SoDienThoai NVARCHAR(15) NOT NULL,
    Email NVARCHAR(100),
    MaSanPham INT NOT NULL,
    MaChiTietSanPham INT NOT NULL,
    SoLuong INT NOT NULL CHECK (SoLuong > 0),
    GhiChu NVARCHAR(500),
    MaDonHang INT NULL,
    NgayLienHe DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    CONSTRAINT CHK_Email_LienHe CHECK (Email IS NULL OR Email LIKE '%_@__%.__%'),
    CONSTRAINT CHK_SoDienThoai_LienHe CHECK (SoDienThoai LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham),
    FOREIGN KEY (MaChiTietSanPham) REFERENCES ChiTietSanPham(MaChiTietSanPham),
    FOREIGN KEY (MaDonHang) REFERENCES DonHang(MaDonHang),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 13: KhoHang (Kho hàng)
CREATE TABLE KhoHang (
    MaKhoHang INT PRIMARY KEY IDENTITY(1,1),
    MaChiTietSanPham INT NOT NULL,
    SoLuong INT NOT NULL CHECK (SoLuong >= 0),
    ViTriKho NVARCHAR(100),
    NgayNhapKho DATETIME,
    NgayXuatKho DATETIME,
    NgayCapNhat DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiCapNhat INT,
    FOREIGN KEY (MaChiTietSanPham) REFERENCES ChiTietSanPham(MaChiTietSanPham),
    FOREIGN KEY (NguoiCapNhat) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 14: PhieuNhapKho (Phiếu nhập kho)
CREATE TABLE PhieuNhapKho (
    MaPhieuNhapKho INT PRIMARY KEY IDENTITY(1,1),
    MaNhaCungCap INT NOT NULL,
    NgayNhapKho DATETIME DEFAULT GETDATE(),
    TongGiaTri DECIMAL(18,2) NOT NULL CHECK (TongGiaTri >= 0),
    GhiChu NVARCHAR(500),
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (MaNhaCungCap) REFERENCES NhaCungCap(MaNhaCungCap),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 15: PhieuXuatKho (Phiếu xuất kho) 
CREATE TABLE PhieuXuatKho (
    MaPhieuXuatKho INT PRIMARY KEY IDENTITY(1,1),
    MaDonHang INT NOT NULL,
    NgayXuatKho DATETIME DEFAULT GETDATE(),
    TongGiaTri DECIMAL(18,2) NOT NULL CHECK (TongGiaTri >= 0),
    GhiChu NVARCHAR(500),
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (MaDonHang) REFERENCES DonHang(MaDonHang),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 16: KhuyenMai (Khuyến mãi)
CREATE TABLE KhuyenMai (
    MaKhuyenMai INT PRIMARY KEY IDENTITY(1,1),
    TenKhuyenMai NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(500),
    PhanTramGiamGia DECIMAL(5,2) CHECK (PhanTramGiamGia BETWEEN 0 AND 100),
    DieuKienApDung NVARCHAR(500),
    SoLuongApDung INT DEFAULT 0,
    ApDungChoDatTruoc BIT DEFAULT 0,
    NgayBatDau DATETIME NOT NULL,
    NgayKetThuc DATETIME NOT NULL,
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    CHECK (NgayKetThuc > NgayBatDau),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 17: DanhGiaSanPham (Đánh giá sản phẩm)
CREATE TABLE DanhGiaSanPham (
    MaDanhGia INT PRIMARY KEY IDENTITY(1,1),
    MaSanPham INT NOT NULL,
    MaKhachHang INT NOT NULL,
    DiemDanhGia INT NOT NULL CHECK (DiemDanhGia BETWEEN 1 AND 5),
    BinhLuan NVARCHAR(500),
    HinhAnhDanhGia NVARCHAR(500),
    NgayDanhGia DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham),
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 18: PhienDangNhap (Phiên đăng nhập)
CREATE TABLE PhienDangNhap (
    MaPhien INT PRIMARY KEY IDENTITY(1,1),
    MaNhanVien INT NULL,
    MaKhachHang INT NULL,
    Token NVARCHAR(500) NOT NULL,
    NgayDangNhap DATETIME DEFAULT GETDATE(),
    NgayHetHan DATETIME,
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien),
    CONSTRAINT CHK_PhienDangNhap CHECK ((MaNhanVien IS NOT NULL AND MaKhachHang IS NULL) OR (MaNhanVien IS NULL AND MaKhachHang IS NOT NULL))
);

-- Bảng 19: ResetPasswordToken (Token khôi phục mật khẩu)
CREATE TABLE ResetPasswordToken (
    MaToken INT PRIMARY KEY IDENTITY(1,1),
    MaNhanVien INT NULL,
    MaKhachHang INT NULL,
    Token NVARCHAR(500) NOT NULL,
    NgayTao DATETIME DEFAULT GETDATE(),
    NgayHetHan DATETIME,
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien),
    CONSTRAINT UQ_Token UNIQUE (Token),
    CONSTRAINT CHK_ResetPasswordToken CHECK ((MaNhanVien IS NOT NULL AND MaKhachHang IS NULL) OR (MaNhanVien IS NULL AND MaKhachHang IS NOT NULL))
);

-- Bảng 20: PhuongThucVanChuyen (Phương thức vận chuyển)
CREATE TABLE PhuongThucVanChuyen (
    MaPhuongThucVanChuyen INT PRIMARY KEY IDENTITY(1,1),
    TenPhuongThuc NVARCHAR(50) NOT NULL,
    ChiPhi DECIMAL(18,2) NOT NULL CHECK (ChiPhi >= 0),
    ThoiGianDuKien NVARCHAR(50),
    ThoiGianToiDa INT,
    MoTa NVARCHAR(200),
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 21: ThanhToan (Thanh toán)  
CREATE TABLE ThanhToan (
    MaThanhToan INT PRIMARY KEY IDENTITY(1,1),
    MaDonHang INT NOT NULL,
    PhuongThucThanhToan NVARCHAR(50) NOT NULL CHECK (PhuongThucThanhToan IN (N'Thẻ tín dụng', N'Tiền mặt', N'Chuyển khoản')),
    SoTien DECIMAL(18,2) NOT NULL CHECK (SoTien >= 0),
    MaGiaoDich NVARCHAR(100),
    NgayThanhToan DATETIME DEFAULT GETDATE(),
    TrangThaiThanhToan NVARCHAR(50) NOT NULL CHECK (TrangThaiThanhToan IN (N'Chờ xử lý', N'Hoàn thành', N'Thất bại')),
    NgayCapNhat DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    NguoiCapNhat INT,
    FOREIGN KEY (MaDonHang) REFERENCES DonHang(MaDonHang),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (NguoiCapNhat) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 22: HinhAnhSanPham (Hình ảnh sản phẩm)
CREATE TABLE HinhAnhSanPham (
    MaHinhAnh INT PRIMARY KEY IDENTITY(1,1),
    MaChiTietSanPham INT NOT NULL,
    DuongDanHinhAnh NVARCHAR(500) NOT NULL,
    LaHinhChinh BIT DEFAULT 0,
    ThuTuHienThi INT DEFAULT 0,
    KichThuoc NVARCHAR(50),
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (MaChiTietSanPham) REFERENCES ChiTietSanPham(MaChiTietSanPham),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 23: DiaChi (Địa chỉ khách hàng)
CREATE TABLE DiaChi (
    MaDiaChi INT PRIMARY KEY IDENTITY(1,1),
    MaKhachHang INT NOT NULL,
    DiaChiChiTiet NVARCHAR(200) NOT NULL,
    ThanhPho NVARCHAR(50) NOT NULL,
    Tinh NVARCHAR(50),
    QuanHuyen NVARCHAR(50),
    MaBuuDien NVARCHAR(20),
    QuocGia NVARCHAR(50) NOT NULL,
    LaDiaChiMacDinh BIT DEFAULT 0,
    LaDiaChiGiaoHangMacDinh BIT DEFAULT 0,
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 24: DanhSachYeuThich (Danh sách yêu thích)
CREATE TABLE DanhSachYeuThich (
    MaDanhSachYeuThich INT PRIMARY KEY IDENTITY(1,1),
    MaKhachHang INT NOT NULL,
    MaSanPham INT NOT NULL,
    NgayThem DATETIME DEFAULT GETDATE(),
    NgayXoa DATETIME,
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (MaKhachHang) REFERENCES KhachHang(MaKhachHang),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 25: VaiTro (Quản lý vai trò)
CREATE TABLE VaiTro (
    MaVaiTro INT PRIMARY KEY IDENTITY(1,1),
    TenVaiTro NVARCHAR(50) NOT NULL UNIQUE,
    MoTa NVARCHAR(200),
    MoTaNangCap NVARCHAR(200),
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 26: Quyen (Quản lý quyền)
CREATE TABLE Quyen (
    MaQuyen INT PRIMARY KEY IDENTITY(1,1),
    TenQuyen NVARCHAR(50) NOT NULL UNIQUE,
    MoTa NVARCHAR(200),
    Bang NVARCHAR(50),
    MoTaNangCap NVARCHAR(200),
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 27: VaiTro_Quyen (Liên kết vai trò với quyền)
CREATE TABLE VaiTro_Quyen (
    MaVaiTro_Quyen INT PRIMARY KEY IDENTITY(1,1),
    MaVaiTro INT NOT NULL,
    MaQuyen INT NOT NULL,
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (MaVaiTro) REFERENCES VaiTro(MaVaiTro),
    FOREIGN KEY (MaQuyen) REFERENCES Quyen(MaQuyen),
    CONSTRAINT UQ_VaiTro_Quyen UNIQUE (MaVaiTro, MaQuyen),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 28: NguoiDung_VaiTro (Liên kết nhân viên với vai trò)
CREATE TABLE NguoiDung_VaiTro (
    MaNguoiDung_VaiTro INT PRIMARY KEY IDENTITY(1,1),
    MaNhanVien INT NOT NULL,
    MaVaiTro INT NOT NULL,
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (MaNhanVien) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (MaVaiTro) REFERENCES VaiTro(MaVaiTro),
    CONSTRAINT UQ_NguoiDung_VaiTro UNIQUE (MaNhanVien, MaVaiTro),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 29: ThuocTinhSanPham (Loại thuộc tính sản phẩm)
CREATE TABLE ThuocTinhSanPham (
    MaThuocTinh INT PRIMARY KEY IDENTITY(1,1),
    TenThuocTinh NVARCHAR(50) NOT NULL UNIQUE,
    MoTa NVARCHAR(200),
    LoaiGiaTri NVARCHAR(50),
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 30: ChiTietSanPham (Chi tiết sản phẩm)
CREATE TABLE ChiTietSanPham (
    MaChiTietSanPham INT PRIMARY KEY IDENTITY(1,1),
    MaSanPham INT NOT NULL,
    MaThuocTinh INT NOT NULL,
    GiaTri NVARCHAR(100) NOT NULL,
    Gia DECIMAL(18,2) NOT NULL CHECK (Gia >= 0),
    SoLuongTon INT NOT NULL CHECK (SoLuongTon >= 0),
    SKU NVARCHAR(50) UNIQUE,
    MaBaoHanh INT NULL,
    NgayHetHan DATETIME,
    NgayTao DATETIME DEFAULT GETDATE(),
    NgayCapNhat DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    NguoiCapNhat INT,
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham),
    FOREIGN KEY (MaThuocTinh) REFERENCES ThuocTinhSanPham(MaThuocTinh),
    FOREIGN KEY (MaBaoHanh) REFERENCES BaoHanh(MaBaoHanh),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien),
    FOREIGN KEY (NguoiCapNhat) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 31: BaoHanh (Bảo hành)
CREATE TABLE BaoHanh (
    MaBaoHanh INT PRIMARY KEY IDENTITY(1,1),
    MaSanPham INT NOT NULL,
    ThoiGianBaoHanh INT NOT NULL CHECK (ThoiGianBaoHanh > 0),
    DieuKienBaoHanh NVARCHAR(500),
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Bảng 32: CauHinhHeThong (Cấu hình hệ thống)
CREATE TABLE CauHinhHeThong (
    MaCauHinh INT PRIMARY KEY IDENTITY(1,1),
    TenCauHinh NVARCHAR(100) NOT NULL,
    GiaTri NVARCHAR(500) NOT NULL,
    MoTa NVARCHAR(200),
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien),
    CONSTRAINT UQ_TenCauHinh UNIQUE (TenCauHinh)
);

-- Bảng 33: BaoCaoBanHang (Báo cáo bán hàng)
CREATE TABLE BaoCaoBanHang (
    MaBaoCao INT PRIMARY KEY IDENTITY(1,1),
    ThoiGianBatDau DATETIME NOT NULL,
    ThoiGianKetThuc DATETIME NOT NULL,
    TongDoanhThu DECIMAL(18,2) NOT NULL,
    SoDonHang INT NOT NULL,
    NgayTao DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1,
    NguoiTao INT,
    CHECK (ThoiGianKetThuc > ThoiGianBatDau),
    FOREIGN KEY (NguoiTao) REFERENCES NhanVien(MaNhanVien)
);

-- Thêm chỉ mục
CREATE INDEX IDX_KhachHang_Email ON KhachHang(Email);
CREATE INDEX IDX_SanPham_TenSanPham ON SanPham(TenSanPham);
CREATE INDEX IDX_DonHang_MaKhachHang ON DonHang(MaKhachHang);
CREATE INDEX IDX_DonHang_TrangThaiDonHang ON DonHang(TrangThaiDonHang);
CREATE INDEX IDX_ChiTietDonHang_MaDonHang ON ChiTietDonHang(MaDonHang);
CREATE INDEX IDX_LienHeDatHang_SoDienThoai ON LienHeDatHang(SoDienThoai);
CREATE INDEX IDX_KhoHang_MaChiTietSanPham ON KhoHang(MaChiTietSanPham);
CREATE INDEX IDX_DanhGiaSanPham_MaSanPham ON DanhGiaSanPham(MaSanPham);
CREATE INDEX IDX_NhanVien_Email ON NhanVien(Email);
CREATE INDEX IDX_HinhAnhSanPham_MaChiTietSanPham ON HinhAnhSanPham(MaChiTietSanPham);
CREATE INDEX IDX_DanhSachYeuThich_MaKhachHang ON DanhSachYeuThich(MaKhachHang);
CREATE INDEX IDX_ChiTietSanPham_MaSanPham ON ChiTietSanPham(MaSanPham);
CREATE INDEX IDX_DonHang_MaNhanVienXuLy ON DonHang(MaNhanVienXuLy);