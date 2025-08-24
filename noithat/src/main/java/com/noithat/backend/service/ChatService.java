package com.noithat.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.noithat.backend.dto.ChatMessageDTO;
import com.noithat.backend.dto.ChatSessionDTO;
import com.noithat.backend.dto.request.ChatMessageRequest;
import com.noithat.backend.dto.request.ChatSessionRequest;

public interface ChatService {
    
    // Chat Session Management
    ChatSessionDTO createSession(ChatSessionRequest request);
    ChatSessionDTO getSessionById(Integer id);
    ChatSessionDTO getLatestSessionByUser(Integer taiKhoanId);
    List<ChatSessionDTO> getSessionsByUser(Integer taiKhoanId);
    List<ChatSessionDTO> getGuestSessions();
    Page<ChatSessionDTO> getAllSessions(Pageable pageable);
    void endSession(Integer sessionId);
    
    // Chat Message Management
    ChatMessageDTO sendMessage(ChatMessageRequest request);
    ChatMessageDTO sendBotResponse(Integer sessionId, String message, Integer sanPhamId);
    List<ChatMessageDTO> getMessagesBySession(Integer sessionId);
    Page<ChatMessageDTO> getMessagesBySessionPaged(Integer sessionId, Pageable pageable);
    List<ChatMessageDTO> searchMessages(String keyword);
    
    // AI Bot Features
    ChatMessageDTO processUserMessage(ChatMessageRequest request);
    List<ChatMessageDTO> suggestProducts(Integer sessionId, String userMessage);
    
    // Analytics
    Long getMessageCount(Integer sessionId);
    List<ChatSessionDTO> getActiveSessionsInDateRange(LocalDateTime start, LocalDateTime end);
    List<ChatMessageDTO> getBotSuggestions();
}