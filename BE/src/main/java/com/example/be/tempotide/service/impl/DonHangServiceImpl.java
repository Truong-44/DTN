package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.donhangdto;
import com.example.be.tempotide.entity.donhang;
import com.example.be.tempotide.repository.donhangrepository;
import com.example.be.tempotide.repository.khachhangrepository;
import com.example.be.tempotide.repository.nhanvienrepository;
import com.example.be.tempotide.service.DonHangService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DonHangServiceImpl implements DonHangService {

    private static final Logger logger = LoggerFactory.getLogger(DonHangServiceImpl.class);

    private final donhangrepository donHangRepository;
    private final khachhangrepository khachHangRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public DonHangServiceImpl(donhangrepository donHangRepository, khachhangrepository khachHangRepository,
                              nhanvienrepository nhanVienRepository) {
        this.donHangRepository = donHangRepository;
        this.khachHangRepository = khachHangRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public donhangdto createDonHang(donhangdto donHangDto) {
        logger.info("Creating new DonHang for customer: {}", donHangDto.getMaKhachHang());
        donhang donHang = mapToEntity(donHangDto);
        donHang.setMaKhachhang(khachHangRepository.findById(donHangDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("MaKhachHang not found with id: {}", donHangDto.getMaKhachHang());
                    return new RuntimeException("MaKhachHang not found");
                }));
        if (donHangDto.getNguoiTaoId() != null) {
            donHang.setNguoiTao(nhanVienRepository.findById(donHangDto.getNguoiTaoId())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", donHangDto.getNguoiTaoId());
                        return new RuntimeException("NguoiTao not found");
                    }));
        }
        if (donHangDto.getNguoiCapNhatId() != null) {
            donHang.setNguoiCapNhat(nhanVienRepository.findById(donHangDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", donHangDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }
        donHang = donHangRepository.save(donHang);
        logger.info("DonHang created with id: {}", donHang.getMaDonHang());
        return mapToDto(donHang);
    }

    @Override
    public donhangdto getDonHangById(Integer id) {
        logger.info("Fetching DonHang with id: {}", id);
        donhang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DonHang not found with id: {}", id);
                    return new RuntimeException("DonHang not found with id: " + id);
                });
        return mapToDto(donHang);
    }

    @Override
    public List<donhangdto> getAllDonHang() {
        logger.info("Fetching all DonHang");
        return donHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public donhangdto updateDonHang(Integer id, donhangdto donHangDto) {
        logger.info("Updating DonHang with id: {}", id);
        donhang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DonHang not found with id: {}", id);
                    return new RuntimeException("DonHang not found with id: " + id);
                });

        donHang.setMaKhachhang(khachHangRepository.findById(donHangDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("MaKhachHang not found with id: {}", donHangDto.getMaKhachHang());
                    return new RuntimeException("MaKhachHang not found");
                }));
        donHang.setNgayDatHang(donHangDto.getNgayDatHang());
        donHang.setTongTien(donHangDto.getTongTien());
        donHang.setMaPhuongThucVanChuyen(donHangDto.getMaPhuongThucVanChuyen());
        donHang.setMaDiaChiGiaoHang(donHangDto.getMaDiaChiGiaoHang());
        donHang.setTrangThaiDonHang(donHangDto.getTrangThaiDonHang());
        donHang.setNgayCapNhat(donHangDto.getNgayCapNhat() != null ? donHangDto.getNgayCapNhat() : LocalDateTime.now());

        if (donHangDto.getNguoiCapNhatId() != null) {
            donHang.setNguoiCapNhat(nhanVienRepository.findById(donHangDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", donHangDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }

        donHang = donHangRepository.save(donHang);
        logger.info("DonHang updated with id: {}", donHang.getMaDonHang());
        return mapToDto(donHang);
    }

    @Override
    public void deleteDonHang(Integer id) {
        logger.info("Deleting DonHang with id: {}", id);
        donhang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DonHang not found with id: {}", id);
                    return new RuntimeException("DonHang not found with id: " + id);
                });
        donHangRepository.delete(donHang);
        logger.info("DonHang deleted with id: {}", id);
    }

    private donhangdto mapToDto(donhang donHang) {
        return donhangdto.builder()
                .maDonHang(donHang.getMaDonHang())
                .maKhachHang(donHang.getMaKhachhang().getMaKhachHang())
                .ngayDatHang(donHang.getNgayDatHang())
                .tongTien(donHang.getTongTien())
                .maPhuongThucVanChuyen(donHang.getMaPhuongThucVanChuyen())
                .maDiaChiGiaoHang(donHang.getMaDiaChiGiaoHang())
                .trangThaiDonHang(donHang.getTrangThaiDonHang())
                .maKhuyenMai(donHang.getMaKhuyenmai() != null ? donHang.getMaKhuyenmai().getMaKhuyenMai() : null)
                .ngayTao(donHang.getNgayTao())
                .ngayCapNhat(donHang.getNgayCapNhat())
                .trangThai(donHang.getTrangThai())
                .nguoiTaoId(donHang.getNguoiTao() != null ? donHang.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(donHang.getNguoiCapNhat() != null ? donHang.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private donhang mapToEntity(donhangdto donHangDto) {
        return donhang.builder()
                .maDonHang(donHangDto.getMaDonHang())
                .ngayDatHang(donHangDto.getNgayDatHang())
                .tongTien(donHangDto.getTongTien())
                .maPhuongThucVanChuyen(donHangDto.getMaPhuongThucVanChuyen())
                .maDiaChiGiaoHang(donHangDto.getMaDiaChiGiaoHang())
                .trangThaiDonHang(donHangDto.getTrangThaiDonHang())
                .ngayTao(donHangDto.getNgayTao() != null ? donHangDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(donHangDto.getNgayCapNhat() != null ? donHangDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(donHangDto.getTrangThai() != null ? donHangDto.getTrangThai() : true)
                .build();
    }
}