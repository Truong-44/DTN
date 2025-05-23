package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class ThuocTinhSanPhamDTO {
    private Integer maThuocTinh;
    private String tenThuocTinh;
    private String moTa;
    private String loaiGiaTri;
    private LocalDateTime ngayTao;
    private Boolean trangThai;
    private Integer nguoiTaoId;
}