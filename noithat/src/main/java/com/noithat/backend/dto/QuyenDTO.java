package com.noithat.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuyenDTO {
    private Integer id;
    private String ten;
    private String mota;

    // Alias method for compatibility
    public String getTenquyen() {
        return this.ten;
    }
}
