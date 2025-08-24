package com.noithat.backend.dto.request;

import lombok.Data;

@Data
public class ChatSessionRequest {
    private Integer taiKhoanId; // null cho guest user
    private String tinNhanDau; // tin nhắn đầu tiên (optional)
}