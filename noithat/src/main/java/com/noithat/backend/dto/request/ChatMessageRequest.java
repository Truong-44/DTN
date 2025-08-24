package com.noithat.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatMessageRequest {
    @NotNull(message = "Session ID không được để trống")
    private Integer sessionId;
    
    @NotBlank(message = "Nội dung tin nhắn không được để trống")
    private String noiDung;
    
    private Integer sanPhamId; // optional, cho bot suggestions
}