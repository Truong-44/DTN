package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.danhsachyeuthichdto;
import com.example.be.tempotide.entity.danhsachyeuthich;
import com.example.be.tempotide.entity.khachhang;
import com.example.be.tempotide.entity.sanpham;
import com.example.be.tempotide.repository.danhsachyeuthichrepository;
import com.example.be.tempotide.repository.khachhangrepository;
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
public class danhsachyeuthichserviceImpl implements com.example.be.tempotide.service.danhsachyeuthichservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.danhsachyeuthichserviceImpl.class);

    private final danhsachyeuthichrepository danhSachYeuThichRepository;
    private final khachhangrepository khachHangRepository;
    private final sanphamrepository sanPhamRepository;

    @Autowired
    public danhsachyeuthichserviceImpl(danhsachyeuthichrepository danhSachYeuThichRepository,
                                       khachhangrepository khachHangRepository,
                                       sanphamrepository sanPhamRepository) {
        this.danhSachYeuThichRepository = danhSachYeuThichRepository;
        this.khachHangRepository = khachHangRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public danhsachyeuthichdto createDanhSachYeuThich(danhsachyeuthichdto danhSachYeuThichDto) {
        logger.info("Creating new DanhSachYeuThich for MaKhachHang: {} and MaSanPham: {}", danhSachYeuThichDto.getMaKhachHang(), danhSachYeuThichDto.getMaSanPham());
        danhsachyeuthich danhSachYeuThich = mapToEntity(danhSachYeuThichDto);

        khachhang khachHang = khachHangRepository.findById(danhSachYeuThichDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("KhachHang not found with id: {}", danhSachYeuThichDto.getMaKhachHang());
                    return new RuntimeException("KhachHang not found");
                });
        danhSachYeuThich.setKhachHang(khachHang);

        sanpham sanPham = sanPhamRepository.findById(danhSachYeuThichDto.getMaSanPham())
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
    public danhsachyeuthichdto getDanhSachYeuThichById(Integer id) {
        logger.info("Fetching DanhSachYeuThich with id: {}", id);
        danhsachyeuthich danhSachYeuThich = danhSachYeuThichRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DanhSachYeuThich not found with id: {}", id);
                    return new RuntimeException("DanhSachYeuThich not found with id: " + id);
                });
        return mapToDto(danhSachYeuThich);
    }

    @Override
    public List<danhsachyeuthichdto> getAllDanhSachYeuThich() {
        logger.info("Fetching all DanhSachYeuThich");
        return danhSachYeuThichRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public danhsachyeuthichdto updateDanhSachYeuThich(Integer id, danhsachyeuthichdto danhSachYeuThichDto) {
        logger.info("Updating DanhSachYeuThich with id: {}", id);
        danhsachyeuthich danhSachYeuThich = danhSachYeuThichRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DanhSachYeuThich not found with id: {}", id);
                    return new RuntimeException("DanhSachYeuThich not found with id: " + id);
                });

        danhSachYeuThich.setMaKhachHang(danhSachYeuThichDto.getMaKhachHang());
        danhSachYeuThich.setMaSanPham(danhSachYeuThichDto.getMaSanPham());
        danhSachYeuThich.setTrangThai(danhSachYeuThichDto.getTrangThai());

        khachhang khachHang = khachHangRepository.findById(danhSachYeuThichDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("KhachHang not found with id: {}", danhSachYeuThichDto.getMaKhachHang());
                    return new RuntimeException("KhachHang not found");
                });
        danhSachYeuThich.setKhachHang(khachHang);

        sanpham sanPham = sanPhamRepository.findById(danhSachYeuThichDto.getMaSanPham())
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
        danhsachyeuthich danhSachYeuThich = danhSachYeuThichRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DanhSachYeuThich not found with id: {}", id);
                    return new RuntimeException("DanhSachYeuThich not found with id: " + id);
                });
        danhSachYeuThichRepository.delete(danhSachYeuThich);
        logger.info("DanhSachYeuThich deleted with id: {}", id);
    }

    private danhsachyeuthichdto mapToDto(danhsachyeuthich danhSachYeuThich) {
        return danhsachyeuthichdto.builder()
                .maYeuThich(danhSachYeuThich.getMaYeuThich())
                .maKhachHang(danhSachYeuThich.getMaKhachHang())
                .maSanPham(danhSachYeuThich.getMaSanPham())
                .trangThai(danhSachYeuThich.getTrangThai())
                .build();
    }

    private danhsachyeuthich mapToEntity(danhsachyeuthichdto danhSachYeuThichDto) {
        return danhsachyeuthich.builder()
                .maYeuThich(danhSachYeuThichDto.getMaYeuThich())
                .maKhachHang(danhSachYeuThichDto.getMaKhachHang())
                .maSanPham(danhSachYeuThichDto.getMaSanPham())
                .trangThai(danhSachYeuThichDto.getTrangThai())
                .build();
    }
}