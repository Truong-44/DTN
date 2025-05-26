package com.example.be.tempotide.service.impl;


import com.example.be.tempotide.dto.lienhedathangdto;
import com.example.be.tempotide.entity.*;
import com.example.be.tempotide.repository.*;
import com.example.be.tempotide.service.LienHeDatHangService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LienHeDatHangServiceImpl implements LienHeDatHangService {

    private static final Logger logger = LoggerFactory.getLogger(LienHeDatHangServiceImpl.class);

    private final lienhedathangrepository lienHeDatHangRepository;
    private final sanphamrepository sanPhamRepository;
    private final chitietsanphamrepository chiTietSanPhamRepository;
    private final donhangrepository donHangRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public LienHeDatHangServiceImpl(lienhedathangrepository lienHeDatHangRepository,
                                    sanphamrepository sanPhamRepository,
                                    chitietsanphamrepository chiTietSanPhamRepository,
                                    donhangrepository donHangRepository,
                                    nhanvienrepository nhanVienRepository) {
        this.lienHeDatHangRepository = lienHeDatHangRepository;
        this.sanPhamRepository = sanPhamRepository;
        this.chiTietSanPhamRepository = chiTietSanPhamRepository;
        this.donHangRepository = donHangRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public lienhedathangdto createLienHeDatHang(lienhedathangdto lienHeDatHangDto) {
        logger.info("Creating new LienHeDatHang for HoTen: {}", lienHeDatHangDto.getHoTen());
        lienhedathang lienHeDatHang = mapToEntity(lienHeDatHangDto);

        sanpham sanPham = sanPhamRepository.findById(lienHeDatHangDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", lienHeDatHangDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                });
        lienHeDatHang.setSanPham(sanPham);

        chitietsanpham chiTietSanPham = chiTietSanPhamRepository.findById(lienHeDatHangDto.getMaChiTietSanPham())
                .orElseThrow(() -> {
                    logger.error("ChiTietSanPham not found with id: {}", lienHeDatHangDto.getMaChiTietSanPham());
                    return new RuntimeException("ChiTietSanPham not found");
                });
        lienHeDatHang.setChiTietSanPham(chiTietSanPham);

        if (lienHeDatHangDto.getMaDonHang() != null) {
            donhang donHang = donHangRepository.findById(lienHeDatHangDto.getMaDonHang())
                    .orElseThrow(() -> {
                        logger.error("DonHang not found with id: {}", lienHeDatHangDto.getMaDonHang());
                        return new RuntimeException("DonHang not found");
                    });
            lienHeDatHang.setDonHang(donHang);
        }

        if (lienHeDatHangDto.getNguoiTao() != null) {
            nhanvien nguoiTao = nhanVienRepository.findById(lienHeDatHangDto.getNguoiTao())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", lienHeDatHangDto.getNguoiTao());
                        return new RuntimeException("NhanVien not found");
                    });
            lienHeDatHang.setNguoiTao(nguoiTao);
        }

        lienHeDatHang.setNgayLienHe(LocalDateTime.now());
        lienHeDatHang = lienHeDatHangRepository.save(lienHeDatHang);
        logger.info("LienHeDatHang created with id: {}", lienHeDatHang.getMaLienHe());
        return mapToDto(lienHeDatHang);
    }

    @Override
    public lienhedathangdto getLienHeDatHangById(Integer id) {
        logger.info("Fetching LienHeDatHang with id: {}", id);
        lienhedathang lienHeDatHang = lienHeDatHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("LienHeDatHang not found with id: {}", id);
                    return new RuntimeException("LienHeDatHang not found with id: " + id);
                });
        return mapToDto(lienHeDatHang);
    }

    @Override
    public List<lienhedathangdto> getAllLienHeDatHang() {
        logger.info("Fetching all LienHeDatHang");
        return lienHeDatHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public lienhedathangdto updateLienHeDatHang(Integer id, lienhedathangdto lienHeDatHangDto) {
        logger.info("Updating LienHeDatHang with id: {}", id);
        lienhedathang lienHeDatHang = lienHeDatHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("LienHeDatHang not found with id: {}", id);
                    return new RuntimeException("LienHeDatHang not found with id: " + id);
                });

        lienHeDatHang.setHoTen(lienHeDatHangDto.getHoTen());
        lienHeDatHang.setSoDienThoai(lienHeDatHangDto.getSoDienThoai());
        lienHeDatHang.setEmail(lienHeDatHangDto.getEmail());
        lienHeDatHang.setMaSanPham(lienHeDatHangDto.getMaSanPham());
        lienHeDatHang.setMaChiTietSanPham(lienHeDatHangDto.getMaChiTietSanPham());
        lienHeDatHang.setSoLuong(lienHeDatHangDto.getSoLuong());
        lienHeDatHang.setGhiChu(lienHeDatHangDto.getGhiChu());
        lienHeDatHang.setMaDonHang(lienHeDatHangDto.getMaDonHang());
        lienHeDatHang.setTrangThai(lienHeDatHangDto.getTrangThai());

        sanpham sanPham = sanPhamRepository.findById(lienHeDatHangDto.getMaSanPham())
                .orElseThrow(() -> {
                    logger.error("SanPham not found with id: {}", lienHeDatHangDto.getMaSanPham());
                    return new RuntimeException("SanPham not found");
                });
        lienHeDatHang.setSanPham(sanPham);

        chitietsanpham chiTietSanPham = chiTietSanPhamRepository.findById(lienHeDatHangDto.getMaChiTietSanPham())
                .orElseThrow(() -> {
                    logger.error("ChiTietSanPham not found with id: {}", lienHeDatHangDto.getMaChiTietSanPham());
                    return new RuntimeException("ChiTietSanPham not found");
                });
        lienHeDatHang.setChiTietSanPham(chiTietSanPham);

        if (lienHeDatHangDto.getMaDonHang() != null) {
            donhang donHang = donHangRepository.findById(lienHeDatHangDto.getMaDonHang())
                    .orElseThrow(() -> {
                        logger.error("DonHang not found with id: {}", lienHeDatHangDto.getMaDonHang());
                        return new RuntimeException("DonHang not found");
                    });
            lienHeDatHang.setDonHang(donHang);
        }

        if (lienHeDatHangDto.getNguoiTao() != null) {
            nhanvien nguoiTao = nhanVienRepository.findById(lienHeDatHangDto.getNguoiTao())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", lienHeDatHangDto.getNguoiTao());
                        return new RuntimeException("NhanVien not found");
                    });
            lienHeDatHang.setNguoiTao(nguoiTao);
        }

        lienHeDatHang = lienHeDatHangRepository.save(lienHeDatHang);
        logger.info("LienHeDatHang updated with id: {}", lienHeDatHang.getMaLienHe());
        return mapToDto(lienHeDatHang);
    }

    @Override
    public void deleteLienHeDatHang(Integer id) {
        logger.info("Deleting LienHeDatHang with id: {}", id);
        lienhedathang lienHeDatHang = lienHeDatHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("LienHeDatHang not found with id: {}", id);
                    return new RuntimeException("LienHeDatHang not found with id: " + id);
                });
        lienHeDatHangRepository.delete(lienHeDatHang);
        logger.info("LienHeDatHang deleted with id: {}", id);
    }

    private lienhedathangdto mapToDto(lienhedathang lienHeDatHang) {
        return lienhedathangdto.builder()
                .maLienHe(lienHeDatHang.getMaLienHe())
                .hoTen(lienHeDatHang.getHoTen())
                .soDienThoai(lienHeDatHang.getSoDienThoai())
                .email(lienHeDatHang.getEmail())
                .maSanPham(lienHeDatHang.getMaSanPham())
                .maChiTietSanPham(lienHeDatHang.getMaChiTietSanPham())
                .soLuong(lienHeDatHang.getSoLuong())
                .ghiChu(lienHeDatHang.getGhiChu())
                .maDonHang(lienHeDatHang.getMaDonHang())
                .ngayLienHe(lienHeDatHang.getNgayLienHe())
                .trangThai(lienHeDatHang.getTrangThai())
                .nguoiTao(lienHeDatHang.getNguoiTao() != null ? lienHeDatHang.getNguoiTao().getMaNhanVien() : null)
                .build();
    }

    private lienhedathang mapToEntity(lienhedathangdto lienHeDatHangDto) {
        return lienhedathang.builder()
                .maLienHe(lienHeDatHangDto.getMaLienHe())
                .hoTen(lienHeDatHangDto.getHoTen())
                .soDienThoai(lienHeDatHangDto.getSoDienThoai())
                .email(lienHeDatHangDto.getEmail())
                .maSanPham(lienHeDatHangDto.getMaSanPham())
                .maChiTietSanPham(lienHeDatHangDto.getMaChiTietSanPham())
                .soLuong(lienHeDatHangDto.getSoLuong())
                .ghiChu(lienHeDatHangDto.getGhiChu())
                .maDonHang(lienHeDatHangDto.getMaDonHang())
                .ngayLienHe(lienHeDatHangDto.getNgayLienHe() != null ? lienHeDatHangDto.getNgayLienHe() : LocalDateTime.now())
                .trangThai(lienHeDatHangDto.getTrangThai())
                .build();
    }
}