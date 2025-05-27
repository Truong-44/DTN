package com.example.be.tempotide.service.impl;


import com.example.be.tempotide.dto.lichsudonhangdto;
import com.example.be.tempotide.entity.donhang;
import com.example.be.tempotide.entity.lichsudonhang;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.repository.donhangrepository;
import com.example.be.tempotide.repository.lichsudonhangrepository;
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
public class lichsudonhangserviceImpl implements com.example.be.tempotide.service.lichsudonhangservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.lichsudonhangserviceImpl.class);

    private final lichsudonhangrepository lichSuDonHangRepository;
    private final donhangrepository donHangRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public lichsudonhangserviceImpl(lichsudonhangrepository lichSuDonHangRepository,
                                    donhangrepository donHangRepository,
                                    nhanvienrepository nhanVienRepository) {
        this.lichSuDonHangRepository = lichSuDonHangRepository;
        this.donHangRepository = donHangRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public lichsudonhangdto createLichSuDonHang(lichsudonhangdto lichSuDonHangDto) {
        logger.info("Creating new LichSuDonHang for MaDonHang: {}", lichSuDonHangDto.getMaDonHang());
        lichsudonhang lichSuDonHang = mapToEntity(lichSuDonHangDto);

        donhang donHang = donHangRepository.findById(lichSuDonHangDto.getMaDonHang())
                .orElseThrow(() -> {
                    logger.error("DonHang not found with id: {}", lichSuDonHangDto.getMaDonHang());
                    return new RuntimeException("DonHang not found");
                });
        lichSuDonHang.setDonHang(donHang);

        if (lichSuDonHangDto.getNguoiCapNhat() != null) {
            nhanvien nguoiCapNhat = nhanVienRepository.findById(lichSuDonHangDto.getNguoiCapNhat())
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
    public lichsudonhangdto getLichSuDonHangById(Integer id) {
        logger.info("Fetching LichSuDonHang with id: {}", id);
        lichsudonhang lichSuDonHang = lichSuDonHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("LichSuDonHang not found with id: {}", id);
                    return new RuntimeException("LichSuDonHang not found with id: " + id);
                });
        return mapToDto(lichSuDonHang);
    }

    @Override
    public List<lichsudonhangdto> getAllLichSuDonHang() {
        logger.info("Fetching all LichSuDonHang");
        return lichSuDonHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public lichsudonhangdto updateLichSuDonHang(Integer id, lichsudonhangdto lichSuDonHangDto) {
        logger.info("Updating LichSuDonHang with id: {}", id);
        lichsudonhang lichSuDonHang = lichSuDonHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("LichSuDonHang not found with id: {}", id);
                    return new RuntimeException("LichSuDonHang not found with id: " + id);
                });

        lichSuDonHang.setTrangThaiCu(lichSuDonHangDto.getTrangThaiCu());
        lichSuDonHang.setTrangThaiMoi(lichSuDonHangDto.getTrangThaiMoi());
        lichSuDonHang.setLyDo(lichSuDonHangDto.getLyDo());
        if (lichSuDonHangDto.getNguoiCapNhat() != null) {
            nhanvien nguoiCapNhat = nhanVienRepository.findById(lichSuDonHangDto.getNguoiCapNhat())
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
        lichsudonhang lichSuDonHang = lichSuDonHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("LichSuDonHang not found with id: {}", id);
                    return new RuntimeException("LichSuDonHang not found with id: " + id);
                });
        lichSuDonHangRepository.delete(lichSuDonHang);
        logger.info("LichSuDonHang deleted with id: {}", id);
    }

    private lichsudonhangdto mapToDto(lichsudonhang lichSuDonHang) {
        return lichsudonhangdto.builder()
                .maLichSu(lichSuDonHang.getMaLichSu())
                .maDonHang(lichSuDonHang.getMaDonHang())
                .trangThaiCu(lichSuDonHang.getTrangThaiCu())
                .trangThaiMoi(lichSuDonHang.getTrangThaiMoi())
                .lyDo(lichSuDonHang.getLyDo())
                .ngayCapNhat(lichSuDonHang.getNgayCapNhat())
                .nguoiCapNhat(lichSuDonHang.getNguoiCapNhat() != null ? lichSuDonHang.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private lichsudonhang mapToEntity(lichsudonhangdto lichSuDonHangDto) {
        return lichsudonhang.builder()
                .maLichSu(lichSuDonHangDto.getMaLichSu())
                .maDonHang(lichSuDonHangDto.getMaDonHang())
                .trangThaiCu(lichSuDonHangDto.getTrangThaiCu())
                .trangThaiMoi(lichSuDonHangDto.getTrangThaiMoi())
                .lyDo(lichSuDonHangDto.getLyDo())
                .ngayCapNhat(lichSuDonHangDto.getNgayCapNhat() != null ? lichSuDonHangDto.getNgayCapNhat() : LocalDateTime.now())
                .build();
    }
}