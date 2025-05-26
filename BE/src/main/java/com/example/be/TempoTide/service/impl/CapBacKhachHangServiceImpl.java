package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.CapBacKhachHangDto;
import com.example.be.TempoTide.entity.CapBacKhachHang;
import com.example.be.TempoTide.entity.NhanVien;
import com.example.be.TempoTide.repository.CapBacKhachHangRepository;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.service.CapBacKhachHangService;
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
public class CapBacKhachHangServiceImpl implements CapBacKhachHangService {

    private static final Logger logger = LoggerFactory.getLogger(CapBacKhachHangServiceImpl.class);

    private final CapBacKhachHangRepository capBacKhachHangRepository;
    private final NhanVienRepository nhanVienRepository;

    @Autowired
    public CapBacKhachHangServiceImpl(CapBacKhachHangRepository capBacKhachHangRepository,
                                      NhanVienRepository nhanVienRepository) {
        this.capBacKhachHangRepository = capBacKhachHangRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public CapBacKhachHangDto createCapBacKhachHang(CapBacKhachHangDto capBacKhachHangDto) {
        logger.info("Creating new CapBacKhachHang for TenCapBac: {}", capBacKhachHangDto.getTenCapBac());
        CapBacKhachHang capBacKhachHang = mapToEntity(capBacKhachHangDto);

        if (capBacKhachHangDto.getNguoiTao() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(capBacKhachHangDto.getNguoiTao())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", capBacKhachHangDto.getNguoiTao());
                        return new RuntimeException("NguoiTao not found");
                    });
            capBacKhachHang.setNguoiTaoNhanVien(nguoiTao);
        }

        capBacKhachHang.setNgayTao(LocalDateTime.now());
        capBacKhachHang = capBacKhachHangRepository.save(capBacKhachHang);
        logger.info("CapBacKhachHang created with id: {}", capBacKhachHang.getMaCapBac());
        return mapToDto(capBacKhachHang);
    }

    @Override
    public CapBacKhachHangDto getCapBacKhachHangById(Integer id) {
        logger.info("Fetching CapBacKhachHang with id: {}", id);
        CapBacKhachHang capBacKhachHang = capBacKhachHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("CapBacKhachHang not found with id: {}", id);
                    return new RuntimeException("CapBacKhachHang not found with id: " + id);
                });
        return mapToDto(capBacKhachHang);
    }

    @Override
    public List<CapBacKhachHangDto> getAllCapBacKhachHang() {
        logger.info("Fetching all CapBacKhachHang");
        return capBacKhachHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CapBacKhachHangDto updateCapBacKhachHang(Integer id, CapBacKhachHangDto capBacKhachHangDto) {
        logger.info("Updating CapBacKhachHang with id: {}", id);
        CapBacKhachHang capBacKhachHang = capBacKhachHangRepository.findById(id)
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
            NhanVien nguoiTao = nhanVienRepository.findById(capBacKhachHangDto.getNguoiTao())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", capBacKhachHangDto.getNguoiTao());
                        return new RuntimeException("NguoiTao not found");
                    });
            capBacKhachHang.setNguoiTaoNhanVien(nguoiTao);
        }

        capBacKhachHang = capBacKhachHangRepository.save(capBacKhachHang);
        logger.info("CapBacKhachHang updated with id: {}", capBacKhachHang.getMaCapBac());
        return mapToDto(capBacKhachHang);
    }

    @Override
    public void deleteCapBacKhachHang(Integer id) {
        logger.info("Deleting CapBacKhachHang with id: {}", id);
        CapBacKhachHang capBacKhachHang = capBacKhachHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("CapBacKhachHang not found with id: {}", id);
                    return new RuntimeException("CapBacKhachHang not found with id: " + id);
                });
        capBacKhachHangRepository.delete(capBacKhachHang);
        logger.info("CapBacKhachHang deleted with id: {}", id);
    }

    private CapBacKhachHangDto mapToDto(CapBacKhachHang capBacKhachHang) {
        return CapBacKhachHangDto.builder()
                .maCapBac(capBacKhachHang.getMaCapBac())
                .tenCapBac(capBacKhachHang.getTenCapBac())
                .diemToiThieu(capBacKhachHang.getDiemToiThieu())
                .giamGiaMacDinh(capBacKhachHang.getGiamGiaMacDinh())
                .ngayTao(capBacKhachHang.getNgayTao())
                .trangThai(capBacKhachHang.getTrangThai())
                .nguoiTao(capBacKhachHang.getNguoiTao())
                .build();
    }

    private CapBacKhachHang mapToEntity(CapBacKhachHangDto capBacKhachHangDto) {
        return CapBacKhachHang.builder()
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