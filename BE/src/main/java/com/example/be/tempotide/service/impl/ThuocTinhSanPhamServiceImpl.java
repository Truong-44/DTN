package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.thuoctinhsanphamdto;
import com.example.be.tempotide.entity.sanpham;
import com.example.be.tempotide.entity.thuoctinhsanpham;
import com.example.be.tempotide.repository.sanphamrepository;
import com.example.be.tempotide.repository.thuoctinhsanphamrepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class thuoctinhsanphamserviceImpl implements com.example.be.tempotide.service.thuoctinhsanphamservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.thuoctinhsanphamserviceImpl.class);

    private final thuoctinhsanphamrepository thuocTinhSanPhamRepository;
    private final sanphamrepository sanPhamRepository;

    @Autowired
    public thuoctinhsanphamserviceImpl(thuoctinhsanphamrepository thuocTinhSanPhamRepository,
                                       sanphamrepository sanPhamRepository) {
        this.thuocTinhSanPhamRepository = thuocTinhSanPhamRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public thuoctinhsanphamdto createThuocTinhSanPham(thuoctinhsanphamdto thuocTinhSanPhamDto) {
        logger.info("Creating new ThuocTinhSanPham for MaSanPham: {}", thuocTinhSanPhamDto.getMaSanPham());
        thuoctinhsanpham thuocTinhSanPham = mapToEntity(thuocTinhSanPhamDto);

        sanpham sanPham = sanPhamRepository.findById(thuocTinhSanPhamDto.getMaSanPham())
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
    public thuoctinhsanphamdto getThuocTinhSanPhamById(Integer id) {
        logger.info("Fetching ThuocTinhSanPham with id: {}", id);
        thuoctinhsanpham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThuocTinhSanPham not found with id: {}", id);
                    return new RuntimeException("ThuocTinhSanPham not found with id: " + id);
                });
        return mapToDto(thuocTinhSanPham);
    }

    @Override
    public List<thuoctinhsanphamdto> getAllThuocTinhSanPham() {
        logger.info("Fetching all ThuocTinhSanPham");
        return thuocTinhSanPhamRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public thuoctinhsanphamdto updateThuocTinhSanPham(Integer id, thuoctinhsanphamdto thuocTinhSanPhamDto) {
        logger.info("Updating ThuocTinhSanPham with id: {}", id);
        thuoctinhsanpham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThuocTinhSanPham not found with id: {}", id);
                    return new RuntimeException("ThuocTinhSanPham not found with id: " + id);
                });

        thuocTinhSanPham.setMaSanPham(thuocTinhSanPhamDto.getMaSanPham());
        thuocTinhSanPham.setTenThuocTinh(thuocTinhSanPhamDto.getTenThuocTinh());
        thuocTinhSanPham.setGiaTri(thuocTinhSanPhamDto.getGiaTri());
        thuocTinhSanPham.setTrangThai(thuocTinhSanPhamDto.getTrangThai());

        sanpham sanPham = sanPhamRepository.findById(thuocTinhSanPhamDto.getMaSanPham())
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
        thuoctinhsanpham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThuocTinhSanPham not found with id: {}", id);
                    return new RuntimeException("ThuocTinhSanPham not found with id: " + id);
                });
        thuocTinhSanPhamRepository.delete(thuocTinhSanPham);
        logger.info("ThuocTinhSanPham deleted with id: {}", id);
    }

    private thuoctinhsanphamdto mapToDto(thuoctinhsanpham thuocTinhSanPham) {
        return thuoctinhsanphamdto.builder()
                .maThuocTinh(thuocTinhSanPham.getMaThuocTinh())
                .maSanPham(thuocTinhSanPham.getMaSanPham())
                .tenThuocTinh(thuocTinhSanPham.getTenThuocTinh())
                .giaTri(thuocTinhSanPham.getGiaTri())
                .trangThai(thuocTinhSanPham.getTrangThai())
                .build();
    }

    private thuoctinhsanpham mapToEntity(thuoctinhsanphamdto thuocTinhSanPhamDto) {
        return thuoctinhsanpham.builder()
                .maThuocTinh(thuocTinhSanPhamDto.getMaThuocTinh())
                .maSanPham(thuocTinhSanPhamDto.getMaSanPham())
                .tenThuocTinh(thuocTinhSanPhamDto.getTenThuocTinh())
                .giaTri(thuocTinhSanPhamDto.getGiaTri())
                .trangThai(thuocTinhSanPhamDto.getTrangThai())
                .build();
    }
}