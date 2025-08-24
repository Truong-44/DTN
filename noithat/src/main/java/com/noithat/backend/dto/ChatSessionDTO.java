package com.noithat.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ChatSessionDTO {
    private Integer id;
    private Integer taiKhoanId;
    private LocalDateTime thoiGianBatDau;
    private String tenTaiKhoan; // để hiển thị
    private List<ChatMessageDTO> messages;
    private Integer soLuongTinNhan;
}