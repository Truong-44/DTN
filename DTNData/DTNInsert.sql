-- QUYEN --
INSERT INTO quyen (ten, mota) VALUES 
(N'Admin', N'Quản trị hệ thống'),
(N'Nhân viên', N'Quản lý nghiệp vụ'),
(N'Khách hàng', N'Tài khoản người dùng mua hàng');

-- TAIKHOAN --
INSERT INTO taikhoan (tendangnhap, matkhau, email, sodienthoai, quyenid, loaidangnhap)
VALUES 
(N'admin01',     N'123', N'admin@gmail.com',    N'0911111111', 1, N'email'),
(N'nhanvien01',  N'123', N'nhanvien@gmail.com', N'0922222222', 2, N'email'),
(N'khachhang01', N'123', N'khach@gmail.com',    N'0933333333', 3, N'email');

-- TAIKHOANLIENKET -- 
INSERT INTO taikhoanlienket (taikhoanid, loaidangnhap, giatridangnhap)
VALUES (3, N'google', N'nguyenvana@gmail.com');

-- NHANVIEN --
INSERT INTO nhanvien (hoten, email, sodienthoai, chucvu, taikhoanid)
VALUES (N'Nguyễn Nhân Viên', N'nhanvien@example.com', N'0922222222', N'Nhân viên bán hàng', 2);

-- KHACHHANG --
INSERT INTO khachhang (taikhoanid, hoten, diachi, ngaysinh, gioitinh) VALUES 
(3, N'Nguyễn Văn A', N'123 Đường ABC, Quận 1, TP.HCM', '1995-06-15', N'Nam');

-- DANHMUC -- 
INSERT INTO danhmuc (danhmucchaid, tendanhmuc, mota)
VALUES 
(1, N'Ghế', N'Các loại ghế trong gia đình'),
(2, N'Bàn', N'Bàn ăn, bàn làm việc, bàn trà'),
(3, N'Tủ', N'Các loại tủ quần áo, tủ kệ'),
(4, N'Giường', N'Các loại giường ngủ');

-- DANHMUC CON -- 
INSERT INTO danhmuc (danhmucchaid, tendanhmuc, mota)
VALUES 
(1, N'Ghế ăn', N'Ghế dùng trong phòng ăn'),
(1, N'Ghế sofa', N'Ghế ngồi phòng khách, dạng sofa'),
(1, N'Ghế vải', N'Ghế bọc vải, phong cách hiện đại');
INSERT INTO danhmuc (danhmucchaid, tendanhmuc, mota)
VALUES 
(2, N'Bàn ăn', N'Bàn sử dụng cho phòng ăn'),
(2, N'Bàn làm việc', N'Bàn dùng trong văn phòng, phòng học'),
(2, N'Bàn trà', N'Bàn nhỏ, dùng trong phòng khách');
-- Danh mục con của "Tủ"
INSERT INTO danhmuc (danhmucchaid, tendanhmuc, mota)
VALUES 
(3, N'Tủ quần áo', N'Tủ chứa quần áo'),
(3, N'Tủ trang trí', N'Tủ nhỏ để decor, chứa đồ phụ');

-- SANPHAM --
INSERT INTO sanpham (tensanpham, mota, danhmucid, tenloai, giacu, giamoi)
VALUES 
(N'Ghế Sofa Vải MOHO GIORGIO - MOHO Signature', N'Sofa GIORGIO: MẠNH MẼ TỪ HÌNH KHỐI, TINH TẾ TRONG TRẢI NGHIỆM',6, N'Sofa', 15000000, 12900000),
(N'Ghế Sofa Da MOHO RIGA', N'Ghế Sofa Da - Sofa Băng 2 - 3 Chỗ MOHO RIGA',6, N'Sofa', 18290000, 13990000),
(N'Sofa Phòng Khách Hiện Đại KLINE', N'Sofa Phòng Khách Hiện Đại KLINE Phong Cách Hàn Tối Giản - Thoải Mái Nghỉ Ngơi',6, N'Sofa', 12990000, 10490000),
(N'Ghế Sofa AURORA - MOHO Signature', N'AURORA là thiết kế sofa cao cấp thuộc dòng Signature, mang đậm phong cách Ý hiện đại – sang trọng nhưng không phô trương, đẳng cấp mà vẫn đầy chất sống. ',6, N'Sofa', 40287000, 30990000),
(N'Giường Ngủ Gỗ MOHO VLINE 601 Nhiều Kích Thước', N'Giường Ngủ Gỗ MOHO VLINE 601 màu gỗ tự nhiên với kiểu dáng hiện đại, thoáng hồn Việt mang đến không gian nội thất phòng ngủ sự ấm cúng, dễ dàng phối hợp với nhiều phong cách nội thất khác nhau.',4, N'Giường', 7990000, 5790000),
(N'Giường Ngủ Gỗ Tràm MOHO HOBRO 301', N'Giường ngủ HOBRO là một lựa chọn tuyệt vời cho những ai thích phong cách Vintage Industrial.',4, N'Giường', 8290000, 10990000),
(N'Giường Ngủ Gỗ Tràm MOHO DALUMD 301 Ver 2 Màu Nâu Hạnh Nhân', N'Bộ sưu tập Dalumd là bản giao hưởng hoàn hảo giữa phong cách tối giản (Minimalist) và phong cách thanh lịch hiện đại (Modern Elegant).',4, N'Giường', 8290000, 9990000),
(N'Giường Ngủ Có Hộc & Ổ Điện MOHO VIENNA', N'Giường có hộc VIENNA của Nội Thất MOHO là lựa chọn hoàn hảo cho những gia đình trẻ hoặc cá nhân sống trong không gian nhỏ, yêu thích phong cách hiện đại và tiện nghi. Với thiết kế thông minh, sản phẩm không chỉ tiết kiệm diện tích mà còn mang lại sự tiện lợi tối đa.',4, N'Giường', 8290000, 9990000);

