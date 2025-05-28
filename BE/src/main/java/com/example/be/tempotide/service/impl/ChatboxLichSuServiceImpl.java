package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.ChatboxLichSuDTO;
import com.example.be.tempotide.entity.ChatboxLichSu;
import com.example.be.tempotide.entity.KhachHang;
import com.example.be.tempotide.entity.SanPham;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.ChatboxLichSuMapper;
import com.example.be.tempotide.repository.ChatboxLichSuRepository;
import com.example.be.tempotide.repository.KhachHangRepository;
import com.example.be.tempotide.repository.SanPhamRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.ChatboxLichSuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatboxLichSuServiceImpl implements ChatboxLichSuService {
    private final ChatboxLichSuRepository chatboxLichSuRepository;
    private final ChatboxLichSuMapper chatboxLichSuMapper;
    private final KhachHangRepository khachHangRepository;
    private final SanPhamRepository sanPhamRepository;
    private final NhanVienRepository nhanVienRepository;

    @Override
    public List<ChatboxLichSuDTO> getAllChatboxLichSus() {
        return chatboxLichSuRepository.findAll()
                .stream()
                .map(chatboxLichSuMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ChatboxLichSuDTO getChatboxLichSuById(Integer id) {
        ChatboxLichSu chatboxLichSu = chatboxLichSuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChatboxLichSu not found with ID: " + id));
        return chatboxLichSuMapper.toDTO(chatboxLichSu);
    }

    @Override
    @Transactional
    public ChatboxLichSuDTO createChatboxLichSu(ChatboxLichSuDTO chatboxLichSuDTO) {
        ChatboxLichSu chatboxLichSu = chatboxLichSuMapper.toEntity(chatboxLichSuDTO);
        chatboxLichSu.setNgaytao(LocalDateTime.now());

        if (chatboxLichSuDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(chatboxLichSuDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + chatboxLichSuDTO.getMakhachhang()));
            chatboxLichSu.setMakhachhang(khachHang);
        }

        if (chatboxLichSuDTO.getGiosanpham() != null) {
            SanPham sanPham = sanPhamRepository.findById(chatboxLichSuDTO.getGiosanpham())
                    .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + chatboxLichSuDTO.getGiosanpham()));
            chatboxLichSu.setGiosanpham(sanPham);
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        chatboxLichSu.setNguoitao(nguoitao);

        ChatboxLichSu savedChatboxLichSu = chatboxLichSuRepository.save(chatboxLichSu);
        return chatboxLichSuMapper.toDTO(savedChatboxLichSu);
    }

    @Override
    @Transactional
    public ChatboxLichSuDTO updateChatboxLichSu(Integer id, ChatboxLichSuDTO chatboxLichSuDTO) {
        ChatboxLichSu existingChatboxLichSu = chatboxLichSuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChatboxLichSu not found with ID: " + id));

        existingChatboxLichSu.setSodienthoai(chatboxLichSuDTO.getSodienthoai());
        existingChatboxLichSu.setNoidung(chatboxLichSuDTO.getNoidung());
        existingChatboxLichSu.setLoaiCauhoi(chatboxLichSuDTO.getLoaiCauhoi());
        existingChatboxLichSu.setNgaytao(chatboxLichSuDTO.getNgaytao());
        existingChatboxLichSu.setTrangthai(chatboxLichSuDTO.getTrangthai());

        if (chatboxLichSuDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(chatboxLichSuDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + chatboxLichSuDTO.getMakhachhang()));
            existingChatboxLichSu.setMakhachhang(khachHang);
        } else {
            existingChatboxLichSu.setMakhachhang(null);
        }

        if (chatboxLichSuDTO.getGiosanpham() != null) {
            SanPham sanPham = sanPhamRepository.findById(chatboxLichSuDTO.getGiosanpham())
                    .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + chatboxLichSuDTO.getGiosanpham()));
            existingChatboxLichSu.setGiosanpham(sanPham);
        } else {
            existingChatboxLichSu.setGiosanpham(null);
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        existingChatboxLichSu.setNguoitao(nguoitao);

        ChatboxLichSu updatedChatboxLichSu = chatboxLichSuRepository.save(existingChatboxLichSu);
        return chatboxLichSuMapper.toDTO(updatedChatboxLichSu);
    }

    @Override
    @Transactional
    public void deleteChatboxLichSu(Integer id) {
        ChatboxLichSu chatboxLichSu = chatboxLichSuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChatboxLichSu not found with ID: " + id));
        chatboxLichSu.setTrangthai(false);
        chatboxLichSuRepository.save(chatboxLichSu);
    }

    @Override
    public List<ChatboxLichSuDTO> getChatboxLichSuByKhachHangId(Integer makhachhang) {
        return chatboxLichSuRepository.findByMakhachhang_Makhachhang(makhachhang)
                .stream()
                .map(chatboxLichSuMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatboxLichSuDTO> getChatboxLichSuBySodienthoai(String sodienthoai) {
        return chatboxLichSuRepository.findBySodienthoai(sodienthoai)
                .stream()
                .map(chatboxLichSuMapper::toDTO)
                .collect(Collectors.toList());
    }
}