-- Tạo cơ sở dữ liệu
CREATE DATABASE tempotide;
GO

USE tempotide;
GO

-- ======================== Định nghĩa các bảng ========================
-- Bảng nhanvien
CREATE TABLE nhanvien (
    manhanvien INT PRIMARY KEY IDENTITY(1,1),
    ho NVARCHAR(50) NOT NULL,
    ten NVARCHAR(50) NOT NULL,
    email NVARCHAR(100) NOT NULL,
    sodienthoai NVARCHAR(15),
    ngaytuyendung DATE NOT NULL,
    masothue NVARCHAR(50),
    ngaynghiviec DATE,
    matkhau NVARCHAR(255) NOT NULL,
    ngaytao DATETIME DEFAULT GETDATE(),
    ngaycapnhat DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    nguoicapnhat INT,
    CONSTRAINT chk_email_nhanvien CHECK (email LIKE '%_@__%.__%'),
    CONSTRAINT chk_sodienthoai_nhanvien CHECK (sodienthoai IS NULL OR sodienthoai LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    CONSTRAINT uq_email_nhanvien UNIQUE (email),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoicapnhat) REFERENCES nhanvien(manhanvien)
);

-- Bảng vaitro
CREATE TABLE vaitro (
    mavaitro INT PRIMARY KEY IDENTITY(1,1),
    tenvaitro NVARCHAR(50) NOT NULL UNIQUE,
    mota NVARCHAR(200),
    motanangcap NVARCHAR(200),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng quyen
CREATE TABLE quyen (
    maquyen INT PRIMARY KEY IDENTITY(1,1),
    tenquyen NVARCHAR(50) NOT NULL UNIQUE,
    mota NVARCHAR(200),
    bang NVARCHAR(50),
    motanangcap NVARCHAR(200),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng vaitro_quyen
CREATE TABLE vaitro_quyen (
    mavaitro_quyen INT PRIMARY KEY IDENTITY(1,1),
    mavaitro INT NOT NULL,
    maquyen INT NOT NULL,
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (mavaitro) REFERENCES vaitro(mavaitro),
    FOREIGN KEY (maquyen) REFERENCES quyen(maquyen),
    CONSTRAINT uq_vaitro_quyen UNIQUE (mavaitro, maquyen),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng nguoidung_vaitro
CREATE TABLE nguoidung_vaitro (
    manguoidung_vaitro INT PRIMARY KEY IDENTITY(1,1),
    manhanvien INT NOT NULL,
    mavaitro INT NOT NULL,
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (manhanvien) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (mavaitro) REFERENCES vaitro(mavaitro),
    CONSTRAINT uq_nguoidung_vaitro UNIQUE (manhanvien, mavaitro),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng danhmuc
CREATE TABLE danhmuc (
    madanhmuc INT PRIMARY KEY IDENTITY(1,1),
    tendanhmuc NVARCHAR(50) NOT NULL,
    mota NVARCHAR(200),
    madanhmuccha INT NULL,
    thutuhienthi INT DEFAULT 0,
    hinhanh NVARCHAR(500),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (madanhmuccha) REFERENCES danhmuc(madanhmuc),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng nhacungcap
CREATE TABLE nhacungcap (
    manhacungcap INT PRIMARY KEY IDENTITY(1,1),
    tennhacungcap NVARCHAR(100) NOT NULL,
    nguoilienhe NVARCHAR(100),
    sodienthoai NVARCHAR(15),
    email NVARCHAR(100),
    diachi NVARCHAR(200),
    mataikhoannganhang NVARCHAR(50),
    trangweb NVARCHAR(200),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    CONSTRAINT chk_email_nhacungcap CHECK (email IS NULL OR email LIKE '%_@__%.__%'),
    CONSTRAINT chk_sodienthoai_nhacungcap CHECK (sodienthoai IS NULL OR sodienthoai LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng thuonghieu
CREATE TABLE thuonghieu (
    mathuonghieu INT PRIMARY KEY IDENTITY(1,1),
    tenthuonghieu NVARCHAR(100) NOT NULL,
    mota NVARCHAR(200),
    hinhanh NVARCHAR(500),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    nguoicapnhat INT,
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoicapnhat) REFERENCES nhanvien(manhanvien),
    CONSTRAINT uq_tenthuonghieu UNIQUE (tenthuonghieu)
);

-- Bảng capbackhachhang
CREATE TABLE capbackhachhang (
    macapbac INT PRIMARY KEY IDENTITY(1,1),
    tencapbac NVARCHAR(50) NOT NULL,
    diemtoithieu INT NOT NULL CHECK (diemtoithieu >= 0),
    giamgiamacdinh DECIMAL(5,2) CHECK (giamgiamacdinh BETWEEN 0 AND 100),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    CONSTRAINT uq_tencapbac UNIQUE (tencapbac)
);

-- Bảng khachhang
CREATE TABLE khachhang (
    makhachhang INT PRIMARY KEY IDENTITY(1,1),
    ho NVARCHAR(50) NOT NULL,
    ten NVARCHAR(50) NOT NULL,
    email NVARCHAR(100) NOT NULL,
    sodienthoai NVARCHAR(15),
    ngaysinh DATE,
    matkhau NVARCHAR(255) NOT NULL,
    lakhachvanglai BIT DEFAULT 0,
    diemtichluy INT DEFAULT 0,
    macapbac INT NULL,
    ngaytao DATETIME DEFAULT GETDATE(),
    ngaycapnhat DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    nguoicapnhat INT,
    CONSTRAINT chk_email_khachhang CHECK (email LIKE '%_@__%.__%'),
    CONSTRAINT chk_sodienthoai_khachhang CHECK (sodienthoai IS NULL OR sodienthoai LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    CONSTRAINT uq_email_khachhang UNIQUE (email),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoicapnhat) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (macapbac) REFERENCES capbackhachhang(macapbac)
);

-- Bảng sanpham
CREATE TABLE sanpham (
    masanpham INT PRIMARY KEY IDENTITY(1,1),
    tensanpham NVARCHAR(100) NOT NULL,
    mota NVARCHAR(500),
    gianiemyet DECIMAL(18,2) NOT NULL CHECK (gianiemyet >= 0),
    giabanmacdinh DECIMAL(18,2) NOT NULL CHECK (giabanmacdinh >= 0),
    madanhmuc INT NOT NULL,
    manhacungcap INT NOT NULL,
    mathuonghieu INT NULL,
    hinhanhmacdinh NVARCHAR(500),
    trangthai BIT DEFAULT 1,
    trangthaiban BIT DEFAULT 1,
    ngaytao DATETIME DEFAULT GETDATE(),
    ngaycapnhat DATETIME DEFAULT GETDATE(),
    nguoitao INT,
    nguoicapnhat INT,
    nguoiduyet INT,
    FOREIGN KEY (madanhmuc) REFERENCES danhmuc(madanhmuc),
    FOREIGN KEY (manhacungcap) REFERENCES nhacungcap(manhacungcap),
    FOREIGN KEY (mathuonghieu) REFERENCES thuonghieu(mathuonghieu),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoicapnhat) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoiduyet) REFERENCES nhanvien(manhanvien)
);

-- Bảng thuoctinhsanpham
CREATE TABLE thuoctinhsanpham (
    mathuoctinh INT PRIMARY KEY IDENTITY(1,1),
    tenthuoctinh NVARCHAR(50) NOT NULL UNIQUE,
    mota NVARCHAR(200),
    loaigiatri NVARCHAR(50),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng baohanh
CREATE TABLE baohanh (
    mabaohanh INT PRIMARY KEY IDENTITY(1,1),
    masanpham INT NOT NULL,
    thoigianbaohanh INT NOT NULL CHECK (thoigianbaohanh > 0),
    dieukienbaohanh NVARCHAR(500),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (masanpham) REFERENCES sanpham(masanpham),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng chitietsanpham
CREATE TABLE chitietsanpham (
    machitietsanpham INT PRIMARY KEY IDENTITY(1,1),
    masanpham INT NOT NULL,
    mathuoctinh INT NOT NULL,
    giatri NVARCHAR(100) NOT NULL,
    gia DECIMAL(18,2) NOT NULL CHECK (gia >= 0),
    soluongton INT NOT NULL CHECK (soluongton >= 0),
    sku NVARCHAR(50) UNIQUE,
    mabaohanh INT NULL,
    ngayhethan DATETIME,
    ngaytao DATETIME DEFAULT GETDATE(),
    ngaycapnhat DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    nguoicapnhat INT,
    FOREIGN KEY (masanpham) REFERENCES sanpham(masanpham),
    FOREIGN KEY (mathuoctinh) REFERENCES thuoctinhsanpham(mathuoctinh),
    FOREIGN KEY (mabaohanh) REFERENCES baohanh(mabaohanh),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoicapnhat) REFERENCES nhanvien(manhanvien)
);

-- Bảng khohang
CREATE TABLE khohang (
    makhohang INT PRIMARY KEY IDENTITY(1,1),
    machitietsanpham INT NOT NULL,
    soluong INT NOT NULL CHECK (soluong >= 0),
    vitrikho NVARCHAR(100),
    ngaynhapkho DATETIME,
    ngayxuatkho DATETIME,
    ngaycapnhat DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoicapnhat INT,
    FOREIGN KEY (machitietsanpham) REFERENCES chitietsanpham(machitietsanpham),
    FOREIGN KEY (nguoicapnhat) REFERENCES nhanvien(manhanvien)
);

-- Bảng phuongthucvanchuyen
CREATE TABLE phuongthucvanchuyen (
    maphuongthucvanchuyen INT PRIMARY KEY IDENTITY(1,1),
    tenphuongthuc NVARCHAR(50) NOT NULL,
    chiphi DECIMAL(18,2) NOT NULL CHECK (chiphi >= 0),
    thoigiandukien NVARCHAR(50),
    thoigiantoida INT,
    mota NVARCHAR(200),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng diachi
CREATE TABLE diachi (
    madiachi INT PRIMARY KEY IDENTITY(1,1),
    makhachhang INT NOT NULL,
    diachichitiet NVARCHAR(200) NOT NULL,
    thanhpho NVARCHAR(50) NOT NULL,
    tinh NVARCHAR(50),
    quanhuyen NVARCHAR(50),
    mabuudien NVARCHAR(20),
    quocgia NVARCHAR(50) NOT NULL,
    ladiachimacdinh BIT DEFAULT 0,
    ladiachigiaohangmacdinh BIT DEFAULT 0,
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (makhachhang) REFERENCES khachhang(makhachhang),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng khuyenmai
CREATE TABLE khuyenmai (
    makhuyenmai INT PRIMARY KEY IDENTITY(1,1),
    tenkhuyenmai NVARCHAR(100) NOT NULL,
    mota NVARCHAR(500),
    phantramgiamgia DECIMAL(5,2) CHECK (phantramgiamgia BETWEEN 0 AND 100),
    dieukienapdung NVARCHAR(500),
    soluongapdung INT DEFAULT 0,
    apdungchodattruoc BIT DEFAULT 0,
    ngaybatdau DATETIME NOT NULL,
    ngayketthuc DATETIME NOT NULL,
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    CHECK (ngayketthuc > ngaybatdau),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng donhang
CREATE TABLE donhang (
    madonhang INT PRIMARY KEY IDENTITY(1,1),
    makhachhang INT NULL,
    manhanvienxuly INT NULL,
    ngaydathang DATETIME DEFAULT GETDATE(),
    tongtien DECIMAL(18,2) NOT NULL CHECK (tongtien >= 0),
    maphuongthucvanchuyen INT NOT NULL,
    madiachigiaohang INT NOT NULL,
    trangthaidonhang NVARCHAR(50) NOT NULL CHECK (trangthaidonhang IN (N'chờ xử lý', N'đang xử lý', N'đã giao', N'hoàn thành', N'đã hủy', N'đặt trước')),
    makhuyenmai INT NULL,
    ghichu NVARCHAR(500),
    ladonhangvanglai BIT DEFAULT 0,
    ngaytao DATETIME DEFAULT GETDATE(),
    ngaycapnhat DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    nguoicapnhat INT,
    FOREIGN KEY (makhachhang) REFERENCES khachhang(makhachhang),
    FOREIGN KEY (maphuongthucvanchuyen) REFERENCES phuongthucvanchuyen(maphuongthucvanchuyen),
    FOREIGN KEY (madiachigiaohang) REFERENCES diachi(madiachi),
    FOREIGN KEY (makhuyenmai) REFERENCES khuyenmai(makhuyenmai),
    FOREIGN KEY (manhanvienxuly) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoicapnhat) REFERENCES nhanvien(manhanvien)
);

-- Bảng giaodichtichdiem
CREATE TABLE giaodichtichdiem (
    magiaodich INT PRIMARY KEY IDENTITY(1,1),
    makhachhang INT NOT NULL,
    sodiem INT NOT NULL CHECK (sodiem <> 0),
    loaigiaodich NVARCHAR(50) NOT NULL CHECK (loaigiaodich IN (N'tích', N'tiêu')),
    madonhang INT NULL,
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (makhachhang) REFERENCES khachhang(makhachhang),
    FOREIGN KEY (madonhang) REFERENCES donhang(madonhang),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng lichsudonhang
CREATE TABLE lichsudonhang (
    malichsu INT PRIMARY KEY IDENTITY(1,1),
    madonhang INT NOT NULL,
    trangthaicu NVARCHAR(50),
    trangthaimoi NVARCHAR(50),
    lydo NVARCHAR(500),
    ngaycapnhat DATETIME DEFAULT GETDATE(),
    nguoicapnhat INT,
    FOREIGN KEY (madonhang) REFERENCES donhang(madonhang),
    FOREIGN KEY (nguoicapnhat) REFERENCES nhanvien(manhanvien),
    CONSTRAINT chk_trangthai_lichsu CHECK (trangthaicu IN (N'chờ xử lý', N'đang xử lý', N'đã giao', N'hoàn thành', N'đã hủy', N'đặt trước') 
        AND trangthaimoi IN (N'chờ xử lý', N'đang xử lý', N'đã giao', N'hoàn thành', N'đã hủy', N'đặt trước'))
);

-- Bảng chitietdonhang
CREATE TABLE chitietdonhang (
    machitietdonhang INT PRIMARY KEY IDENTITY(1,1),
    madonhang INT NOT NULL,
    machitietsanpham INT NOT NULL,
    soluong INT NOT NULL CHECK (soluong > 0),
    dongia DECIMAL(18,2) NOT NULL CHECK (dongia >= 0),
    giamgia DECIMAL(18,2) DEFAULT 0 CHECK (giamgia >= 0),
    ladattruoc BIT DEFAULT 0,
    ngaytao DATETIME DEFAULT GETDATE(),
    ngaycapnhat DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    nguoicapnhat INT,
    FOREIGN KEY (madonhang) REFERENCES donhang(madonhang),
    FOREIGN KEY (machitietsanpham) REFERENCES chitietsanpham(machitietsanpham),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoicapnhat) REFERENCES nhanvien(manhanvien)
);

-- Bảng lienhedathang
CREATE TABLE lienhedathang (
    malienhe INT PRIMARY KEY IDENTITY(1,1),
    hoten NVARCHAR(100) NOT NULL,
    sodienthoai NVARCHAR(15) NOT NULL,
    email NVARCHAR(100),
    masanpham INT NOT NULL,
    machitietsanpham INT NOT NULL,
    soluong INT NOT NULL CHECK (soluong > 0),
    ghichu NVARCHAR(500),
    madonhang INT NULL,
    ngaylienhe DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    CONSTRAINT chk_email_lienhe CHECK (email IS NULL OR email LIKE '%_@__%.__%'),
    CONSTRAINT chk_sodienthoai_lienhe CHECK (sodienthoai LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    FOREIGN KEY (masanpham) REFERENCES sanpham(masanpham),
    FOREIGN KEY (machitietsanpham) REFERENCES chitietsanpham(machitietsanpham),
    FOREIGN KEY (madonhang) REFERENCES donhang(madonhang),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng phieunhapkho
CREATE TABLE phieunhapkho (
    maphieunhapkho INT PRIMARY KEY IDENTITY(1,1),
    manhacungcap INT NOT NULL,
    ngaynhapkho DATETIME DEFAULT GETDATE(),
    tonggiatri DECIMAL(18,2) NOT NULL CHECK (tonggiatri >= 0),
    ghichu NVARCHAR(500),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (manhacungcap) REFERENCES nhacungcap(manhacungcap),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng phieuxuatkho
CREATE TABLE phieuxuatkho (
    maphieuxuatkho INT PRIMARY KEY IDENTITY(1,1),
    madonhang INT NOT NULL,
    ngayxuatkho DATETIME DEFAULT GETDATE(),
    tonggiatri DECIMAL(18,2) NOT NULL CHECK (tonggiatri >= 0),
    ghichu NVARCHAR(500),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (madonhang) REFERENCES donhang(madonhang),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng danhgiasanpham
CREATE TABLE danhgiasanpham (
    madanhgia INT PRIMARY KEY IDENTITY(1,1),
    masanpham INT NOT NULL,
    makhachhang INT NOT NULL,
    diemdanhgia INT NOT NULL CHECK (diemdanhgia BETWEEN 1 AND 5),
    binhluan NVARCHAR(500),
    hinhanhdanhgia NVARCHAR(500),
    ngaydanhgia DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (masanpham) REFERENCES sanpham(masanpham),
    FOREIGN KEY (makhachhang) REFERENCES khachhang(makhachhang),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng phiendangnhap
CREATE TABLE phiendangnhap (
    maphien INT PRIMARY KEY IDENTITY(1,1),
    manhanvien INT NULL,
    makhachhang INT NULL,
    token NVARCHAR(500) NOT NULL,
    ngaydangnhap DATETIME DEFAULT GETDATE(),
    ngayhethan DATETIME,
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (manhanvien) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (makhachhang) REFERENCES khachhang(makhachhang),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    CONSTRAINT chk_phiendangnhap CHECK ((manhanvien IS NOT NULL AND makhachhang IS NULL) OR (manhanvien IS NULL AND makhachhang IS NOT NULL))
);

-- Bảng resetpasswordtoken
CREATE TABLE resetpasswordtoken (
    matoken INT PRIMARY KEY IDENTITY(1,1),
    manhanvien INT NULL,
    makhachhang INT NULL,
    token NVARCHAR(500) NOT NULL,
    ngaytao DATETIME DEFAULT GETDATE(),
    ngayhethan DATETIME,
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (manhanvien) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (makhachhang) REFERENCES khachhang(makhachhang),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    CONSTRAINT uq_token UNIQUE (token),
    CONSTRAINT chk_resetpasswordtoken CHECK ((manhanvien IS NOT NULL AND makhachhang IS NULL) OR (manhanvien IS NULL AND makhachhang IS NOT NULL))
);

-- Bảng thanhtoan
CREATE TABLE thanhtoan (
    mathanhtoan INT PRIMARY KEY IDENTITY(1,1),
    madonhang INT NOT NULL,
    phuongthucthanhtoan NVARCHAR(50) NOT NULL CHECK (phuongthucthanhtoan IN (N'thẻ tín dụng', N'tiền mặt', N'chuyển khoản')),
    sotien DECIMAL(18,2) NOT NULL CHECK (sotien >= 0),
    magiaodich NVARCHAR(100),
    ngaythanhtoan DATETIME DEFAULT GETDATE(),
    trangthaithanhtoan NVARCHAR(50) NOT NULL CHECK (trangthaithanhtoan IN (N'chờ xử lý', N'hoàn thành', N'thất bại')),
    ngaycapnhat DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    nguoicapnhat INT,
    FOREIGN KEY (madonhang) REFERENCES donhang(madonhang),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoicapnhat) REFERENCES nhanvien(manhanvien)
);

-- Bảng hinhanhsanpham
CREATE TABLE hinhanhsanpham (
    mahinhanh INT PRIMARY KEY IDENTITY(1,1),
    machitietsanpham INT NOT NULL,
    duongdanhinhanh NVARCHAR(500) NOT NULL,
    lahinhchinh BIT DEFAULT 0,
    thutuhienthi INT DEFAULT 0,
    kichthuoc NVARCHAR(50),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (machitietsanpham) REFERENCES chitietsanpham(machitietsanpham),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng danhsachyeuthich
CREATE TABLE danhsachyeuthich (
    madanhsachyeuthich INT PRIMARY KEY IDENTITY(1,1),
    makhachhang INT NOT NULL,
    masanpham INT NOT NULL,
    ngaythem DATETIME DEFAULT GETDATE(),
    ngayxoa DATETIME,
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (makhachhang) REFERENCES khachhang(makhachhang),
    FOREIGN KEY (masanpham) REFERENCES sanpham(masanpham),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Bảng cauhinhhethong
CREATE TABLE cauhinhhethong (
    macauhinh INT PRIMARY KEY IDENTITY(1,1),
    tencauhinh NVARCHAR(100) NOT NULL,
    giatri NVARCHAR(500) NOT NULL,
    mota NVARCHAR(200),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    CONSTRAINT uq_tencauhinh UNIQUE (tencauhinh)
);

-- Bảng baocaobanhang
CREATE TABLE baocaobanhang (
    mabaocao INT PRIMARY KEY IDENTITY(1,1),
    thoigianbatdau DATETIME NOT NULL,
    thoigianketthuc DATETIME NOT NULL,
    tongdoanhthu DECIMAL(18,2) NOT NULL,
    sodonhang INT NOT NULL,
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    CHECK (thoigianketthuc > thoigianbatdau),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Thêm chỉ mục
CREATE INDEX idx_khachhang_email ON khachhang(email);
CREATE INDEX idx_sanpham_tensanpham ON sanpham(tensanpham);
CREATE INDEX idx_donhang_makhachhang ON donhang(makhachhang);
CREATE INDEX idx_donhang_trangthaidonhang ON donhang(trangthaidonhang);
CREATE INDEX idx_chitietdonhang_madonhang ON chitietdonhang(madonhang);
CREATE INDEX idx_lienhedathang_sodienthoai ON lienhedathang(sodienthoai);
CREATE INDEX idx_khohang_machitietsanpham ON khohang(machitietsanpham);
CREATE INDEX idx_danhgiasanpham_masanpham ON danhgiasanpham(masanpham);
CREATE INDEX idx_nhanvien_email ON nhanvien(email);
CREATE INDEX idx_hinhanhsanpham_machitietsanpham ON hinhanhsanpham(machitietsanpham);
CREATE INDEX idx_danhsachyeuthich_makhachhang ON danhsachyeuthich(makhachhang);
CREATE INDEX idx_chitietsanpham_masanpham ON chitietsanpham(masanpham);
CREATE INDEX idx_donhang_manhanvienxuly ON donhang(manhanvienxuly);

-- ======================== Chèn dữ liệu mẫu ========================
INSERT INTO nhanvien (ho, ten, email, sodienthoai, ngaytuyendung, matkhau, nguoitao, nguoicapnhat)
VALUES (N'Nguyễn', N'Văn A', 'nva@example.com', '0123456789', '2025-01-01', 'hashed_password_123', NULL, NULL);

INSERT INTO vaitro (tenvaitro, mota, nguoitao)
VALUES (N'admin', N'Quản trị viên hệ thống', 1);

INSERT INTO quyen (tenquyen, mota, bang, nguoitao)
VALUES (N'view_product', N'Quyền xem sản phẩm', 'sanpham', 1);

INSERT INTO vaitro_quyen (mavaitro, maquyen, nguoitao)
VALUES (1, 1, 1);

INSERT INTO nguoidung_vaitro (manhanvien, mavaitro, nguoitao)
VALUES (1, 1, 1);

INSERT INTO danhmuc (tendanhmuc, mota, nguoitao)
VALUES (N'Đồng hồ nam', N'Danh mục đồng hồ dành cho nam', 1);

INSERT INTO nhacungcap (tennhacungcap, nguoilienhe, sodienthoai, email, diachi, nguoitao)
VALUES (N'Công ty ABC', N'Trần Văn B', '0987654321', 'abc@example.com', N'123 Đường Láng, Hà Nội', 1);

INSERT INTO thuonghieu (tenthuonghieu, mota, nguoitao, nguoicapnhat)
VALUES (N'Rolex', N'Thương hiệu đồng hồ cao cấp', 1, 1);

INSERT INTO capbackhachhang (tencapbac, diemtoithieu, giamgiamacdinh, nguoitao)
VALUES (N'Vàng', 1000, 10.00, 1);

INSERT INTO khachhang (ho, ten, email, sodienthoai, ngaysinh, matkhau, macapbac, nguoitao, nguoicapnhat)
VALUES (N'Lê', N'Thị C', 'ltc@example.com', '0912345678', '1990-05-15', 'hashed_password_456', 1, 1, 1);

INSERT INTO sanpham (tensanpham, mota, gianiemyet, giabanmacdinh, madanhmuc, manhacungcap, mathuonghieu, nguoitao, nguoicapnhat, nguoiduyet)
VALUES (N'Đồng hồ Rolex Submariner', N'Đồng hồ cao cấp cho nam', 200000000.00, 180000000.00, 1, 1, 1, 1, 1, 1);

INSERT INTO thuoctinhsanpham (tenthuoctinh, mota, loaigiatri, nguoitao)
VALUES (N'Màu sắc', N'Màu sắc của sản phẩm', N'Văn bản', 1);

INSERT INTO baohanh (masanpham, thoigianbaohanh, dieukienbaohanh, nguoitao)
VALUES (1, 24, N'Bảo hành chính hãng 2 năm', 1);

INSERT INTO chitietsanpham (masanpham, mathuoctinh, giatri, gia, soluongton, sku, mabaohanh, nguoitao, nguoicapnhat)
VALUES (1, 1, N'Đen', 180000000.00, 10, 'ROLEX-SUB-BLACK-001', 1, 1, 1);

INSERT INTO khohang (machitietsanpham, soluong, vitrikho, ngaynhapkho, nguoicapnhat)
VALUES (1, 10, 'Kệ A1', '2025-05-01', 1);

INSERT INTO phuongthucvanchuyen (tenphuongthuc, chiphi, thoigiandukien, nguoitao)
VALUES (N'Giao hàng nhanh', 50000.00, N'1-2 ngày', 1);

INSERT INTO diachi (makhachhang, diachichitiet, thanhpho, quocgia, nguoitao)
VALUES (1, N'456 Đường Nguyễn Trãi', N'Hà Nội', N'Việt Nam', 1);

INSERT INTO khuyenmai (tenkhuyenmai, mota, phantramgiamgia, ngaybatdau, ngayketthuc, nguoitao)
VALUES (N'Giảm giá mùa hè', N'Giảm giá 10% cho đồng hồ', 10.00, '2025-06-01', '2025-06-30', 1);

INSERT INTO donhang (makhachhang, manhanvienxuly, tongtien, maphuongthucvanchuyen, madiachigiaohang, trangthaidonhang, makhuyenmai, nguoitao, nguoicapnhat)
VALUES (1, 1, 180000000.00, 1, 1, N'chờ xử lý', 1, 1, 1);

INSERT INTO giaodichtichdiem (makhachhang, sodiem, loaigiaodich, madonhang, nguoitao)
VALUES (1, 100, N'tích', 1, 1);

INSERT INTO lichsudonhang (madonhang, trangthaicu, trangthaimoi, lydo, nguoicapnhat)
VALUES (1, N'chờ xử lý', N'đang xử lý', N'Bắt đầu xử lý đơn hàng', 1);

INSERT INTO chitietdonhang (madonhang, machitietsanpham, soluong, dongia, nguoitao, nguoicapnhat)
VALUES (1, 1, 1, 180000000.00, 1, 1);

INSERT INTO lienhedathang (hoten, sodienthoai, email, masanpham, machitietsanpham, soluong, nguoitao)
VALUES (N'Trần Văn D', '0934567890', 'tvd@example.com', 1, 1, 1, 1);

INSERT INTO phieunhapkho (manhacungcap, tonggiatri, nguoitao)
VALUES (1, 2000000000.00, 1);

INSERT INTO phieuxuatkho (madonhang, tonggiatri, nguoitao)
VALUES (1, 180000000.00, 1);

INSERT INTO danhgiasanpham (masanpham, makhachhang, diemdanhgia, binhluan, nguoitao)
VALUES (1, 1, 5, N'Sản phẩm tuyệt vời!', 1);

INSERT INTO phiendangnhap (makhachhang, token, ngayhethan, nguoitao)
VALUES (1, 'sample_token_123', '2025-06-01', 1);

INSERT INTO resetpasswordtoken (makhachhang, token, ngayhethan, nguoitao)
VALUES (1, 'reset_token_456', '2025-05-27 16:32:00', 1);

INSERT INTO thanhtoan (madonhang, phuongthucthanhtoan, sotien, trangthaithanhtoan, nguoitao, nguoicapnhat)
VALUES (1, N'thẻ tín dụng', 180000000.00, N'hoàn thành', 1, 1);

INSERT INTO hinhanhsanpham (machitietsanpham, duongdanhinhanh, lahinhchinh, nguoitao)
VALUES (1, '/images/rolex_sub_black.jpg', 1, 1);

INSERT INTO danhsachyeuthich (makhachhang, masanpham, nguoitao)
VALUES (1, 1, 1);

INSERT INTO cauhinhhethong (tencauhinh, giatri, mota, nguoitao)
VALUES (N'max_order_value', '1000000000', N'Giá trị đơn hàng tối đa', 1);

INSERT INTO baocaobanhang (thoigianbatdau, thoigianketthuc, tongdoanhthu, sodonhang, nguoitao)
VALUES ('2025-05-01', '2025-05-31', 180000000.00, 1, 1);

-- ======================== Định nghĩa các trigger ========================
-- Trigger tự động cập nhật ngaycapnhat cho nhanvien
CREATE TRIGGER trg_update_ngaycapnhat_nhanvien
ON nhanvien
AFTER UPDATE
AS
BEGIN
    UPDATE nhanvien
    SET ngaycapnhat = GETDATE()
    FROM nhanvien n
    INNER JOIN inserted i ON n.manhanvien = i.manhanvien;
END;
GO

-- Trigger tự động cập nhật ngaycapnhat cho sanpham
CREATE TRIGGER trg_update_ngaycapnhat_sanpham
ON sanpham
AFTER UPDATE
AS
BEGIN
    UPDATE sanpham
    SET ngaycapnhat = GETDATE()
    FROM sanpham s
    INNER JOIN inserted i ON s.masanpham = i.masanpham;
END;
GO

-- Trigger tự động cập nhật ngaycapnhat cho chitietsanpham
CREATE TRIGGER trg_update_ngaycapnhat_chitietsanpham
ON chitietsanpham
AFTER UPDATE
AS
BEGIN
    UPDATE chitietsanpham
    SET ngaycapnhat = GETDATE()
    FROM chitietsanpham c
    INNER JOIN inserted i ON c.machitietsanpham = i.machitietsanpham;
END;
GO

-- Trigger tự động cập nhật ngaycapnhat cho donhang
CREATE TRIGGER trg_update_ngaycapnhat_donhang
ON donhang
AFTER UPDATE
AS
BEGIN
    UPDATE donhang
    SET ngaycapnhat = GETDATE()
    FROM donhang d
    INNER JOIN inserted i ON d.madonhang = i.madonhang;
END;
GO

-- Trigger đồng bộ số lượng tồn kho
CREATE TRIGGER trg_sync_soluongton_khohang
ON khohang
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    UPDATE c
    SET c.soluongton = ISNULL((SELECT SUM(k.soluong) FROM khohang k WHERE k.machitietsanpham = c.machitietsanpham AND k.trangthai = 1), 0)
    FROM chitietsanpham c
    INNER JOIN inserted i ON c.machitietsanpham = i.machitietsanpham
    WHERE c.trangthai = 1;
END;
GO