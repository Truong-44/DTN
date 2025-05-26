package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.BaoCaoBanHangDto;
import com.example.be.TempoTide.entity.BaoCaoBanHang;
import com.example.be.TempoTide.repository.BaoCaoBanHangRepository;
import com.example.be.TempoTide.service.BaoCaoBanHangService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BaoCaoBanHangServiceImpl implements BaoCaoBanHangService {

    private static final Logger logger = LoggerFactory.getLogger(BaoCaoBanHangServiceImpl.class);

    private final BaoCaoBanHangRepository baoCaoBanHangRepository;

    @Autowired
    public BaoCaoBanHangServiceImpl(BaoCaoBanHangRepository baoCaoBanHangRepository) {
        this.baoCaoBanHangRepository = baoCaoBanHangRepository;
    }

    @Override
    public BaoCaoBanHangDto createBaoCaoBanHang(BaoCaoBanHangDto baoCaoBanHangDto) {
        logger.info("Creating new BaoCaoBanHang for period: {} to {}", baoCaoBanHangDto.getThoiGianBatDau(), baoCaoBanHangDto.getThoiGianKetThuc());
        BaoCaoBanHang baoCaoBanHang = mapToEntity(baoCaoBanHangDto);
        baoCaoBanHang = baoCaoBanHangRepository.save(baoCaoBanHang);
        logger.info("BaoCaoBanHang created with id: {}", baoCaoBanHang.getMaBaoCao());
        return mapToDto(baoCaoBanHang);
    }

    @Override
    public BaoCaoBanHangDto getBaoCaoBanHangById(Integer id) {
        logger.info("Fetching BaoCaoBanHang with id: {}", id);
        BaoCaoBanHang baoCaoBanHang = baoCaoBanHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("BaoCaoBanHang not found with id: {}", id);
                    return new RuntimeException("BaoCaoBanHang not found with id: " + id);
                });
        return mapToDto(baoCaoBanHang);
    }

    @Override
    public List<BaoCaoBanHangDto> getAllBaoCaoBanHang() {
        logger.info("Fetching all BaoCaoBanHang");
        return baoCaoBanHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BaoCaoBanHangDto updateBaoCaoBanHang(Integer id, BaoCaoBanHangDto baoCaoBanHangDto) {
        logger.info("Updating BaoCaoBanHang with id: {}", id);
        BaoCaoBanHang baoCaoBanHang = baoCaoBanHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("BaoCaoBanHang not found with id: {}", id);
                    return new RuntimeException("BaoCaoBanHang not found with id: " + id);
                });

        baoCaoBanHang.setThoiGianBatDau(baoCaoBanHangDto.getThoiGianBatDau());
        baoCaoBanHang.setThoiGianKetThuc(baoCaoBanHangDto.getThoiGianKetThuc());
        baoCaoBanHang.setTongDoanhThu(baoCaoBanHangDto.getTongDoanhThu());
        baoCaoBanHang.setSoDonHang(baoCaoBanHangDto.getSoDonHang());
        baoCaoBanHang.setTrangThai(baoCaoBanHangDto.getTrangThai());

        baoCaoBanHang = baoCaoBanHangRepository.save(baoCaoBanHang);
        logger.info("BaoCaoBanHang updated with id: {}", baoCaoBanHang.getMaBaoCao());
        return mapToDto(baoCaoBanHang);
    }

    @Override
    public void deleteBaoCaoBanHang(Integer id) {
        logger.info("Deleting BaoCaoBanHang with id: {}", id);
        BaoCaoBanHang baoCaoBanHang = baoCaoBanHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("BaoCaoBanHang not found with id: {}", id);
                    return new RuntimeException("BaoCaoBanHang not found with id: " + id);
                });
        baoCaoBanHangRepository.delete(baoCaoBanHang);
        logger.info("BaoCaoBanHang deleted with id: {}", id);
    }

    private BaoCaoBanHangDto mapToDto(BaoCaoBanHang baoCaoBanHang) {
        return BaoCaoBanHangDto.builder()
                .maBaoCao(baoCaoBanHang.getMaBaoCao())
                .thoiGianBatDau(baoCaoBanHang.getThoiGianBatDau())
                .thoiGianKetThuc(baoCaoBanHang.getThoiGianKetThuc())
                .tongDoanhThu(baoCaoBanHang.getTongDoanhThu())
                .soDonHang(baoCaoBanHang.getSoDonHang())
                .trangThai(baoCaoBanHang.getTrangThai())
                .build();
    }

    private BaoCaoBanHang mapToEntity(BaoCaoBanHangDto baoCaoBanHangDto) {
        return BaoCaoBanHang.builder()
                .maBaoCao(baoCaoBanHangDto.getMaBaoCao())
                .thoiGianBatDau(baoCaoBanHangDto.getThoiGianBatDau())
                .thoiGianKetThuc(baoCaoBanHangDto.getThoiGianKetThuc())
                .tongDoanhThu(baoCaoBanHangDto.getTongDoanhThu())
                .soDonHang(baoCaoBanHangDto.getSoDonHang())
                .trangThai(baoCaoBanHangDto.getTrangThai())
                .build();
    }
}