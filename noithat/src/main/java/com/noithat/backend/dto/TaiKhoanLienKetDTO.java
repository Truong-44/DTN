package com.noithat.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaiKhoanLienKetDTO {
    private Integer id;
    private String loaidangnhap;
    private String giatridangnhap;
    private Integer taikhoanid;

    // Alias method for compatibility
    public String getProvider() {
        return this.loaidangnhap;
    }
}
