package com.noithat.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DanhMucDTO {
    private Integer id;
    private String tendanhmuc;
    private String mota;
    private Integer danhmucChaId;
    private String danhmucChaTen;
}
