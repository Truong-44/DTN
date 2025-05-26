package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.KhachHangDto;
import com.example.be.TempoTide.entity.KhachHang;
import com.example.be.TempoTide.repository.KhachHangRepository;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.service.KhachHangService;
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
public class KhachHangServiceImpl implements KhachHangService {

    private static final Logger logger = LoggerFactory.getLogger(KhachHangServiceImpl.class);

    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;
    private final CapBacKhachHangRepository capBacKhachHangRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public KhachHangServiceImpl(KhachHangRepository khachHangRepository, NhanVienRepository nhanVienRepository,
                                CapBacKhachHangRepository capBacKhachHangRepository, PasswordEncoder passwordEncoder) {
        this.khachHangRepository = khachHangRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.capBacKhachHangRepository = capBacKhachHangRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public KhachHangDto createKhachHang(KhachHangDto khachHangDto) {
        logger.info("Creating new KhachHang with email: {}", khachHangDto.getEmail());
        if (khachHangRepository.findByEmail(khachHangDto.getEmail()).isPresent()) {
            logger.error("Email already exists: {}", khachHangDto.getEmail());
            throw new RuntimeException("Email already exists");
        }
        KhachHang khachHang = mapToEntity(khachHangDto);
        khachHang.setMatKhau(khachHangDto.getMatKhau() != null ? khachHangDto.getMatKhau() : "defaultPassword", passwordEncoder);
        if (khachHangDto.getNguoiTaoId() != null) {
            khachHang.setNguoiTao(nhanVienRepository.findById(khachHangDto.getNguoiTaoId())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", khachHangDto.getNguoiTaoId());
                        return new RuntimeException("NguoiTao not found");
                    }));
        }
        if (khachHangDto.getNguoiCapNhatId() != null) {
            khachHang.setNguoiCapNhat(nhanVienRepository.findById(khachHangDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", khachHangDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }
        if (khachHangDto.getMaCapBacId() != null) {
            khachHang.setMaCapBac(capBacKhachHangRepository.findById(khachHangDto.getMaCapBacId())
                    .orElseThrow(() -> {
                        logger.error("MaCapBac not found with id: {}", khachHangDto.getMaCapBacId());
                        return new RuntimeException("MaCapBac not found");
                    }));
        }
        khachHang = khachHangRepository.save(khachHang);
        logger.info("KhachHang created with id: {}", khachHang.getMaKhachHang());
        return mapToDto(khachHang);
    }

    @Override
    public KhachHangDto getKhachHangById(Integer id) {
        logger.info("Fetching KhachHang with id: {}", id);
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhachHang not found with id: {}", id);
                    return new RuntimeException("KhachHang not found with id: " + id);
                });
        return mapToDto(khachHang);
    }

    @Override
    public List<KhachHangDto> getAllKhachHang() {
        logger.info("Fetching all KhachHang");
        return khachHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public KhachHangDto updateKhachHang(Integer id, KhachHangDto khachHangDto) {
        logger.info("Updating KhachHang with id: {}", id);
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhachHang not found with id: {}", id);
                    return new RuntimeException("KhachHang not found with id: " + id);
                });

        khachHang.setHo(khachHangDto.getHo());
        khachHang.setTen(khachHangDto.getTen());
        khachHang.setEmail(khachHangDto.getEmail());
        khachHang.setSoDienThoai(khachHangDto.getSoDienThoai());
        khachHang.setNgaySinh(khachHangDto.getNgaySinh());
        khachHang.setLaKhachVangLai(khachHangDto.getLaKhachVangLai());
        khachHang.setDiemTichLuy(khachHangDto.getDiemTichLuy());
        khachHang.setTrangThai(khachHangDto.getTrangThai());
        khachHang.setNgayCapNhat(khachHangDto.getNgayCapNhat() != null ? khachHangDto.getNgayCapNhat() : LocalDateTime.now());
        if (khachHangDto.getMatKhau() != null) {
            khachHang.setMatKhau(khachHangDto.getMatKhau(), passwordEncoder);
        }

        if (khachHangDto.getNguoiCapNhatId() != null) {
            khachHang.setNguoiCapNhat(nhanVienRepository.findById(khachHangDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", khachHangDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }
        if (khachHangDto.getMaCapBacId() != null) {
            khachHang.setMaCapBac(capBacKhachHangRepository.findById(khachHangDto.getMaCapBacId())
                    .orElseThrow(() -> {
                        logger.error("MaCapBac not found with id: {}", khachHangDto.getMaCapBacId());
                        return new RuntimeException("MaCapBac not found");
                    }));
        }

        khachHang = khachHangRepository.save(khachHang);
        logger.info("KhachHang updated with id: {}", khachHang.getMaKhachHang());
        return mapToDto(khachHang);
    }

    @Override
    public void deleteKhachHang(Integer id) {
        logger.info("Deleting KhachHang with id: {}", id);
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhachHang not found with id: {}", id);
                    return new RuntimeException("KhachHang not found with id: " + id);
                });
        khachHangRepository.delete(khachHang);
        logger.info("KhachHang deleted with id: {}", id);
    }

    private KhachHangDto mapToDto(KhachHang khachHang) {
        return KhachHangDto.builder()
                .maKhachHang(khachHang.getMaKhachHang())
                .ho(khachHang.getHo())
                .ten(khachHang.getTen())
                .email(khachHang.getEmail())
                .soDienThoai(khachHang.getSoDienThoai())
                .ngaySinh(khachHang.getNgaySinh())
                .laKhachVangLai(khachHang.getLaKhachVangLai())
                .diemTichLuy(khachHang.getDiemTichLuy())
                .maCapBacId(khachHang.getMaCapBac() != null ? khachHang.getMaCapBac().getMaCapBac() : null)
                .ngayTao(khachHang.getNgayTao())
                .ngayCapNhat(khachHang.getNgayCapNhat())
                .trangThai(khachHang.getTrangThai())
                .nguoiTaoId(khachHang.getNguoiTao() != null ? khachHang.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(khachHang.getNguoiCapNhat() != null ? khachHang.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private KhachHang mapToEntity(KhachHangDto khachHangDto) {
        return KhachHang.builder()
                .maKhachHang(khachHangDto.getMaKhachHang())
                .ho(khachHangDto.getHo())
                .ten(khachHangDto.getTen())
                .email(khachHangDto.getEmail())
                .soDienThoai(khachHangDto.getSoDienThoai())
                .ngaySinh(khachHangDto.getNgaySinh())
                .laKhachVangLai(khachHangDto.getLaKhachVangLai() != null ? khachHangDto.getLaKhachVangLai() : false)
                .diemTichLuy(khachHangDto.getDiemTichLuy() != null ? khachHangDto.getDiemTichLuy() : 0)
                .ngayTao(khachHangDto.getNgayTao() != null ? khachHangDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(khachHangDto.getNgayCapNhat() != null ? khachHangDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(khachHangDto.getTrangThai() != null ? khachHangDto.getTrangThai() : true)
                .build();
    }
}
