package com.example.tempotide.service.impl;

import com.example.tempotide.dto.GioHangDTO;
import com.example.tempotide.entity.GioHang;
import com.example.tempotide.entity.KhachHang;
import com.example.tempotide.entity.NhanVien;
import com.example.tempotide.mapper.GioHangMapper;
import com.example.tempotide.repository.GioHangRepository;
import com.example.tempotide.repository.KhachHangRepository;
import com.example.tempotide.repository.NhanVienRepository;
import com.example.tempotide.service.GioHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GioHangServiceImpl implements GioHangService {
    private final GioHangRepository gioHangRepository;
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;
    private final GioHangMapper gioHangMapper;

    @Override
    public List<GioHangDTO> getAllActiveCarts() {
        return gioHangRepository.findByTrangthaiTrue()
                .stream()
                .map(gioHangMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GioHangDTO getUserCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        KhachHang khachHang = khachHangRepository.findByEmail(username)
                .orElse(null);

        GioHang gioHang;
        if (khachHang != null) {
            gioHang = gioHangRepository.findByKhachhangMakhachhangAndTrangthaiTrue(khachHang.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("Cart not found for user: " + username));
        } else {
            throw new RuntimeException("User not found or not authenticated");
        }
        return gioHangMapper.toDTO(gioHang);
    }

    @Override
    @Transactional
    public GioHangDTO createCart(GioHangDTO gioHangDTO) {
        // Kiểm tra makhachhang hoặc sodienthoai
        if (gioHangDTO.getMakhachhang() == null && gioHangDTO.getSodienthoai() == null) {
            throw new RuntimeException("Either customer ID or phone number must be provided");
        }

        // Kiểm tra giỏ hàng đã tồn tại
        if (gioHangDTO.getMakhachhang() != null) {
            if (gioHangRepository.findByKhachhangMakhachhangAndTrangthaiTrue(gioHangDTO.getMakhachhang()).isPresent()) {
                throw new RuntimeException("Active cart already exists for customer ID: " + gioHangDTO.getMakhachhang());
            }
            KhachHang khachHang = khachHangRepository.findById(gioHangDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + gioHangDTO.getMakhachhang()));
        } else if (gioHangDTO.getSodienthoai() != null) {
            if (gioHangRepository.findBySodienthoaiAndTrangthaiTrue(gioHangDTO.getSodienthoai()).isPresent()) {
                throw new RuntimeException("Active cart already exists for phone number: " + gioHangDTO.getSodienthoai());
            }
        }

        GioHang gioHang = gioHangMapper.toEntity(gioHangDTO);

        if (gioHangDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(gioHangDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + gioHangDTO.getMakhachhang()));
            gioHang.setKhachhang(khachHang);
        }

        // Gán nguoitao
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        gioHang.setNguoitao(nguoitao);

        gioHang = gioHangRepository.save(gioHang);
        return gioHangMapper.toDTO(gioHang);
    }

    @Override
    @Transactional
    public GioHangDTO updateCart(Integer id, GioHangDTO gioHangDTO) {
        GioHang gioHang = gioHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + id));

        // Kiểm tra quyền sở hữu hoặc ADMIN
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        KhachHang currentUser = khachHangRepository.findByEmail(username).orElse(null);
        if (currentUser == null || (gioHang.getKhachhang() != null && !gioHang.getKhachhang().getMakhachhang().equals(currentUser.getMakhachhang()))) {
            throw new RuntimeException("Unauthorized to update this cart");
        }

        // Cập nhật sodienthoai hoặc makhachhang
        if (gioHangDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(gioHangDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + gioHangDTO.getMakhachhang()));
            gioHang.setKhachhang(khachHang);
            gioHang.setSodienthoai(null);
        } else if (gioHangDTO.getSodienthoai() != null) {
            gioHang.setKhachhang(null);
            gioHang.setSodienthoai(gioHangDTO.getSodienthoai());
        }

        gioHang.setTrangthai(gioHangDTO.getTrangthai());
        gioHang.setNgaycapnhat(LocalDateTime.now());

        gioHang = gioHangRepository.save(gioHang);
        return gioHangMapper.toDTO(gioHang);
    }

    @Override
    @Transactional
    public void deleteCart(Integer id) {
        GioHang gioHang = gioHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + id));

        // Kiểm tra quyền sở hữu hoặc ADMIN
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        KhachHang currentUser = khachHangRepository.findByEmail(username).orElse(null);
        if (currentUser == null || (gioHang.getKhachhang() != null && !gioHang.getKhachhang().getMakhachhang().equals(currentUser.getMakhachhang()))) {
            throw new RuntimeException("Unauthorized to delete this cart");
        }

        gioHang.setTrangthai(false);
        gioHangRepository.save(gioHang);
    }
}