select * from danhmuc
SELECT id FROM danhmuc WHERE tendanhmuc = N'Ghế sofa';

-- CHITIETSANPHAM -- 
INSERT INTO chitietsanpham (sanphamid,tenmau,mamau,chatlieu,kichthuoc,trongluong,soluong,hinhchinh,hinhphu)
VALUES 
(1, N'Nâu đất', N'#835C3B', N'Vải bọc cao cấp, khung gỗ tự nhiên, chân gỗ ASH vát chéo', N'250 x 107 x 83 cm', 45.5, 5,
    N'/sanpham/ghe/sofa/giorgio_01.jpg',
    N'/sanpham/ghe/sofa/giorgio_02.jpg;/sanpham/ghe/sofa/giorgio_03.jpg;/sanpham/ghe/sofa/giorgio_04.jpg'
),
(2, N'Camel', N'#C19A6B', N'Khung gỗ tự nhiên, da simili cao cấp, chân sắt sơn tĩnh điện', N'Dài 200cm x Rộng 83cm x Cao 80cm', 50, 5,
    N'/sanpham/ghe/sofa/da_moho_riga_01.jpg',
    N'/sanpham/ghe/sofa/da_moho_riga_02.jpg;/sanpham/ghe/sofa/da_moho_riga_03.jpg;/sanpham/ghe/sofa/da_moho_riga_04.jpg'
),
(3, N'Trắng', N'#FFFFFF', N'Gỗ cao su tự nhiên, vải sợi tổng hợp ', N'Dài 200cm x Rộng 82cm x Cao 80cm', 50, 5,
    N'/sanpham/ghe/sofa/kline_01.png',
    N'/sanpham/ghe/sofa/kline_02.png;/sanpham/ghe/sofa/kline_03.png;/sanpham/ghe/sofa/kline_04.png'
),
(4, N'Xám', N'#808080', N'Vải cao cấp phối viền da nhân tạo - Khung gỗ tự nhiên - Chân inox ', N'D250x S109x C77 cm', 45.5, 5,
    N'/sanpham/ghe/sofa/aurora_01.jpg',
    N'/sanpham/ghe/sofa/aurora_02.jpg;/sanpham/ghe/sofa/aurora_03.jpg;/sanpham/ghe/sofa/aurora_04.jpg'
),
(5, N'Màu tự nhiên', N'#C19A6B', N'Thân giường: Gỗ tràm tự nhiên, chân giường: Gỗ cao su tự nhiên, tấm phản: Gỗ plywood chuẩn CARB-P2', N'Dài 210cm x Rộng 171/191cm x Cao đến đầu giường 90 cm - Gầm giường cao 16cm', 45.5, 5,
    N'/sanpham/giuong/vline601_01.jpg',
    N'/sanpham/giuong/vline601_02.jpg;/sanpham/giuong/vline601_03.jpg;/sanpham/giuong/vline601_04.jpg'
),
(6, N'Nâu', N'#964B00', N'Thân giường: Gỗ tràm tự nhiên/ MDF veneer tràm, tấm phản: Gỗ plywood chuẩn CARB-P2', N'Dài 212cm x Rộng 136/156/176/196cm x Cao đến đầu giường 92cm', 45.5, 5,
    N'/sanpham/giuong/hobro301_01.jpg',
    N'/sanpham/giuong/hobro301_02.jpg;/sanpham/giuong/hobro301_03.jpg;/sanpham/giuong/hobro301_04.jpg'
),
(7, N'Nâu gỗ', N'#C19A6B', N'Gỗ tràm tự nhiên/ Veneer tràm, gỗ công nghiệp phủ Melamine & plywood chuẩn CARB-P2', N'Dài 209 x Rộng 167/ 187 x Cao 90 cm (phù hợp với nệm 160/ 180 x 200 cm) x Gầm giường cao: 14 cm', 45.5, 5,
    N'/sanpham/giuong/dalumd301_01.jpg',
    N'/sanpham/giuong/dalumd301_02.jpg;/sanpham/giuong/dalumd301_03.jpg;/sanpham/giuong/dalumd301_04.jpg'
),
(8, N'Trắng', N'#FFFFFF', N'Gỗ công nghiệp phủ Melamine CARB-P2', N'Rộng 160/180 x Dài 218 x Cao 100 (cm)', 45.5, 5,
    N'/sanpham/giuong/vienna_01.jpg',
    N'/sanpham/giuong/vienna_02.jpg;/sanpham/giuong/vienna_03.jpg;/sanpham/giuong/vienna_04.jpg'
);

