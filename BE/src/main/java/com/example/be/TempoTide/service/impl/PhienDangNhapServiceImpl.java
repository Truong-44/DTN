package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.PhienDangNhapDto;
import com.example.be.TempoTide.entity.NhanVien;
import com.example.be.TempoTide.entity.PhienDangNhap;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.repository.PhienDangNhapRepository;
import com.example.be.TempoTide.service.PhienDangNhapService;
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
public class PhienDangNhapServiceImpl implements PhienDangNhapService {

    private static final Logger logger = LoggerFactory.getLogger(PhienDangNhapServiceImpl.class);

    private final PhienDangNhapRepository phienDangNhapRepository;
    private final NhanVienRepository nhanVienRepository;

    @Autowired
    public PhienDangNhapServiceImpl(PhienDangNhapRepository phienDangNhapRepository,
                                    NhanVienRepository nhanVienRepository) {
        this.phienDangNhapRepository = phienDangNhapRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public PhienDangNhapDto createPhienDangNhap(PhienDangNhapDto phienDangNhapDto) {
        logger.info("Creating new PhienDangNhap for MaNhanVien: {}", phienDangNhapDto.getMaNhanVien());
        PhienDangNhap phienDangNhap = mapToEntity(phienDangNhapDto);

        NhanVien nhanVien = nhanVienRepository.findById(phienDangNhapDto.getMaNhanVien())
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
    public PhienDangNhapDto getPhienDangNhapById(Integer id) {
        logger.info("Fetching PhienDangNhap with id: {}", id);
        PhienDangNhap phienDangNhap = phienDangNhapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhienDangNhap not found with id: {}", id);
                    return new RuntimeException("PhienDangNhap not found with id: " + id);
                });
        return mapToDto(phienDangNhap);
    }

    @Override
    public List<PhienDangNhapDto> getAllPhienDangNhap() {
        logger.info("Fetching all PhienDangNhap");
        return phienDangNhapRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PhienDangNhapDto updatePhienDangNhap(Integer id, PhienDangNhapDto phienDangNhapDto) {
        logger.info("Updating PhienDangNhap with id: {}", id);
        PhienDangNhap phienDangNhap = phienDangNhapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhienDangNhap not found with id: {}", id);
                    return new RuntimeException("PhienDangNhap not found with id: " + id);
                });

        phienDangNhap.setMaNhanVien(phienDangNhapDto.getMaNhanVien());
        phienDangNhap.setToken(phienDangNhapDto.getToken());
        phienDangNhap.setNgayHetHan(phienDangNhapDto.getNgayHetHan());
        phienDangNhap.setDiaChiIP(phienDangNhapDto.getDiaChiIP());
        phienDangNhap.setTrangThai(phienDangNhapDto.getTrangThai());

        NhanVien nhanVien = nhanVienRepository.findById(phienDangNhapDto.getMaNhanVien())
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
        PhienDangNhap phienDangNhap = phienDangNhapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhienDangNhap not found with id: {}", id);
                    return new RuntimeException("PhienDangNhap not found with id: " + id);
                });
        phienDangNhapRepository.delete(phienDangNhap);
        logger.info("PhienDangNhap deleted with id: {}", id);
    }

    private PhienDangNhapDto mapToDto(PhienDangNhap phienDangNhap) {
        return PhienDangNhapDto.builder()
                .maPhien(phienDangNhap.getMaPhien())
                .maNhanVien(phienDangNhap.getMaNhanVien())
                .token(phienDangNhap.getToken())
                .ngayDangNhap(phienDangNhap.getNgayDangNhap())
                .ngayHetHan(phienDangNhap.getNgayHetHan())
                .diaChiIP(phienDangNhap.getDiaChiIP())
                .trangThai(phienDangNhap.getTrangThai())
                .build();
    }

    private PhienDangNhap mapToEntity(PhienDangNhapDto phienDangNhapDto) {
        return PhienDangNhap.builder()
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