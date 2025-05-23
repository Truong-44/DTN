package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class PhuongThucVanChuyenDTO {
    private Integer maPhuongThucVanChuyen;
    private String tenPhuongThuc;
    private BigDecimal chiPhi;
    private String thoiGianDuKien;
    private Integer thoiGianToiDa;
    private String moTa;
    private LocalDateTime ngayTao;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}