package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.PhieuNhapHangDto;
import com.example.be.TempoTide.entity.PhieuNhapHang;
import com.example.be.TempoTide.repository.NhaCungCapRepository;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.repository.PhieuNhapHangRepository;
import com.example.be.TempoTide.service.PhieuNhapHangService;
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
public class PhieuNhapHangServiceImpl implements PhieuNhapHangService {

    private static final Logger logger = LoggerFactory.getLogger(PhieuNhapHangServiceImpl.class);

    private final PhieuNhapHangRepository phieuNhapHangRepository;
    private final NhaCungCapRepository nhaCungCapRepository;
    private final NhanVienRepository nhanVienRepository;

    @Autowired
    public PhieuNhapHangServiceImpl(PhieuNhapHangRepository phieuNhapHangRepository,
                                    NhaCungCapRepository nhaCungCapRepository,
                                    NhanVienRepository nhanVienRepository) {
        this.phieuNhapHangRepository = phieuNhapHangRepository;
        this.nhaCungCapRepository = nhaCungCapRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public PhieuNhapHangDto createPhieuNhapHang(PhieuNhapHangDto phieuNhapHangDto) {
        logger.info("Creating new PhieuNhapHang for supplier: {}", phieuNhapHangDto.getMaNhaCungCap());
        PhieuNhapHang phieuNhapHang = mapToEntity(phieuNhapHangDto);
        phieuNhapHang.setNhaCungCap(nhaCungCapRepository.findById(phieuNhapHangDto.getMaNhaCungCap())
                .orElseThrow(() -> {
                    logger.error("NhaCungCap not found with id: {}", phieuNhapHangDto.getMaNhaCungCap());
                    return new RuntimeException("NhaCungCap not found");
                }));
        if (phieuNhapHangDto.getNguoiTaoId() != null) {
            phieuNhapHang.setNguoiTao(nhanVienRepository.findById(phieuNhapHangDto.getNguoiTaoId())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", phieuNhapHangDto.getNguoiTaoId());
                        return new RuntimeException("NguoiTao not found");
                    }));
        }
        if (phieuNhapHangDto.getNguoiCapNhatId() != null) {
            phieuNhapHang.setNguoiCapNhat(nhanVienRepository.findById(phieuNhapHangDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", phieuNhapHangDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }
        phieuNhapHang = phieuNhapHangRepository.save(phieuNhapHang);
        logger.info("PhieuNhapHang created with id: {}", phieuNhapHang.getMaPhieuNhap());
        return mapToDto(phieuNhapHang);
    }

    @Override
    public PhieuNhapHangDto getPhieuNhapHangById(Integer id) {
        logger.info("Fetching PhieuNhapHang with id: {}", id);
        PhieuNhapHang phieuNhapHang = phieuNhapHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhieuNhapHang not found with id: {}", id);
                    return new RuntimeException("PhieuNhapHang not found with id: " + id);
                });
        return mapToDto(phieuNhapHang);
    }

    @Override
    public List<PhieuNhapHangDto> getAllPhieuNhapHang() {
        logger.info("Fetching all PhieuNhapHang");
        return phieuNhapHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PhieuNhapHangDto updatePhieuNhapHang(Integer id, PhieuNhapHangDto phieuNhapHangDto) {
        logger.info("Updating PhieuNhapHang with id: {}", id);
        PhieuNhapHang phieuNhapHang = phieuNhapHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhieuNhapHang not found with id: {}", id);
                    return new RuntimeException("PhieuNhapHang not found with id: " + id);
                });

        phieuNhapHang.setNhaCungCap(nhaCungCapRepository.findById(phieuNhapHangDto.getMaNhaCungCap())
                .orElseThrow(() -> {
                    logger.error("NhaCungCap not found with id: {}", phieuNhapHangDto.getMaNhaCungCap());
                    return new RuntimeException("NhaCungCap not found");
                }));
        phieuNhapHang.setNgayNhap(phieuNhapHangDto.getNgayNhap());
        phieuNhapHang.setTongTien(phieuNhapHangDto.getTongTien());
        phieuNhapHang.setTrangThai(phieuNhapHangDto.getTrangThai());
        phieuNhapHang.setNgayCapNhat(phieuNhapHangDto.getNgayCapNhat() != null ? phieuNhapHangDto.getNgayCapNhat() : LocalDateTime.now());

        if (phieuNhapHangDto.getNguoiCapNhatId() != null) {
            phieuNhapHang.setNguoiCapNhat(nhanVienRepository.findById(phieuNhapHangDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", phieuNhapHangDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }

        phieuNhapHang = phieuNhapHangRepository.save(phieuNhapHang);
        logger.info("PhieuNhapHang updated with id: {}", phieuNhapHang.getMaPhieuNhap());
        return mapToDto(phieuNhapHang);
    }

    @Override
    public void deletePhieuNhapHang(Integer id) {
        logger.info("Deleting PhieuNhapHang with id: {}", id);
        PhieuNhapHang phieuNhapHang = phieuNhapHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhieuNhapHang not found with id: {}", id);
                    return new RuntimeException("PhieuNhapHang not found with id: " + id);
                });
        phieuNhapHangRepository.delete(phieuNhapHang);
        logger.info("PhieuNhapHang deleted with id: {}", id);
    }

    private PhieuNhapHangDto mapToDto(PhieuNhapHang phieuNhapHang) {
        return PhieuNhapHangDto.builder()
                .maPhieuNhap(phieuNhapHang.getMaPhieuNhap())
                .maNhaCungCap(phieuNhapHang.getNhaCungCap().getMaNhaCungCap())
                .ngayNhap(phieuNhapHang.getNgayNhap())
                .tongTien(phieuNhapHang.getTongTien())
                .trangThai(phieuNhapHang.getTrangThai())
                .ngayTao(phieuNhapHang.getNgayTao())
                .ngayCapNhat(phieuNhapHang.getNgayCapNhat())
                .nguoiTaoId(phieuNhapHang.getNguoiTao() != null ? phieuNhapHang.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(phieuNhapHang.getNguoiCapNhat() != null ? phieuNhapHang.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private PhieuNhapHang mapToEntity(PhieuNhapHangDto phieuNhapHangDto) {
        return PhieuNhapHang.builder()
                .maPhieuNhap(phieuNhapHangDto.getMaPhieuNhap())
                .ngayNhap(phieuNhapHangDto.getNgayNhap())
                .tongTien(phieuNhapHangDto.getTongTien())
                .trangThai(phieuNhapHangDto.getTrangThai())
                .ngayTao(phieuNhapHangDto.getNgayTao() != null ? phieuNhapHangDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(phieuNhapHangDto.getNgayCapNhat() != null ? phieuNhapHangDto.getNgayCapNhat() : LocalDateTime.now())
                .build();
    }
}
