package com.noithat.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVienDTO {

    private Integer id;
    private String hoten;
    private String email;
    private String sodienthoai;
    private String chucvu;
    private LocalDateTime ngayvaolam;
    private Integer taikhoanId;
    private String tendangnhap; // để hiển thị
}
