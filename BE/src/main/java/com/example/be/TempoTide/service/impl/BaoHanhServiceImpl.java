package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.BaoHanhDto;
import com.example.be.TempoTide.entity.BaoHanh;
import com.example.be.TempoTide.entity.SanPham;
import com.example.be.TempoTide.repository.BaoHanhRepository;
import com.example.be.TempoTide.repository.SanPhamRepository;
import com.example.be.TempoTide.service.BaoHanhService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BaoHanhServiceImpl implements BaoHanhService {

    private static final Logger logger = LoggerFactory.getLogger(BaoHanhServiceImpl.class);

    private final BaoHanhRepository baoHanhRepository;
    private final SanPhamRepository sanPhamRepository;

    @Autowired
    public BaoHanhServiceImpl(BaoHanhRepository baoHanhRepository,
                              SanPhamRepository sanPhamRepository) {
        this.baoHanhRepository = baoHanhRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public BaoHanhDto createBaoHanh(BaoHanhDto baoHanhDto) {
        logger.info("Creating new BaoHanh for MaSanPham: {}", baoHanhDto.getMaSanPham());
        BaoHanh baoHanh = mapToEntity(baoHanhDto);

        SanPham sanPham = sanPhamRepository.findById(baoHanhDto.getMaSanPham())
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
    public BaoHanhDto getBaoHanhById(Integer id) {
        logger.info("Fetching BaoHanh with id: {}", id);
        BaoHanh baoHanh = baoHanhRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("BaoHanh not found with id: {}", id);
                    return new RuntimeException("BaoHanh not found with id: " + id);
                });
        return mapToDto(baoHanh);
    }

    @Override
    public List<BaoHanhDto> getAllBaoHanh() {
        logger.info("Fetching all BaoHanh");
        return baoHanhRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BaoHanhDto updateBaoHanh(Integer id, BaoHanhDto baoHanhDto) {
        logger.info("Updating BaoHanh with id: {}", id);
        BaoHanh baoHanh = baoHanhRepository.findById(id)
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

        SanPham sanPham = sanPhamRepository.findById(baoHanhDto.getMaSanPham())
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
        BaoHanh baoHanh = baoHanhRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("BaoHanh not found with id: {}", id);
                    return new RuntimeException("BaoHanh not found with id: " + id);
                });
        baoHanhRepository.delete(baoHanh);
        logger.info("BaoHanh deleted with id: {}", id);
    }

    private BaoHanhDto mapToDto(BaoHanh baoHanh) {
        return BaoHanhDto.builder()
                .maBaoHanh(baoHanh.getMaBaoHanh())
                .maSanPham(baoHanh.getMaSanPham())
                .thoiHanBaoHanh(baoHanh.getThoiHanBaoHanh())
                .ngayBatDau(baoHanh.getNgayBatDau())
                .ngayKetThuc(baoHanh.getNgayKetThuc())
                .dieuKienBaoHanh(baoHanh.getDieuKienBaoHanh())
                .trangThai(baoHanh.getTrangThai())
                .build();
    }

    private BaoHanh mapToEntity(BaoHanhDto baoHanhDto) {
        return BaoHanh.builder()
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