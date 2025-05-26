package com.example.be.TempoTide.service.impl;


import com.example.be.TempoTide.dto.ChiTietPhieuNhapDto;
import com.example.be.TempoTide.entity.ChiTietPhieuNhap;
import com.example.be.TempoTide.repository.ChiTietPhieuNhapRepository;
import com.example.be.TempoTide.repository.PhieuNhapHangRepository;
import com.example.be.TempoTide.repository.SanPhamRepository;
import com.example.be.TempoTide.service.ChiTietPhieuNhapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChiTietPhieuNhapServiceImpl implements ChiTietPhieuNhapService {

    private static final Logger logger = LoggerFactory.getLogger(ChiTietPhieuNhapServiceImpl.class);

    private final ChiTietPhieuNhapRepository chiTietPhieuNhapRepository;
    private final PhieuNhapHangRepository phieuNhapHangRepository;
    private final SanPhamRepository sanPhamRepository;

    @Autowired
    public ChiTietPhieuNhapServiceImpl(ChiTietPhieuNhapRepository chiTietPhieuNhapRepository,
                                       PhieuNhapHangRepository phieuNhapHangRepository,
                                       SanPhamRepository sanPhamRepository) {
        this.chiTietPhieuNhapRepository = chiTietPhieuNhapRepository;
        this.phieuNhapHangRepository = phieuNhapHangRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public ChiTietPhieuNhapDto createChiTietPhieuNhap(ChiTietPhieuNhapDto chiTietPhieuNhapDto) {
        logger.info("Creating new ChiTietPhieuNhap for PhieuNhap: {}", chiTietPhieuNhapDto.getMaPhieuNhap());
        ChiTietPhieuNhap chiTietPhieuNhap = mapToEntity(chiTietPhieuNhapDto);
        chiTietPhieuNhap.setPhieuNhapHang(phieuNhapHangRepository.findById(chiTietPhieuNhapDto.getMaPhieuNhap())
                .orElseThrow(() -> {
                    logger.error("PhieuNhapHang not found with id: {}", chiTietPhieuNhapDto.getMaPhieuNhap());
                    return new RuntimeException("PhieuNhapHang not found");
                }));
        chiTietPhieuNhap.setSanPham(sanPhamRepository.findById(chiTietPhieuNhapDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", chiTietPhieuNhapDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                }));
        chiTietPhieuNhap = chiTietPhieuNhapRepository.save(chiTietPhieuNhap);
        logger.info("ChiTietPhieuNhap created with id: {}", chiTietPhieuNhap.getMaChiTietPhieuNhap());
        return mapToDto(chiTietPhieuNhap);
    }

    @Override
    public ChiTietPhieuNhapDto getChiTietPhieuNhapById(Integer id) {
        logger.info("Fetching ChiTietPhieuNhap with id: {}", id);
        ChiTietPhieuNhap chiTietPhieuNhap = chiTietPhieuNhapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietPhieuNhap not found with id: {}", id);
                    return new RuntimeException("ChiTietPhieuNhap not found with id: " + id);
                });
        return mapToDto(chiTietPhieuNhap);
    }

    @Override
    public List<ChiTietPhieuNhapDto> getAllChiTietPhieuNhap() {
        logger.info("Fetching all ChiTietPhieuNhap");
        return chiTietPhieuNhapRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChiTietPhieuNhapDto updateChiTietPhieuNhap(Integer id, ChiTietPhieuNhapDto chiTietPhieuNhapDto) {
        logger.info("Updating ChiTietPhieuNhap with id: {}", id);
        ChiTietPhieuNhap chiTietPhieuNhap = chiTietPhieuNhapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietPhieuNhap not found with id: {}", id);
                    return new RuntimeException("ChiTietPhieuNhap not found with id: " + id);
                });

        chiTietPhieuNhap.setPhieuNhapHang(phieuNhapHangRepository.findById(chiTietPhieuNhapDto.getMaPhieuNhap())
                .orElseThrow(() -> {
                    logger.error("PhieuNhapHang not found with id: {}", chiTietPhieuNhapDto.getMaPhieuNhap());
                    return new RuntimeException("PhieuNhapHang not found");
                }));
        chiTietPhieuNhap.setSanPham(sanPhamRepository.findById(chiTietPhieuNhapDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", chiTietPhieuNhapDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                }));
        chiTietPhieuNhap.setSoLuong(chiTietPhieuNhapDto.getSoLuong());
        chiTietPhieuNhap.setDonGia(chiTietPhieuNhapDto.getDonGia());

        chiTietPhieuNhap = chiTietPhieuNhapRepository.save(chiTietPhieuNhap);
        logger.info("ChiTietPhieuNhap updated with id: {}", chiTietPhieuNhap.getMaChiTietPhieuNhap());
        return mapToDto(chiTietPhieuNhap);
    }

    @Override
    public void deleteChiTietPhieuNhap(Integer id) {
        logger.info("Deleting ChiTietPhieuNhap with id: {}", id);
        ChiTietPhieuNhap chiTietPhieuNhap = chiTietPhieuNhapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietPhieuNhap not found with id: {}", id);
                    return new RuntimeException("ChiTietPhieuNhap not found with id: " + id);
                });
        chiTietPhieuNhapRepository.delete(chiTietPhieuNhap);
        logger.info("ChiTietPhieuNhap deleted with id: {}", id);
    }

    private ChiTietPhieuNhapDto mapToDto(ChiTietPhieuNhap chiTietPhieuNhap) {
        return ChiTietPhieuNhapDto.builder()
                .maChiTietPhieuNhap(chiTietPhieuNhap.getMaChiTietPhieuNhap())
                .maPhieuNhap(chiTietPhieuNhap.getPhieuNhapHang().getMaPhieuNhap())
                .maSanPham(chiTietPhieuNhap.getSanPham().getMaSanPham())
                .soLuong(chiTietPhieuNhap.getSoLuong())
                .donGia(chiTietPhieuNhap.getDonGia())
                .build();
    }

    private ChiTietPhieuNhap mapToEntity(ChiTietPhieuNhapDto chiTietPhieuNhapDto) {
        return ChiTietPhieuNhap.builder()
                .maChiTietPhieuNhap(chiTietPhieuNhapDto.getMaChiTietPhieuNhap())
                .soLuong(chiTietPhieuNhapDto.getSoLuong())
                .donGia(chiTietPhieuNhapDto.getDonGia())
                .build();
    }
}