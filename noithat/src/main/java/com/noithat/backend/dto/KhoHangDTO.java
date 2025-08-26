package com.noithat.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhoHangDTO {
    private Integer id;
    private Integer chitietsanphamId;
    private String tensanpham;
    private Integer soluongton;
    private LocalDateTime ngaycapnhat;
}
