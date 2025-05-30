package com.example.be.tempotide.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DonHangDTO {
    private Integer madonhang;
    private Integer makhachhang;
    private Integer manhanvienxuly;
    private LocalDateTime ngaydathang;
    private BigDecimal tongtien;
    private String diachigiaohang;
    private String phuongthucvanchuyen;
    private String phuongthucthanhtoan;
    private BigDecimal sotien;
    private String trangthaithanhtoan;
    private BigDecimal giamgia;
    private String trangthaidonhang;
    private String tenkhachhang;
    private String sodienthoai;
    private String email;
    private LocalDateTime ngaythanhtoan;
    private String ghichu;
    private Boolean trangthaiHoadon;
    private Boolean ladonhangvanglai;
    private LocalDateTime ngaytao;
    private LocalDateTime ngaycapnhat;
    private Boolean trangthai;
    private Integer nguoitao;
    private Integer nguoicapnhat;
}