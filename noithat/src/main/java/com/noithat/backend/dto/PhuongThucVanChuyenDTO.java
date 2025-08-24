package com.noithat.backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhuongThucVanChuyenDTO {
    private Integer id;
    private String ten;
    private String mota;
    private String thoigiandukien;
    private BigDecimal phivanchuyen;
}
