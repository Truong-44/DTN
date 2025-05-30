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
        GioHang gioHang = gioHangMapper.toEntity(gioHangDTO);

        if (gioHangDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(gioHangDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + gioHangDTO.getMakhachhang()));
            gioHang.setMakhachhang(khachHang);
        }

        if (gioHangDTO.getNguoitao() != null) {
            NhanVien nguoitao = nhanVienRepository.findById(gioHangDTO.getNguoitao())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + gioHangDTO.getNguoitao()));
            gioHang.setNguoitao(nguoitao);
        }

        gioHang.setNgaytao(LocalDateTime.now());
        gioHang.setNgaycapnhat(LocalDateTime.now());
        GioHang savedGioHang = gioHangRepository.save(gioHang);
        return gioHangMapper.toDTO(savedGioHang);
    }

    @Override
    @Transactional
    public GioHangDTO updateGioHang(Integer id, GioHangDTO gioHangDTO) {
        GioHang existingGioHang = gioHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GioHang not found with ID: " + id));

        existingGioHang.setSodienthoai(gioHangDTO.getSodienthoai());
        existingGioHang.setTrangthai(gioHangDTO.getTrangthai());
        existingGioHang.setNgaycapnhat(LocalDateTime.now());

        if (gioHangDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(gioHangDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + gioHangDTO.getMakhachhang()));
            existingGioHang.setMakhachhang(khachHang);
        } else {
            existingGioHang.setMakhachhang(null);
        }

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
}