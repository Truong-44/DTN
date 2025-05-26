package com.example.be.tempotide.service.impl;


import com.example.be.tempotide.dto.chitietdonhangdto;
import com.example.be.tempotide.entity.chitietdonhang;
import com.example.be.tempotide.repository.chitietdonhangrepository;
import com.example.be.tempotide.repository.donhangrepository;
import com.example.be.tempotide.repository.sanphamrepository;
import com.example.be.tempotide.service.ChiTietDonHangService;
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

    private final chitietdonhangrepository chiTietDonHangRepository;
    private final donhangrepository donHangRepository;
    private final sanphamrepository sanPhamRepository;

    @Autowired
    public ChiTietDonHangServiceImpl(chitietdonhangrepository chiTietDonHangRepository,
                                     donhangrepository donHangRepository,
                                     sanphamrepository sanPhamRepository) {
        this.chiTietDonHangRepository = chiTietDonHangRepository;
        this.donHangRepository = donHangRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public chitietdonhangdto createChiTietDonHang(chitietdonhangdto chiTietDonHangDto) {
        logger.info("Creating new ChiTietDonHang for DonHang: {}", chiTietDonHangDto.getMaDonHang());
        chitietdonhang chiTietDonHang = mapToEntity(chiTietDonHangDto);
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
    public chitietdonhangdto getChiTietDonHangById(Integer id) {
        logger.info("Fetching ChiTietDonHang with id: {}", id);
        chitietdonhang chiTietDonHang = chiTietDonHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietDonHang not found with id: {}", id);
                    return new RuntimeException("ChiTietDonHang not found with id: " + id);
                });
        return mapToDto(chiTietDonHang);
    }

    @Override
    public List<chitietdonhangdto> getAllChiTietDonHang() {
        logger.info("Fetching all ChiTietDonHang");
        return chiTietDonHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public chitietdonhangdto updateChiTietDonHang(Integer id, chitietdonhangdto chiTietDonHangDto) {
        logger.info("Updating ChiTietDonHang with id: {}", id);
        chitietdonhang chiTietDonHang = chiTietDonHangRepository.findById(id)
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
        chitietdonhang chiTietDonHang = chiTietDonHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietDonHang not found with id: {}", id);
                    return new RuntimeException("ChiTietDonHang not found with id: " + id);
                });
        chiTietDonHangRepository.delete(chiTietDonHang);
        logger.info("ChiTietDonHang deleted with id: {}", id);
    }

    private chitietdonhangdto mapToDto(chitietdonhang chiTietDonHang) {
        return chitietdonhangdto.builder()
                .maChiTietDonHang(chiTietDonHang.getMaChiTietDonHang())
                .maDonHang(chiTietDonHang.getDonHang().getMaDonHang())
                .maSanPham(chiTietDonHang.getSanPham().getMaSanPham())
                .soLuong(chiTietDonHang.getSoLuong())
                .donGia(chiTietDonHang.getDonGia())
                .build();
    }

    private chitietdonhang mapToEntity(chitietdonhangdto chiTietDonHangDto) {
        return chitietdonhang.builder()
                .maChiTietDonHang(chiTietDonHangDto.getMaChiTietDonHang())
                .soLuong(chiTietDonHangDto.getSoLuong())
                .donGia(chiTietDonHangDto.getDonGia())
                .build();
    }
}