package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.DonHangDTO;
import com.example.be.tempotide.entity.DonHang;
import com.example.be.tempotide.entity.KhachHang;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.DonHangMapper;
import com.example.be.tempotide.repository.DonHangRepository;
import com.example.be.tempotide.repository.KhachHangRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonHangServiceImpl implements DonHangService {
    private final DonHangRepository donHangRepository;
    private final DonHangMapper donHangMapper;
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;

    @Override
    public List<DonHangDTO> getAllDonHangs() {
        return donHangRepository.findAll()
                .stream()
                .map(donHangMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DonHangDTO getDonHangById(Integer id) {
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DonHang not found with ID: " + id));
        return donHangMapper.toDTO(donHang);
    }

    @Override
    @Transactional
    public DonHangDTO createDonHang(DonHangDTO donHangDTO) {
        DonHang donHang = donHangMapper.toEntity(donHangDTO);
        donHang.setNgaytao(LocalDateTime.now());

        KhachHang khachHang = khachHangRepository.findById(donHangDTO.getMakhachhang())
                .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + donHangDTO.getMakhachhang()));
        donHang.setMakhachhang(khachHang);

        if (donHangDTO.getManhanvienxuly() != null) {
            NhanVien nhanVien = nhanVienRepository.findById(donHangDTO.getManhanvienxuly())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + donHangDTO.getManhanvienxuly()));
            donHang.setManhanvienxuly(nhanVien);
        } else {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            NhanVien nhanVien = nhanVienRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("User not found: " + username));
            donHang.setManhanvienxuly(nhanVien);
        }

        DonHang savedDonHang = donHangRepository.save(donHang);
        return donHangMapper.toDTO(savedDonHang);
    }

    @Override
    @Transactional
    public DonHangDTO updateDonHang(Integer id, DonHangDTO donHangDTO) {
        DonHang existingDonHang = donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DonHang not found with ID: " + id));

        existingDonHang.setTongtien(donHangDTO.getTongtien());
        existingDonHang.setPhuongthucvanchuyen(donHangDTO.getPhuongthucvanchuyen());
        existingDonHang.setSotien(donHangDTO.getSotien());
        existingDonHang.setTrangthaithanhtoan(donHangDTO.getTrangthaithanhtoan());
        existingDonHang.setGiamgia(donHangDTO.getGiamgia());
        existingDonHang.setTrangthaidonhang(donHangDTO.getTrangthaidonhang());
        existingDonHang.setTenkhachhang(donHangDTO.getTenkhachhang());
        existingDonHang.setSodienthoai(donHangDTO.getSodienthoai());
        existingDonHang.setEmail(donHangDTO.getEmail());
        existingDonHang.setNgaythanhtoan(donHangDTO.getNgaythanhtoan());
        existingDonHang.setGhichu(donHangDTO.getGhichu());
        existingDonHang.setTrangthaiHoadon(donHangDTO.getTrangthaiHoadon()); // Đảm bảo kiểu Boolean
        existingDonHang.setLadonhangvanglai(donHangDTO.getLadonhangvanglai()); // Đảm bảo kiểu Boolean
        existingDonHang.setNgaycapnhat(LocalDateTime.now());

        if (donHangDTO.getTrangthai() != null) {
            existingDonHang.setTrangthai(donHangDTO.getTrangthai());
        }

        if (donHangDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(donHangDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + donHangDTO.getMakhachhang()));
            existingDonHang.setMakhachhang(khachHang);
        }

        if (donHangDTO.getManhanvienxuly() != null) {
            NhanVien nhanVien = nhanVienRepository.findById(donHangDTO.getManhanvienxuly())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + donHangDTO.getManhanvienxuly()));
            existingDonHang.setManhanvienxuly(nhanVien);
        }

        DonHang updatedDonHang = donHangRepository.save(existingDonHang);
        return donHangMapper.toDTO(updatedDonHang);
    }

    @Override
    @Transactional
    public void deleteDonHang(Integer id) {
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DonHang not found with ID: " + id));
        donHang.setTrangthai(false);
        donHangRepository.save(donHang);
    }

    @Override
    public List<DonHangDTO> getDonHangByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<DonHang> donHangs = donHangRepository.findByNgaytaoBetween(startDate, endDate);
        return donHangs.stream()
                .map(donHangMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonHangDTO> getDonHangByKhachHangId(Integer makhachhang) {
        List<DonHang> donHangs = donHangRepository.findByMakhachhangMakhachhang(makhachhang);
        return donHangs.stream()
                .map(donHangMapper::toDTO)
                .collect(Collectors.toList());
    }
}