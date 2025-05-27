package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.phiendangnhapdto;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.entity.phiendangnhap;
import com.example.be.tempotide.repository.nhanvienrepository;
import com.example.be.tempotide.repository.phiendangnhaprepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class phiendangnhapserviceImpl implements com.example.be.tempotide.service.phiendangnhapservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.phiendangnhapserviceImpl.class);

    private final phiendangnhaprepository phienDangNhapRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public phiendangnhapserviceImpl(phiendangnhaprepository phienDangNhapRepository,
                                    nhanvienrepository nhanVienRepository) {
        this.phienDangNhapRepository = phienDangNhapRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public phiendangnhapdto createPhienDangNhap(phiendangnhapdto phienDangNhapDto) {
        logger.info("Creating new PhienDangNhap for MaNhanVien: {}", phienDangNhapDto.getMaNhanVien());
        phiendangnhap phienDangNhap = mapToEntity(phienDangNhapDto);

        nhanvien nhanVien = nhanVienRepository.findById(phienDangNhapDto.getMaNhanVien())
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", phienDangNhapDto.getMaNhanVien());
                    return new RuntimeException("NhanVien not found");
                });
        phienDangNhap.setNhanVien(nhanVien);

        phienDangNhap.setNgayDangNhap(LocalDateTime.now());
        phienDangNhap = phienDangNhapRepository.save(phienDangNhap);
        logger.info("PhienDangNhap created with id: {}", phienDangNhap.getMaPhien());
        return mapToDto(phienDangNhap);
    }

    @Override
    public phiendangnhapdto getPhienDangNhapById(Integer id) {
        logger.info("Fetching PhienDangNhap with id: {}", id);
        phiendangnhap phienDangNhap = phienDangNhapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhienDangNhap not found with id: {}", id);
                    return new RuntimeException("PhienDangNhap not found with id: " + id);
                });
        return mapToDto(phienDangNhap);
    }

    @Override
    public List<phiendangnhapdto> getAllPhienDangNhap() {
        logger.info("Fetching all PhienDangNhap");
        return phienDangNhapRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public phiendangnhapdto updatePhienDangNhap(Integer id, phiendangnhapdto phienDangNhapDto) {
        logger.info("Updating PhienDangNhap with id: {}", id);
        phiendangnhap phienDangNhap = phienDangNhapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhienDangNhap not found with id: {}", id);
                    return new RuntimeException("PhienDangNhap not found with id: " + id);
                });

        phienDangNhap.setMaNhanVien(phienDangNhapDto.getMaNhanVien());
        phienDangNhap.setToken(phienDangNhapDto.getToken());
        phienDangNhap.setNgayHetHan(phienDangNhapDto.getNgayHetHan());
        phienDangNhap.setDiaChiIP(phienDangNhapDto.getDiaChiIP());
        phienDangNhap.setTrangThai(phienDangNhapDto.getTrangThai());

        nhanvien nhanVien = nhanVienRepository.findById(phienDangNhapDto.getMaNhanVien())
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", phienDangNhapDto.getMaNhanVien());
                    return new RuntimeException("NhanVien not found");
                });
        phienDangNhap.setNhanVien(nhanVien);

        phienDangNhap = phienDangNhapRepository.save(phienDangNhap);
        logger.info("PhienDangNhap updated with id: {}", phienDangNhap.getMaPhien());
        return mapToDto(phienDangNhap);
    }

    @Override
    public void deletePhienDangNhap(Integer id) {
        logger.info("Deleting PhienDangNhap with id: {}", id);
        phiendangnhap phienDangNhap = phienDangNhapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhienDangNhap not found with id: {}", id);
                    return new RuntimeException("PhienDangNhap not found with id: " + id);
                });
        phienDangNhapRepository.delete(phienDangNhap);
        logger.info("PhienDangNhap deleted with id: {}", id);
    }

    private phiendangnhapdto mapToDto(phiendangnhap phienDangNhap) {
        return phiendangnhapdto.builder()
                .maPhien(phienDangNhap.getMaPhien())
                .maNhanVien(phienDangNhap.getMaNhanVien())
                .token(phienDangNhap.getToken())
                .ngayDangNhap(phienDangNhap.getNgayDangNhap())
                .ngayHetHan(phienDangNhap.getNgayHetHan())
                .diaChiIP(phienDangNhap.getDiaChiIP())
                .trangThai(phienDangNhap.getTrangThai())
                .build();
    }

    private phiendangnhap mapToEntity(phiendangnhapdto phienDangNhapDto) {
        return phiendangnhap.builder()
                .maPhien(phienDangNhapDto.getMaPhien())
                .maNhanVien(phienDangNhapDto.getMaNhanVien())
                .token(phienDangNhapDto.getToken())
                .ngayDangNhap(phienDangNhapDto.getNgayDangNhap() != null ? phienDangNhapDto.getNgayDangNhap() : LocalDateTime.now())
                .ngayHetHan(phienDangNhapDto.getNgayHetHan())
                .diaChiIP(phienDangNhapDto.getDiaChiIP())
                .trangThai(phienDangNhapDto.getTrangThai())
                .build();
    }
}