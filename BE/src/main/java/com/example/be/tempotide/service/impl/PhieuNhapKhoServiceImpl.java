package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.phieunhapkhodto;
import com.example.be.tempotide.entity.khohang;
import com.example.be.tempotide.entity.nhacungcap;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.entity.phieunhapkho;
import com.example.be.tempotide.repository.khohangrepository;
import com.example.be.tempotide.repository.nhacungcaprepository;
import com.example.be.tempotide.repository.nhanvienrepository;
import com.example.be.tempotide.repository.phieunhapkhorepository;
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
public class phieunhapkhoserviceImpl implements com.example.be.tempotide.service.phieunhapkhoservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.phieunhapkhoserviceImpl.class);

    private final phieunhapkhorepository phieuNhapKhoRepository;
    private final khohangrepository khoHangRepository;
    private final nhacungcaprepository nhaCungCapRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public phieunhapkhoserviceImpl(phieunhapkhorepository phieuNhapKhoRepository,
                                   khohangrepository khoHangRepository,
                                   nhacungcaprepository nhaCungCapRepository,
                                   nhanvienrepository nhanVienRepository) {
        this.phieuNhapKhoRepository = phieuNhapKhoRepository;
        this.khoHangRepository = khoHangRepository;
        this.nhaCungCapRepository = nhaCungCapRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public phieunhapkhodto createPhieuNhapKho(phieunhapkhodto phieuNhapKhoDto) {
        logger.info("Creating new PhieuNhapKho for MaKho: {}", phieuNhapKhoDto.getMaKho());
        phieunhapkho phieuNhapKho = mapToEntity(phieuNhapKhoDto);

        khohang khoHang = khoHangRepository.findById(phieuNhapKhoDto.getMaKho())
                .orElseThrow(() -> {
                    logger.error("KhoHang not found with id: {}", phieuNhapKhoDto.getMaKho());
                    return new RuntimeException("KhoHang not found");
                });
        phieuNhapKho.setKhoHang(khoHang);

        nhacungcap nhaCungCap = nhaCungCapRepository.findById(phieuNhapKhoDto.getMaNhaCungCap())
                .orElseThrow(() -> {
                    logger.error("NhaCungCap not found with id: {}", phieuNhapKhoDto.getMaNhaCungCap());
                    return new RuntimeException("NhaCungCap not found");
                });
        phieuNhapKho.setNhaCungCap(nhaCungCap);

        if (phieuNhapKhoDto.getNguoiTao() != null) {
            nhanvien nguoiTao = nhanVienRepository.findById(phieuNhapKhoDto.getNguoiTao())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", phieuNhapKhoDto.getNguoiTao());
                        return new RuntimeException("NhanVien not found");
                    });
            phieuNhapKho.setNguoiTao(nguoiTao);
        }
        if (phieuNhapKhoDto.getNguoiCapNhat() != null) {
            nhanvien nguoiCapNhat = nhanVienRepository.findById(phieuNhapKhoDto.getNguoiCapNhat())
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
    public phieunhapkhodto getPhieuNhapKhoById(Integer id) {
        logger.info("Fetching PhieuNhapKho with id: {}", id);
        phieunhapkho phieuNhapKho = phieuNhapKhoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhieuNhapKho not found with id: {}", id);
                    return new RuntimeException("PhieuNhapKho not found with id: " + id);
                });
        return mapToDto(phieuNhapKho);
    }

    @Override
    public List<phieunhapkhodto> getAllPhieuNhapKho() {
        logger.info("Fetching all PhieuNhapKho");
        return phieuNhapKhoRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public phieunhapkhodto updatePhieuNhapKho(Integer id, phieunhapkhodto phieuNhapKhoDto) {
        logger.info("Updating PhieuNhapKho with id: {}", id);
        phieunhapkho phieuNhapKho = phieuNhapKhoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhieuNhapKho not found with id: {}", id);
                    return new RuntimeException("PhieuNhapKho not found with id: " + id);
                });

        phieuNhapKho.setMaKho(phieuNhapKhoDto.getMaKho());
        phieuNhapKho.setMaNhaCungCap(phieuNhapKhoDto.getMaNhaCungCap());
        phieuNhapKho.setTongTien(phieuNhapKhoDto.getTongTien());
        phieuNhapKho.setGhiChu(phieuNhapKhoDto.getGhiChu());
        phieuNhapKho.setTrangThai(phieuNhapKhoDto.getTrangThai());

        khohang khoHang = khoHangRepository.findById(phieuNhapKhoDto.getMaKho())
                .orElseThrow(() -> {
                    logger.error("KhoHang not found with id: {}", phieuNhapKhoDto.getMaKho());
                    return new RuntimeException("KhoHang not found");
                });
        phieuNhapKho.setKhoHang(khoHang);

        nhacungcap nhaCungCap = nhaCungCapRepository.findById(phieuNhapKhoDto.getMaNhaCungCap())
                .orElseThrow(() -> {
                    logger.error("NhaCungCap not found with id: {}", phieuNhapKhoDto.getMaNhaCungCap());
                    return new RuntimeException("NhaCungCap not found");
                });
        phieuNhapKho.setNhaCungCap(nhaCungCap);

        if (phieuNhapKhoDto.getNguoiCapNhat() != null) {
            nhanvien nguoiCapNhat = nhanVienRepository.findById(phieuNhapKhoDto.getNguoiCapNhat())
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
        phieunhapkho phieuNhapKho = phieuNhapKhoRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhieuNhapKho not found with id: {}", id);
                    return new RuntimeException("PhieuNhapKho not found with id: " + id);
                });
        phieuNhapKhoRepository.delete(phieuNhapKho);
        logger.info("PhieuNhapKho deleted with id: {}", id);
    }

    private phieunhapkhodto mapToDto(phieunhapkho phieuNhapKho) {
        return phieunhapkhodto.builder()
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

    private phieunhapkho mapToEntity(phieunhapkhodto phieuNhapKhoDto) {
        return phieunhapkho.builder()
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