package com.example.be.TempoTide.service.impl;


import com.example.be.TempoTide.dto.LichSuDonHangDto;
import com.example.be.TempoTide.entity.DonHang;
import com.example.be.TempoTide.entity.LichSuDonHang;
import com.example.be.TempoTide.entity.NhanVien;
import com.example.be.TempoTide.repository.DonHangRepository;
import com.example.be.TempoTide.repository.LichSuDonHangRepository;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.service.LichSuDonHangService;
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
public class LichSuDonHangServiceImpl implements LichSuDonHangService {

    private static final Logger logger = LoggerFactory.getLogger(LichSuDonHangServiceImpl.class);

    private final LichSuDonHangRepository lichSuDonHangRepository;
    private final DonHangRepository donHangRepository;
    private final NhanVienRepository nhanVienRepository;

    @Autowired
    public LichSuDonHangServiceImpl(LichSuDonHangRepository lichSuDonHangRepository,
                                    DonHangRepository donHangRepository,
                                    NhanVienRepository nhanVienRepository) {
        this.lichSuDonHangRepository = lichSuDonHangRepository;
        this.donHangRepository = donHangRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public LichSuDonHangDto createLichSuDonHang(LichSuDonHangDto lichSuDonHangDto) {
        logger.info("Creating new LichSuDonHang for MaDonHang: {}", lichSuDonHangDto.getMaDonHang());
        LichSuDonHang lichSuDonHang = mapToEntity(lichSuDonHangDto);

        DonHang donHang = donHangRepository.findById(lichSuDonHangDto.getMaDonHang())
                .orElseThrow(() -> {
                    logger.error("DonHang not found with id: {}", lichSuDonHangDto.getMaDonHang());
                    return new RuntimeException("DonHang not found");
                });
        lichSuDonHang.setDonHang(donHang);

        if (lichSuDonHangDto.getNguoiCapNhat() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(lichSuDonHangDto.getNguoiCapNhat())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", lichSuDonHangDto.getNguoiCapNhat());
                        return new RuntimeException("NhanVien not found");
                    });
            lichSuDonHang.setNguoiCapNhat(nguoiCapNhat);
        }

        lichSuDonHang.setNgayCapNhat(LocalDateTime.now());
        lichSuDonHang = lichSuDonHangRepository.save(lichSuDonHang);
        logger.info("LichSuDonHang created with id: {}", lichSuDonHang.getMaLichSu());
        return mapToDto(lichSuDonHang);
    }

    @Override
    public LichSuDonHangDto getLichSuDonHangById(Integer id) {
        logger.info("Fetching LichSuDonHang with id: {}", id);
        LichSuDonHang lichSuDonHang = lichSuDonHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("LichSuDonHang not found with id: {}", id);
                    return new RuntimeException("LichSuDonHang not found with id: " + id);
                });
        return mapToDto(lichSuDonHang);
    }

    @Override
    public List<LichSuDonHangDto> getAllLichSuDonHang() {
        logger.info("Fetching all LichSuDonHang");
        return lichSuDonHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public LichSuDonHangDto updateLichSuDonHang(Integer id, LichSuDonHangDto lichSuDonHangDto) {
        logger.info("Updating LichSuDonHang with id: {}", id);
        LichSuDonHang lichSuDonHang = lichSuDonHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("LichSuDonHang not found with id: {}", id);
                    return new RuntimeException("LichSuDonHang not found with id: " + id);
                });

        lichSuDonHang.setTrangThaiCu(lichSuDonHangDto.getTrangThaiCu());
        lichSuDonHang.setTrangThaiMoi(lichSuDonHangDto.getTrangThaiMoi());
        lichSuDonHang.setLyDo(lichSuDonHangDto.getLyDo());
        if (lichSuDonHangDto.getNguoiCapNhat() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(lichSuDonHangDto.getNguoiCapNhat())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", lichSuDonHangDto.getNguoiCapNhat());
                        return new RuntimeException("NhanVien not found");
                    });
            lichSuDonHang.setNguoiCapNhat(nguoiCapNhat);
        }
        lichSuDonHang.setNgayCapNhat(LocalDateTime.now());

        lichSuDonHang = lichSuDonHangRepository.save(lichSuDonHang);
        logger.info("LichSuDonHang updated with id: {}", lichSuDonHang.getMaLichSu());
        return mapToDto(lichSuDonHang);
    }

    @Override
    public void deleteLichSuDonHang(Integer id) {
        logger.info("Deleting LichSuDonHang with id: {}", id);
        LichSuDonHang lichSuDonHang = lichSuDonHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("LichSuDonHang not found with id: {}", id);
                    return new RuntimeException("LichSuDonHang not found with id: " + id);
                });
        lichSuDonHangRepository.delete(lichSuDonHang);
        logger.info("LichSuDonHang deleted with id: {}", id);
    }

    private LichSuDonHangDto mapToDto(LichSuDonHang lichSuDonHang) {
        return LichSuDonHangDto.builder()
                .maLichSu(lichSuDonHang.getMaLichSu())
                .maDonHang(lichSuDonHang.getMaDonHang())
                .trangThaiCu(lichSuDonHang.getTrangThaiCu())
                .trangThaiMoi(lichSuDonHang.getTrangThaiMoi())
                .lyDo(lichSuDonHang.getLyDo())
                .ngayCapNhat(lichSuDonHang.getNgayCapNhat())
                .nguoiCapNhat(lichSuDonHang.getNguoiCapNhat() != null ? lichSuDonHang.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private LichSuDonHang mapToEntity(LichSuDonHangDto lichSuDonHangDto) {
        return LichSuDonHang.builder()
                .maLichSu(lichSuDonHangDto.getMaLichSu())
                .maDonHang(lichSuDonHangDto.getMaDonHang())
                .trangThaiCu(lichSuDonHangDto.getTrangThaiCu())
                .trangThaiMoi(lichSuDonHangDto.getTrangThaiMoi())
                .lyDo(lichSuDonHangDto.getLyDo())
                .ngayCapNhat(lichSuDonHangDto.getNgayCapNhat() != null ? lichSuDonHangDto.getNgayCapNhat() : LocalDateTime.now())
                .build();
    }
}