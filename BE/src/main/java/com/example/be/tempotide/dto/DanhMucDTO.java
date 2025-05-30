package com.example.be.tempotide.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DanhMucDTO {
    private Integer madanhmuc;
    private String tendanhmuc;
    private Integer madanhmuccha;
    private String mota;
    private LocalDateTime ngaytao;
    private Boolean trangthai;
    private Integer nguoitao;
}