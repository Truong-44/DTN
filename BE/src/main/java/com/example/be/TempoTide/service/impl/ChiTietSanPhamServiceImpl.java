package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.ChiTietSanPhamDto;
import com.example.be.TempoTide.entity.ChiTietSanPham;
import com.example.be.TempoTide.entity.SanPham;
import com.example.be.TempoTide.repository.ChiTietSanPhamRepository;
import com.example.be.TempoTide.repository.SanPhamRepository;
import com.example.be.TempoTide.service.ChiTietSanPhamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {

    private static final Logger logger = LoggerFactory.getLogger(ChiTietSanPhamServiceImpl.class);

    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final SanPhamRepository sanPhamRepository;

    @Autowired
    public ChiTietSanPhamServiceImpl(ChiTietSanPhamRepository chiTietSanPhamRepository,
                                     SanPhamRepository sanPhamRepository) {
        this.chiTietSanPhamRepository = chiTietSanPhamRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public ChiTietSanPhamDto createChiTietSanPham(ChiTietSanPhamDto chiTietSanPhamDto) {
        logger.info("Creating new ChiTietSanPham for MaSanPham: {}", chiTietSanPhamDto.getMaSanPham());
        ChiTietSanPham chiTietSanPham = mapToEntity(chiTietSanPhamDto);

        SanPham sanPham = sanPhamRepository.findById(chiTietSanPhamDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", chiTietSanPhamDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                });
        chiTietSanPham.setSanPham(sanPham);

        chiTietSanPham = chiTietSanPhamRepository.save(chiTietSanPham);
        logger.info("ChiTietSanPham created with id: {}", chiTietSanPham.getMaChiTietSanPham());
        return mapToDto(chiTietSanPham);
    }

    @Override
    public ChiTietSanPhamDto getChiTietSanPhamById(Integer id) {
        logger.info("Fetching ChiTietSanPham with id: {}", id);
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietSanPham not found with id: {}", id);
                    return new RuntimeException("ChiTietSanPham not found with id: " + id);
                });
        return mapToDto(chiTietSanPham);
    }

    @Override
    public List<ChiTietSanPhamDto> getAllChiTietSanPham() {
        logger.info("Fetching all ChiTietSanPham");
        return chiTietSanPhamRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChiTietSanPhamDto updateChiTietSanPham(Integer id, ChiTietSanPhamDto chiTietSanPhamDto) {
        logger.info("Updating ChiTietSanPham with id: {}", id);
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietSanPham not found with id: {}", id);
                    return new RuntimeException("ChiTietSanPham not found with id: " + id);
                });

        chiTietSanPham.setMaSanPham(chiTietSanPhamDto.getMaSanPham());
        chiTietSanPham.setSoLuongTon(chiTietSanPhamDto.getSoLuongTon());
        chiTietSanPham.setGiaBan(chiTietSanPhamDto.getGiaBan());
        chiTietSanPham.setTrangThai(chiTietSanPhamDto.getTrangThai());

        SanPham sanPham = sanPhamRepository.findById(chiTietSanPhamDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", chiTietSanPhamDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                });
        chiTietSanPham.setSanPham(sanPham);

        chiTietSanPham = chiTietSanPhamRepository.save(chiTietSanPham);
        logger.info("ChiTietSanPham updated with id: {}", chiTietSanPham.getMaChiTietSanPham());
        return mapToDto(chiTietSanPham);
    }

    @Override
    public void deleteChiTietSanPham(Integer id) {
        logger.info("Deleting ChiTietSanPham with id: {}", id);
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietSanPham not found with id: {}", id);
                    return new RuntimeException("ChiTietSanPham not found with id: " + id);
                });
        chiTietSanPhamRepository.delete(chiTietSanPham);
        logger.info("ChiTietSanPham deleted with id: {}", id);
    }

    private ChiTietSanPhamDto mapToDto(ChiTietSanPham chiTietSanPham) {
        return ChiTietSanPhamDto.builder()
                .maChiTietSanPham(chiTietSanPham.getMaChiTietSanPham())
                .maSanPham(chiTietSanPham.getMaSanPham())
                .soLuongTon(chiTietSanPham.getSoLuongTon())
                .giaBan(chiTietSanPham.getGiaBan())
                .trangThai(chiTietSanPham.getTrangThai())
                .build();
    }

    private ChiTietSanPham mapToEntity(ChiTietSanPhamDto chiTietSanPhamDto) {
        return ChiTietSanPham.builder()
                .maChiTietSanPham(chiTietSanPhamDto.getMaChiTietSanPham())
                .maSanPham(chiTietSanPhamDto.getMaSanPham())
                .soLuongTon(chiTietSanPhamDto.getSoLuongTon())
                .giaBan(chiTietSanPhamDto.getGiaBan())
                .trangThai(chiTietSanPhamDto.getTrangThai())
                .build();
    }
}