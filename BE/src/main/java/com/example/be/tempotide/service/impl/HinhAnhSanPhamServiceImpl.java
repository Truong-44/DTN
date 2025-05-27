package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.hinhanhsanphamdto;
import com.example.be.tempotide.entity.hinhanhsanpham;
import com.example.be.tempotide.entity.sanpham;
import com.example.be.tempotide.repository.hinhanhsanphamrepository;
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
public class hinhanhsanphamserviceImpl implements com.example.be.tempotide.service.hinhanhsanphamservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.hinhanhsanphamserviceImpl.class);

    private final hinhanhsanphamrepository hinhAnhSanPhamRepository;
    private final sanphamrepository sanPhamRepository;

    @Autowired
    public hinhanhsanphamserviceImpl(hinhanhsanphamrepository hinhAnhSanPhamRepository,
                                     sanphamrepository sanPhamRepository) {
        this.hinhAnhSanPhamRepository = hinhAnhSanPhamRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public hinhanhsanphamdto createHinhAnhSanPham(hinhanhsanphamdto hinhAnhSanPhamDto) {
        logger.info("Creating new HinhAnhSanPham for MaSanPham: {}", hinhAnhSanPhamDto.getMaSanPham());
        hinhanhsanpham hinhAnhSanPham = mapToEntity(hinhAnhSanPhamDto);

        sanpham sanPham = sanPhamRepository.findById(hinhAnhSanPhamDto.getMaSanPham())
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
    public hinhanhsanphamdto getHinhAnhSanPhamById(Integer id) {
        logger.info("Fetching HinhAnhSanPham with id: {}", id);
        hinhanhsanpham hinhAnhSanPham = hinhAnhSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("HinhAnhSanPham not found with id: {}", id);
                    return new RuntimeException("HinhAnhSanPham not found with id: " + id);
                });
        return mapToDto(hinhAnhSanPham);
    }

    @Override
    public List<hinhanhsanphamdto> getAllHinhAnhSanPham() {
        logger.info("Fetching all HinhAnhSanPham");
        return hinhAnhSanPhamRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public hinhanhsanphamdto updateHinhAnhSanPham(Integer id, hinhanhsanphamdto hinhAnhSanPhamDto) {
        logger.info("Updating HinhAnhSanPham with id: {}", id);
        hinhanhsanpham hinhAnhSanPham = hinhAnhSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("HinhAnhSanPham not found with id: {}", id);
                    return new RuntimeException("HinhAnhSanPham not found with id: " + id);
                });

        hinhAnhSanPham.setMaSanPham(hinhAnhSanPhamDto.getMaSanPham());
        hinhAnhSanPham.setDuongDan(hinhAnhSanPhamDto.getDuongDan());
        hinhAnhSanPham.setLaAnhChinh(hinhAnhSanPhamDto.getLaAnhChinh());
        hinhAnhSanPham.setTrangThai(hinhAnhSanPhamDto.getTrangThai());

        sanpham sanPham = sanPhamRepository.findById(hinhAnhSanPhamDto.getMaSanPham())
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
        hinhanhsanpham hinhAnhSanPham = hinhAnhSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("HinhAnhSanPham not found with id: {}", id);
                    return new RuntimeException("HinhAnhSanPham not found with id: " + id);
                });
        hinhAnhSanPhamRepository.delete(hinhAnhSanPham);
        logger.info("HinhAnhSanPham deleted with id: {}", id);
    }

    private hinhanhsanphamdto mapToDto(hinhanhsanpham hinhAnhSanPham) {
        return hinhanhsanphamdto.builder()
                .maHinhAnh(hinhAnhSanPham.getMaHinhAnh())
                .maSanPham(hinhAnhSanPham.getMaSanPham())
                .duongDan(hinhAnhSanPham.getDuongDan())
                .laAnhChinh(hinhAnhSanPham.getLaAnhChinh())
                .trangThai(hinhAnhSanPham.getTrangThai())
                .build();
    }

    private hinhanhsanpham mapToEntity(hinhanhsanphamdto hinhAnhSanPhamDto) {
        return hinhanhsanpham.builder()
                .maHinhAnh(hinhAnhSanPhamDto.getMaHinhAnh())
                .maSanPham(hinhAnhSanPhamDto.getMaSanPham())
                .duongDan(hinhAnhSanPhamDto.getDuongDan())
                .laAnhChinh(hinhAnhSanPhamDto.getLaAnhChinh())
                .trangThai(hinhAnhSanPhamDto.getTrangThai())
                .build();
    }
}