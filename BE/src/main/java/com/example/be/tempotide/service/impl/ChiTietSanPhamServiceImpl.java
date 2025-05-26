package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.chitietsanphamdto;
import com.example.be.tempotide.entity.chitietsanpham;
import com.example.be.tempotide.entity.sanpham;
import com.example.be.tempotide.repository.chitietsanphamrepository;
import com.example.be.tempotide.repository.sanphamrepository;
import com.example.be.tempotide.service.ChiTietSanPhamService;
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

    private final chitietsanphamrepository chiTietSanPhamRepository;
    private final sanphamrepository sanPhamRepository;

    @Autowired
    public ChiTietSanPhamServiceImpl(chitietsanphamrepository chiTietSanPhamRepository,
                                     sanphamrepository sanPhamRepository) {
        this.chiTietSanPhamRepository = chiTietSanPhamRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public chitietsanphamdto createChiTietSanPham(chitietsanphamdto chiTietSanPhamDto) {
        logger.info("Creating new ChiTietSanPham for MaSanPham: {}", chiTietSanPhamDto.getMaSanPham());
        chitietsanpham chiTietSanPham = mapToEntity(chiTietSanPhamDto);

        sanpham sanPham = sanPhamRepository.findById(chiTietSanPhamDto.getMaSanPham())
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
    public chitietsanphamdto getChiTietSanPhamById(Integer id) {
        logger.info("Fetching ChiTietSanPham with id: {}", id);
        chitietsanpham chiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietSanPham not found with id: {}", id);
                    return new RuntimeException("ChiTietSanPham not found with id: " + id);
                });
        return mapToDto(chiTietSanPham);
    }

    @Override
    public List<chitietsanphamdto> getAllChiTietSanPham() {
        logger.info("Fetching all ChiTietSanPham");
        return chiTietSanPhamRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public chitietsanphamdto updateChiTietSanPham(Integer id, chitietsanphamdto chiTietSanPhamDto) {
        logger.info("Updating ChiTietSanPham with id: {}", id);
        chitietsanpham chiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietSanPham not found with id: {}", id);
                    return new RuntimeException("ChiTietSanPham not found with id: " + id);
                });

        chiTietSanPham.setMaSanPham(chiTietSanPhamDto.getMaSanPham());
        chiTietSanPham.setSoLuongTon(chiTietSanPhamDto.getSoLuongTon());
        chiTietSanPham.setGiaBan(chiTietSanPhamDto.getGiaBan());
        chiTietSanPham.setTrangThai(chiTietSanPhamDto.getTrangThai());

        sanpham sanPham = sanPhamRepository.findById(chiTietSanPhamDto.getMaSanPham())
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
        chitietsanpham chiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ChiTietSanPham not found with id: {}", id);
                    return new RuntimeException("ChiTietSanPham not found with id: " + id);
                });
        chiTietSanPhamRepository.delete(chiTietSanPham);
        logger.info("ChiTietSanPham deleted with id: {}", id);
    }

    private chitietsanphamdto mapToDto(chitietsanpham chiTietSanPham) {
        return chitietsanphamdto.builder()
                .maChiTietSanPham(chiTietSanPham.getMaChiTietSanPham())
                .maSanPham(chiTietSanPham.getMaSanPham())
                .soLuongTon(chiTietSanPham.getSoLuongTon())
                .giaBan(chiTietSanPham.getGiaBan())
                .trangThai(chiTietSanPham.getTrangThai())
                .build();
    }

    private chitietsanpham mapToEntity(chitietsanphamdto chiTietSanPhamDto) {
        return chitietsanpham.builder()
                .maChiTietSanPham(chiTietSanPhamDto.getMaChiTietSanPham())
                .maSanPham(chiTietSanPhamDto.getMaSanPham())
                .soLuongTon(chiTietSanPhamDto.getSoLuongTon())
                .giaBan(chiTietSanPhamDto.getGiaBan())
                .trangThai(chiTietSanPhamDto.getTrangThai())
                .build();
    }
}