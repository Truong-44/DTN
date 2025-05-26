package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.NhanVienDto;
import com.example.be.TempoTide.entity.NhanVien;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.service.NhanVienService;
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
public class NhanVienServiceImpl implements NhanVienService {

    private static final Logger logger = LoggerFactory.getLogger(NhanVienServiceImpl.class);

    private final NhanVienRepository nhanVienRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public NhanVienServiceImpl(NhanVienRepository nhanVienRepository, PasswordEncoder passwordEncoder) {
        this.nhanVienRepository = nhanVienRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public NhanVienDto createNhanVien(NhanVienDto nhanVienDto) {
        logger.info("Creating new NhanVien with email: {}", nhanVienDto.getEmail());
        if (nhanVienRepository.findByEmail(nhanVienDto.getEmail()).isPresent()) {
            logger.error("Email already exists: {}", nhanVienDto.getEmail());
            throw new RuntimeException("Email already exists");
        }
        NhanVien nhanVien = mapToEntity(nhanVienDto);
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
    public NhanVienDto getNhanVienById(Integer id) {
        logger.info("Fetching NhanVien with id: {}", id);
        NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", id);
                    return new RuntimeException("NhanVien not found with id: " + id);
                });
        return mapToDto(nhanVien);
    }

    @Override
    public List<NhanVienDto> getAllNhanVien() {
        logger.info("Fetching all NhanVien");
        return nhanVienRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NhanVienDto updateNhanVien(Integer id, NhanVienDto nhanVienDto) {
        logger.info("Updating NhanVien with id: {}", id);
        NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", id);
                    return new RuntimeException("NhanVien not found with id: " + id);
                });

        nhanVien.setHo(nhanVienDto.getHo());
        nhanVien.setTen(nhanVienDto.getTen());
        nhanVien.setEmail(nhanVienDto.getEmail());
        nhanVien.setSoDienThoai(nhanVienDto.getSoDienThoai());
        nhanVien.setNgayTuyenDung(nhanVienDto.getNgayTuyenDung());
        nhanVien.setMaSoThue(nhanVienDto.getMaSoThue());
        nhanVien.setNgayNghiViec(nhanVienDto.getNgayNghiViec());
        nhanVien.setTrangThai(nhanVienDto.getTrangThai());
        nhanVien.setNgayCapNhat(nhanVienDto.getNgayCapNhat() != null ? nhanVienDto.getNgayCapNhat() : LocalDateTime.now());
        if (nhanVienDto.getMatKhau() != null) {
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
        NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", id);
                    return new RuntimeException("NhanVien not found with id: " + id);
                });
        nhanVienRepository.delete(nhanVien);
        logger.info("NhanVien deleted with id: {}", id);
    }

    private NhanVienDto mapToDto(NhanVien nhanVien) {
        return NhanVienDto.builder()
                .maNhanVien(nhanVien.getMaNhanVien())
                .ho(nhanVien.getHo())
                .ten(nhanVien.getTen())
                .email(nhanVien.getEmail())
                .soDienThoai(nhanVien.getSoDienThoai())
                .ngayTuyenDung(nhanVien.getNgayTuyenDung())
                .maSoThue(nhanVien.getMaSoThue())
                .ngayNghiViec(nhanVien.getNgayNghiViec())
                .ngayTao(nhanVien.getNgayTao())
                .ngayCapNhat(nhanVien.getNgayCapNhat())
                .trangThai(nhanVien.getTrangThai())
                .nguoiTaoId(nhanVien.getNguoiTao() != null ? nhanVien.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(nhanVien.getNguoiCapNhat() != null ? nhanVien.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private NhanVien mapToEntity(NhanVienDto nhanVienDto) {
        return NhanVien.builder()
                .maNhanVien(nhanVienDto.getMaNhanVien())
                .ho(nhanVienDto.getHo())
                .ten(nhanVienDto.getTen())
                .email(nhanVienDto.getEmail())
                .soDienThoai(nhanVienDto.getSoDienThoai())
                .ngayTuyenDung(nhanVienDto.getNgayTuyenDung())
                .maSoThue(nhanVienDto.getMaSoThue())
                .ngayNghiViec(nhanVienDto.getNgayNghiViec())
                .ngayTao(nhanVienDto.getNgayTao() != null ? nhanVienDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(nhanVienDto.getNgayCapNhat() != null ? nhanVienDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(nhanVienDto.getTrangThai() != null ? nhanVienDto.getTrangThai() : true)
                .build();
    }
}
