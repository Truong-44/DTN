package com.noithat.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonHangDTO {

    private Integer id;
    private Integer khachHangId;
    private String khachHangHoTen;

    private LocalDateTime ngaydat;
    private String diachinhan;
    private String trangthaidonhang;
    private BigDecimal tongtien;
    private String phuongthucthanhtoan;
    private Boolean trangthaithanhtoan;
    private LocalDateTime ngaythanhtoan;
    private String ghichu;

    // Alias method for compatibility
    public Integer getKhachhang() {
        return this.khachHangId;
    }
}