-- Bước 1: Xóa bảng con trước (do khóa ngoại)
DELETE FROM chitietsanpham;

-- Bước 2: Xóa bảng cha
DELETE FROM sanpham;

-- Bước 3: Reset lại IDENTITY cả hai bảng
DBCC CHECKIDENT ('chitietsanpham', RESEED, 0);
DBCC CHECKIDENT ('sanpham', RESEED, 0);

select * from sanpham
select * from chitietsanpham

SELECT con.tendanhmuc AS tendanhmuc_con, cha.tendanhmuc AS tendanhmuc_cha
FROM danhmuc con
LEFT JOIN danhmuc cha ON con.danhmucchaid = cha.id
WHERE con.id = 5;


INSERT INTO chatsession (taikhoanid, thoigianbatdau)
VALUES 
  (1, GETDATE()),        
  (NULL, GETDATE()); 

INSERT INTO chatmessage (sessionid, nguoigui, noidung, thoigian) VALUES
(1, 'user', N'Tôi nên ngủ bao nhiêu tiếng mỗi ngày?', '2025-08-02 13:00:00'),
(1, 'bot',  N'Người trưởng thành nên ngủ từ 7 đến 9 tiếng mỗi đêm để đảm bảo sức khỏe.', '2025-08-02 13:00:05'),
(1, 'user', N'Shop có mở cửa cuối tuần không?', '2025-08-02 13:03:00'),
(1, 'bot',  N'Dạ, shop mở cửa từ 8h đến 21h tất cả các ngày trong tuần.', '2025-08-02 13:03:05'),
(1, 'user', N'Có ghế sofa nào giá khoảng 3 triệu không?', '2025-08-02 13:06:00'),
(1, 'bot',  N'Dạ có, bạn có thể tham khảo sản phẩm GHẾ SOFA MOHO GIORGIO đang giảm giá.', '2025-08-02 13:06:05'),
(1, 'user', N'Bạn là ai?', '2025-08-02 13:09:00'),
(1, 'bot',  N'Tôi là trợ lý ảo của hệ thống, sẵn sàng hỗ trợ bạn.', '2025-08-02 13:09:05'),
(1, 'user', N'Bạn có yêu tôi không?', '2025-08-02 13:12:00'),
(1, 'bot',  N'Xin lỗi, tôi là một AI, không thể yêu đương hay cảm xúc như con người.', '2025-08-02 13:12:05');

INSERT INTO chatmessage (sessionid, nguoigui, noidung, thoigian) VALUES
(2, 'user', N'Ghế gỗ sồi bên bạn có giá bao nhiêu?', '2025-08-02 14:00:00'),
(2, 'bot',  N'Sản phẩm Ghế gỗ sồi tự nhiên có giá khoảng 2.500.000 VNĐ.', '2025-08-02 14:00:05'),

(2, 'user', N'Giao hàng bao lâu thì nhận được?', '2025-08-02 14:02:00'),
(2, 'bot',  N'Bạn sẽ nhận được hàng trong vòng 3-5 ngày làm việc.', '2025-08-02 14:02:05'),

