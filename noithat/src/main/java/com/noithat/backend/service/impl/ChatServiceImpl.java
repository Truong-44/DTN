package com.noithat.backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.noithat.backend.dto.ChatMessageDTO;
import com.noithat.backend.dto.ChatSessionDTO;
import com.noithat.backend.dto.request.ChatMessageRequest;
import com.noithat.backend.dto.request.ChatSessionRequest;
import com.noithat.backend.entity.ChatMessage;
import com.noithat.backend.entity.ChatSession;
import com.noithat.backend.entity.SanPham;
import com.noithat.backend.mapper.ChatMapper;
import com.noithat.backend.repository.ChatMessageRepository;
import com.noithat.backend.repository.ChatSessionRepository;
import com.noithat.backend.repository.SanPhamRepository;
import com.noithat.backend.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChatServiceImpl implements ChatService {
    
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final SanPhamRepository sanPhamRepository;
    private final ChatMapper chatMapper;
    
    @Override
    public ChatSessionDTO createSession(ChatSessionRequest request) {
        ChatSession session = ChatSession.builder()
                .taiKhoanId(request.getTaiKhoanId())
                .thoiGianBatDau(LocalDateTime.now())
                .build();
        
        ChatSession savedSession = chatSessionRepository.save(session);
        
        // Gửi tin nhắn chào mừng từ bot
        if (request.getTinNhanDau() != null && !request.getTinNhanDau().trim().isEmpty()) {
            sendUserMessage(savedSession.getId(), request.getTinNhanDau());
        }
        sendBotWelcomeMessage(savedSession.getId());
        
        return chatMapper.toDTO(savedSession);
    }
    
    @Override
    public ChatSessionDTO getSessionById(Integer id) {
        ChatSession session = chatSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chat session với ID: " + id));
        return chatMapper.toDTO(session);
    }
    
    @Override
    public ChatSessionDTO getLatestSessionByUser(Integer taiKhoanId) {
        Optional<ChatSession> session = chatSessionRepository.findFirstByTaiKhoanIdOrderByThoiGianBatDauDesc(taiKhoanId);
        return session.map(chatMapper::toDTO).orElse(null);
    }
    
    @Override
    public List<ChatSessionDTO> getSessionsByUser(Integer taiKhoanId) {
        List<ChatSession> sessions = chatSessionRepository.findByTaiKhoanIdOrderByThoiGianBatDauDesc(taiKhoanId);
        return chatMapper.toDTOList(sessions);
    }
    
    @Override
    public List<ChatSessionDTO> getGuestSessions() {
        List<ChatSession> sessions = chatSessionRepository.findByTaiKhoanIdIsNullOrderByThoiGianBatDauDesc();
        return chatMapper.toDTOList(sessions);
    }
    
    @Override
    public Page<ChatSessionDTO> getAllSessions(Pageable pageable) {
        Page<ChatSession> sessions = chatSessionRepository.findAllByOrderByThoiGianBatDauDesc(pageable);
        return sessions.map(chatMapper::toDTO);
    }
    
    @Override
    public void endSession(Integer sessionId) {
        // Gửi tin nhắn kết thúc từ bot
        sendBotResponse(sessionId, "Cảm ơn bạn đã chat với chúng tôi! Chúc bạn một ngày tốt lành! 😊", null);
    }
    
    @Override
    public ChatMessageDTO sendMessage(ChatMessageRequest request) {
        ChatMessage message = ChatMessage.builder()
                .sessionId(request.getSessionId())
                .nguoiGui(ChatMessage.MessageSender.user)
                .noiDung(request.getNoiDung())
                .sanPhamId(request.getSanPhamId())
                .thoiGian(LocalDateTime.now())
                .build();
        
        ChatMessage savedMessage = chatMessageRepository.save(message);
        return chatMapper.toDTO(savedMessage);
    }
    
    @Override
    public ChatMessageDTO sendBotResponse(Integer sessionId, String message, Integer sanPhamId) {
        ChatMessage botMessage = ChatMessage.builder()
                .sessionId(sessionId)
                .nguoiGui(ChatMessage.MessageSender.bot)
                .noiDung(message)
                .sanPhamId(sanPhamId)
                .thoiGian(LocalDateTime.now())
                .build();
        
        ChatMessage savedMessage = chatMessageRepository.save(botMessage);
        return chatMapper.toDTO(savedMessage);
    }
    
    @Override
    public List<ChatMessageDTO> getMessagesBySession(Integer sessionId) {
        List<ChatMessage> messages = chatMessageRepository.findBySessionIdOrderByThoiGian(sessionId);
        return chatMapper.toMessageDTOList(messages);
    }
    
    @Override
    public Page<ChatMessageDTO> getMessagesBySessionPaged(Integer sessionId, Pageable pageable) {
        Page<ChatMessage> messages = chatMessageRepository.findBySessionIdOrderByThoiGian(sessionId, pageable);
        return messages.map(chatMapper::toDTO);
    }
    
    @Override
    public List<ChatMessageDTO> searchMessages(String keyword) {
        List<ChatMessage> messages = chatMessageRepository.searchByContent(keyword);
        return chatMapper.toMessageDTOList(messages);
    }
    
    @Override
    public ChatMessageDTO processUserMessage(ChatMessageRequest request) {
        // Lưu tin nhắn của user
        ChatMessageDTO userMessage = sendMessage(request);
        
        // Xử lý và phản hồi từ bot
        String botResponse = generateBotResponse(request.getNoiDung());
        Integer suggestedProductId = findSuggestedProduct(request.getNoiDung());
        
        sendBotResponse(request.getSessionId(), botResponse, suggestedProductId);
        
        return userMessage;
    }
    
    @Override
    public List<ChatMessageDTO> suggestProducts(Integer sessionId, String userMessage) {
        List<SanPham> suggestedProducts = findProductsByKeyword(userMessage);
        
        for (SanPham product : suggestedProducts.subList(0, Math.min(3, suggestedProducts.size()))) {
            String suggestion = String.format("Tôi nghĩ bạn sẽ thích sản phẩm này: %s - Giá: %,.0f VNĐ", 
                    product.getTensanpham(), product.getGiamoi());
            sendBotResponse(sessionId, suggestion, product.getId());
        }
        
        return getMessagesBySession(sessionId);
    }
    
    @Override
    public Long getMessageCount(Integer sessionId) {
        return chatMessageRepository.countBySessionId(sessionId);
    }
    
    @Override
    public List<ChatSessionDTO> getActiveSessionsInDateRange(LocalDateTime start, LocalDateTime end) {
        List<ChatSession> sessions = chatSessionRepository.findByDateRange(start, end);
        return chatMapper.toDTOList(sessions);
    }
    
    @Override
    public List<ChatMessageDTO> getBotSuggestions() {
        List<ChatMessage> suggestions = chatMessageRepository.findBySanPhamIdIsNotNullOrderByThoiGianDesc();
        return chatMapper.toMessageDTOList(suggestions);
    }
    
    // Private helper methods
    private void sendUserMessage(Integer sessionId, String content) {
        ChatMessage userMessage = ChatMessage.builder()
                .sessionId(sessionId)
                .nguoiGui(ChatMessage.MessageSender.user)
                .noiDung(content)
                .thoiGian(LocalDateTime.now())
                .build();
        chatMessageRepository.save(userMessage);
    }
    
    private void sendBotWelcomeMessage(Integer sessionId) {
        String welcomeMessage = "Xin chào! Tôi là trợ lý ảo của cửa hàng nội thất. " +
                "Tôi có thể giúp bạn tìm kiếm sản phẩm, tư vấn thiết kế và trả lời các câu hỏi. " +
                "Bạn cần hỗ trợ gì hôm nay? 😊";
        sendBotResponse(sessionId, welcomeMessage, null);
    }
    
    private String generateBotResponse(String userMessage) {
        String message = userMessage.toLowerCase();
        
        if (message.contains("chào") || message.contains("hello") || message.contains("hi")) {
            return "Xin chào! Rất vui được hỗ trợ bạn hôm nay! 😊";
        } else if (message.contains("giá") || message.contains("bao nhiêu")) {
            return "Để biết giá chính xác, tôi sẽ gợi ý một số sản phẩm phù hợp với bạn.";
        } else if (message.contains("sofa") || message.contains("ghế")) {
            return "Chúng tôi có nhiều mẫu sofa và ghế đẹp. Hãy để tôi gợi ý một số sản phẩm:";
        } else if (message.contains("bàn")) {
            return "Bạn đang tìm loại bàn nào? Bàn ăn, bàn làm việc hay bàn trang trí?";
        } else if (message.contains("tủ")) {
            return "Chúng tôi có tủ quần áo, tủ giày, tủ tivi... Bạn cần loại nào?";
        } else if (message.contains("cảm ơn")) {
            return "Không có gì! Tôi luôn sẵn sàng hỗ trợ bạn! 😊";
        } else {
            return "Tôi hiểu bạn đang quan tâm đến nội thất. Hãy để tôi tìm những sản phẩm phù hợp nhất cho bạn!";
        }
    }
    
    private Integer findSuggestedProduct(String userMessage) {
        List<SanPham> products = findProductsByKeyword(userMessage);
        return products.isEmpty() ? null : products.get(0).getId();
    }
    
    private List<SanPham> findProductsByKeyword(String keyword) {
        return sanPhamRepository.findByTensanphamContainingIgnoreCaseAndTrangthaiTrue(keyword);
    }
}