package com.noithat.backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.noithat.backend.entity.ChatSession;

public interface ChatSessionRepository extends JpaRepository<ChatSession, Integer> {
    
    // Tìm session của user
    List<ChatSession> findByTaiKhoanIdOrderByThoiGianBatDauDesc(Integer taiKhoanId);
    
    // Session mới nhất của user
    Optional<ChatSession> findFirstByTaiKhoanIdOrderByThoiGianBatDauDesc(Integer taiKhoanId);
    
    // Session của guest (chưa đăng nhập)
    List<ChatSession> findByTaiKhoanIdIsNullOrderByThoiGianBatDauDesc();
    
    // Session trong khoảng thời gian
    @Query("SELECT cs FROM ChatSession cs WHERE cs.thoiGianBatDau BETWEEN :startDate AND :endDate ORDER BY cs.thoiGianBatDau DESC")
    List<ChatSession> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // Phân trang tất cả session
    Page<ChatSession> findAllByOrderByThoiGianBatDauDesc(Pageable pageable);
    
    // Session có tin nhắn
    @Query("SELECT DISTINCT cs FROM ChatSession cs JOIN cs.messages m ORDER BY cs.thoiGianBatDau DESC")
    List<ChatSession> findSessionsWithMessages();
}