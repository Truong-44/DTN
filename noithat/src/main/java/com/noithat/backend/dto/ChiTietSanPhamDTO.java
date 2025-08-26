package com.noithat.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietSanPhamDTO {

    private Integer id;
    private Integer sanphamId;
    private String tensanpham;

    private String tenmau;
    private String mamau;
    private String chatlieu;
    private String kichthuoc;
    private Double trongluong;
    private Integer soluong;
    private String hinhchinh;
    private String hinhphu;

    // Alias method for compatibility
    public String getSanpham() {
        return this.tensanpham;
    }
}
