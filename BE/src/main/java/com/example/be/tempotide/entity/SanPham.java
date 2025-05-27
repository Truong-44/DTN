package com.example.tempotide.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sanpham")
@Data
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "masanpham")
    private Integer masanpham; // Mã sản phẩm (khóa chính, tự tăng)

    @Column(name = "tensanpham", nullable = false)
    private String tensanpham; // Tên sản phẩm, không được để trống

    @Column(name = "mota")
    private String mota; // Mô tả sản phẩm (tùy chọn)

    @Column(name = "gianiemyet", nullable = false, precision = 18, scale = 2)
    private BigDecimal gianiemyet; // Giá niêm yết, không âm

    @Column(name = "giabanmacdinh", nullable = false, precision = 18, scale = 2)
    private BigDecimal giabanmacdinh; // Giá bán mặc định, không âm

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "madanhmuc", nullable = false)
    private DanhMuc danhmuc; // Quan hệ với danh mục (khóa ngoại)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manhacungcap", nullable = false)
    private NhaCungCap nhacungcap; // Quan hệ với nhà cung cấp (khóa ngoại)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mathuonghieu")
    private ThuongHieu thuonghieu; // Quan hệ với thương hiệu (khóa ngoại, tùy chọn)

    @Column(name = "hinhanhmacdinh")
    private String hinhanhmacdinh; // Đường dẫn hình ảnh mặc định

    @Column(name = "trangthai", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangthai; // Trạng thái (mặc định true)

    @Column(name = "trangthaiban", columnDefinition = "BIT DEFAULT 1")
    private Boolean trangthaiban; // Trạng thái bán (mặc định true)

    @Column(name = "ngaytao", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime ngaytao; // Ngày tạo (mặc định thời gian hiện tại)

    @Column(name = "ngaycapnhat", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime ngaycapnhat; // Ngày cập nhật (mặc định thời gian hiện tại)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoitao")
    private NhanVien nguoitao; // Người tạo (khóa ngoại tham chiếu nhanvien)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoicapnhat")
    private NhanVien nguoicapnhat; // Người cập nhật (khóa ngoại tham chiếu nhanvien)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoiduyet")
    private NhanVien nguoiduyet; // Người duyệt (khóa ngoại tham chiếu nhanvien)
}

//Sử dụng @Entity và @Table để ánh xạ với bảng sanpham trong SQL.
//@ManyToOne và @JoinColumn xử lý các mối quan hệ khóa ngoại với danhmuc, nhacungcap, thuonghieu, và nhanvien.
//columnDefinition giữ nguyên các giá trị mặc định từ SQL (như DEFAULT GETDATE()).