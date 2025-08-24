CREATE DATABASE noithat;
USE noithat;

-- ==========================
-- CÁC BẢNG TÀI KHOẢN
-- ==========================

CREATE TABLE quyen (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten NVARCHAR(50) NOT NULL UNIQUE,
    mota NVARCHAR(255)
);
select * from quyen

CREATE TABLE taikhoan (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tendangnhap NVARCHAR(100) NOT NULL UNIQUE,
    matkhau NVARCHAR(255) NOT NULL,
    email NVARCHAR(100),
    sodienthoai NVARCHAR(20),
    trangthai BIT DEFAULT 1,
    ngaytao DATETIME DEFAULT GETDATE(),
    quyenid INT DEFAULT 3,
    loaidangnhap NVARCHAR(20) NOT NULL DEFAULT 'email',
    FOREIGN KEY (quyenid) REFERENCES quyen(id)
);
select * from taikhoan

CREATE TABLE khachhang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    taikhoanid INT NOT NULL UNIQUE,
    hoten NVARCHAR(100),
    diachi NVARCHAR(MAX),
    ngaysinh DATE,
    gioitinh NVARCHAR(10),
    FOREIGN KEY (taikhoanid) REFERENCES taikhoan(id)
);
select * from khachhang

CREATE TABLE taikhoanlienket (
    id INT IDENTITY(1,1) PRIMARY KEY,
    taikhoanid INT NOT NULL,
    loaidangnhap NVARCHAR(50),
    giatridangnhap NVARCHAR(255),
    UNIQUE (loaidangnhap, giatridangnhap),
    FOREIGN KEY (taikhoanid) REFERENCES taikhoan(id)
);
select * from taikhoanlienket

CREATE TABLE nhanvien (
    id INT IDENTITY(1,1) PRIMARY KEY,
    hoten NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) UNIQUE,
    sodienthoai NVARCHAR(20),
    chucvu NVARCHAR(100),
    taikhoanid INT UNIQUE,
    ngayvaolam DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (taikhoanid) REFERENCES taikhoan(id)
);
select * from nhanvien

-- ==========================
-- CÁC BẢNG SẢN PHẨM
-- ==========================

CREATE TABLE danhmuc (
    id INT IDENTITY(1,1) PRIMARY KEY,
	danhmucchaid INT,
    tendanhmuc NVARCHAR(100) NOT NULL UNIQUE,
    mota NVARCHAR(MAX)
);
select * from danhmuc

CREATE TABLE sanpham (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tensanpham NVARCHAR(200) NOT NULL,
    mota NVARCHAR(MAX),
    danhmucid INT NOT NULL,
    tenloai NVARCHAR(100),
    giacu DECIMAL(18,2),
    giamoi DECIMAL(18,2),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    FOREIGN KEY (danhmucid) REFERENCES danhmuc(id)
);
select * from sanpham

CREATE TABLE chitietsanpham (
    id INT IDENTITY(1,1) PRIMARY KEY,
    sanphamid INT NOT NULL,
    tenmau NVARCHAR(50),
    mamau NVARCHAR(10), -- mã HEX hoặc tên
    chatlieu NVARCHAR(100),
    kichthuoc NVARCHAR(100), -- VD: 200x80x75cm
    trongluong FLOAT,        -- đơn vị: kg
    soluong INT DEFAULT 0,
    hinhchinh NVARCHAR(500),
    hinhphu NVARCHAR(MAX), -- nhiều hình phụ cách nhau dấu ;
    FOREIGN KEY (sanphamid) REFERENCES sanpham(id)
);
select * from chitietsanpham

-- ==========================
-- GIỎ HÀNG
-- ==========================

CREATE TABLE giohang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    khachhangid INT NOT NULL,
    ngaycapnhat DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (khachhangid) REFERENCES khachhang(id)
);
select * from giohang

CREATE TABLE chitietgiohang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    giohangid INT NOT NULL,
    chitietsanphamid INT NOT NULL,
    soluong INT NOT NULL CHECK (soluong > 0),
    dongia DECIMAL(18,2) NOT NULL,
    ngaythem DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (giohangid) REFERENCES giohang(id),
    FOREIGN KEY (chitietsanphamid) REFERENCES chitietsanpham(id)
);
select * from chitietgiohang

-- ==========================
-- ĐƠN HÀNG 
-- ==========================

