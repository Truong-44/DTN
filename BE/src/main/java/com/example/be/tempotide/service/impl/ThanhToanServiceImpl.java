package com.example.be.tempotide.service.impl;


import com.example.be.tempotide.dto.thanhtoandto;
import com.example.be.tempotide.entity.thanhtoan;
import com.example.be.tempotide.repository.donhangrepository;
import com.example.be.tempotide.repository.thanhtoanrepository;
import com.example.be.tempotide.service.ThanhToanService;
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

    private final thanhtoanrepository thanhToanRepository;
    private final donhangrepository donHangRepository;

    @Autowired
    public ThanhToanServiceImpl(thanhtoanrepository thanhToanRepository, donhangrepository donHangRepository) {
        this.thanhToanRepository = thanhToanRepository;
        this.donHangRepository = donHangRepository;
    }

    @Override
    public thanhtoandto createThanhToan(thanhtoandto thanhToanDto) {
        logger.info("Creating new ThanhToan for DonHang: {}", thanhToanDto.getMaDonHang());
        thanhtoan thanhToan = mapToEntity(thanhToanDto);
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
    public thanhtoandto getThanhToanById(Integer id) {
        logger.info("Fetching ThanhToan with id: {}", id);
        thanhtoan thanhToan = thanhToanRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThanhToan not found with id: {}", id);
                    return new RuntimeException("ThanhToan not found with id: " + id);
                });
        return mapToDto(thanhToan);
    }

    @Override
    public List<thanhtoandto> getAllThanhToan() {
        logger.info("Fetching all ThanhToan");
        return thanhToanRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public thanhtoandto updateThanhToan(Integer id, thanhtoandto thanhToanDto) {
        logger.info("Updating ThanhToan with id: {}", id);
        thanhtoan thanhToan = thanhToanRepository.findById(id)
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
        thanhtoan thanhToan = thanhToanRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThanhToan not found with id: {}", id);
                    return new RuntimeException("ThanhToan not found with id: " + id);
                });
        thanhToanRepository.delete(thanhToan);
        logger.info("ThanhToan deleted with id: {}", id);
    }

    private thanhtoandto mapToDto(thanhtoan thanhToan) {
        return thanhtoandto.builder()
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

    private thanhtoan mapToEntity(thanhtoandto thanhToanDto) {
        return thanhtoan.builder()
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