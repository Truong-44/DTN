package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.ThuocTinhSanPhamDto;
import com.example.be.TempoTide.entity.SanPham;
import com.example.be.TempoTide.entity.ThuocTinhSanPham;
import com.example.be.TempoTide.repository.SanPhamRepository;
import com.example.be.TempoTide.repository.ThuocTinhSanPhamRepository;
import com.example.be.TempoTide.service.ThuocTinhSanPhamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ThuocTinhSanPhamServiceImpl implements ThuocTinhSanPhamService {

    private static final Logger logger = LoggerFactory.getLogger(ThuocTinhSanPhamServiceImpl.class);

    private final ThuocTinhSanPhamRepository thuocTinhSanPhamRepository;
    private final SanPhamRepository sanPhamRepository;

    @Autowired
    public ThuocTinhSanPhamServiceImpl(ThuocTinhSanPhamRepository thuocTinhSanPhamRepository,
                                       SanPhamRepository sanPhamRepository) {
        this.thuocTinhSanPhamRepository = thuocTinhSanPhamRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public ThuocTinhSanPhamDto createThuocTinhSanPham(ThuocTinhSanPhamDto thuocTinhSanPhamDto) {
        logger.info("Creating new ThuocTinhSanPham for MaSanPham: {}", thuocTinhSanPhamDto.getMaSanPham());
        ThuocTinhSanPham thuocTinhSanPham = mapToEntity(thuocTinhSanPhamDto);

        SanPham sanPham = sanPhamRepository.findById(thuocTinhSanPhamDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", thuocTinhSanPhamDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                });
        thuocTinhSanPham.setSanPham(sanPham);

        thuocTinhSanPham = thuocTinhSanPhamRepository.save(thuocTinhSanPham);
        logger.info("ThuocTinhSanPham created with id: {}", thuocTinhSanPham.getMaThuocTinh());
        return mapToDto(thuocTinhSanPham);
    }

    @Override
    public ThuocTinhSanPhamDto getThuocTinhSanPhamById(Integer id) {
        logger.info("Fetching ThuocTinhSanPham with id: {}", id);
        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThuocTinhSanPham not found with id: {}", id);
                    return new RuntimeException("ThuocTinhSanPham not found with id: " + id);
                });
        return mapToDto(thuocTinhSanPham);
    }

    @Override
    public List<ThuocTinhSanPhamDto> getAllThuocTinhSanPham() {
        logger.info("Fetching all ThuocTinhSanPham");
        return thuocTinhSanPhamRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ThuocTinhSanPhamDto updateThuocTinhSanPham(Integer id, ThuocTinhSanPhamDto thuocTinhSanPhamDto) {
        logger.info("Updating ThuocTinhSanPham with id: {}", id);
        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThuocTinhSanPham not found with id: {}", id);
                    return new RuntimeException("ThuocTinhSanPham not found with id: " + id);
                });

        thuocTinhSanPham.setMaSanPham(thuocTinhSanPhamDto.getMaSanPham());
        thuocTinhSanPham.setTenThuocTinh(thuocTinhSanPhamDto.getTenThuocTinh());
        thuocTinhSanPham.setGiaTri(thuocTinhSanPhamDto.getGiaTri());
        thuocTinhSanPham.setTrangThai(thuocTinhSanPhamDto.getTrangThai());

        SanPham sanPham = sanPhamRepository.findById(thuocTinhSanPhamDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", thuocTinhSanPhamDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                });
        thuocTinhSanPham.setSanPham(sanPham);

        thuocTinhSanPham = thuocTinhSanPhamRepository.save(thuocTinhSanPham);
        logger.info("ThuocTinhSanPham updated with id: {}", thuocTinhSanPham.getMaThuocTinh());
        return mapToDto(thuocTinhSanPham);
    }

    @Override
    public void deleteThuocTinhSanPham(Integer id) {
        logger.info("Deleting ThuocTinhSanPham with id: {}", id);
        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThuocTinhSanPham not found with id: {}", id);
                    return new RuntimeException("ThuocTinhSanPham not found with id: " + id);
                });
        thuocTinhSanPhamRepository.delete(thuocTinhSanPham);
        logger.info("ThuocTinhSanPham deleted with id: {}", id);
    }

    private ThuocTinhSanPhamDto mapToDto(ThuocTinhSanPham thuocTinhSanPham) {
        return ThuocTinhSanPhamDto.builder()
                .maThuocTinh(thuocTinhSanPham.getMaThuocTinh())
                .maSanPham(thuocTinhSanPham.getMaSanPham())
                .tenThuocTinh(thuocTinhSanPham.getTenThuocTinh())
                .giaTri(thuocTinhSanPham.getGiaTri())
                .trangThai(thuocTinhSanPham.getTrangThai())
                .build();
    }

    private ThuocTinhSanPham mapToEntity(ThuocTinhSanPhamDto thuocTinhSanPhamDto) {
        return ThuocTinhSanPham.builder()
                .maThuocTinh(thuocTinhSanPhamDto.getMaThuocTinh())
                .maSanPham(thuocTinhSanPhamDto.getMaSanPham())
                .tenThuocTinh(thuocTinhSanPhamDto.getTenThuocTinh())
                .giaTri(thuocTinhSanPhamDto.getGiaTri())
                .trangThai(thuocTinhSanPhamDto.getTrangThai())
                .build();
    }
}