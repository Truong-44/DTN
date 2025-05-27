package com.example.be.tempotide.service.impl;


import com.example.be.tempotide.dto.chitietphieunhapdto;
import com.example.be.tempotide.entity.chitietphieunhap;
import com.example.be.tempotide.repository.chitietphieunhaprepository;
import com.example.be.tempotide.repository.phieunhaphangrepository;
import com.example.be.tempotide.repository.sanphamrepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class chitietphieunhapserviceImpl implements com.example.be.tempotide.service.chitietphieunhapservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.chitietphieunhapserviceImpl.class);

    private final chitietphieunhaprepository chiTietPhieuNhapRepository;
    private final phieunhaphangrepository phieuNhapHangRepository;
    private final sanphamrepository sanPhamRepository;

    @Autowired
    public chitietphieunhapserviceImpl(chitietphieunhaprepository chiTietPhieuNhapRepository,
                                       phieunhaphangrepository phieuNhapHangRepository,
                                       sanphamrepository sanPhamRepository) {
        this.chiTietPhieuNhapRepository = chiTietPhieuNhapRepository;
        this.phieuNhapHangRepository = phieuNhapHangRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public chitietphieunhapdto createChiTietPhieuNhap(chitietphieunhapdto chiTietPhieuNhapDto) {
        logger.info("Creating new ChiTietPhieuNhap for PhieuNhap: {}", chiTietPhieuNhapDto.getMaPhieuNhap());
        chitietphieunhap chiTietPhieuNhap = mapToEntity(chiTietPhieuNhapDto);
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
    public chitietphieunhapdto getChiTietPhieuNhapById(Integer id) {
        logger.info("Fetching ChiTietPhieuNhap with id: {}", id);
        chitietphieunhap chiTietPhieuNhap = chiTietPhieuNhapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietPhieuNhap not found with id: {}", id);
                    return new RuntimeException("ChiTietPhieuNhap not found with id: " + id);
                });
        return mapToDto(chiTietPhieuNhap);
    }

    @Override
    public List<chitietphieunhapdto> getAllChiTietPhieuNhap() {
        logger.info("Fetching all ChiTietPhieuNhap");
        return chiTietPhieuNhapRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public chitietphieunhapdto updateChiTietPhieuNhap(Integer id, chitietphieunhapdto chiTietPhieuNhapDto) {
        logger.info("Updating ChiTietPhieuNhap with id: {}", id);
        chitietphieunhap chiTietPhieuNhap = chiTietPhieuNhapRepository.findById(id)
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
        chitietphieunhap chiTietPhieuNhap = chiTietPhieuNhapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietPhieuNhap not found with id: {}", id);
                    return new RuntimeException("ChiTietPhieuNhap not found with id: " + id);
                });
        chiTietPhieuNhapRepository.delete(chiTietPhieuNhap);
        logger.info("ChiTietPhieuNhap deleted with id: {}", id);
    }

    private chitietphieunhapdto mapToDto(chitietphieunhap chiTietPhieuNhap) {
        return chitietphieunhapdto.builder()
                .maChiTietPhieuNhap(chiTietPhieuNhap.getMaChiTietPhieuNhap())
                .maPhieuNhap(chiTietPhieuNhap.getPhieuNhapHang().getMaPhieuNhap())
                .maSanPham(chiTietPhieuNhap.getSanPham().getMaSanPham())
                .soLuong(chiTietPhieuNhap.getSoLuong())
                .donGia(chiTietPhieuNhap.getDonGia())
                .build();
    }

    private chitietphieunhap mapToEntity(chitietphieunhapdto chiTietPhieuNhapDto) {
        return chitietphieunhap.builder()
                .maChiTietPhieuNhap(chiTietPhieuNhapDto.getMaChiTietPhieuNhap())
                .soLuong(chiTietPhieuNhapDto.getSoLuong())
                .donGia(chiTietPhieuNhapDto.getDonGia())
                .build();
    }
}