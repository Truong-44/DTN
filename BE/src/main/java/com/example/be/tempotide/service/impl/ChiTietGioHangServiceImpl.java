package com.example.be.tempotide.service.impl;


import com.example.be.tempotide.dto.chitietgiohangdto;
import com.example.be.tempotide.entity.chitietgiohang;
import com.example.be.tempotide.repository.chitietgiohangrepository;
import com.example.be.tempotide.repository.giohangrepository;
import com.example.be.tempotide.repository.sanphamrepository;
import com.example.be.tempotide.service.ChiTietGioHangService;
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

    private final chitietgiohangrepository chiTietGioHangRepository;
    private final giohangrepository gioHangRepository;
    private final sanphamrepository sanPhamRepository;

    @Autowired
    public ChiTietGioHangServiceImpl(chitietgiohangrepository chiTietGioHangRepository,
                                     giohangrepository gioHangRepository,
                                     sanphamrepository sanPhamRepository) {
        this.chiTietGioHangRepository = chiTietGioHangRepository;
        this.gioHangRepository = gioHangRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public chitietgiohangdto createChiTietGioHang(chitietgiohangdto chiTietGioHangDto) {
        logger.info("Creating new ChiTietGioHang for GioHang: {}", chiTietGioHangDto.getMaGioHang());
        chitietgiohang chiTietGioHang = mapToEntity(chiTietGioHangDto);
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
    public chitietgiohangdto getChiTietGioHangById(Integer id) {
        logger.info("Fetching ChiTietGioHang with id: {}", id);
        chitietgiohang chiTietGioHang = chiTietGioHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietGioHang not found with id: {}", id);
                    return new RuntimeException("ChiTietGioHang not found with id: " + id);
                });
        return mapToDto(chiTietGioHang);
    }

    @Override
    public List<chitietgiohangdto> getAllChiTietGioHang() {
        logger.info("Fetching all ChiTietGioHang");
        return chiTietGioHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public chitietgiohangdto updateChiTietGioHang(Integer id, chitietgiohangdto chiTietGioHangDto) {
        logger.info("Updating ChiTietGioHang with id: {}", id);
        chitietgiohang chiTietGioHang = chiTietGioHangRepository.findById(id)
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
        chitietgiohang chiTietGioHang = chiTietGioHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietGioHang not found with id: {}", id);
                    return new RuntimeException("ChiTietGioHang not found with id: " + id);
                });
        chiTietGioHangRepository.delete(chiTietGioHang);
        logger.info("ChiTietGioHang deleted with id: {}", id);
    }

    private chitietgiohangdto mapToDto(chitietgiohang chiTietGioHang) {
        return chitietgiohangdto.builder()
                .maChiTietGioHang(chiTietGioHang.getMaChiTietGioHang())
                .maGioHang(chiTietGioHang.getGioHang().getMaGioHang())
                .maSanPham(chiTietGioHang.getSanPham().getMaSanPham())
                .soLuong(chiTietGioHang.getSoLuong())
                .build();
    }

    private chitietgiohang mapToEntity(chitietgiohangdto chiTietGioHangDto) {
        return chitietgiohang.builder()
                .maChiTietGioHang(chiTietGioHangDto.getMaChiTietGioHang())
                .soLuong(chiTietGioHangDto.getSoLuong())
                .build();
    }
}