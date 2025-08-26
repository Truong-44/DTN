package com.noithat.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietGioHangDTO {

    private Integer id;
    private Integer giohangId;
    private Integer chitietsanphamId;
    private String tensanpham;
    private Integer soluong;
    private BigDecimal dongia;
    private LocalDateTime ngaythem;
}
