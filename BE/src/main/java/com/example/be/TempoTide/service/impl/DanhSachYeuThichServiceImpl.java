package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.DanhSachYeuThichDto;
import com.example.be.TempoTide.entity.DanhSachYeuThich;
import com.example.be.TempoTide.entity.KhachHang;
import com.example.be.TempoTide.entity.SanPham;
import com.example.be.TempoTide.repository.DanhSachYeuThichRepository;
import com.example.be.TempoTide.repository.KhachHangRepository;
import com.example.be.TempoTide.repository.SanPhamRepository;
import com.example.be.TempoTide.service.DanhSachYeuThichService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DanhSachYeuThichServiceImpl implements DanhSachYeuThichService {

    private static final Logger logger = LoggerFactory.getLogger(DanhSachYeuThichServiceImpl.class);

    private final DanhSachYeuThichRepository danhSachYeuThichRepository;
    private final KhachHangRepository khachHangRepository;
    private final SanPhamRepository sanPhamRepository;

    @Autowired
    public DanhSachYeuThichServiceImpl(DanhSachYeuThichRepository danhSachYeuThichRepository,
                                       KhachHangRepository khachHangRepository,
                                       SanPhamRepository sanPhamRepository) {
        this.danhSachYeuThichRepository = danhSachYeuThichRepository;
        this.khachHangRepository = khachHangRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public DanhSachYeuThichDto createDanhSachYeuThich(DanhSachYeuThichDto danhSachYeuThichDto) {
        logger.info("Creating new DanhSachYeuThich for MaKhachHang: {} and MaSanPham: {}", danhSachYeuThichDto.getMaKhachHang(), danhSachYeuThichDto.getMaSanPham());
        DanhSachYeuThich danhSachYeuThich = mapToEntity(danhSachYeuThichDto);

        KhachHang khachHang = khachHangRepository.findById(danhSachYeuThichDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("KhachHang not found with id: {}", danhSachYeuThichDto.getMaKhachHang());
                    return new RuntimeException("KhachHang not found");
                });
        danhSachYeuThich.setKhachHang(khachHang);

        SanPham sanPham = sanPhamRepository.findById(danhSachYeuThichDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", danhSachYeuThichDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                });
        danhSachYeuThich.setSanPham(sanPham);

        danhSachYeuThich = danhSachYeuThichRepository.save(danhSachYeuThich);
        logger.info("DanhSachYeuThich created with id: {}", danhSachYeuThich.getMaYeuThich());
        return mapToDto(danhSachYeuThich);
    }

    @Override
    public DanhSachYeuThichDto getDanhSachYeuThichById(Integer id) {
        logger.info("Fetching DanhSachYeuThich with id: {}", id);
        DanhSachYeuThich danhSachYeuThich = danhSachYeuThichRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DanhSachYeuThich not found with id: {}", id);
                    return new RuntimeException("DanhSachYeuThich not found with id: " + id);
                });
        return mapToDto(danhSachYeuThich);
    }

    @Override
    public List<DanhSachYeuThichDto> getAllDanhSachYeuThich() {
        logger.info("Fetching all DanhSachYeuThich");
        return danhSachYeuThichRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DanhSachYeuThichDto updateDanhSachYeuThich(Integer id, DanhSachYeuThichDto danhSachYeuThichDto) {
        logger.info("Updating DanhSachYeuThich with id: {}", id);
        DanhSachYeuThich danhSachYeuThich = danhSachYeuThichRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DanhSachYeuThich not found with id: {}", id);
                    return new RuntimeException("DanhSachYeuThich not found with id: " + id);
                });

        danhSachYeuThich.setMaKhachHang(danhSachYeuThichDto.getMaKhachHang());
        danhSachYeuThich.setMaSanPham(danhSachYeuThichDto.getMaSanPham());
        danhSachYeuThich.setTrangThai(danhSachYeuThichDto.getTrangThai());

        KhachHang khachHang = khachHangRepository.findById(danhSachYeuThichDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("KhachHang not found with id: {}", danhSachYeuThichDto.getMaKhachHang());
                    return new RuntimeException("KhachHang not found");
                });
        danhSachYeuThich.setKhachHang(khachHang);

        SanPham sanPham = sanPhamRepository.findById(danhSachYeuThichDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", danhSachYeuThichDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                });
        danhSachYeuThich.setSanPham(sanPham);

        danhSachYeuThich = danhSachYeuThichRepository.save(danhSachYeuThich);
        logger.info("DanhSachYeuThich updated with id: {}", danhSachYeuThich.getMaYeuThich());
        return mapToDto(danhSachYeuThich);
    }

    @Override
    public void deleteDanhSachYeuThich(Integer id) {
        logger.info("Deleting DanhSachYeuThich with id: {}", id);
        DanhSachYeuThich danhSachYeuThich = danhSachYeuThichRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DanhSachYeuThich not found with id: {}", id);
                    return new RuntimeException("DanhSachYeuThich not found with id: " + id);
                });
        danhSachYeuThichRepository.delete(danhSachYeuThich);
        logger.info("DanhSachYeuThich deleted with id: {}", id);
    }

    private DanhSachYeuThichDto mapToDto(DanhSachYeuThich danhSachYeuThich) {
        return DanhSachYeuThichDto.builder()
                .maYeuThich(danhSachYeuThich.getMaYeuThich())
                .maKhachHang(danhSachYeuThich.getMaKhachHang())
                .maSanPham(danhSachYeuThich.getMaSanPham())
                .trangThai(danhSachYeuThich.getTrangThai())
                .build();
    }

    private DanhSachYeuThich mapToEntity(DanhSachYeuThichDto danhSachYeuThichDto) {
        return DanhSachYeuThich.builder()
                .maYeuThich(danhSachYeuThichDto.getMaYeuThich())
                .maKhachHang(danhSachYeuThichDto.getMaKhachHang())
                .maSanPham(danhSachYeuThichDto.getMaSanPham())
                .trangThai(danhSachYeuThichDto.getTrangThai())
                .build();
    }
}