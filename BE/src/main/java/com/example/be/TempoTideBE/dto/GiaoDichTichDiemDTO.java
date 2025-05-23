package com.example.be.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class GiaoDichTichDiemDTO {
    private Integer maGiaoDich;
    private Integer maKhachHang;
    private Integer soDiem;
    private String loaiGiaoDich;
    private Integer maDonHang;
    private LocalDateTime ngayTao;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}