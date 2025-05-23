package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class ThongBaoDTO {
    private Integer maThongBao;
    private Integer maNhanVien;
    private String tieuDe;
    private String noiDung;
    private LocalDateTime ngayTao;
    private Boolean trangThai;
}