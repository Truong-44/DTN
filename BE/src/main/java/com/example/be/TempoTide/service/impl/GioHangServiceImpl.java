package com.example.be.TempoTide.service.impl;


import com.example.be.TempoTide.dto.GioHangDto;
import com.example.be.TempoTide.entity.GioHang;
import com.example.be.TempoTide.repository.GioHangRepository;
import com.example.be.TempoTide.repository.KhachHangRepository;
import com.example.be.TempoTide.service.GioHangService;
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
public class GioHangServiceImpl implements GioHangService {

    private static final Logger logger = LoggerFactory.getLogger(GioHangServiceImpl.class);

    private final GioHangRepository gioHangRepository;
    private final KhachHangRepository khachHangRepository;

    @Autowired
    public GioHangServiceImpl(GioHangRepository gioHangRepository, KhachHangRepository khachHangRepository) {
        this.gioHangRepository = gioHangRepository;
        this.khachHangRepository = khachHangRepository;
    }

    @Override
    public GioHangDto createGioHang(GioHangDto gioHangDto) {
        logger.info("Creating new GioHang for customer: {}", gioHangDto.getMaKhachHang());
        GioHang gioHang = mapToEntity(gioHangDto);
        gioHang.setKhachHang(khachHangRepository.findById(gioHangDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("KhachHang not found with id: {}", gioHangDto.getMaKhachHang());
                    return new RuntimeException("KhachHang not found");
                }));
        gioHang = gioHangRepository.save(gioHang);
        logger.info("GioHang created with id: {}", gioHang.getMaGioHang());
        return mapToDto(gioHang);
    }

    @Override
    public GioHangDto getGioHangById(Integer id) {
        logger.info("Fetching GioHang with id: {}", id);
        GioHang gioHang = gioHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("GioHang not found with id: {}", id);
                    return new RuntimeException("GioHang not found with id: " + id);
                });
        return mapToDto(gioHang);
    }

    @Override
    public List<GioHangDto> getAllGioHang() {
        logger.info("Fetching all GioHang");
        return gioHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GioHangDto updateGioHang(Integer id, GioHangDto gioHangDto) {
        logger.info("Updating GioHang with id: {}", id);
        GioHang gioHang = gioHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("GioHang not found with id: {}", id);
                    return new RuntimeException("GioHang not found with id: " + id);
                });

        gioHang.setKhachHang(khachHangRepository.findById(gioHangDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("KhachHang not found with id: {}", gioHangDto.getMaKhachHang());
                    return new RuntimeException("KhachHang not found");
                }));
        gioHang.setTrangThai(gioHangDto.getTrangThai());
        gioHang.setNgayCapNhat(gioHangDto.getNgayCapNhat() != null ? gioHangDto.getNgayCapNhat() : LocalDateTime.now());

        gioHang = gioHangRepository.save(gioHang);
        logger.info("GioHang updated with id: {}", gioHang.getMaGioHang());
        return mapToDto(gioHang);
    }

    @Override
    public void deleteGioHang(Integer id) {
        logger.info("Deleting GioHang with id: {}", id);
        GioHang gioHang = gioHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("GioHang not found with id: {}", id);
                    return new RuntimeException("GioHang not found with id: " + id);
                });
        gioHangRepository.delete(gioHang);
        logger.info("GioHang deleted with id: {}", id);
    }

    private GioHangDto mapToDto(GioHang gioHang) {
        return GioHangDto.builder()
                .maGioHang(gioHang.getMaGioHang())
                .maKhachHang(gioHang.getKhachHang().getMaKhachHang())
                .ngayTao(gioHang.getNgayTao())
                .ngayCapNhat(gioHang.getNgayCapNhat())
                .trangThai(gioHang.getTrangThai())
                .build();
    }

    private GioHang mapToEntity(GioHangDto gioHangDto) {
        return GioHang.builder()
                .maGioHang(gioHangDto.getMaGioHang())
                .ngayTao(gioHangDto.getNgayTao() != null ? gioHangDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(gioHangDto.getNgayCapNhat() != null ? gioHangDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(gioHangDto.getTrangThai() != null ? gioHangDto.getTrangThai() : true)
                .build();
    }
}