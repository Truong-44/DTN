package com.noithat.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.noithat.backend.dto.ChatMessageDTO;
import com.noithat.backend.dto.ChatSessionDTO;
import com.noithat.backend.dto.request.ChatMessageRequest;
import com.noithat.backend.dto.request.ChatSessionRequest;
import com.noithat.backend.service.ChatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Tag(name = "Chat API", description = "API quản lý chat bot và hỗ trợ khách hàng")
public class ChatController {
    
    private final ChatService chatService;
    
    // ========== CHAT SESSION ENDPOINTS ==========
    
    @PostMapping("/sessions")
    @Operation(summary = "Tạo chat session mới")
    public ResponseEntity<ChatSessionDTO> createSession(@Valid @RequestBody ChatSessionRequest request) {
        ChatSessionDTO session = chatService.createSession(request);
        return ResponseEntity.ok(session);
    }
    
    @GetMapping("/sessions/{id}")
    @Operation(summary = "Lấy thông tin chat session")
    public ResponseEntity<ChatSessionDTO> getSession(@PathVariable Integer id) {
        ChatSessionDTO session = chatService.getSessionById(id);
        return ResponseEntity.ok(session);
    }
    
    @GetMapping("/sessions")
    @Operation(summary = "Lấy tất cả chat sessions với phân trang")
    public ResponseEntity<Page<ChatSessionDTO>> getAllSessions(Pageable pageable) {
        Page<ChatSessionDTO> sessions = chatService.getAllSessions(pageable);
        return ResponseEntity.ok(sessions);
    }
    
    @GetMapping("/sessions/user/{taiKhoanId}")
    @Operation(summary = "Lấy chat sessions của user")
    public ResponseEntity<List<ChatSessionDTO>> getSessionsByUser(@PathVariable Integer taiKhoanId) {
        List<ChatSessionDTO> sessions = chatService.getSessionsByUser(taiKhoanId);
        return ResponseEntity.ok(sessions);
    }
    
    @GetMapping("/sessions/user/{taiKhoanId}/latest")
    @Operation(summary = "Lấy chat session mới nhất của user")
    public ResponseEntity<ChatSessionDTO> getLatestSessionByUser(@PathVariable Integer taiKhoanId) {
        ChatSessionDTO session = chatService.getLatestSessionByUser(taiKhoanId);
        return ResponseEntity.ok(session);
    }
    
    @GetMapping("/sessions/guest")
    @Operation(summary = "Lấy chat sessions của khách (chưa đăng nhập)")
    public ResponseEntity<List<ChatSessionDTO>> getGuestSessions() {
        List<ChatSessionDTO> sessions = chatService.getGuestSessions();
        return ResponseEntity.ok(sessions);
    }
    
    @PostMapping("/sessions/{id}/end")
    @Operation(summary = "Kết thúc chat session")
    public ResponseEntity<Void> endSession(@PathVariable Integer id) {
        chatService.endSession(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/sessions/date-range")
    @Operation(summary = "Lấy sessions trong khoảng thời gian")
    public ResponseEntity<List<ChatSessionDTO>> getSessionsInDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<ChatSessionDTO> sessions = chatService.getActiveSessionsInDateRange(start, end);
        return ResponseEntity.ok(sessions);
    }
    
    // ========== CHAT MESSAGE ENDPOINTS ==========
    
    @PostMapping("/messages")
    @Operation(summary = "Gửi tin nhắn từ user")
    public ResponseEntity<ChatMessageDTO> sendMessage(@Valid @RequestBody ChatMessageRequest request) {
        ChatMessageDTO message = chatService.processUserMessage(request);
        return ResponseEntity.ok(message);
    }
    
    @PostMapping("/messages/bot-response")
    @Operation(summary = "Gửi phản hồi từ bot (admin only)")
    public ResponseEntity<ChatMessageDTO> sendBotResponse(
            @RequestParam Integer sessionId,
            @RequestParam String message,
            @RequestParam(required = false) Integer sanPhamId) {
        ChatMessageDTO botMessage = chatService.sendBotResponse(sessionId, message, sanPhamId);
        return ResponseEntity.ok(botMessage);
    }
    
    @GetMapping("/sessions/{sessionId}/messages")
    @Operation(summary = "Lấy tất cả tin nhắn của session")
    public ResponseEntity<List<ChatMessageDTO>> getMessagesBySession(@PathVariable Integer sessionId) {
        List<ChatMessageDTO> messages = chatService.getMessagesBySession(sessionId);
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/sessions/{sessionId}/messages/paged")
    @Operation(summary = "Lấy tin nhắn của session với phân trang")
    public ResponseEntity<Page<ChatMessageDTO>> getMessagesBySessionPaged(
            @PathVariable Integer sessionId, Pageable pageable) {
        Page<ChatMessageDTO> messages = chatService.getMessagesBySessionPaged(sessionId, pageable);
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/messages/search")
    @Operation(summary = "Tìm kiếm tin nhắn theo nội dung")
    public ResponseEntity<List<ChatMessageDTO>> searchMessages(@RequestParam String keyword) {
        List<ChatMessageDTO> messages = chatService.searchMessages(keyword);
        return ResponseEntity.ok(messages);
    }
    
    @PostMapping("/sessions/{sessionId}/suggest-products")
    @Operation(summary = "Gợi ý sản phẩm cho user")
    public ResponseEntity<List<ChatMessageDTO>> suggestProducts(
            @PathVariable Integer sessionId,
            @RequestParam String userMessage) {
        List<ChatMessageDTO> messages = chatService.suggestProducts(sessionId, userMessage);
        return ResponseEntity.ok(messages);
    }
    
    // ========== ANALYTICS ENDPOINTS ==========
    
    @GetMapping("/sessions/{sessionId}/message-count")
    @Operation(summary = "Đếm số tin nhắn trong session")
    public ResponseEntity<Long> getMessageCount(@PathVariable Integer sessionId) {
        Long count = chatService.getMessageCount(sessionId);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/messages/bot-suggestions")
    @Operation(summary = "Lấy tất cả gợi ý sản phẩm từ bot")
    public ResponseEntity<List<ChatMessageDTO>> getBotSuggestions() {
        List<ChatMessageDTO> suggestions = chatService.getBotSuggestions();
        return ResponseEntity.ok(suggestions);
    }
}