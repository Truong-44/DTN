package com.noithat.backend.dto;

import java.time.LocalDateTime;

import com.noithat.backend.entity.ChatMessage;

import lombok.Data;

@Data
public class ChatMessageDTO {
    private Integer id;
    private Integer sessionId;
    private ChatMessage.MessageSender nguoiGui;
    private String noiDung;
    private LocalDateTime thoiGian;
    private Integer sanPhamId;
    private String tenSanPham; // để hiển thị
    private String hinhAnhSanPham; // để gợi ý
}