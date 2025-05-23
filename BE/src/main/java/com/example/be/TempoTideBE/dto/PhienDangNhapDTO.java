package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class PhienDangNhapDTO {
    private Integer maPhien;
    private Integer maNhanVien;
    private Integer maKhachHang;
    private String token;
    private LocalDateTime ngayDangNhap;
    private LocalDateTime ngayHetHan;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}