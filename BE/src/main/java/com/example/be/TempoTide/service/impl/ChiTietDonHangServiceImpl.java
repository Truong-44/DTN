package com.example.be.TempoTide.service.impl;


import com.example.be.TempoTide.dto.ChiTietDonHangDto;
import com.example.be.TempoTide.entity.ChiTietDonHang;
import com.example.be.TempoTide.repository.ChiTietDonHangRepository;
import com.example.be.TempoTide.repository.DonHangRepository;
import com.example.be.TempoTide.repository.SanPhamRepository;
import com.example.be.TempoTide.service.ChiTietDonHangService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {

    private static final Logger logger = LoggerFactory.getLogger(ChiTietDonHangServiceImpl.class);

    private final ChiTietDonHangRepository chiTietDonHangRepository;
    private final DonHangRepository donHangRepository;
    private final SanPhamRepository sanPhamRepository;

    @Autowired
    public ChiTietDonHangServiceImpl(ChiTietDonHangRepository chiTietDonHangRepository,
                                     DonHangRepository donHangRepository,
                                     SanPhamRepository sanPhamRepository) {
        this.chiTietDonHangRepository = chiTietDonHangRepository;
        this.donHangRepository = donHangRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public ChiTietDonHangDto createChiTietDonHang(ChiTietDonHangDto chiTietDonHangDto) {
        logger.info("Creating new ChiTietDonHang for DonHang: {}", chiTietDonHangDto.getMaDonHang());
        ChiTietDonHang chiTietDonHang = mapToEntity(chiTietDonHangDto);
        chiTietDonHang.setDonHang(donHangRepository.findById(chiTietDonHangDto.getMaDonHang())
                .orElseThrow(() -> {
                    logger.error("DonHang not found with id: {}", chiTietDonHangDto.getMaDonHang());
                    return new RuntimeException("DonHang not found");
                }));
        chiTietDonHang.setSanPham(sanPhamRepository.findById(chiTietDonHangDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", chiTietDonHangDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                }));
        chiTietDonHang = chiTietDonHangRepository.save(chiTietDonHang);
        logger.info("ChiTietDonHang created with id: {}", chiTietDonHang.getMaChiTietDonHang());
        return mapToDto(chiTietDonHang);
    }

    @Override
    public ChiTietDonHangDto getChiTietDonHangById(Integer id) {
        logger.info("Fetching ChiTietDonHang with id: {}", id);
        ChiTietDonHang chiTietDonHang = chiTietDonHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietDonHang not found with id: {}", id);
                    return new RuntimeException("ChiTietDonHang not found with id: " + id);
                });
        return mapToDto(chiTietDonHang);
    }

    @Override
    public List<ChiTietDonHangDto> getAllChiTietDonHang() {
        logger.info("Fetching all ChiTietDonHang");
        return chiTietDonHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChiTietDonHangDto updateChiTietDonHang(Integer id, ChiTietDonHangDto chiTietDonHangDto) {
        logger.info("Updating ChiTietDonHang with id: {}", id);
        ChiTietDonHang chiTietDonHang = chiTietDonHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietDonHang not found with id: {}", id);
                    return new RuntimeException("ChiTietDonHang not found with id: " + id);
                });

        chiTietDonHang.setDonHang(donHangRepository.findById(chiTietDonHangDto.getMaDonHang())
                .orElseThrow(() -> {
                    logger.error("DonHang not found with id: {}", chiTietDonHangDto.getMaDonHang());
                    return new RuntimeException("DonHang not found");
                }));
        chiTietDonHang.setSanPham(sanPhamRepository.findById(chiTietDonHangDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", chiTietDonHangDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                }));
        chiTietDonHang.setSoLuong(chiTietDonHangDto.getSoLuong());
        chiTietDonHang.setDonGia(chiTietDonHangDto.getDonGia());

        chiTietDonHang = chiTietDonHangRepository.save(chiTietDonHang);
        logger.info("ChiTietDonHang updated with id: {}", chiTietDonHang.getMaChiTietDonHang());
        return mapToDto(chiTietDonHang);
    }

    @Override
    public void deleteChiTietDonHang(Integer id) {
        logger.info("Deleting ChiTietDonHang with id: {}", id);
        ChiTietDonHang chiTietDonHang = chiTietDonHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietDonHang not found with id: {}", id);
                    return new RuntimeException("ChiTietDonHang not found with id: " + id);
                });
        chiTietDonHangRepository.delete(chiTietDonHang);
        logger.info("ChiTietDonHang deleted with id: {}", id);
    }

    private ChiTietDonHangDto mapToDto(ChiTietDonHang chiTietDonHang) {
        return ChiTietDonHangDto.builder()
                .maChiTietDonHang(chiTietDonHang.getMaChiTietDonHang())
                .maDonHang(chiTietDonHang.getDonHang().getMaDonHang())
                .maSanPham(chiTietDonHang.getSanPham().getMaSanPham())
                .soLuong(chiTietDonHang.getSoLuong())
                .donGia(chiTietDonHang.getDonGia())
                .build();
    }

    private ChiTietDonHang mapToEntity(ChiTietDonHangDto chiTietDonHangDto) {
        return ChiTietDonHang.builder()
                .maChiTietDonHang(chiTietDonHangDto.getMaChiTietDonHang())
                .soLuong(chiTietDonHangDto.getSoLuong())
                .donGia(chiTietDonHangDto.getDonGia())
                .build();
    }
}