package com.example.be.TempoTide.service.impl;


import com.example.be.TempoTide.dto.ChiTietGioHangDto;
import com.example.be.TempoTide.entity.ChiTietGioHang;
import com.example.be.TempoTide.repository.ChiTietGioHangRepository;
import com.example.be.TempoTide.repository.GioHangRepository;
import com.example.be.TempoTide.repository.SanPhamRepository;
import com.example.be.TempoTide.service.ChiTietGioHangService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService {

    private static final Logger logger = LoggerFactory.getLogger(ChiTietGioHangServiceImpl.class);

    private final ChiTietGioHangRepository chiTietGioHangRepository;
    private final GioHangRepository gioHangRepository;
    private final SanPhamRepository sanPhamRepository;

    @Autowired
    public ChiTietGioHangServiceImpl(ChiTietGioHangRepository chiTietGioHangRepository,
                                     GioHangRepository gioHangRepository,
                                     SanPhamRepository sanPhamRepository) {
        this.chiTietGioHangRepository = chiTietGioHangRepository;
        this.gioHangRepository = gioHangRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public ChiTietGioHangDto createChiTietGioHang(ChiTietGioHangDto chiTietGioHangDto) {
        logger.info("Creating new ChiTietGioHang for GioHang: {}", chiTietGioHangDto.getMaGioHang());
        ChiTietGioHang chiTietGioHang = mapToEntity(chiTietGioHangDto);
        chiTietGioHang.setGioHang(gioHangRepository.findById(chiTietGioHangDto.getMaGioHang())
                .orElseThrow(() -> {
                    logger.error("GioHang not found with id: {}", chiTietGioHangDto.getMaGioHang());
                    return new RuntimeException("GioHang not found");
                }));
        chiTietGioHang.setSanPham(sanPhamRepository.findById(chiTietGioHangDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", chiTietGioHangDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                }));
        chiTietGioHang = chiTietGioHangRepository.save(chiTietGioHang);
        logger.info("ChiTietGioHang created with id: {}", chiTietGioHang.getMaChiTietGioHang());
        return mapToDto(chiTietGioHang);
    }

    @Override
    public ChiTietGioHangDto getChiTietGioHangById(Integer id) {
        logger.info("Fetching ChiTietGioHang with id: {}", id);
        ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietGioHang not found with id: {}", id);
                    return new RuntimeException("ChiTietGioHang not found with id: " + id);
                });
        return mapToDto(chiTietGioHang);
    }

    @Override
    public List<ChiTietGioHangDto> getAllChiTietGioHang() {
        logger.info("Fetching all ChiTietGioHang");
        return chiTietGioHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChiTietGioHangDto updateChiTietGioHang(Integer id, ChiTietGioHangDto chiTietGioHangDto) {
        logger.info("Updating ChiTietGioHang with id: {}", id);
        ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietGioHang not found with id: {}", id);
                    return new RuntimeException("ChiTietGioHang not found with id: " + id);
                });

        chiTietGioHang.setGioHang(gioHangRepository.findById(chiTietGioHangDto.getMaGioHang())
                .orElseThrow(() -> {
                    logger.error("GioHang not found with id: {}", chiTietGioHangDto.getMaGioHang());
                    return new RuntimeException("GioHang not found");
                }));
        chiTietGioHang.setSanPham(sanPhamRepository.findById(chiTietGioHangDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", chiTietGioHangDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                }));
        chiTietGioHang.setSoLuong(chiTietGioHangDto.getSoLuong());

        chiTietGioHang = chiTietGioHangRepository.save(chiTietGioHang);
        logger.info("ChiTietGioHang updated with id: {}", chiTietGioHang.getMaChiTietGioHang());
        return mapToDto(chiTietGioHang);
    }

    @Override
    public void deleteChiTietGioHang(Integer id) {
        logger.info("Deleting ChiTietGioHang with id: {}", id);
        ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietGioHang not found with id: {}", id);
                    return new RuntimeException("ChiTietGioHang not found with id: " + id);
                });
        chiTietGioHangRepository.delete(chiTietGioHang);
        logger.info("ChiTietGioHang deleted with id: {}", id);
    }

    private ChiTietGioHangDto mapToDto(ChiTietGioHang chiTietGioHang) {
        return ChiTietGioHangDto.builder()
                .maChiTietGioHang(chiTietGioHang.getMaChiTietGioHang())
                .maGioHang(chiTietGioHang.getGioHang().getMaGioHang())
                .maSanPham(chiTietGioHang.getSanPham().getMaSanPham())
                .soLuong(chiTietGioHang.getSoLuong())
                .build();
    }

    private ChiTietGioHang mapToEntity(ChiTietGioHangDto chiTietGioHangDto) {
        return ChiTietGioHang.builder()
                .maChiTietGioHang(chiTietGioHangDto.getMaChiTietGioHang())
                .soLuong(chiTietGioHangDto.getSoLuong())
                .build();
    }
}