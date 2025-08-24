package com.noithat.backend.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHangDTO {

    private Integer id;
    private String hoten;
    private String diachi;
    private LocalDate ngaysinh;
    private String gioitinh;
    private Integer taikhoanId;
    private String tendangnhap; // để hiển thị nếu cần
}
