package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class DanhGiaSanPhamDTO {
    private Integer maDanhGia;
    private Integer maSanPham;
    private Integer maKhachHang;
    private Integer diemDanhGia;
    private String binhLuan;
    private String hinhAnhDanhGia;
    private LocalDateTime ngayDanhGia;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}