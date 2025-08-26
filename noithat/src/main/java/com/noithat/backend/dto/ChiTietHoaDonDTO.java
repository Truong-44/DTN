package com.noithat.backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietHoaDonDTO {

    private Integer id;
    private Integer hoadonId;
    private String tensanpham;
    private String tenmau;
    private String chatlieu;
    private String kichthuoc;
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
