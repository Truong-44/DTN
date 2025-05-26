package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.NhaCungCapDto;
import com.example.be.TempoTide.entity.NhaCungCap;
import com.example.be.TempoTide.repository.NhaCungCapRepository;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.service.NhaCungCapService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NhaCungCapServiceImpl implements NhaCungCapService {

    private static final Logger logger = LoggerFactory.getLogger(NhaCungCapServiceImpl.class);

    private final NhaCungCapRepository nhaCungCapRepository;
    private final NhanVienRepository nhanVienRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public NhaCungCapServiceImpl(NhaCungCapRepository nhaCungCapRepository, NhanVienRepository nhanVienRepository,
                                 PasswordEncoder passwordEncoder) {
        this.nhaCungCapRepository = nhaCungCapRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public NhaCungCapDto createNhaCungCap(NhaCungCapDto nhaCungCapDto) {
        logger.info("Creating new NhaCungCap: {}", nhaCungCapDto.getTenNhaCungCap());
        NhaCungCap nhaCungCap = mapToEntity(nhaCungCapDto);
        nhaCungCap.setMatKhau(nhaCungCapDto.getMatKhau() != null ? nhaCungCapDto.getMatKhau() : "defaultPassword", passwordEncoder);
        if (nhaCungCapDto.getNguoiTaoId() != null) {
            nhaCungCap.setNguoiTao(nhanVienRepository.findById(nhaCungCapDto.getNguoiTaoId())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", nhaCungCapDto.getNguoiTaoId());
                        return new RuntimeException("NguoiTao not found");
                    }));
        }
        if (nhaCungCapDto.getNguoiCapNhatId() != null) {
            nhaCungCap.setNguoiCapNhat(nhanVienRepository.findById(nhaCungCapDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", nhaCungCapDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }
        nhaCungCap = nhaCungCapRepository.save(nhaCungCap);
        logger.info("NhaCungCap created with id: {}", nhaCungCap.getMaNhaCungCap());
        return mapToDto(nhaCungCap);
    }

    @Override
    public NhaCungCapDto getNhaCungCapById(Integer id) {
        logger.info("Fetching NhaCungCap with id: {}", id);
        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("NhaCungCap not found with id: {}", id);
                    return new RuntimeException("NhaCungCap not found with id: " + id);
                });
        return mapToDto(nhaCungCap);
    }

    @Override
    public List<NhaCungCapDto> getAllNhaCungCap() {
        logger.info("Fetching all NhaCungCap");
        return nhaCungCapRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NhaCungCapDto updateNhaCungCap(Integer id, NhaCungCapDto nhaCungCapDto) {
        logger.info("Updating NhaCungCap with id: {}", id);
        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("NhaCungCap not found with id: {}", id);
                    return new RuntimeException("NhaCungCap not found with id: " + id);
                });

        nhaCungCap.setTenNhaCungCap(nhaCungCapDto.getTenNhaCungCap());
        nhaCungCap.setEmail(nhaCungCapDto.getEmail());
        nhaCungCap.setSoDienThoai(nhaCungCapDto.getSoDienThoai());
        nhaCungCap.setDiaChi(nhaCungCapDto.getDiaChi());
        if (nhaCungCapDto.getMatKhau() != null && !nhaCungCapDto.getMatKhau().isBlank()) {
            nhaCungCap.setMatKhau(nhaCungCapDto.getMatKhau(), passwordEncoder);
        }
        nhaCungCap.setTrangThai(nhaCungCapDto.getTrangThai());
        nhaCungCap.setNgayCapNhat(nhaCungCapDto.getNgayCapNhat() != null ? nhaCungCapDto.getNgayCapNhat() : LocalDateTime.now());

        if (nhaCungCapDto.getNguoiCapNhatId() != null) {
            nhaCungCap.setNguoiCapNhat(nhanVienRepository.findById(nhaCungCapDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", nhaCungCapDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }

        nhaCungCap = nhaCungCapRepository.save(nhaCungCap);
        logger.info("NhaCungCap updated with id: {}", nhaCungCap.getMaNhaCungCap());
        return mapToDto(nhaCungCap);
    }

    @Override
    public void deleteNhaCungCap(Integer id) {
        logger.info("Deleting NhaCungCap with id: {}", id);
        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("NhaCungCap not found with id: {}", id);
                    return new RuntimeException("NhaCungCap not found with id: " + id);
                });
        nhaCungCapRepository.delete(nhaCungCap);
        logger.info("NhaCungCap deleted with id: {}", id);
    }

    private NhaCungCapDto mapToDto(NhaCungCap nhaCungCap) {
        return NhaCungCapDto.builder()
                .maNhaCungCap(nhaCungCap.getMaNhaCungCap())
                .tenNhaCungCap(nhaCungCap.getTenNhaCungCap())
                .email(nhaCungCap.getEmail())
                .soDienThoai(nhaCungCap.getSoDienThoai())
                .diaChi(nhaCungCap.getDiaChi())
                .matKhau(null) // Do not expose password
                .ngayTao(nhaCungCap.getNgayTao())
                .ngayCapNhat(nhaCungCap.getNgayCapNhat())
                .trangThai(nhaCungCap.getTrangThai())
                .nguoiTaoId(nhaCungCap.getNguoiTao() != null ? nhaCungCap.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(nhaCungCap.getNguoiCapNhat() != null ? nhaCungCap.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private NhaCungCap mapToEntity(NhaCungCapDto nhaCungCapDto) {
        return NhaCungCap.builder()
                .maNhaCungCap(nhaCungCapDto.getMaNhaCungCap())
                .tenNhaCungCap(nhaCungCapDto.getTenNhaCungCap())
                .email(nhaCungCapDto.getEmail())
                .soDienThoai(nhaCungCapDto.getSoDienThoai())
                .diaChi(nhaCungCapDto.getDiaChi())
                .matKhau(nhaCungCapDto.getMatKhau())
                .ngayTao(nhaCungCapDto.getNgayTao() != null ? nhaCungCapDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(nhaCungCapDto.getNgayCapNhat() != null ? nhaCungCapDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(nhaCungCapDto.getTrangThai() != null ? nhaCungCapDto.getTrangThai() : true)
                .build();
    }
}