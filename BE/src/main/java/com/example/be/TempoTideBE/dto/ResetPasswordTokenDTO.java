package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class ResetPasswordTokenDTO {
    private Integer maToken;
    private Integer maNhanVien;
    private Integer maKhachHang;
    private String token;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayHetHan;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}