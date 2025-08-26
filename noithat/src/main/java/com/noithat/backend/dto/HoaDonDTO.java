package com.noithat.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonDTO {
    private Integer id;
    private Integer donhangId;
    private String nguoixuathoadon;
    private String tenkhachhang;
    private String diachinguoinhan;
    private BigDecimal tongtien;
    private String ghichu;
    private LocalDateTime ngayxuathoadon;

    // Alias method for compatibility with controller
    public String getKhachhang() {
        return this.tenkhachhang;
    }
}
