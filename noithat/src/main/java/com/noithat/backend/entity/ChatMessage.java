package com.noithat.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chatmessage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "sessionid", nullable = false)
    private Integer sessionId;
    
    @Column(name = "nguoigui", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageSender nguoiGui; // 'user' hoặc 'bot'
    
    @Column(name = "noidung", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String noiDung;
    
    @Column(name = "thoigian")
    @Builder.Default
    private LocalDateTime thoiGian = LocalDateTime.now();
    
    @Column(name = "sanphamid")
    private Integer sanPhamId; // nếu bot đang gợi ý sản phẩm
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessionid", insertable = false, updatable = false)
    private ChatSession chatSession;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sanphamid", insertable = false, updatable = false)
    private SanPham sanPham;
    
    public enum MessageSender {
        user, bot
    }
}