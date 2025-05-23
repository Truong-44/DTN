package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class PhuongThucThanhToanDTO {
    private Integer maPhuongThucThanhToan;
    private String tenPhuongThuc;
    private String moTa;
    private LocalDateTime ngayTao;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}