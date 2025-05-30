package com.example.be.tempotide.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VaiTroQuyenDTO {
    private Integer mavaitroQuyen;
    private Integer mavaitro;
    private Integer maquyen;
    private LocalDateTime ngaytao;
    private Boolean trangthai;
    private Integer nguoitao;
}