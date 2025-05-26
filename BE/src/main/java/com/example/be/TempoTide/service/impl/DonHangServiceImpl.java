package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.DonHangDto;
import com.example.be.TempoTide.entity.DonHang;
import com.example.be.TempoTide.repository.DonHangRepository;
import com.example.be.TempoTide.repository.KhachHangRepository;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.service.DonHangService;
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

    private final DonHangRepository donHangRepository;
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;

    @Autowired
    public DonHangServiceImpl(DonHangRepository donHangRepository, KhachHangRepository khachHangRepository,
                              NhanVienRepository nhanVienRepository) {
        this.donHangRepository = donHangRepository;
        this.khachHangRepository = khachHangRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public DonHangDto createDonHang(DonHangDto donHangDto) {
        logger.info("Creating new DonHang for customer: {}", donHangDto.getMaKhachHang());
        DonHang donHang = mapToEntity(donHangDto);
        donHang.setMaKhachHang(khachHangRepository.findById(donHangDto.getMaKhachHang())
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
    public DonHangDto getDonHangById(Integer id) {
        logger.info("Fetching DonHang with id: {}", id);
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DonHang not found with id: {}", id);
                    return new RuntimeException("DonHang not found with id: " + id);
                });
        return mapToDto(donHang);
    }

    @Override
    public List<DonHangDto> getAllDonHang() {
        logger.info("Fetching all DonHang");
        return donHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DonHangDto updateDonHang(Integer id, DonHangDto donHangDto) {
        logger.info("Updating DonHang with id: {}", id);
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DonHang not found with id: {}", id);
                    return new RuntimeException("DonHang not found with id: " + id);
                });

        donHang.setMaKhachHang(khachHangRepository.findById(donHangDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("MaKhachHang not found with id: {}", donHangDto.getMaKhachHang());
                    return new RuntimeException("MaKhachHang not found");
                }));
        donHang.setNgayDatHang(donHangDto.getNgayDatHang());
        donHang.setTongTien(donHangDto.getTongTien());
        donHang.setTrangThaiDonHang(donHangDto.getTrangThaiDonHang());
        donHang.setDiaChiGiaoHang(donHangDto.getDiaChiGiaoHang());
        donHang.setTrangThai(donHangDto.getTrangThai());
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
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DonHang not found with id: {}", id);
                    return new RuntimeException("DonHang not found with id: " + id);
                });
        donHangRepository.delete(donHang);
        logger.info("DonHang deleted with id: {}", id);
    }

    private DonHangDto mapToDto(DonHang donHang) {
        return DonHangDto.builder()
                .maDonHang(donHang.getMaDonHang())
                .maKhachHang(donHang.getMaKhachHang().getMaKhachHang())
                .ngayDatHang(donHang.getNgayDatHang())
                .tongTien(donHang.getTongTien())
                .trangThaiDonHang(donHang.getTrangThaiDonHang())
                .diaChiGiaoHang(donHang.getDiaChiGiaoHang())
                .ngayTao(donHang.getNgayTao())
                .ngayCapNhat(donHang.getNgayCapNhat())
                .trangThai(donHang.getTrangThai())
                .nguoiTaoId(donHang.getNguoiTao() != null ? donHang.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(donHang.getNguoiCapNhat() != null ? donHang.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private DonHang mapToEntity(DonHangDto donHangDto) {
        return DonHang.builder()
                .maDonHang(donHangDto.getMaDonHang())
                .ngayDatHang(donHangDto.getNgayDatHang())
                .tongTien(donHangDto.getTongTien())
                .trangThaiDonHang(donHangDto.getTrangThaiDonHang())
                .diaChiGiaoHang(donHangDto.getDiaChiGiaoHang())
                .ngayTao(donHangDto.getNgayTao() != null ? donHangDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(donHangDto.getNgayCapNhat() != null ? donHangDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(donHangDto.getTrangThai() != null ? donHangDto.getTrangThai() : true)
                .build();
    }
}
