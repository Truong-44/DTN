package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.HinhAnhSanPhamDto;
import com.example.be.TempoTide.entity.HinhAnhSanPham;
import com.example.be.TempoTide.entity.SanPham;
import com.example.be.TempoTide.repository.HinhAnhSanPhamRepository;
import com.example.be.TempoTide.repository.SanPhamRepository;
import com.example.be.TempoTide.service.HinhAnhSanPhamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HinhAnhSanPhamServiceImpl implements HinhAnhSanPhamService {

    private static final Logger logger = LoggerFactory.getLogger(HinhAnhSanPhamServiceImpl.class);

    private final HinhAnhSanPhamRepository hinhAnhSanPhamRepository;
    private final SanPhamRepository sanPhamRepository;

    @Autowired
    public HinhAnhSanPhamServiceImpl(HinhAnhSanPhamRepository hinhAnhSanPhamRepository,
                                     SanPhamRepository sanPhamRepository) {
        this.hinhAnhSanPhamRepository = hinhAnhSanPhamRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public HinhAnhSanPhamDto createHinhAnhSanPham(HinhAnhSanPhamDto hinhAnhSanPhamDto) {
        logger.info("Creating new HinhAnhSanPham for MaSanPham: {}", hinhAnhSanPhamDto.getMaSanPham());
        HinhAnhSanPham hinhAnhSanPham = mapToEntity(hinhAnhSanPhamDto);

        SanPham sanPham = sanPhamRepository.findById(hinhAnhSanPhamDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", hinhAnhSanPhamDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                });
        hinhAnhSanPham.setSanPham(sanPham);

        hinhAnhSanPham = hinhAnhSanPhamRepository.save(hinhAnhSanPham);
        logger.info("HinhAnhSanPham created with id: {}", hinhAnhSanPham.getMaHinhAnh());
        return mapToDto(hinhAnhSanPham);
    }

    @Override
    public HinhAnhSanPhamDto getHinhAnhSanPhamById(Integer id) {
        logger.info("Fetching HinhAnhSanPham with id: {}", id);
        HinhAnhSanPham hinhAnhSanPham = hinhAnhSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("HinhAnhSanPham not found with id: {}", id);
                    return new RuntimeException("HinhAnhSanPham not found with id: " + id);
                });
        return mapToDto(hinhAnhSanPham);
    }

    @Override
    public List<HinhAnhSanPhamDto> getAllHinhAnhSanPham() {
        logger.info("Fetching all HinhAnhSanPham");
        return hinhAnhSanPhamRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public HinhAnhSanPhamDto updateHinhAnhSanPham(Integer id, HinhAnhSanPhamDto hinhAnhSanPhamDto) {
        logger.info("Updating HinhAnhSanPham with id: {}", id);
        HinhAnhSanPham hinhAnhSanPham = hinhAnhSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("HinhAnhSanPham not found with id: {}", id);
                    return new RuntimeException("HinhAnhSanPham not found with id: " + id);
                });

        hinhAnhSanPham.setMaSanPham(hinhAnhSanPhamDto.getMaSanPham());
        hinhAnhSanPham.setDuongDan(hinhAnhSanPhamDto.getDuongDan());
        hinhAnhSanPham.setLaAnhChinh(hinhAnhSanPhamDto.getLaAnhChinh());
        hinhAnhSanPham.setTrangThai(hinhAnhSanPhamDto.getTrangThai());

        SanPham sanPham = sanPhamRepository.findById(hinhAnhSanPhamDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", hinhAnhSanPhamDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                });
        hinhAnhSanPham.setSanPham(sanPham);

        hinhAnhSanPham = hinhAnhSanPhamRepository.save(hinhAnhSanPham);
        logger.info("HinhAnhSanPham updated with id: {}", hinhAnhSanPham.getMaHinhAnh());
        return mapToDto(hinhAnhSanPham);
    }

    @Override
    public void deleteHinhAnhSanPham(Integer id) {
        logger.info("Deleting HinhAnhSanPham with id: {}", id);
        HinhAnhSanPham hinhAnhSanPham = hinhAnhSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("HinhAnhSanPham not found with id: {}", id);
                    return new RuntimeException("HinhAnhSanPham not found with id: " + id);
                });
        hinhAnhSanPhamRepository.delete(hinhAnhSanPham);
        logger.info("HinhAnhSanPham deleted with id: {}", id);
    }

    private HinhAnhSanPhamDto mapToDto(HinhAnhSanPham hinhAnhSanPham) {
        return HinhAnhSanPhamDto.builder()
                .maHinhAnh(hinhAnhSanPham.getMaHinhAnh())
                .maSanPham(hinhAnhSanPham.getMaSanPham())
                .duongDan(hinhAnhSanPham.getDuongDan())
                .laAnhChinh(hinhAnhSanPham.getLaAnhChinh())
                .trangThai(hinhAnhSanPham.getTrangThai())
                .build();
    }

    private HinhAnhSanPham mapToEntity(HinhAnhSanPhamDto hinhAnhSanPhamDto) {
        return HinhAnhSanPham.builder()
                .maHinhAnh(hinhAnhSanPhamDto.getMaHinhAnh())
                .maSanPham(hinhAnhSanPhamDto.getMaSanPham())
                .duongDan(hinhAnhSanPhamDto.getDuongDan())
                .laAnhChinh(hinhAnhSanPhamDto.getLaAnhChinh())
                .trangThai(hinhAnhSanPhamDto.getTrangThai())
                .build();
    }
}