package com.noithat.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GioHangDTO {

    private Integer id;
    private Integer khachHangId;
    private String khachHangHoTen;
    private LocalDateTime ngayCapNhat;
    
    // Getter method for khachhang (alias for khachHangId)
    public Integer getKhachhang() {
        return this.khachHangId;
    }
}
