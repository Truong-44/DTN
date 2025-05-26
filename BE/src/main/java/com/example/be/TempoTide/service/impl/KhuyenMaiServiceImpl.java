package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.KhuyenMaiDto;
import com.example.be.TempoTide.entity.KhuyenMai;
import com.example.be.TempoTide.repository.KhuyenMaiRepository;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.service.KhuyenMaiService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class KhuyenMaiServiceImpl implements KhuyenMaiService {

    private static final Logger logger = LoggerFactory.getLogger(KhuyenMaiServiceImpl.class);

    private final KhuyenMaiRepository khuyenMaiRepository;
    private final NhanVienRepository nhanVienRepository;

    @Autowired
    public KhuyenMaiServiceImpl(KhuyenMaiRepository khuyenMaiRepository, NhanVienRepository nhanVienRepository) {
        this.khuyenMaiRepository = khuyenMaiRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public KhuyenMaiDto createKhuyenMai(KhuyenMaiDto khuyenMaiDto) {
        logger.info("Creating new KhuyenMai: {}", khuyenMaiDto.getTenKhuyenMai());
        KhuyenMai khuyenMai = mapToEntity(khuyenMaiDto);
        if (khuyenMaiDto.getNguoiTaoId() != null) {
            khuyenMai.setNguoiTao(nhanVienRepository.findById(khuyenMaiDto.getNguoiTaoId())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", khuyenMaiDto.getNguoiTaoId());
                        return new RuntimeException("NguoiTao not found");
                    }));
        }
        if (khuyenMaiDto.getNguoiCapNhatId() != null) {
            khuyenMai.setNguoiCapNhat(nhanVienRepository.findById(khuyenMaiDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", khuyenMaiDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }
        khuyenMai = khuyenMaiRepository.save(khuyenMai);
        logger.info("KhuyenMai created with id: {}", khuyenMai.getMaKhuyenMai());
        return mapToDto(khuyenMai);
    }

    @Override
    public KhuyenMaiDto getKhuyenMaiById(Integer id) {
        logger.info("Fetching KhuyenMai with id: {}", id);
        KhuyenMai khuyenMai = khuyenMaiRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhuyenMai not found with id: {}", id);
                    return new RuntimeException("KhuyenMai not found with id: " + id);
                });
        return mapToDto(khuyenMai);
    }

    @Override
    public List<KhuyenMaiDto> getAllKhuyenMai() {
        logger.info("Fetching all KhuyenMai");
        return khuyenMaiRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public KhuyenMaiDto updateKhuyenMai(Integer id, KhuyenMaiDto khuyenMaiDto) {
        logger.info("Updating KhuyenMai with id: {}", id);
        KhuyenMai khuyenMai = khuyenMaiRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhuyenMai not found with id: {}", id);
                    return new RuntimeException("KhuyenMai not found with id: " + id);
                });

        khuyenMai.setTenKhuyenMai(khuyenMaiDto.getTenKhuyenMai());
        khuyenMai.setMoTa(khuyenMaiDto.getMoTa());
        khuyenMai.setPhanTramGiamGia(khuyenMaiDto.getPhanTramGiamGia());
        khuyenMai.setNgayBatDau(khuyenMaiDto.getNgayBatDau());
        khuyenMai.setNgayKetThuc(khuyenMaiDto.getNgayKetThuc());
        khuyenMai.setTrangThai(khuyenMaiDto.getTrangThai());
        khuyenMai.setNgayCapNhat(khuyenMaiDto.getNgayCapNhat() != null ? khuyenMaiDto.getNgayCapNhat() : LocalDateTime.now());

        if (khuyenMaiDto.getNguoiCapNhatId() != null) {
            khuyenMai.setNguoiCapNhat(nhanVienRepository.findById(khuyenMaiDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", khuyenMaiDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }

        khuyenMai = khuyenMaiRepository.save(khuyenMai);
        logger.info("KhuyenMai updated with id: {}", khuyenMai.getMaKhuyenMai());
        return mapToDto(khuyenMai);
    }

    @Override
    public void deleteKhuyenMai(Integer id) {
        logger.info("Deleting KhuyenMai with id: {}", id);
        KhuyenMai khuyenMai = khuyenMaiRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhuyenMai not found with id: {}", id);
                    return new RuntimeException("KhuyenMai not found with id: " + id);
                });
        khuyenMaiRepository.delete(khuyenMai);
        logger.info("KhuyenMai deleted with id: {}", id);
    }

    private KhuyenMaiDto mapToDto(KhuyenMai khuyenMai) {
        return KhuyenMaiDto.builder()
                .maKhuyenMai(khuyenMai.getMaKhuyenMai())
                .tenKhuyenMai(khuyenMai.getTenKhuyenMai())
                .moTa(khuyenMai.getMoTa())
                .phanTramGiamGia(khuyenMai.getPhanTramGiamGia())
                .ngayBatDau(khuyenMai.getNgayBatDau())
                .ngayKetThuc(khuyenMai.getNgayKetThuc())
                .ngayTao(khuyenMai.getNgayTao())
                .ngayCapNhat(khuyenMai.getNgayCapNhat())
                .trangThai(khuyenMai.getTrangThai())
                .nguoiTaoId(khuyenMai.getNguoiTao() != null ? khuyenMai.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(khuyenMai.getNguoiCapNhat() != null ? khuyenMai.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private KhuyenMai mapToEntity(KhuyenMaiDto khuyenMaiDto) {
        return KhuyenMai.builder()
                .maKhuyenMai(khuyenMaiDto.getMaKhuyenMai())
                .tenKhuyenMai(khuyenMaiDto.getTenKhuyenMai())
                .moTa(khuyenMaiDto.getMoTa())
                .phanTramGiamGia(khuyenMaiDto.getPhanTramGiamGia())
                .ngayBatDau(khuyenMaiDto.getNgayBatDau())
                .ngayKetThuc(khuyenMaiDto.getNgayKetThuc())
                .ngayTao(khuyenMaiDto.getNgayTao() != null ? khuyenMaiDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(khuyenMaiDto.getNgayCapNhat() != null ? khuyenMaiDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(khuyenMaiDto.getTrangThai() != null ? khuyenMaiDto.getTrangThai() : true)
                .build();
    }
}