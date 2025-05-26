package com.example.be.tempotide.service.impl;


import com.example.be.tempotide.dto.giohangdto;
import com.example.be.tempotide.entity.giohang;
import com.example.be.tempotide.repository.giohangrepository;
import com.example.be.tempotide.repository.khachhangrepository;
import com.example.be.tempotide.service.GioHangService;
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

    private final giohangrepository gioHangRepository;
    private final khachhangrepository khachHangRepository;

    @Autowired
    public GioHangServiceImpl(giohangrepository gioHangRepository, khachhangrepository khachHangRepository) {
        this.gioHangRepository = gioHangRepository;
        this.khachHangRepository = khachHangRepository;
    }

    @Override
    public giohangdto createGioHang(giohangdto gioHangDto) {
        logger.info("Creating new GioHang for customer: {}", gioHangDto.getMaKhachHang());
        giohang gioHang = mapToEntity(gioHangDto);
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
    public giohangdto getGioHangById(Integer id) {
        logger.info("Fetching GioHang with id: {}", id);
        giohang gioHang = gioHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("GioHang not found with id: {}", id);
                    return new RuntimeException("GioHang not found with id: " + id);
                });
        return mapToDto(gioHang);
    }

    @Override
    public List<giohangdto> getAllGioHang() {
        logger.info("Fetching all GioHang");
        return gioHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public giohangdto updateGioHang(Integer id, giohangdto gioHangDto) {
        logger.info("Updating GioHang with id: {}", id);
        giohang gioHang = gioHangRepository.findById(id)
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
        giohang gioHang = gioHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("GioHang not found with id: {}", id);
                    return new RuntimeException("GioHang not found with id: " + id);
                });
        gioHangRepository.delete(gioHang);
        logger.info("GioHang deleted with id: {}", id);
    }

    private giohangdto mapToDto(giohang gioHang) {
        return giohangdto.builder()
                .maGioHang(gioHang.getMaGioHang())
                .maKhachHang(gioHang.getKhachHang().getMaKhachHang())
                .ngayTao(gioHang.getNgayTao())
                .ngayCapNhat(gioHang.getNgayCapNhat())
                .trangThai(gioHang.getTrangThai())
                .build();
    }

    private giohang mapToEntity(giohangdto gioHangDto) {
        return giohang.builder()
                .maGioHang(gioHangDto.getMaGioHang())
                .ngayTao(gioHangDto.getNgayTao() != null ? gioHangDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(gioHangDto.getNgayCapNhat() != null ? gioHangDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(gioHangDto.getTrangThai() != null ? gioHangDto.getTrangThai() : true)
                .build();
    }
}