CREATE TABLE donhang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    khachhangid INT NOT NULL,
    ngaydat DATETIME DEFAULT GETDATE(),
    diachinhan NVARCHAR(MAX),
    trangthaidonhang NVARCHAR(50) DEFAULT N'Đang xử lý', 
    tongtien DECIMAL(18,2),
    phuongthucthanhtoan NVARCHAR(50),
    trangthaithanhtoan BIT DEFAULT 0, 
    ngaythanhtoan DATETIME NULL,
    FOREIGN KEY (khachhangid) REFERENCES khachhang(id)
);
select * from donhang

-- Chi tiết đơn hàng
CREATE TABLE chitietdonhang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    donhangid INT NOT NULL,
    chitietsanphamid INT NOT NULL,
    soluong INT NOT NULL CHECK (soluong > 0),
    dongia DECIMAL(18,2) NOT NULL,
    FOREIGN KEY (donhangid) REFERENCES donhang(id),
    FOREIGN KEY (chitietsanphamid) REFERENCES chitietsanpham(id)
);
select * from chitietdonhang

-- ==========================
-- HÓA ĐƠN 
-- ==========================

CREATE TABLE hoadon (
    id INT IDENTITY(1,1) PRIMARY KEY,
    donhangid INT NOT NULL,
    ngayxuathoadon DATETIME DEFAULT GETDATE(),
    nguoixuathoadon NVARCHAR(100), 
    tenkhachhang NVARCHAR(100),
    diachinguoinhan NVARCHAR(MAX),
    tongtien DECIMAL(18,2),
    ghichu NVARCHAR(MAX),
    FOREIGN KEY (donhangid) REFERENCES donhang(id)
);
select * from hoadon

CREATE TABLE chitiethoadon (
    id INT IDENTITY(1,1) PRIMARY KEY,
    hoadonid INT NOT NULL,
    tensanpham NVARCHAR(200),
    tenmau NVARCHAR(50),
    chatlieu NVARCHAR(100),
    kichthuoc NVARCHAR(100),
    soluong INT NOT NULL,
    dongia DECIMAL(18,2) NOT NULL,
    FOREIGN KEY (hoadonid) REFERENCES hoadon(id)
);
select * from chitiethoadon

-- ==========================
-- PHƯƠNG THỨC VẬN CHUYỂN
-- ==========================

CREATE TABLE phuongthucvanchuyen (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten NVARCHAR(100) NOT NULL UNIQUE, -- ví dụ: Giao tiêu chuẩn, Giao nhanh, Tự đến lấy
    mota NVARCHAR(MAX),
    thoigiandukien NVARCHAR(100), -- VD: 3-5 ngày, trong ngày
    phivanchuyen DECIMAL(18,2) DEFAULT 0
);
select * from phuongthucvanchuyen

-- Gắn phương thức vận chuyển vào đơn hàng
ALTER TABLE donhang
ADD vanchuyenid INT;

ALTER TABLE donhang
ADD CONSTRAINT FK_donhang_vanchuyen FOREIGN KEY (vanchuyenid) REFERENCES phuongthucvanchuyen(id);

-- ==========================
-- KHO HÀNG
-- ==========================
CREATE TABLE khohang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    chitietsanphamid INT NOT NULL,
    soluongton INT DEFAULT 0,
    ngaycapnhat DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (chitietsanphamid) REFERENCES chitietsanpham(id)
);
select * from khohang

-- ==========================
-- CHAT BOX
-- ==========================

CREATE TABLE chatsession (
  id INT IDENTITY(1,1) PRIMARY KEY,
  taikhoanid INT NULL, -- null nếu chưa đăng nhập
  thoigianbatdau DATETIME DEFAULT GETDATE(),
  FOREIGN KEY (taikhoanid) REFERENCES taikhoan(id)
);

CREATE TABLE chatmessage (
  id INT IDENTITY(1,1) PRIMARY KEY,
  sessionid INT NOT NULL REFERENCES chatsession(id),
  nguoigui NVARCHAR(20) CHECK (nguoigui IN ('user', 'bot')),
  noidung NVARCHAR(MAX) NOT NULL,
  thoigian DATETIME DEFAULT GETDATE(),
  sanphamid INT NULL,  -- nếu bot đang gợi ý sản phẩm
  FOREIGN KEY (sanphamid) REFERENCES sanpham(id)
);