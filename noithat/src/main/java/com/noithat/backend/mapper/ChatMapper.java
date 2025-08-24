// filepath: src/main/java/com/noithat/backend/mapper/ChatMapper.java
package com.noithat.backend.mapper;

import com.noithat.backend.dto.ChatSessionDTO;
import com.noithat.backend.dto.ChatMessageDTO;
import com.noithat.backend.entity.ChatSession;
import com.noithat.backend.entity.ChatMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatMapper {

    public ChatSessionDTO toDTO(ChatSession entity) {
        if (entity == null) return null;

        ChatSessionDTO dto = new ChatSessionDTO();
        dto.setId(entity.getId());
        dto.setTaiKhoanId(entity.getTaiKhoanId());
        dto.setThoiGianBatDau(entity.getThoiGianBatDau());

        // Map tên tài khoản
        if (entity.getTaiKhoan() != null) {
            dto.setTenTaiKhoan(entity.getTaiKhoan().getTendangnhap());
        }

        // Map messages
        if (entity.getMessages() != null) {
            dto.setMessages(toMessageDTOList(entity.getMessages()));
            dto.setSoLuongTinNhan(entity.getMessages().size());
        } else {
            dto.setSoLuongTinNhan(0);
        }

        return dto;
    }

    public ChatMessageDTO toDTO(ChatMessage entity) {
        if (entity == null) return null;

        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setId(entity.getId());
        dto.setSessionId(entity.getSessionId());
        dto.setNguoiGui(entity.getNguoiGui());
        dto.setNoiDung(entity.getNoiDung());
        dto.setThoiGian(entity.getThoiGian());
        dto.setSanPhamId(entity.getSanPhamId());

        // Map sản phẩm info
        if (entity.getSanPham() != null) {
            dto.setTenSanPham(entity.getSanPham().getTensanpham());
            // Lấy hình ảnh đầu tiên
            if (entity.getSanPham().getChiTietSanPhams() != null &&
                    !entity.getSanPham().getChiTietSanPhams().isEmpty()) {
                dto.setHinhAnhSanPham(entity.getSanPham().getChiTietSanPhams().get(0).getHinhchinh());
            }
        }

        return dto;
    }

    public List<ChatSessionDTO> toDTOList(List<ChatSession> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ChatMessageDTO> toMessageDTOList(List<ChatMessage> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}