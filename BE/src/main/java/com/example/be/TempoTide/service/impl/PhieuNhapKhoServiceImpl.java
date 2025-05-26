package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.PhieuNhapKhoDto;
import com.example.be.TempoTide.entity.KhoHang;
import com.example.be.TempoTide.entity.NhaCungCap;
import com.example.be.TempoTide.entity.NhanVien;
import com.example.be.TempoTide.entity.PhieuNhapKho;
import com.example.be.TempoTide.repository.KhoHangRepository;
import com.example.be.TempoTide.repository.NhaCungCapRepository;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.repository.PhieuNhapKhoRepository;
import com.example.be.TempoTide.service.PhieuNhapKhoService;
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
public class PhieuNhapKhoServiceImpl implements PhieuNhapKhoService {

    private static final Logger logger = LoggerFactory.getLogger(PhieuNhapKhoServiceImpl.class);

    private final PhieuNhapKhoRepository phieuNhapKhoRepository;
    private final KhoHangRepository khoHangRepository;
    private final NhaCungCapRepository nhaCungCapRepository;
    private final NhanVienRepository nhanVienRepository;

    @Autowired
    public PhieuNhapKhoServiceImpl(PhieuNhapKhoRepository phieuNhapKhoRepository,
                                   KhoHangRepository khoHangRepository,
                                   NhaCungCapRepository nhaCungCapRepository,
                                   NhanVienRepository nhanVienRepository) {
        this.phieuNhapKhoRepository = phieuNhapKhoRepository;
        this.khoHangRepository = khoHangRepository;
        this.nhaCungCapRepository = nhaCungCapRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public PhieuNhapKhoDto createPhieuNhapKho(PhieuNhapKhoDto phieuNhapKhoDto) {
        logger.info("Creating new PhieuNhapKho for MaKho: {}", phieuNhapKhoDto.getMaKho());
        PhieuNhapKho phieuNhapKho = mapToEntity(phieuNhapKhoDto);

        KhoHang khoHang = khoHangRepository.findById(phieuNhapKhoDto.getMaKho())
                .orElseThrow(() -> {
                    logger.error("KhoHang not found with id: {}", phieuNhapKhoDto.getMaKho());
                    return new RuntimeException("KhoHang not found");
                });
        phieuNhapKho.setKhoHang(khoHang);

        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(phieuNhapKhoDto.getMaNhaCungCap())
                .orElseThrow(() -> {
                    logger.error("NhaCungCap not found with id: {}", phieuNhapKhoDto.getMaNhaCungCap());
                    return new RuntimeException("NhaCungCap not found");
                });
        phieuNhapKho.setNhaCungCap(nhaCungCap);

        if (phieuNhapKhoDto.getNguoiTao() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(phieuNhapKhoDto.getNguoiTao())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", phieuNhapKhoDto.getNguoiTao());
                        return new RuntimeException("NhanVien not found");
                    });
            phieuNhapKho.setNguoiTao(nguoiTao);
        }
        if (phieuNhapKhoDto.getNguoiCapNhat() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(phieuNhapKhoDto.getNguoiCapNhat())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", phieuNhapKhoDto.getNguoiCapNhat());
                        return new RuntimeException("NhanVien not found");
                    });
            phieuNhapKho.setNguoiCapNhat(nguoiCapNhat);
        }

        phieuNhapKho.setNgayNhap(LocalDateTime.now());
        phieuNhapKho = phieuNhapKhoRepository.save(phieuNhapKho);
        logger.info("PhieuNhapKho created with id: {}", phieuNhapKho.getMaPhieuNhap());
        return mapToDto(phieuNhapKho);
    }

    @Override
    public PhieuNhapKhoDto getPhieuNhapKhoById(Integer id) {
        logger.info("Fetching PhieuNhapKho with id: {}", id);
        PhieuNhapKho phieuNhapKho = phieuNhapKhoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhieuNhapKho not found with id: {}", id);
                    return new RuntimeException("PhieuNhapKho not found with id: " + id);
                });
        return mapToDto(phieuNhapKho);
    }

    @Override
    public List<PhieuNhapKhoDto> getAllPhieuNhapKho() {
        logger.info("Fetching all PhieuNhapKho");
        return phieuNhapKhoRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PhieuNhapKhoDto updatePhieuNhapKho(Integer id, PhieuNhapKhoDto phieuNhapKhoDto) {
        logger.info("Updating PhieuNhapKho with id: {}", id);
        PhieuNhapKho phieuNhapKho = phieuNhapKhoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhieuNhapKho not found with id: {}", id);
                    return new RuntimeException("PhieuNhapKho not found with id: " + id);
                });

        phieuNhapKho.setMaKho(phieuNhapKhoDto.getMaKho());
        phieuNhapKho.setMaNhaCungCap(phieuNhapKhoDto.getMaNhaCungCap());
        phieuNhapKho.setTongTien(phieuNhapKhoDto.getTongTien());
        phieuNhapKho.setGhiChu(phieuNhapKhoDto.getGhiChu());
        phieuNhapKho.setTrangThai(phieuNhapKhoDto.getTrangThai());

        KhoHang khoHang = khoHangRepository.findById(phieuNhapKhoDto.getMaKho())
                .orElseThrow(() -> {
                    logger.error("KhoHang not found with id: {}", phieuNhapKhoDto.getMaKho());
                    return new RuntimeException("KhoHang not found");
                });
        phieuNhapKho.setKhoHang(khoHang);

        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(phieuNhapKhoDto.getMaNhaCungCap())
                .orElseThrow(() -> {
                    logger.error("NhaCungCap not found with id: {}", phieuNhapKhoDto.getMaNhaCungCap());
                    return new RuntimeException("NhaCungCap not found");
                });
        phieuNhapKho.setNhaCungCap(nhaCungCap);

        if (phieuNhapKhoDto.getNguoiCapNhat() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(phieuNhapKhoDto.getNguoiCapNhat())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", phieuNhapKhoDto.getNguoiCapNhat());
                        return new RuntimeException("NhanVien not found");
                    });
            phieuNhapKho.setNguoiCapNhat(nguoiCapNhat);
        }

        phieuNhapKho = phieuNhapKhoRepository.save(phieuNhapKho);
        logger.info("PhieuNhapKho updated with id: {}", phieuNhapKho.getMaPhieuNhap());
        return mapToDto(phieuNhapKho);
    }

    @Override
    public void deletePhieuNhapKho(Integer id) {
        logger.info("Deleting PhieuNhapKho with id: {}", id);
        PhieuNhapKho phieuNhapKho = phieuNhapKhoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhieuNhapKho not found with id: {}", id);
                    return new RuntimeException("PhieuNhapKho not found with id: " + id);
                });
        phieuNhapKhoRepository.delete(phieuNhapKho);
        logger.info("PhieuNhapKho deleted with id: {}", id);
    }

    private PhieuNhapKhoDto mapToDto(PhieuNhapKho phieuNhapKho) {
        return PhieuNhapKhoDto.builder()
                .maPhieuNhap(phieuNhapKho.getMaPhieuNhap())
                .maKho(phieuNhapKho.getMaKho())
                .maNhaCungCap(phieuNhapKho.getMaNhaCungCap())
                .tongTien(phieuNhapKho.getTongTien())
                .ngayNhap(phieuNhapKho.getNgayNhap())
                .ghiChu(phieuNhapKho.getGhiChu())
                .trangThai(phieuNhapKho.getTrangThai())
                .nguoiTao(phieuNhapKho.getNguoiTao() != null ? phieuNhapKho.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhat(phieuNhapKho.getNguoiCapNhat() != null ? phieuNhapKho.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private PhieuNhapKho mapToEntity(PhieuNhapKhoDto phieuNhapKhoDto) {
        return PhieuNhapKho.builder()
                .maPhieuNhap(phieuNhapKhoDto.getMaPhieuNhap())
                .maKho(phieuNhapKhoDto.getMaKho())
                .maNhaCungCap(phieuNhapKhoDto.getMaNhaCungCap())
                .tongTien(phieuNhapKhoDto.getTongTien())
                .ngayNhap(phieuNhapKhoDto.getNgayNhap() != null ? phieuNhapKhoDto.getNgayNhap() : LocalDateTime.now())
                .ghiChu(phieuNhapKhoDto.getGhiChu())
                .trangThai(phieuNhapKhoDto.getTrangThai())
                .build();
    }
}