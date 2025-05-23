package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ChiTietKhuyenMaiDTO {
    private Integer maChiTietKhuyenMai;
    private Integer maKhuyenMai;
    private Integer maSanPham;
    private Boolean trangThai;
}