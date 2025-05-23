package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class NhanVienDTO {
    private Integer maNhanVien;
    private String ho;
    private String ten;
    private String email;
    private String soDienThoai;
    private LocalDate ngayTuyenDung;
    private String maSoThue;
    private LocalDate ngayNghiViec;
    private String matKhau;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    private Boolean trangThai;
    private Integer nguoiTaoId;
    private Integer nguoiCapNhatId;
}