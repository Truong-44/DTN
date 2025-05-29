package com.example.tempotide.service.impl;

import com.example.tempotide.dto.ChatBoxLichSuDTO;
import com.example.tempotide.entity.ChatBoxLichSu;
import com.example.tempotide.entity.KhachHang;
import com.example.tempotide.entity.NhanVien;
import com.example.tempotide.entity.SanPham;
import com.example.tempotide.mapper.ChatBoxLichSuMapper;
import com.example.tempotide.repository.ChatBoxLichSuRepository;
import com.example.tempotide.repository.KhachHangRepository;
import com.example.tempotide.repository.NhanVienRepository;
import com.example.tempotide.repository.SanPhamRepository;
import com.example.tempotide.service.ChatBoxLichSuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatBoxLichSuServiceImpl implements ChatBoxLichSuService {
    private final ChatBoxLichSuRepository chatBoxLichSuRepository;
    private final KhachHangRepository khachHangRepository;
    private final SanPhamRepository sanPhamRepository;
    private final NhanVienRepository nhanVienRepository;
    private final ChatBoxLichSuMapper chatBoxLichSuMapper;

    @Override
    public List<ChatBoxLichSuDTO> getAllChatBoxLichSus() {
        return chatBoxLichSuRepository.findAll()
                .stream()
                .map(chatBoxLichSuMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ChatBoxLichSuDTO getChatBoxLichSuById(Integer id) {
        ChatBoxLichSu chatBoxLichSu = chatBoxLichSuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChatBoxLichSu not found with ID: " + id));
        return chatBoxLichSuMapper.toDTO(chatBoxLichSu);
    }

    @Override
    @Transactional
    public ChatBoxLichSuDTO createChatBoxLichSu(ChatBoxLichSuDTO chatBoxLichSuDTO) {
        ChatBoxLichSu chatBoxLichSu = chatBoxLichSuMapper.toEntity(chatBoxLichSuDTO);

        if (chatBoxLichSuDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(chatBoxLichSuDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + chatBoxLichSuDTO.getMakhachhang()));
            chatBoxLichSu.setMakhachhang(khachHang);
        }

        if (chatBoxLichSuDTO.getGiosanpham() != null) {
            SanPham sanPham = sanPhamRepository.findById(chatBoxLichSuDTO.getGiosanpham())
                    .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + chatBoxLichSuDTO.getGiosanpham()));
            chatBoxLichSu.setGiosanpham(sanPham);
        }

        if (chatBoxLichSuDTO.getNguoitao() != null) {
            NhanVien nguoitao = nhanVienRepository.findById(chatBoxLichSuDTO.getNguoitao())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + chatBoxLichSuDTO.getNguoitao()));
            chatBoxLichSu.setNguoitao(nguoitao);
        }

        chatBoxLichSu.setNgaytao(LocalDateTime.now());
        ChatBoxLichSu savedChatBoxLichSu = chatBoxLichSuRepository.save(chatBoxLichSu);
        return chatBoxLichSuMapper.toDTO(savedChatBoxLichSu);
    }

    @Override
    @Transactional
    public ChatBoxLichSuDTO updateChatBoxLichSu(Integer id, ChatBoxLichSuDTO chatBoxLichSuDTO) {
        ChatBoxLichSu existingChatBoxLichSu = chatBoxLichSuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChatBoxLichSu not found with ID: " + id));

        existingChatBoxLichSu.setSodienthoai(chatBoxLichSuDTO.getSodienthoai());
        existingChatBoxLichSu.setNoidung(chatBoxLichSuDTO.getNoidung());
        existingChatBoxLichSu.setLoaiCauhoi(chatBoxLichSuDTO.getLoaiCauhoi());
        existingChatBoxLichSu.setTrangthai(chatBoxLichSuDTO.getTrangthai());

        if (chatBoxLichSuDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(chatBoxLichSuDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + chatBoxLichSuDTO.getMakhachhang()));
            existingChatBoxLichSu.setMakhachhang(khachHang);
        } else {
            existingChatBoxLichSu.setMakhachhang(null);
        }

        if (chatBoxLichSuDTO.getGiosanpham() != null) {
            SanPham sanPham = sanPhamRepository.findById(chatBoxLichSuDTO.getGiosanpham())
                    .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + chatBoxLichSuDTO.getGiosanpham()));
            existingChatBoxLichSu.setGiosanpham(sanPham);
        } else {
            existingChatBoxLichSu.setGiosanpham(null);
        }

        ChatBoxLichSu updatedChatBoxLichSu = chatBoxLichSuRepository.save(existingChatBoxLichSu);
        return chatBoxLichSuMapper.toDTO(updatedChatBoxLichSu);
    }

    @Override
    @Transactional
    public void deleteChatBoxLichSu(Integer id) {
        ChatBoxLichSu chatBoxLichSu = chatBoxLichSuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChatBoxLichSu not found with ID: " + id));
        chatBoxLichSu.setTrangthai(false);
        chatBoxLichSuRepository.save(chatBoxLichSu);
    }
}