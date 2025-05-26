package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.KhoHangDto;
import com.example.be.TempoTide.entity.KhoHang;
import com.example.be.TempoTide.entity.NhanVien;
import com.example.be.TempoTide.repository.KhoHangRepository;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.service.KhoHangService;
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
public class KhoHangServiceImpl implements KhoHangService {

    private static final Logger logger = LoggerFactory.getLogger(KhoHangServiceImpl.class);

    private final KhoHangRepository khoHangRepository;
    private final NhanVienRepository nhanVienRepository;

    @Autowired
    public KhoHangServiceImpl(KhoHangRepository khoHangRepository,
                              NhanVienRepository nhanVienRepository) {
        this.khoHangRepository = khoHangRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public KhoHangDto createKhoHang(KhoHangDto khoHangDto) {
        logger.info("Creating new KhoHang: {}", khoHangDto.getTenKho());
        KhoHang khoHang = mapToEntity(khoHangDto);

        if (khoHangDto.getNguoiTao() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(khoHangDto.getNguoiTao())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", khoHangDto.getNguoiTao());
                        return new RuntimeException("NhanVien not found");
                    });
            khoHang.setNguoiTao(nguoiTao);
        }
        if (khoHangDto.getNguoiCapNhat() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(khoHangDto.getNguoiCapNhat())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", khoHangDto.getNguoiCapNhat());
                        return new RuntimeException("NhanVien not found");
                    });
            khoHang.setNguoiCapNhat(nguoiCapNhat);
        }

        khoHang.setNgayTao(LocalDateTime.now());
        khoHang = khoHangRepository.save(khoHang);
        logger.info("KhoHang created with id: {}", khoHang.getMaKho());
        return mapToDto(khoHang);
    }

    @Override
    public KhoHangDto getKhoHangById(Integer id) {
        logger.info("Fetching KhoHang with id: {}", id);
        KhoHang khoHang = khoHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhoHang not found with id: {}", id);
                    return new RuntimeException("KhoHang not found with id: " + id);
                });
        return mapToDto(khoHang);
    }

    @Override
    public List<KhoHangDto> getAllKhoHang() {
        logger.info("Fetching all KhoHang");
        return khoHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public KhoHangDto updateKhoHang(Integer id, KhoHangDto khoHangDto) {
        logger.info("Updating KhoHang with id: {}", id);
        KhoHang khoHang = khoHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhoHang not found with id: {}", id);
                    return new RuntimeException("KhoHang not found with id: " + id);
                });

        khoHang.setTenKho(khoHangDto.getTenKho());
        khoHang.setDiaChi(khoHangDto.getDiaChi());
        khoHang.setSoDienThoai(khoHangDto.getSoDienThoai());
        khoHang.setDungLuongToiDa(khoHangDto.getDungLuongToiDa());
        khoHang.setDungLuongHienTai(khoHangDto.getDungLuongHienTai());
        khoHang.setTrangThai(khoHangDto.getTrangThai());

        if (khoHangDto.getNguoiCapNhat() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(khoHangDto.getNguoiCapNhat())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", khoHangDto.getNguoiCapNhat());
                        return new RuntimeException("NhanVien not found");
                    });
            khoHang.setNguoiCapNhat(nguoiCapNhat);
        }

        khoHang = khoHangRepository.save(khoHang);
        logger.info("KhoHang updated with id: {}", khoHang.getMaKho());
        return mapToDto(khoHang);
    }

    @Override
    public void deleteKhoHang(Integer id) {
        logger.info("Deleting KhoHang with id: {}", id);
        KhoHang khoHang = khoHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhoHang not found with id: {}", id);
                    return new RuntimeException("KhoHang not found with id: " + id);
                });
        khoHangRepository.delete(khoHang);
        logger.info("KhoHang deleted with id: {}", id);
    }

    private KhoHangDto mapToDto(KhoHang khoHang) {
        return KhoHangDto.builder()
                .maKho(khoHang.getMaKho())
                .tenKho(khoHang.getTenKho())
                .diaChi(khoHang.getDiaChi())
                .soDienThoai(khoHang.getSoDienThoai())
                .dungLuongToiDa(khoHang.getDungLuongToiDa())
                .dungLuongHienTai(khoHang.getDungLuongHienTai())
                .ngayTao(khoHang.getNgayTao())
                .trangThai(khoHang.getTrangThai())
                .nguoiTao(khoHang.getNguoiTao() != null ? khoHang.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhat(khoHang.getNguoiCapNhat() != null ? khoHang.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private KhoHang mapToEntity(KhoHangDto khoHangDto) {
        return KhoHang.builder()
                .maKho(khoHangDto.getMaKho())
                .tenKho(khoHangDto.getTenKho())
                .diaChi(khoHangDto.getDiaChi())
                .soDienThoai(khoHangDto.getSoDienThoai())
                .dungLuongToiDa(khoHangDto.getDungLuongToiDa())
                .dungLuongHienTai(khoHangDto.getDungLuongHienTai())
                .ngayTao(khoHangDto.getNgayTao() != null ? khoHangDto.getNgayTao() : LocalDateTime.now())
                .trangThai(khoHangDto.getTrangThai())
                .build();
    }
}