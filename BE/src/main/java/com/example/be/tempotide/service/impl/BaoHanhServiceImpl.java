package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.baohanhdto;
import com.example.be.tempotide.entity.baohanh;
import com.example.be.tempotide.entity.sanpham;
import com.example.be.tempotide.repository.baohanhrepository;
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
public class baohanhserviceImpl implements com.example.be.tempotide.service.baohanhservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.baohanhserviceImpl.class);

    private final baohanhrepository baoHanhRepository;
    private final sanphamrepository sanPhamRepository;

    @Autowired
    public baohanhserviceImpl(baohanhrepository baoHanhRepository,
                              sanphamrepository sanPhamRepository) {
        this.baoHanhRepository = baoHanhRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public baohanhdto createBaoHanh(baohanhdto baoHanhDto) {
        logger.info("Creating new BaoHanh for MaSanPham: {}", baoHanhDto.getMaSanPham());
        baohanh baoHanh = mapToEntity(baoHanhDto);

        sanpham sanPham = sanPhamRepository.findById(baoHanhDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", baoHanhDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                });
        baoHanh.setSanPham(sanPham);

        baoHanh = baoHanhRepository.save(baoHanh);
        logger.info("BaoHanh created with id: {}", baoHanh.getMaBaoHanh());
        return mapToDto(baoHanh);
    }

    @Override
    public baohanhdto getBaoHanhById(Integer id) {
        logger.info("Fetching BaoHanh with id: {}", id);
        baohanh baoHanh = baoHanhRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("BaoHanh not found with id: {}", id);
                    return new RuntimeException("BaoHanh not found with id: " + id);
                });
        return mapToDto(baoHanh);
    }

    @Override
    public List<baohanhdto> getAllBaoHanh() {
        logger.info("Fetching all BaoHanh");
        return baoHanhRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public baohanhdto updateBaoHanh(Integer id, baohanhdto baoHanhDto) {
        logger.info("Updating BaoHanh with id: {}", id);
        baohanh baoHanh = baoHanhRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("BaoHanh not found with id: {}", id);
                    return new RuntimeException("BaoHanh not found with id: " + id);
                });

        baoHanh.setMaSanPham(baoHanhDto.getMaSanPham());
        baoHanh.setThoiHanBaoHanh(baoHanhDto.getThoiHanBaoHanh());
        baoHanh.setNgayBatDau(baoHanhDto.getNgayBatDau());
        baoHanh.setNgayKetThuc(baoHanhDto.getNgayKetThuc());
        baoHanh.setDieuKienBaoHanh(baoHanhDto.getDieuKienBaoHanh());
        baoHanh.setTrangThai(baoHanhDto.getTrangThai());

        sanpham sanPham = sanPhamRepository.findById(baoHanhDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", baoHanhDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                });
        baoHanh.setSanPham(sanPham);

        baoHanh = baoHanhRepository.save(baoHanh);
        logger.info("BaoHanh updated with id: {}", baoHanh.getMaBaoHanh());
        return mapToDto(baoHanh);
    }

    @Override
    public void deleteBaoHanh(Integer id) {
        logger.info("Deleting BaoHanh with id: {}", id);
        baohanh baoHanh = baoHanhRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("BaoHanh not found with id: {}", id);
                    return new RuntimeException("BaoHanh not found with id: " + id);
                });
        baoHanhRepository.delete(baoHanh);
        logger.info("BaoHanh deleted with id: {}", id);
    }

    private baohanhdto mapToDto(baohanh baoHanh) {
        return baohanhdto.builder()
                .maBaoHanh(baoHanh.getMaBaoHanh())
                .maSanPham(baoHanh.getMaSanPham())
                .thoiHanBaoHanh(baoHanh.getThoiHanBaoHanh())
                .ngayBatDau(baoHanh.getNgayBatDau())
                .ngayKetThuc(baoHanh.getNgayKetThuc())
                .dieuKienBaoHanh(baoHanh.getDieuKienBaoHanh())
                .trangThai(baoHanh.getTrangThai())
                .build();
    }

    private baohanh mapToEntity(baohanhdto baoHanhDto) {
        return baohanh.builder()
                .maBaoHanh(baoHanhDto.getMaBaoHanh())
                .maSanPham(baoHanhDto.getMaSanPham())
                .thoiHanBaoHanh(baoHanhDto.getThoiHanBaoHanh())
                .ngayBatDau(baoHanhDto.getNgayBatDau())
                .ngayKetThuc(baoHanhDto.getNgayKetThuc())
                .dieuKienBaoHanh(baoHanhDto.getDieuKienBaoHanh())
                .trangThai(baoHanhDto.getTrangThai())
                .build();
    }
}