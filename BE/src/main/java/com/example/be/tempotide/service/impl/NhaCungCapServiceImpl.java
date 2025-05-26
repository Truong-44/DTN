package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.nhacungcapdto;
import com.example.be.tempotide.entity.nhacungcap;
import com.example.be.tempotide.repository.nhacungcaprepository;
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
public class nhacungcapserviceImpl implements com.example.be.tempotide.service.nhacungcapservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.nhacungcapserviceImpl.class);

    private final nhacungcaprepository nhaCungCapRepository;
    private final nhanvienrepository nhanVienRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public nhacungcapserviceImpl(nhacungcaprepository nhaCungCapRepository, nhanvienrepository nhanVienRepository,
                                 PasswordEncoder passwordEncoder) {
        this.nhaCungCapRepository = nhaCungCapRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public nhacungcapdto createNhaCungCap(nhacungcapdto nhaCungCapDto) {
        logger.info("Creating new NhaCungCap: {}", nhaCungCapDto.getTenNhaCungCap());
        nhacungcap nhaCungCap = mapToEntity(nhaCungCapDto);
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
    public nhacungcapdto getNhaCungCapById(Integer id) {
        logger.info("Fetching NhaCungCap with id: {}", id);
        nhacungcap nhaCungCap = nhaCungCapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("NhaCungCap not found with id: {}", id);
                    return new RuntimeException("NhaCungCap not found with id: " + id);
                });
        return mapToDto(nhaCungCap);
    }

    @Override
    public List<nhacungcapdto> getAllNhaCungCap() {
        logger.info("Fetching all NhaCungCap");
        return nhaCungCapRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public nhacungcapdto updateNhaCungCap(Integer id, nhacungcapdto nhaCungCapDto) {
        logger.info("Updating NhaCungCap with id: {}", id);
        nhacungcap nhaCungCap = nhaCungCapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("NhaCungCap not found with id: {}", id);
                    return new RuntimeException("NhaCungCap not found with id: " + id);
                });

        nhaCungCap.setTenNhaCungCap(nhaCungCapDto.getTenNhaCungCap());
        nhaCungCap.setNguoiLienHe(nhaCungCapDto.getNguoiLienHe());
        nhaCungCap.setEmail(nhaCungCapDto.getEmail());
        nhaCungCap.setSoDienThoai(nhaCungCapDto.getSoDienThoai());
        nhaCungCap.setDiaChi(nhaCungCapDto.getDiaChi());
        nhaCungCap.setMaTaiKhoanNganHang(nhaCungCapDto.getMaTaiKhoanNganHang());
        nhaCungCap.setTrangWeb(nhaCungCapDto.getTrangWeb());
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
        nhacungcap nhaCungCap = nhaCungCapRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("NhaCungCap not found with id: {}", id);
                    return new RuntimeException("NhaCungCap not found with id: " + id);
                });
        nhaCungCapRepository.delete(nhaCungCap);
        logger.info("NhaCungCap deleted with id: {}", id);
    }

    private nhacungcapdto mapToDto(nhacungcap nhaCungCap) {
        return nhacungcapdto.builder()
                .maNhaCungCap(nhaCungCap.getMaNhaCungCap())
                .tenNhaCungCap(nhaCungCap.getTenNhaCungCap())
                .nguoiLienHe(nhaCungCap.getNguoiLienHe())
                .email(nhaCungCap.getEmail())
                .soDienThoai(nhaCungCap.getSoDienThoai())
                .diaChi(nhaCungCap.getDiaChi())
                .maTaiKhoanNganHang(nhaCungCap.getMaTaiKhoanNganHang())
                .trangWeb(nhaCungCap.getTrangWeb())
                .matKhau(null) // Do not expose password
                .ngayTao(nhaCungCap.getNgayTao())
                .ngayCapNhat(nhaCungCap.getNgayCapNhat())
                .trangThai(nhaCungCap.getTrangThai())
                .nguoiTaoId(nhaCungCap.getNguoiTao() != null ? nhaCungCap.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(nhaCungCap.getNguoiCapNhat() != null ? nhaCungCap.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private nhacungcap mapToEntity(nhacungcapdto nhaCungCapDto) {
        return nhacungcap.builder()
                .maNhaCungCap(nhaCungCapDto.getMaNhaCungCap())
                .tenNhaCungCap(nhaCungCapDto.getTenNhaCungCap())
                .nguoiLienHe(nhaCungCapDto.getNguoiLienHe())
                .email(nhaCungCapDto.getEmail())
                .soDienThoai(nhaCungCapDto.getSoDienThoai())
                .diaChi(nhaCungCapDto.getDiaChi())
                .maTaiKhoanNganHang(nhaCungCapDto.getMaTaiKhoanNganHang())
                .trangWeb(nhaCungCapDto.getTrangWeb())
                .matKhau(nhaCungCapDto.getMatKhau())
                .ngayTao(nhaCungCapDto.getNgayTao() != null ? nhaCungCapDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(nhaCungCapDto.getNgayCapNhat() != null ? nhaCungCapDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(nhaCungCapDto.getTrangThai() != null ? nhaCungCapDto.getTrangThai() : true)
                .build();
    }
}