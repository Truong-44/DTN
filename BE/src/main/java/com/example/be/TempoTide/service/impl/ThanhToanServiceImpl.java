package com.example.be.TempoTide.service.impl;


import com.example.be.TempoTide.dto.ThanhToanDto;
import com.example.be.TempoTide.entity.ThanhToan;
import com.example.be.TempoTide.repository.DonHangRepository;
import com.example.be.TempoTide.repository.ThanhToanRepository;
import com.example.be.TempoTide.service.ThanhToanService;
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
public class ThanhToanServiceImpl implements ThanhToanService {

    private static final Logger logger = LoggerFactory.getLogger(ThanhToanServiceImpl.class);

    private final ThanhToanRepository thanhToanRepository;
    private final DonHangRepository donHangRepository;

    @Autowired
    public ThanhToanServiceImpl(ThanhToanRepository thanhToanRepository, DonHangRepository donHangRepository) {
        this.thanhToanRepository = thanhToanRepository;
        this.donHangRepository = donHangRepository;
    }

    @Override
    public ThanhToanDto createThanhToan(ThanhToanDto thanhToanDto) {
        logger.info("Creating new ThanhToan for DonHang: {}", thanhToanDto.getMaDonHang());
        ThanhToan thanhToan = mapToEntity(thanhToanDto);
        thanhToan.setDonHang(donHangRepository.findById(thanhToanDto.getMaDonHang())
                .orElseThrow(() -> {
                    logger.error("DonHang not found with id: {}", thanhToanDto.getMaDonHang());
                    return new RuntimeException("DonHang not found");
                }));
        thanhToan = thanhToanRepository.save(thanhToan);
        logger.info("ThanhToan created with id: {}", thanhToan.getMaThanhToan());
        return mapToDto(thanhToan);
    }

    @Override
    public ThanhToanDto getThanhToanById(Integer id) {
        logger.info("Fetching ThanhToan with id: {}", id);
        ThanhToan thanhToan = thanhToanRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThanhToan not found with id: {}", id);
                    return new RuntimeException("ThanhToan not found with id: " + id);
                });
        return mapToDto(thanhToan);
    }

    @Override
    public List<ThanhToanDto> getAllThanhToan() {
        logger.info("Fetching all ThanhToan");
        return thanhToanRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ThanhToanDto updateThanhToan(Integer id, ThanhToanDto thanhToanDto) {
        logger.info("Updating ThanhToan with id: {}", id);
        ThanhToan thanhToan = thanhToanRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThanhToan not found with id: {}", id);
                    return new RuntimeException("ThanhToan not found with id: " + id);
                });

        thanhToan.setDonHang(donHangRepository.findById(thanhToanDto.getMaDonHang())
                .orElseThrow(() -> {
                    logger.error("DonHang not found with id: {}", thanhToanDto.getMaDonHang());
                    return new RuntimeException("DonHang not found");
                }));
        thanhToan.setSoTien(thanhToanDto.getSoTien());
        thanhToan.setPhuongThucThanhToan(thanhToanDto.getPhuongThucThanhToan());
        thanhToan.setTrangThai(thanhToanDto.getTrangThai());
        thanhToan.setNgayThanhToan(thanhToanDto.getNgayThanhToan());
        thanhToan.setNgayCapNhat(thanhToanDto.getNgayCapNhat() != null ? thanhToanDto.getNgayCapNhat() : LocalDateTime.now());

        thanhToan = thanhToanRepository.save(thanhToan);
        logger.info("ThanhToan updated with id: {}", thanhToan.getMaThanhToan());
        return mapToDto(thanhToan);
    }

    @Override
    public void deleteThanhToan(Integer id) {
        logger.info("Deleting ThanhToan with id: {}", id);
        ThanhToan thanhToan = thanhToanRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThanhToan not found with id: {}", id);
                    return new RuntimeException("ThanhToan not found with id: " + id);
                });
        thanhToanRepository.delete(thanhToan);
        logger.info("ThanhToan deleted with id: {}", id);
    }

    private ThanhToanDto mapToDto(ThanhToan thanhToan) {
        return ThanhToanDto.builder()
                .maThanhToan(thanhToan.getMaThanhToan())
                .maDonHang(thanhToan.getDonHang().getMaDonHang())
                .soTien(thanhToan.getSoTien())
                .phuongThucThanhToan(thanhToan.getPhuongThucThanhToan())
                .trangThai(thanhToan.getTrangThai())
                .ngayThanhToan(thanhToan.getNgayThanhToan())
                .ngayTao(thanhToan.getNgayTao())
                .ngayCapNhat(thanhToan.getNgayCapNhat())
                .build();
    }

    private ThanhToan mapToEntity(ThanhToanDto thanhToanDto) {
        return ThanhToan.builder()
                .maThanhToan(thanhToanDto.getMaThanhToan())
                .soTien(thanhToanDto.getSoTien())
                .phuongThucThanhToan(thanhToanDto.getPhuongThucThanhToan())
                .trangThai(thanhToanDto.getTrangThai())
                .ngayThanhToan(thanhToanDto.getNgayThanhToan())
                .ngayTao(thanhToanDto.getNgayTao() != null ? thanhToanDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(thanhToanDto.getNgayCapNhat() != null ? thanhToanDto.getNgayCapNhat() : LocalDateTime.now())
                .build();
    }
}