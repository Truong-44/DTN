package com.noithat.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamDTO {
    private Integer id;
    private String tensanpham;
    private String mota;
    private Integer danhmucId;
    private String danhmucTen;
    private String tenloai;
    private BigDecimal giacu;
    private BigDecimal giamoi;
    private Boolean trangthai;
    private List<ChiTietSanPhamDTO> chitietsanpham;
}
