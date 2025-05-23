package com.example.be.TempoTideBE.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class ThuongHieuDTO {
    private Integer maThuongHieu;
    private String tenThuongHieu;
    private String moTa;
    private String hinhAnh;
    private LocalDateTime ngayTao;
    private Boolean trangThai;
    private Integer nguoiTaoId;
    private Integer nguoiCapNhatId;
}