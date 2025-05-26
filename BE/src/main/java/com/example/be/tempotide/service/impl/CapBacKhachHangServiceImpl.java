package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.capbackhachhangdto;
import com.example.be.tempotide.entity.capbackhachhang;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.repository.capbackhachhangrepository;
import com.example.be.tempotide.repository.nhanvienrepository;
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
public class capbackhachhangserviceImpl implements com.example.be.tempotide.service.capbackhachhangservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.capbackhachhangserviceImpl.class);

    private final capbackhachhangrepository capBacKhachHangRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public capbackhachhangserviceImpl(capbackhachhangrepository capBacKhachHangRepository,
                                      nhanvienrepository nhanVienRepository) {
        this.capBacKhachHangRepository = capBacKhachHangRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public capbackhachhangdto createCapBacKhachHang(capbackhachhangdto capBacKhachHangDto) {
        logger.info("Creating new CapBacKhachHang for TenCapBac: {}", capBacKhachHangDto.getTenCapBac());
        capbackhachhang capBacKhachHang = mapToEntity(capBacKhachHangDto);

        if (capBacKhachHangDto.getNguoiTao() != null) {
            nhanvien nguoiTao = nhanVienRepository.findById(capBacKhachHangDto.getNguoiTao())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", capBacKhachHangDto.getNguoiTao());
                        return new RuntimeException("NguoiTao not found");
                    });
            capBacKhachHang.setNguoiTaoNhanvien(nguoiTao);
        }

        capBacKhachHang.setNgayTao(LocalDateTime.now());
        capBacKhachHang = capBacKhachHangRepository.save(capBacKhachHang);
        logger.info("CapBacKhachHang created with id: {}", capBacKhachHang.getMaCapBac());
        return mapToDto(capBacKhachHang);
    }

    @Override
    public capbackhachhangdto getCapBacKhachHangById(Integer id) {
        logger.info("Fetching CapBacKhachHang with id: {}", id);
        capbackhachhang capBacKhachHang = capBacKhachHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("CapBacKhachHang not found with id: {}", id);
                    return new RuntimeException("CapBacKhachHang not found with id: " + id);
                });
        return mapToDto(capBacKhachHang);
    }

    @Override
    public List<capbackhachhangdto> getAllCapBacKhachHang() {
        logger.info("Fetching all CapBacKhachHang");
        return capBacKhachHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public capbackhachhangdto updateCapBacKhachHang(Integer id, capbackhachhangdto capBacKhachHangDto) {
        logger.info("Updating CapBacKhachHang with id: {}", id);
        capbackhachhang capBacKhachHang = capBacKhachHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("CapBacKhachHang not found with id: {}", id);
                    return new RuntimeException("CapBacKhachHang not found with id: " + id);
                });

        capBacKhachHang.setTenCapBac(capBacKhachHangDto.getTenCapBac());
        capBacKhachHang.setDiemToiThieu(capBacKhachHangDto.getDiemToiThieu());
        capBacKhachHang.setGiamGiaMacDinh(capBacKhachHangDto.getGiamGiaMacDinh());
        capBacKhachHang.setNgayTao(capBacKhachHangDto.getNgayTao());
        capBacKhachHang.setTrangThai(capBacKhachHangDto.getTrangThai());

        if (capBacKhachHangDto.getNguoiTao() != null) {
            nhanvien nguoiTao = nhanVienRepository.findById(capBacKhachHangDto.getNguoiTao())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", capBacKhachHangDto.getNguoiTao());
                        return new RuntimeException("NguoiTao not found");
                    });
            capBacKhachHang.setNguoiTaoNhanvien(nguoiTao);
        }

        capBacKhachHang = capBacKhachHangRepository.save(capBacKhachHang);
        logger.info("CapBacKhachHang updated with id: {}", capBacKhachHang.getMaCapBac());
        return mapToDto(capBacKhachHang);
    }

    @Override
    public void deleteCapBacKhachHang(Integer id) {
        logger.info("Deleting CapBacKhachHang with id: {}", id);
        capbackhachhang capBacKhachHang = capBacKhachHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("CapBacKhachHang not found with id: {}", id);
                    return new RuntimeException("CapBacKhachHang not found with id: " + id);
                });
        capBacKhachHangRepository.delete(capBacKhachHang);
        logger.info("CapBacKhachHang deleted with id: {}", id);
    }

    private capbackhachhangdto mapToDto(capbackhachhang capBacKhachHang) {
        return capbackhachhangdto.builder()
                .maCapBac(capBacKhachHang.getMaCapBac())
                .tenCapBac(capBacKhachHang.getTenCapBac())
                .diemToiThieu(capBacKhachHang.getDiemToiThieu())
                .giamGiaMacDinh(capBacKhachHang.getGiamGiaMacDinh())
                .ngayTao(capBacKhachHang.getNgayTao())
                .trangThai(capBacKhachHang.getTrangThai())
                .nguoiTao(capBacKhachHang.getNguoiTao())
                .build();
    }

    private capbackhachhang mapToEntity(capbackhachhangdto capBacKhachHangDto) {
        return capbackhachhang.builder()
                .maCapBac(capBacKhachHangDto.getMaCapBac())
                .tenCapBac(capBacKhachHangDto.getTenCapBac())
                .diemToiThieu(capBacKhachHangDto.getDiemToiThieu())
                .giamGiaMacDinh(capBacKhachHangDto.getGiamGiaMacDinh())
                .ngayTao(capBacKhachHangDto.getNgayTao())
                .trangThai(capBacKhachHangDto.getTrangThai())
                .nguoiTao(capBacKhachHangDto.getNguoiTao())
                .build();
    }
}