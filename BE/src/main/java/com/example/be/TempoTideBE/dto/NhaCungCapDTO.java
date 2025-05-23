package com.example.be.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class NhaCungCapDTO {
    private Integer maNhaCungCap;
    private String tenNhaCungCap;
    private String nguoiLienHe;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private String maTaiKhoanNganHang;
    private String trangWeb;
    private LocalDateTime ngayTao;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}