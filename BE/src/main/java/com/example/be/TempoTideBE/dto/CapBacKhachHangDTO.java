package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class CapBacKhachHangDTO {
    private Integer maCapBac;
    private String tenCapBac;
    private Integer diemToiThieu;
    private Double giamGiaMacDinh;
    private LocalDateTime ngayTao;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}