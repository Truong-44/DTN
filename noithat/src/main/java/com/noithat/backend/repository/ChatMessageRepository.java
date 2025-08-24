package com.noithat.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.noithat.backend.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    
    // Tin nhắn theo session
    List<ChatMessage> findBySessionIdOrderByThoiGian(Integer sessionId);
    
    // Tin nhắn phân trang theo session
    Page<ChatMessage> findBySessionIdOrderByThoiGian(Integer sessionId, Pageable pageable);
    
    // Tin nhắn từ bot
    List<ChatMessage> findByNguoiGuiOrderByThoiGianDesc(ChatMessage.MessageSender nguoiGui);
    
    // Tin nhắn có gợi ý sản phẩm
    List<ChatMessage> findBySanPhamIdIsNotNullOrderByThoiGianDesc();
    
    // Đếm tin nhắn trong session
    @Query("SELECT COUNT(cm) FROM ChatMessage cm WHERE cm.sessionId = :sessionId")
    Long countBySessionId(@Param("sessionId") Integer sessionId);
    
    // Tin nhắn mới nhất của session
    @Query("SELECT cm FROM ChatMessage cm WHERE cm.sessionId = :sessionId ORDER BY cm.thoiGian DESC LIMIT 1")
    ChatMessage findLatestMessageBySessionId(@Param("sessionId") Integer sessionId);
    
    // Tìm kiếm tin nhắn theo nội dung
    @Query("SELECT cm FROM ChatMessage cm WHERE LOWER(cm.noiDung) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY cm.thoiGian DESC")
    List<ChatMessage> searchByContent(@Param("keyword") String keyword);
    
    // Tin nhắn trong khoảng thời gian
    @Query("SELECT cm FROM ChatMessage cm WHERE cm.thoiGian BETWEEN :startDate AND :endDate ORDER BY cm.thoiGian DESC")
    List<ChatMessage> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}