package com.example.be.tempotide.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class QuyenDTO {
    private Integer maquyen;
    private String tenquyen;
    private String mota;
    private LocalDateTime ngaytao;
    private Boolean trangthai;
    private Integer nguoitao;
}