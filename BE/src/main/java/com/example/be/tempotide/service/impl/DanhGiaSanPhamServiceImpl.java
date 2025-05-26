package com.example.be.tempotide.service.impl;


import com.example.be.tempotide.dto.danhgiasanphamdto;
import com.example.be.tempotide.entity.danhgiasanpham;
import com.example.be.tempotide.repository.danhgiasanphamrepository;
import com.example.be.tempotide.repository.khachhangrepository;
import com.example.be.tempotide.repository.sanphamrepository;
import com.example.be.tempotide.service.DanhGiaSanPhamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DanhGiaSanPhamServiceImpl implements DanhGiaSanPhamService {

    private static final Logger logger = LoggerFactory.getLogger(DanhGiaSanPhamServiceImpl.class);

    private final danhgiasanphamrepository danhGiaSanPhamRepository;
    private final sanphamrepository sanPhamRepository;
    private final khachhangrepository khachHangRepository;

    @Autowired
    public DanhGiaSanPhamServiceImpl(danhgiasanphamrepository danhGiaSanPhamRepository,
                                     sanphamrepository sanPhamRepository,
                                     khachhangrepository khachHangRepository) {
        this.danhGiaSanPhamRepository = danhGiaSanPhamRepository;
        this.sanPhamRepository = sanPhamRepository;
        this.khachHangRepository = khachHangRepository;
    }

    @Override
    public danhgiasanphamdto createDanhGiaSanPham(danhgiasanphamdto danhGiaSanPhamDto) {
        logger.info("Creating new DanhGiaSanPham for product: {}", danhGiaSanPhamDto.getMaSanPham());
        danhgiasanpham danhGiaSanPham = mapToEntity(danhGiaSanPhamDto);
        danhGiaSanPham.setSanPham(sanPhamRepository.findById(danhGiaSanPhamDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", danhGiaSanPhamDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                }));
        danhGiaSanPham.setKhachHang(khachHangRepository.findById(danhGiaSanPhamDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("KhachHang not found with id: {}", danhGiaSanPhamDto.getMaKhachHang());
                    return new RuntimeException("KhachHang not found");
                }));
        danhGiaSanPham = danhGiaSanPhamRepository.save(danhGiaSanPham);
        logger.info("DanhGiaSanPham created with id: {}", danhGiaSanPham.getMaDanhGia());
        return mapToDto(danhGiaSanPham);
    }

    @Override
    public danhgiasanphamdto getDanhGiaSanPhamById(Integer id) {
        logger.info("Fetching DanhGiaSanPham with id: {}", id);
        danhgiasanpham danhGiaSanPham = danhGiaSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DanhGiaSanPham not found with id: {}", id);
                    return new RuntimeException("DanhGiaSanPham not found with id: " + id);
                });
        return mapToDto(danhGiaSanPham);
    }

    @Override
    public List<danhgiasanphamdto> getAllDanhGiaSanPham() {
        logger.info("Fetching all DanhGiaSanPham");
        return danhGiaSanPhamRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public danhgiasanphamdto updateDanhGiaSanPham(Integer id, danhgiasanphamdto danhGiaSanPhamDto) {
        logger.info("Updating DanhGiaSanPham with id: {}", id);
        danhgiasanpham danhGiaSanPham = danhGiaSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DanhGiaSanPham not found with id: {}", id);
                    return new RuntimeException("DanhGiaSanPham not found with id: " + id);
                });

        danhGiaSanPham.setSanPham(sanPhamRepository.findById(danhGiaSanPhamDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", danhGiaSanPhamDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                }));
        danhGiaSanPham.setKhachHang(khachHangRepository.findById(danhGiaSanPhamDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("KhachHang not found with id: {}", danhGiaSanPhamDto.getMaKhachHang());
                    return new RuntimeException("KhachHang not found");
                }));
        danhGiaSanPham.setDiemDanhGia(danhGiaSanPhamDto.getDiemDanhGia());
        danhGiaSanPham.setBinhLuan(danhGiaSanPhamDto.getBinhLuan());
        danhGiaSanPham.setNgayDanhGia(danhGiaSanPhamDto.getNgayDanhGia());

        danhGiaSanPham = danhGiaSanPhamRepository.save(danhGiaSanPham);
        logger.info("DanhGiaSanPham updated with id: {}", danhGiaSanPham.getMaDanhGia());
        return mapToDto(danhGiaSanPham);
    }

    @Override
    public void deleteDanhGiaSanPham(Integer id) {
        logger.info("Deleting DanhGiaSanPham with id: {}", id);
        danhgiasanpham danhGiaSanPham = danhGiaSanPhamRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DanhGiaSanPham not found with id: {}", id);
                    return new RuntimeException("DanhGiaSanPham not found with id: " + id);
                });
        danhGiaSanPhamRepository.delete(danhGiaSanPham);
        logger.info("DanhGiaSanPham deleted with id: {}", id);
    }

    private danhgiasanphamdto mapToDto(danhgiasanpham danhGiaSanPham) {
        return danhgiasanphamdto.builder()
                .maDanhGia(danhGiaSanPham.getMaDanhGia())
                .maSanPham(danhGiaSanPham.getSanPham().getMaSanPham())
                .maKhachHang(danhGiaSanPham.getKhachHang().getMaKhachHang())
                .diemDanhGia(danhGiaSanPham.getDiemDanhGia())
                .binhLuan(danhGiaSanPham.getBinhLuan())
                .ngayDanhGia(danhGiaSanPham.getNgayDanhGia())
                .build();
    }

    private danhgiasanpham mapToEntity(danhgiasanphamdto danhGiaSanPhamDto) {
        return danhgiasanpham.builder()
                .maDanhGia(danhGiaSanPhamDto.getMaDanhGia())
                .diemDanhGia(danhGiaSanPhamDto.getDiemDanhGia())
                .binhLuan(danhGiaSanPhamDto.getBinhLuan())
                .ngayDanhGia(danhGiaSanPhamDto.getNgayDanhGia())
                .build();
    }
}