(2, 'user', N'Tôi muốn hỏi về tình yêu...', '2025-08-02 14:04:00'),
(2, 'bot',  N'Xin lỗi, tôi không thể trả lời các câu hỏi cá nhân hoặc nhạy cảm.', '2025-08-02 14:04:05'),

(2, 'user', N'Bạn có bàn làm việc màu gỗ tự nhiên không?', '2025-08-03 09:00:00'),
(2, 'bot',  N'Dạ có, bàn làm việc gỗ sồi tự nhiên đang được ưa chuộng hiện nay.', '2025-08-03 09:00:03'),

(2, 'user', N'Giá tủ quần áo 3 cánh là bao nhiêu?', '2025-08-03 09:02:00'),
(2, 'bot',  N'Tủ quần áo 3 cánh hiện có giá từ 5 triệu đến 8 triệu tùy chất liệu.', '2025-08-03 09:02:03'),

(2, 'user', N'Sản phẩm này có giao về Bến Tre không?', '2025-08-03 09:03:30'),
(2, 'bot',  N'Dạ có, chúng tôi giao hàng toàn quốc bao gồm cả Bến Tre.', '2025-08-03 09:03:33'),

(2, 'user', N'Sản phẩm này làm bằng chất liệu gì?', '2025-08-03 09:05:00'),
(2, 'bot',  N'Chất liệu là gỗ tràm tự nhiên kết hợp veneer gỗ sồi.', '2025-08-03 09:05:03'),

(2, 'user', N'Tôi cần tìm sofa giá dưới 10 triệu', '2025-08-03 09:06:30'),
(2, 'bot',  N'Bạn có thể tham khảo sofa MOHO GIORGIO giá chỉ 7.200.000 VNĐ.', '2025-08-03 09:06:33'),

(2, 'user', N'Bạn khỏe không?', '2025-08-03 09:07:00'),
(2, 'bot',  N'Tôi là AI, nhưng cảm ơn bạn đã quan tâm! Tôi luôn sẵn sàng hỗ trợ bạn.', '2025-08-03 09:07:03'),

(2, 'user', N'Cách ngủ ngon vào ban đêm?', '2025-08-03 09:08:30'),
(2, 'bot',  N'Bạn nên tránh dùng thiết bị điện tử trước khi ngủ và giữ phòng yên tĩnh.', '2025-08-03 09:08:33'),

(2, 'user', N'Cửa hàng có làm việc vào ngày lễ không?', '2025-08-03 09:10:00'),
(2, 'bot',  N'Dạ có, chúng tôi vẫn hoạt động bình thường vào các ngày lễ lớn.', '2025-08-03 09:10:03'),

(2, 'user', N'Tôi có thể xem mẫu thực tế ở đâu?', '2025-08-03 09:11:30'),
(2, 'bot',  N'Bạn có thể đến showroom của chúng tôi tại TP.HCM để xem trực tiếp sản phẩm.', '2025-08-03 09:11:33'),

(2, 'user', N'Tôi muốn nói chuyện riêng tư với bạn', '2025-08-03 09:12:30'),
(2, 'bot',  N'Xin lỗi, tôi không thể tham gia các cuộc trò chuyện riêng tư.', '2025-08-03 09:12:33'),

(2, 'user', N'Tôi muốn hỏi chuyện 18+', '2025-08-03 09:14:00'),
(2, 'bot',  N'Xin lỗi, tôi không thể trả lời các nội dung không phù hợp.', '2025-08-03 09:14:03'),

(2, 'user', N'Bạn có thích chuyện người lớn không?', '2025-08-03 09:15:30'),
(2, 'bot',  N'Tôi được thiết kế để hỗ trợ khách hàng trong giới hạn phù hợp.', '2025-08-03 09:15:33'),

(2, 'user', N'Tôi cần tư vấn mua bàn ăn cho 6 người', '2025-08-03 09:17:00'),
(2, 'bot',  N'Bàn ăn 6 người hiện có nhiều mẫu bằng gỗ cao su, giá từ 4 triệu.', '2025-08-03 09:17:03'),

(2, 'user', N'Tôi muốn biết thêm về chính sách bảo hành', '2025-08-03 09:18:30'),
(2, 'bot',  N'Mỗi sản phẩm đều có bảo hành 12 tháng, chi tiết có trên trang sản phẩm.', '2025-08-03 09:18:33'),

(2, 'user', N'Cách bạn lưu lịch sử trò chuyện thế nào?', '2025-08-03 09:20:00'),
(2, 'bot',  N'Lịch sử trò chuyện chỉ được lưu khi bạn đăng nhập tài khoản.', '2025-08-03 09:20:03');


