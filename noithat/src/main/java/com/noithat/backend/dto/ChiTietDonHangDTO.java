package com.noithat.backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietDonHangDTO {

    private Integer id;
    private Integer donhangId;
    private Integer chitietsanphamId;
    private String tensanpham;

    private Integer soluong;
    private BigDecimal dongia;
    
    // Alias method for compatibility
    public BigDecimal getGia() {
        return this.dongia;
    }
    
    public void setGia(BigDecimal gia) {
        this.dongia = gia;
    }
}
