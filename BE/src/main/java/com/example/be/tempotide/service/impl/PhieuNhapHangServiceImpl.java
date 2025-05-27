package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.phieunhaphangdto;
import com.example.be.tempotide.entity.phieuxuatkho;
import com.example.be.tempotide.repository.nhacungcaprepository;
import com.example.be.tempotide.repository.nhanvienrepository;
import com.example.be.tempotide.repository.phieunhaphangrepository;
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
public class phieunhaphangserviceImpl implements com.example.be.tempotide.service.phieunhaphangservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.phieunhaphangserviceImpl.class);

    private final phieunhaphangrepository phieuNhapHangRepository;
    private final nhacungcaprepository nhaCungCapRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public phieunhaphangserviceImpl(phieunhaphangrepository phieuNhapHangRepository,
                                    nhacungcaprepository nhaCungCapRepository,
                                    nhanvienrepository nhanVienRepository) {
        this.phieuNhapHangRepository = phieuNhapHangRepository;
        this.nhaCungCapRepository = nhaCungCapRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public phieunhaphangdto createPhieuNhapHang(phieunhaphangdto phieuNhapHangDto) {
        logger.info("Creating new PhieuNhapHang for supplier: {}", phieuNhapHangDto.getMaNhaCungCap());
        phieuxuatkho phieuNhapHang = mapToEntity(phieuNhapHangDto);
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
    public phieunhaphangdto getPhieuNhapHangById(Integer id) {
        logger.info("Fetching PhieuNhapHang with id: {}", id);
        phieuxuatkho phieuNhapHang = phieuNhapHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhieuNhapHang not found with id: {}", id);
                    return new RuntimeException("PhieuNhapHang not found with id: " + id);
                });
        return mapToDto(phieuNhapHang);
    }

    @Override
    public List<phieunhaphangdto> getAllPhieuNhapHang() {
        logger.info("Fetching all PhieuNhapHang");
        return phieuNhapHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public phieunhaphangdto updatePhieuNhapHang(Integer id, phieunhaphangdto phieuNhapHangDto) {
        logger.info("Updating PhieuNhapHang with id: {}", id);
        phieuxuatkho phieuNhapHang = phieuNhapHangRepository.findById(id)
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
        phieuxuatkho phieuNhapHang = phieuNhapHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhieuNhapHang not found with id: {}", id);
                    return new RuntimeException("PhieuNhapHang not found with id: " + id);
                });
        phieuNhapHangRepository.delete(phieuNhapHang);
        logger.info("PhieuNhapHang deleted with id: {}", id);
    }

    private phieunhaphangdto mapToDto(phieuxuatkho phieuNhapHang) {
        return phieunhaphangdto.builder()
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

    private phieuxuatkho mapToEntity(phieunhaphangdto phieuNhapHangDto) {
        return phieuxuatkho.builder()
                .maPhieuNhap(phieuNhapHangDto.getMaPhieuNhap())
                .ngayNhap(phieuNhapHangDto.getNgayNhap())
                .tongTien(phieuNhapHangDto.getTongTien())
                .trangThai(phieuNhapHangDto.getTrangThai())
                .ngayTao(phieuNhapHangDto.getNgayTao() != null ? phieuNhapHangDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(phieuNhapHangDto.getNgayCapNhat() != null ? phieuNhapHangDto.getNgayCapNhat() : LocalDateTime.now())
                .build();
    }
}
