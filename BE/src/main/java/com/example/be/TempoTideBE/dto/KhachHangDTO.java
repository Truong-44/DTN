package com.example.be.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class KhachHangDTO {
    private Integer maKhachHang;
    private String ho;
    private String ten;
    private String email;
    private String soDienThoai;
    private LocalDate ngaySinh;
    private String matKhau;
    private Boolean laKhachVangLai;
    private Integer diemTichLuy;
    private Integer maCapBac;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    private Boolean trangThai;
    private Integer nguoiTaoId;
    private Integer nguoiCapNhatId;
}