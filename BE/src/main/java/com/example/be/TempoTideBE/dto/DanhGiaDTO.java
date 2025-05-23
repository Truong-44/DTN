package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class DanhGiaDTO {
    private Integer maDanhGia;
    private Integer maKhachHang;
    private Integer maChiTietSanPham;
    private Integer danhDiem;
    private String binhLuan;
    private LocalDateTime ngayDanhGia;
    private Boolean trangThai;
}