package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.GioHangDTO;
import com.example.be.tempotide.entity.GioHang;
import com.example.be.tempotide.entity.KhachHang;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.GioHangMapper;
import com.example.be.tempotide.repository.GioHangRepository;
import com.example.be.tempotide.repository.KhachHangRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.GioHangService;
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
    private final GioHangMapper gioHangMapper;
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;

    @Override
    public List<GioHangDTO> getAllGioHangs() {
        return gioHangRepository.findAll()
                .stream()
                .map(gioHangMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GioHangDTO getGioHangById(Integer id) {
        GioHang gioHang = gioHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GioHang not found with ID: " + id));
        return gioHangMapper.toDTO(gioHang);
    }

    @Override
    @Transactional
    public GioHangDTO createGioHang(GioHangDTO gioHangDTO) {
        if (gioHangDTO.getMakhachhang() == null && gioHangDTO.getSodienthoai() == null) {
            throw new IllegalArgumentException("Phải cung cấp ít nhất makhachhang hoặc sodienthoai");
        }

        if (gioHangDTO.getMakhachhang() != null && gioHangRepository.findByMakhachhang_Makhachhang(gioHangDTO.getMakhachhang()).isPresent()) {
            throw new RuntimeException("Giỏ hàng đã tồn tại cho khách hàng với ID: " + gioHangDTO.getMakhachhang());
        }
        if (gioHangDTO.getSodienthoai() != null && gioHangRepository.findBySodienthoai(gioHangDTO.getSodienthoai()).isPresent()) {
            throw new RuntimeException("Giỏ hàng đã tồn tại cho số điện thoại: " + gioHangDTO.getSodienthoai());
        }

        GioHang gioHang = gioHangMapper.toEntity(gioHangDTO);
        gioHang.setNgaytao(LocalDateTime.now());
        gioHang.setNgaycapnhat(LocalDateTime.now());

        if (gioHangDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(gioHangDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + gioHangDTO.getMakhachhang()));
            gioHang.setMakhachhang(khachHang);
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        gioHang.setNguoitao(nguoitao);

        GioHang savedGioHang = gioHangRepository.save(gioHang);
        return gioHangMapper.toDTO(savedGioHang);
    }

    @Override
    @Transactional
    public GioHangDTO updateGioHang(Integer id, GioHangDTO gioHangDTO) {
        GioHang existingGioHang = gioHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GioHang not found with ID: " + id));

        if (gioHangDTO.getMakhachhang() != null && gioHangDTO.getMakhachhang() != existingGioHang.getMakhachhang().getMakhachhang() &&
                gioHangRepository.findByMakhachhang_Makhachhang(gioHangDTO.getMakhachhang()).isPresent()) {
            throw new RuntimeException("Giỏ hàng đã tồn tại cho khách hàng với ID: " + gioHangDTO.getMakhachhang());
        }
        if (gioHangDTO.getSodienthoai() != null && !gioHangDTO.getSodienthoai().equals(existingGioHang.getSodienthoai()) &&
                gioHangRepository.findBySodienthoai(gioHangDTO.getSodienthoai()).isPresent()) {
            throw new RuntimeException("Giỏ hàng đã tồn tại cho số điện thoại: " + gioHangDTO.getSodienthoai());
        }

        existingGioHang.setSodienthoai(gioHangDTO.getSodienthoai());
        existingGioHang.setNgaytao(gioHangDTO.getNgaytao());
        existingGioHang.setNgaycapnhat(LocalDateTime.now());
        existingGioHang.setTrangthai(gioHangDTO.getTrangthai());

        if (gioHangDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(gioHangDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + gioHangDTO.getMakhachhang()));
            existingGioHang.setMakhachhang(khachHang);
        } else {
            existingGioHang.setMakhachhang(null);
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        existingGioHang.setNguoitao(nguoitao);

        GioHang updatedGioHang = gioHangRepository.save(existingGioHang);
        return gioHangMapper.toDTO(updatedGioHang);
    }

    @Override
    @Transactional
    public void deleteGioHang(Integer id) {
        GioHang gioHang = gioHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GioHang not found with ID: " + id));
        gioHang.setTrangthai(false);
        gioHangRepository.save(gioHang);
    }

    @Override
    public GioHangDTO getGioHangByKhachHangId(Integer makhachhang) {
        GioHang gioHang = gioHangRepository.findByMakhachhang_Makhachhang(makhachhang)
                .orElseThrow(() -> new RuntimeException("GioHang not found for KhachHang ID: " + makhachhang));
        return gioHangMapper.toDTO(gioHang);
    }

    @Override
    public GioHangDTO getGioHangBySodienthoai(String sodienthoai) {
        GioHang gioHang = gioHangRepository.findBySodienthoai(sodienthoai)
                .orElseThrow(() -> new RuntimeException("GioHang not found for sodienthoai: " + sodienthoai));
        return gioHangMapper.toDTO(gioHang);
    }
}