package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class DoiTraDTO {
    private Integer maDoiTra;
    private Integer maDonHang;
    private Integer maChiTietSanPham;
    private Integer maKhachHang;
    private String lyDo;
    private String trangThaiDoiTra;
    private LocalDateTime ngayYeuCau;
    private LocalDateTime ngayXuLy;
    private String ghiChu;
    private Boolean trangThai;
    private Integer nguoiXuLyId;
}