package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.nhanviendto;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.repository.nhanvienrepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class nhanvienserviceImpl implements com.example.be.tempotide.service.nhanvienservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.nhanvienserviceImpl.class);

    private final nhanvienrepository nhanVienRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public nhanvienserviceImpl(nhanvienrepository nhanVienRepository, PasswordEncoder passwordEncoder) {
        this.nhanVienRepository = nhanVienRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public nhanviendto createNhanVien(nhanviendto nhanVienDto) {
        logger.info("Creating new NhanVien with email: {}", nhanVienDto.getEmail());
        if (nhanVienRepository.findByEmail(nhanVienDto.getEmail()).isPresent()) {
            logger.error("Email already exists: {}", nhanVienDto.getEmail());
            throw new RuntimeException("Email already exists");
        }
        nhanvien nhanVien = mapToEntity(nhanVienDto);
        nhanVien.setMatKhau(nhanVienDto.getMatKhau() != null ? nhanVienDto.getMatKhau() : "defaultPassword", passwordEncoder);
        if (nhanVienDto.getNguoiTaoId() != null) {
            nhanVien.setNguoiTao(nhanVienRepository.findById(nhanVienDto.getNguoiTaoId())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", nhanVienDto.getNguoiTaoId());
                        return new RuntimeException("NguoiTao not found");
                    }));
        }
        if (nhanVienDto.getNguoiCapNhatId() != null) {
            nhanVien.setNguoiCapNhat(nhanVienRepository.findById(nhanVienDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", nhanVienDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }
        nhanVien = nhanVienRepository.save(nhanVien);
        logger.info("NhanVien created with id: {}", nhanVien.getMaNhanVien());
        return mapToDto(nhanVien);
    }

    @Override
    public nhanviendto getNhanVienById(Integer id) {
        logger.info("Fetching NhanVien with id: {}", id);
        nhanvien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", id);
                    return new RuntimeException("NhanVien not found with id: " + id);
                });
        return mapToDto(nhanVien);
    }

    @Override
    public List<nhanviendto> getAllNhanVien() {
        logger.info("Fetching all NhanVien");
        return nhanVienRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public nhanviendto updateNhanVien(Integer id, nhanviendto nhanVienDto) {
        logger.info("Updating NhanVien with id: {}", id);
        nhanvien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", id);
                    return new RuntimeException("NhanVien not found with id: " + id);
                });

        nhanVien.setHo(nhanVienDto.getHo());
        nhanVien.setTen(nhanVienDto.getTen());
        if (!nhanVien.getEmail().equals(nhanVienDto.getEmail()) && nhanVienRepository.findByEmail(nhanVienDto.getEmail()).isPresent()) {
            logger.error("Email already exists: {}", nhanVienDto.getEmail());
            throw new RuntimeException("Email already exists");
        }
        nhanVien.setEmail(nhanVienDto.getEmail());
        nhanVien.setSoDienThoai(nhanVienDto.getSoDienThoai());
        nhanVien.setNgayTuyenDung(nhanVienDto.getNgayTuyenDung());
        nhanVien.setMaSoThue(nhanVienDto.getMaSoThue());
        nhanVien.setNgayNghiViec(nhanVienDto.getNgayNghiViec());
        nhanVien.setTrangThai(nhanVienDto.getTrangThai());
        nhanVien.setNgayCapNhat(nhanVienDto.getNgayCapNhat() != null ? nhanVienDto.getNgayCapNhat() : LocalDateTime.now());
        if (nhanVienDto.getMatKhau() != null && !nhanVienDto.getMatKhau().isBlank()) {
            nhanVien.setMatKhau(nhanVienDto.getMatKhau(), passwordEncoder);
        }

        if (nhanVienDto.getNguoiCapNhatId() != null) {
            nhanVien.setNguoiCapNhat(nhanVienRepository.findById(nhanVienDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", nhanVienDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }

        nhanVien = nhanVienRepository.save(nhanVien);
        logger.info("NhanVien updated with id: {}", nhanVien.getMaNhanVien());
        return mapToDto(nhanVien);
    }

    @Override
    public void deleteNhanVien(Integer id) {
        logger.info("Deleting NhanVien with id: {}", id);
        nhanvien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", id);
                    return new RuntimeException("NhanVien not found with id: " + id);
                });
        nhanVienRepository.delete(nhanVien);
        logger.info("NhanVien deleted with id: {}", id);
    }

    private nhanviendto mapToDto(nhanvien nhanVien) {
        return nhanviendto.builder()
                .maNhanVien(nhanVien.getMaNhanVien())
                .ho(nhanVien.getHo())
                .ten(nhanVien.getTen())
                .email(nhanVien.getEmail())
                .soDienThoai(nhanVien.getSoDienThoai())
                .ngayTuyenDung(nhanVien.getNgayTuyenDung())
                .maSoThue(nhanVien.getMaSoThue())
                .ngayNghiViec(nhanVien.getNgayNghiViec())
                .matKhau(null) // Do not expose password
                .trangThai(nhanVien.getTrangThai())
                .ngayTao(nhanVien.getNgayTao())
                .ngayCapNhat(nhanVien.getNgayCapNhat())
                .nguoiTaoId(nhanVien.getNguoiTao() != null ? nhanVien.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(nhanVien.getNguoiCapNhat() != null ? nhanVien.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private nhanvien mapToEntity(nhanviendto nhanVienDto) {
        return nhanvien.builder()
                .maNhanVien(nhanVienDto.getMaNhanVien())
                .ho(nhanVienDto.getHo())
                .ten(nhanVienDto.getTen())
                .email(nhanVienDto.getEmail())
                .soDienThoai(nhanVienDto.getSoDienThoai())
                .ngayTuyenDung(nhanVienDto.getNgayTuyenDung())
                .maSoThue(nhanVienDto.getMaSoThue())
                .ngayNghiViec(nhanVienDto.getNgayNghiViec())
                .matKhau(nhanVienDto.getMatKhau())
                .ngayTao(nhanVienDto.getNgayTao() != null ? nhanVienDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(nhanVienDto.getNgayCapNhat() != null ? nhanVienDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(nhanVienDto.getTrangThai() != null ? nhanVienDto.getTrangThai() : true)
                .build();
    }
}