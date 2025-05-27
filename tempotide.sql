CREATE DATABASE tempotide;
USE tempotide;

-- Section 1: Create Tables
-- 1. nhanvien
CREATE TABLE nhanvien (
    manhanvien INT PRIMARY KEY IDENTITY(1,1),
    ho NVARCHAR(50) NOT NULL,
    ten NVARCHAR(50) NOT NULL,
    email NVARCHAR(100) NOT NULL,
    sodienthoai NVARCHAR(15),
    ngaytuyendung DATE NOT NULL,
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

-- 2. vaitro
CREATE TABLE vaitro (
    mavaitro INT PRIMARY KEY IDENTITY(1,1),
    tenvaitro NVARCHAR(50) NOT NULL UNIQUE,
    mota NVARCHAR(200),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- 3. quyen
CREATE TABLE quyen (
    maquyen INT PRIMARY KEY IDENTITY(1,1),
    tenquyen NVARCHAR(50) NOT NULL UNIQUE,
    mota NVARCHAR(200),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- 4. vaitro_quyen
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

-- 5. khachhang (phải tạo trước nguoidung_vaitro)
CREATE TABLE khachhang (
    makhachhang INT PRIMARY KEY IDENTITY(1,1),
    ho NVARCHAR(50) NOT NULL,
    ten NVARCHAR(50) NOT NULL,
    email NVARCHAR(100),
    sodienthoai NVARCHAR(15),
    matkhau NVARCHAR(255) NOT NULL,
    diemtichluy INT DEFAULT 0,
    nhanthongbao BIT DEFAULT 0,
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    nguoicapnhat INT,
    CONSTRAINT chk_email_khachhang CHECK (email IS NULL OR email LIKE '%_@__%.__%'),
    CONSTRAINT chk_sodienthoai_khachhang CHECK (sodienthoai IS NULL OR sodienthoai LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    CONSTRAINT uq_email_sodienthoai UNIQUE (email, sodienthoai),
    CONSTRAINT chk_email_or_sodienthoai CHECK (email IS NOT NULL OR sodienthoai IS NOT NULL),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoicapnhat) REFERENCES nhanvien(manhanvien)
);

-- 6. nguoidung_vaitro (phải tạo sau khachhang)
CREATE TABLE nguoidung_vaitro (
    manguoidung_vaitro INT PRIMARY KEY IDENTITY(1,1),
    manhanvien INT NULL,
    makhachhang INT NULL,
    mavaitro INT NOT NULL,
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (manhanvien) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (makhachhang) REFERENCES khachhang(makhachhang),
    FOREIGN KEY (mavaitro) REFERENCES vaitro(mavaitro),
    CONSTRAINT uq_nguoidung_vaitro UNIQUE (manhanvien, makhachhang, mavaitro),
    CONSTRAINT chk_nguoidung_vaitro CHECK ((manhanvien IS NOT NULL AND makhachhang IS NULL) OR (manhanvien IS NULL AND makhachhang IS NOT NULL)),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- 7. danhmuc
CREATE TABLE danhmuc (
    madanhmuc INT PRIMARY KEY IDENTITY(1,1),
    tendanhmuc NVARCHAR(100) NOT NULL,
    madanhmuccha INT NULL,
    mota NVARCHAR(200),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (madanhmuccha) REFERENCES danhmuc(madanhmuc),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- 8. sanpham
CREATE TABLE sanpham (
    masanpham INT PRIMARY KEY IDENTITY(1,1),
    tensanpham NVARCHAR(100) NOT NULL,
    madanhmuc INT NOT NULL,
    mota NVARCHAR(500),
    gia DECIMAL(18,2) NOT NULL CHECK (gia >= 0),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    nguoicapnhat INT,
    FOREIGN KEY (madanhmuc) REFERENCES danhmuc(madanhmuc),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoicapnhat) REFERENCES nhanvien(manhanvien)
);

-- 9. thuoctinhsanpham
CREATE TABLE thuoctinhsanpham (
    mathuoctinh INT PRIMARY KEY IDENTITY(1,1),
    tenthuoctinh NVARCHAR(50) NOT NULL,
    mota NVARCHAR(200),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- 10. chitietsanpham
CREATE TABLE chitietsanpham (
    machitietsanpham INT PRIMARY KEY IDENTITY(1,1),
    masanpham INT NOT NULL,
    mathuoctinh INT NOT NULL,
    giatri NVARCHAR(100) NOT NULL,
    gia DECIMAL(18,2) NOT NULL CHECK (gia >= 0),
    soluongton INT NOT NULL CHECK (soluongton >= 0),
    sku NVARCHAR(50) UNIQUE,
    duongdanhinhanh NVARCHAR(500),
    lahinhchinh BIT DEFAULT 0,
    ngaytao DATETIME DEFAULT GETDATE(),
    ngaycapnhat DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    nguoicapnhat INT,
    FOREIGN KEY (masanpham) REFERENCES sanpham(masanpham),
    FOREIGN KEY (mathuoctinh) REFERENCES thuoctinhsanpham(mathuoctinh),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoicapnhat) REFERENCES nhanvien(manhanvien)
);

-- 11. giohang
CREATE TABLE giohang (
    magiohang INT PRIMARY KEY IDENTITY(1,1),
    makhachhang INT NULL,
    sodienthoai NVARCHAR(15),
    ngaytao DATETIME DEFAULT GETDATE(),
    ngaycapnhat DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    CONSTRAINT chk_sodienthoai_giohang CHECK (sodienthoai IS NULL OR sodienthoai LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    FOREIGN KEY (makhachhang) REFERENCES khachhang(makhachhang),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- 12. chitietgiohang
CREATE TABLE chitietgiohang (
    machitietgiohang INT PRIMARY KEY IDENTITY(1,1),
    magiohang INT NOT NULL,
    machitietsanpham INT NOT NULL,
    soluong INT NOT NULL CHECK (soluong > 0),
    dongia DECIMAL(18,2) NOT NULL CHECK (dongia >= 0),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (magiohang) REFERENCES giohang(magiohang),
    FOREIGN KEY (machitietsanpham) REFERENCES chitietsanpham(machitietsanpham),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- 13. donhang
CREATE TABLE donhang (
    madonhang INT PRIMARY KEY IDENTITY(1,1),
    makhachhang INT NULL,
    manhanvienxuly INT NULL,
    ngaydathang DATETIME DEFAULT GETDATE(),
    tongtien DECIMAL(18,2) NOT NULL CHECK (tongtien >= 0),
    diachigiaohang NVARCHAR(200) NOT NULL,
    phuongthucvanchuyen NVARCHAR(50) NOT NULL DEFAULT N'Giao hàng nhanh',
    phuongthucthanhtoan NVARCHAR(50) NOT NULL DEFAULT N'Tiền mặt',
    sotien DECIMAL(18,2) NOT NULL CHECK (sotien >= 0),
    trangthaithanhtoan NVARCHAR(50) NOT NULL DEFAULT N'Chờ xử lý' CHECK (trangthaithanhtoan IN (N'Chờ xử lý', N'Hoàn thành', N'Thất bại')),
    giamgia DECIMAL(18,2) DEFAULT 0 CHECK (giamgia >= 0),
    trangthaidonhang NVARCHAR(50) NOT NULL CHECK (trangthaidonhang IN (N'Chờ xử lý', N'Đang xử lý', N'Đã giao', N'Hoàn thành', N'Đã hủy', N'Đặt trước')),
    tenkhachhang NVARCHAR(100),
    sodienthoai NVARCHAR(15),
    email NVARCHAR(100),
    ngaythanhtoan DATETIME,
    ghichu NVARCHAR(500),
    trangthai_hoadon BIT DEFAULT 1,
    ladonhangvanglai BIT DEFAULT 0,
    ngaytao DATETIME DEFAULT GETDATE(),
    ngaycapnhat DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    nguoicapnhat INT,
    CONSTRAINT chk_sodienthoai_donhang CHECK (sodienthoai IS NULL OR sodienthoai LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    CONSTRAINT chk_email_donhang CHECK (email IS NULL OR email LIKE '%_@__%.__%'),
    FOREIGN KEY (makhachhang) REFERENCES khachhang(makhachhang),
    FOREIGN KEY (manhanvienxuly) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien),
    FOREIGN KEY (nguoicapnhat) REFERENCES nhanvien(manhanvien)
);

-- 14. chitietdonhang
CREATE TABLE chitietdonhang (
    machitietdonhang INT PRIMARY KEY IDENTITY(1,1),
    madonhang INT NOT NULL,
    machitietsanpham INT NOT NULL,
    soluong INT NOT NULL CHECK (soluong > 0),
    dongia DECIMAL(18,2) NOT NULL CHECK (dongia >= 0),
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    FOREIGN KEY (madonhang) REFERENCES donhang(madonhang),
    FOREIGN KEY (machitietsanpham) REFERENCES chitietsanpham(machitietsanpham),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- 15. chatbox_lichsu
CREATE TABLE chatbox_lichsu (
    machat INT PRIMARY KEY IDENTITY(1,1),
    makhachhang INT NULL,
    sodienthoai NVARCHAR(15),
    noidung NVARCHAR(1000) NOT NULL,
    loai_cauhoi NVARCHAR(50) CHECK (loai_cauhoi IN (N'Sản phẩm', N'Tài khoản', N'Câu hỏi đơn giản')),
    giosanpham INT NULL,
    ngaytao DATETIME DEFAULT GETDATE(),
    trangthai BIT DEFAULT 1,
    nguoitao INT,
    CONSTRAINT chk_sodienthoai_chatbox CHECK (sodienthoai IS NULL OR sodienthoai LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    FOREIGN KEY (makhachhang) REFERENCES khachhang(makhachhang),
    FOREIGN KEY (giosanpham) REFERENCES sanpham(masanpham),
    FOREIGN KEY (nguoitao) REFERENCES nhanvien(manhanvien)
);

-- Section 2: Create Indexes
CREATE INDEX idx_nhanvien_email ON nhanvien(email);
CREATE INDEX idx_vaitro_tenvaitro ON vaitro(tenvaitro);
CREATE INDEX idx_quyen_tenquyen ON quyen(tenquyen);
CREATE INDEX idx_vaitro_quyen_mavaitro ON vaitro_quyen(mavaitro);
CREATE INDEX idx_vaitro_quyen_maquyen ON vaitro_quyen(maquyen);
CREATE INDEX idx_nguoidung_vaitro_makhachhang ON nguoidung_vaitro(makhachhang);
CREATE INDEX idx_nguoidung_vaitro_mavaitro ON nguoidung_vaitro(mavaitro);
CREATE INDEX idx_khachhang_email ON khachhang(email);
CREATE INDEX idx_khachhang_sodienthoai ON khachhang(sodienthoai);
CREATE INDEX idx_danhmuc_tendanhmuc ON danhmuc(tendanhmuc);
CREATE INDEX idx_sanpham_tensanpham ON sanpham(tensanpham);
CREATE INDEX idx_sanpham_madanhmuc ON sanpham(madanhmuc);
CREATE INDEX idx_thuoctinhsanpham_tenthuoctinh ON thuoctinhsanpham(tenthuoctinh);
CREATE INDEX idx_chitietsanpham_masanpham ON chitietsanpham(masanpham);
CREATE INDEX idx_chitietsanpham_sku ON chitietsanpham(sku);
CREATE INDEX idx_giohang_makhachhang ON giohang(makhachhang);
CREATE INDEX idx_giohang_sodienthoai ON giohang(sodienthoai);
CREATE INDEX idx_chitietgiohang_magiohang ON chitietgiohang(magiohang);
CREATE INDEX idx_chitietgiohang_machitietsanpham ON chitietgiohang(machitietsanpham);
CREATE INDEX idx_donhang_makhachhang ON donhang(makhachhang);
CREATE INDEX idx_donhang_ngaydathang ON donhang(ngaydathang);
CREATE INDEX idx_chitietdonhang_madonhang ON chitietdonhang(madonhang);
CREATE INDEX idx_chitietdonhang_machitietsanpham ON chitietdonhang(machitietsanpham);
CREATE INDEX idx_chatbox_lichsu_makhachhang ON chatbox_lichsu(makhachhang);
CREATE INDEX idx_chatbox_lichsu_sodienthoai ON chatbox_lichsu(sodienthoai);

-- Section 3: Create Triggers
-- Trigger: Giảm số lượng tồn khi thêm vào giỏ hàng
CREATE TRIGGER trg_chitietgiohang_insert
ON chitietgiohang
AFTER INSERT
AS
BEGIN
    UPDATE chitietsanpham
    SET soluongton = soluongton - i.soluong
    FROM chitietsanpham c
    INNER JOIN inserted i ON c.machitietsanpham = i.machitietsanpham
    WHERE c.soluongton >= i.soluong;

    IF @@ROWCOUNT = 0
        RAISERROR (N'Số lượng tồn không đủ để thêm vào giỏ hàng', 16, 1);
END;

-- Trigger: Tăng số lượng tồn khi xóa khỏi giỏ hàng
CREATE TRIGGER trg_chitietgiohang_delete
ON chitietgiohang
AFTER DELETE
AS
BEGIN
    UPDATE chitietsanpham
    SET soluongton = soluongton + d.soluong
    FROM chitietsanpham c
    INNER JOIN deleted d ON c.machitietsanpham = d.machitietsanpham;
END;

-- Trigger: Giảm số lượng tồn khi thêm vào đơn hàng
CREATE TRIGGER trg_chitietdonhang_insert
ON chitietdonhang
AFTER INSERT
AS
BEGIN
    UPDATE chitietsanpham
    SET soluongton = soluongton - i.soluong
    FROM chitietsanpham c
    INNER JOIN inserted i ON c.machitietsanpham = i.machitietsanpham
    WHERE c.soluongton >= i.soluong;

    IF @@ROWCOUNT = 0
        RAISERROR (N'Số lượng tồn không đủ để thêm vào đơn hàng', 16, 1);
END;

-- Trigger: Tăng số lượng tồn khi xóa khỏi đơn hàng
CREATE TRIGGER trg_chitietdonhang_delete
ON chitietdonhang
AFTER DELETE
AS
BEGIN
    UPDATE chitietsanpham
    SET soluongton = soluongton + d.soluong
    FROM chitietsanpham c
    INNER JOIN deleted d ON c.machitietsanpham = d.machitietsanpham;
END;

-- Trigger: Cập nhật thông tin hóa đơn và tích điểm khi thanh toán hoàn thành
CREATE TRIGGER trg_donhang_update_thanhtoan
ON donhang
AFTER UPDATE
AS
BEGIN
    IF UPDATE(trangthaithanhtoan)
    BEGIN
        DECLARE @madonhang INT, @trangthaithanhtoan NVARCHAR(50), @makhachhang INT, @tongtien DECIMAL(18,2), @sodienthoai NVARCHAR(15);
        
        SELECT @madonhang = i.madonhang, @trangthaithanhtoan = i.trangthaithanhtoan, @makhachhang = i.makhachhang, 
               @tongtien = i.tongtien, @sodienthoai = i.sodienthoai
        FROM inserted i;

        IF @trangthaithanhtoan = N'Hoàn thành'
        BEGIN
            -- Cập nhật thông tin hóa đơn
            UPDATE donhang
            SET ngaythanhtoan = GETDATE(),
                tenkhachhang = COALESCE((SELECT ho + ' ' + ten FROM khachhang WHERE makhachhang = @makhachhang), N'Khách vãng lai'),
                email = (SELECT email FROM khachhang WHERE makhachhang = @makhachhang),
                sodienthoai = COALESCE((SELECT sodienthoai FROM khachhang WHERE makhachhang = @makhachhang), @sodienthoai),
                trangthai_hoadon = 1
            WHERE madonhang = @madonhang;

            -- Tích điểm cho khách đăng ký (1 điểm cho mỗi 100,000 VNĐ)
            IF @makhachhang IS NOT NULL
            BEGIN
                UPDATE khachhang
                SET diemtichluy = diemtichluy + FLOOR(@tongtien / 100000)
                WHERE makhachhang = @makhachhang;
            END
        END
    END
END;

-- Section 4: Insert Data (Compact)
-- nhanvien
INSERT INTO nhanvien (ho, ten, email, sodienthoai, ngaytuyendung, matkhau) 
VALUES (N'Nguyễn', N'Admin', 'admin@example.com', '0123456789', '2025-01-01', 'hashed_admin_pass');

-- vaitro
INSERT INTO vaitro (tenvaitro, mota, nguoitao) 
VALUES (N'admin', N'Quản trị viên', 1), 
       (N'user', N'Khách hàng', 1);

-- quyen
INSERT INTO quyen (tenquyen, mota, nguoitao) 
VALUES (N'view_product', N'Xem sản phẩm', 1), 
       (N'manage_product', N'Quản lý sản phẩm', 1);

-- vaitro_quyen
INSERT INTO vaitro_quyen (mavaitro, maquyen, nguoitao) 
VALUES (1, 1, 1), 
       (1, 2, 1),
       (2, 1, 1);

-- khachhang
INSERT INTO khachhang (ho, ten, email, sodienthoai, matkhau, nguoitao)
VALUES (N'Trần', N'User1', 'user1@example.com', NULL, 'hashed_user_pass', 1),
       (N'Lê', N'Guest', NULL, '0987654321', 'hashed_guest_pass', 1);

-- nguoidung_vaitro
INSERT INTO nguoidung_vaitro (manhanvien, makhachhang, mavaitro, nguoitao) 
VALUES (1, NULL, 1, 1),
       (NULL, 1, 2, 1),
       (NULL, 2, 2, 1);

-- danhmuc
INSERT INTO danhmuc (tendanhmuc, madanhmuccha, mota, nguoitao) 
VALUES (N'Đồng hồ', NULL, N'Đồng hồ thời trang', 1),
       (N'Đồng hồ nam', 1, N'Đồng hồ dành cho nam', 1);

-- sanpham
INSERT INTO sanpham (tensanpham, madanhmuc, mota, gia, nguoitao)
VALUES (N'Đồng hồ nam thể thao', 2, N'Đồng hồ phong cách thể thao', 5000000.00, 1);

-- thuoctinhsanpham
INSERT INTO thuoctinhsanpham (tenthuoctinh, mota, nguoitao) 
VALUES (N'Màu sắc', N'Màu sắc sản phẩm', 1);

-- chitietsanpham
INSERT INTO chitietsanpham (masanpham, mathuoctinh, giatri, gia, soluongton, sku, duongdanhinhanh, lahinhchinh, nguoitao)
VALUES (1, 1, N'Đen', 5000000.00, 10, 'DHNT-DEN-001', '/images/dongho-den.jpg', 1, 1);

-- giohang
INSERT INTO giohang (makhachhang, sodienthoai, nguoitao) 
VALUES (1, NULL, 1),
       (NULL, '0912345678', 1);

-- chitietgiohang
INSERT INTO chitietgiohang (magiohang, machitietsanpham, soluong, dongia, nguoitao) 
VALUES (1, 1, 2, 5000000.00, 1);

-- donhang
INSERT INTO donhang (makhachhang, manhanvienxuly, tongtien, diachigiaohang, phuongthucthanhtoan, sotien, trangthaithanhtoan, trangthaidonhang, ladonhangvanglai, nguoitao)
VALUES (1, 1, 10000000.00, N'123 Đường ABC, Quận 1', N'Tiền mặt', 10000000.00, N'Chờ xử lý', N'Chờ xử lý', 0, 1),
       (NULL, 1, 5000000.00, N'456 Đường XYZ, Quận 2', N'Tiền mặt', 5000000.00, N'Chờ xử lý', N'Chờ xử lý', 1, 1);

-- chitietdonhang
INSERT INTO chitietdonhang (madonhang, machitietsanpham, soluong, dongia, nguoitao)
VALUES (1, 1, 2, 5000000.00, 1),
       (2, 1, 1, 5000000.00, 1);

-- chatbox_lichsu
INSERT INTO chatbox_lichsu (makhachhang, sodienthoai, noidung, loai_cauhoi, giosanpham, nguoitao)
VALUES (NULL, '0912345678', N'Đồng hồ màu đen dưới 5 triệu?', N'Sản phẩm', 1, 1);