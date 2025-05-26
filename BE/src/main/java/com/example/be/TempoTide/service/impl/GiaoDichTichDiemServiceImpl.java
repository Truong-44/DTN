package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.GiaoDichTichDiemDto;
import com.example.be.TempoTide.entity.GiaoDichTichDiem;
import com.example.be.TempoTide.repository.GiaoDichTichDiemRepository;
import com.example.be.TempoTide.repository.KhachHangRepository;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.service.GiaoDichTichDiemService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GiaoDichTichDiemServiceImpl implements GiaoDichTichDiemService {

    private static final Logger logger = LoggerFactory.getLogger(GiaoDichTichDiemServiceImpl.class);

    private final GiaoDichTichDiemRepository giaoDichTichDiemRepository;
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;

    @Autowired
    public GiaoDichTichDiemServiceImpl(GiaoDichTichDiemRepository giaoDichTichDiemRepository,
                                       KhachHangRepository khachHangRepository, NhanVienRepository nhanVienRepository) {
        this.giaoDichTichDiemRepository = giaoDichTichDiemRepository;
        this.khachHangRepository = khachHangRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public GiaoDichTichDiemDto createGiaoDichTichDiem(GiaoDichTichDiemDto giaoDichTichDiemDto) {
        logger.info("Creating new GiaoDichTichDiem for customer: {}", giaoDichTichDiemDto.getMaKhachHang());
        GiaoDichTichDiem giaoDichTichDiem = mapToEntity(giaoDichTichDiemDto);
        giaoDichTichDiem.setMaKhachHang(khachHangRepository.findById(giaoDichTichDiemDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("MaKhachHang not found with id: {}", giaoDichTichDiemDto.getMaKhachHang());
                    return new RuntimeException("MaKhachHang not found");
                }));
        if (giaoDichTichDiemDto.getNguoiTaoId() != null) {
            giaoDichTichDiem.setNguoiTao(nhanVienRepository.findById(giaoDichTichDiemDto.getNguoiTaoId())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", giaoDichTichDiemDto.getNguoiTaoId());
                        return new RuntimeException("NguoiTao not found");
                    }));
        }
        if (giaoDichTichDiemDto.getNguoiCapNhatId() != null) {
            giaoDichTichDiem.setNguoiCapNhat(nhanVienRepository.findById(giaoDichTichDiemDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", giaoDichTichDiemDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }
        giaoDichTichDiem = giaoDichTichDiemRepository.save(giaoDichTichDiem);
        logger.info("GiaoDichTichDiem created with id: {}", giaoDichTichDiem.getMaGiaoDich());
        return mapToDto(giaoDichTichDiem);
    }

    @Override
    public GiaoDichTichDiemDto getGiaoDichTichDiemById(Integer id) {
        logger.info("Fetching GiaoDichTichDiem with id: {}", id);
        GiaoDichTichDiem giaoDichTichDiem = giaoDichTichDiemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("GiaoDichTichDiem not found with id: {}", id);
                    return new RuntimeException("GiaoDichTichDiem not found with id: " + id);
                });
        return mapToDto(giaoDichTichDiem);
    }

    @Override
    public List<GiaoDichTichDiemDto> getAllGiaoDichTichDiem() {
        logger.info("Fetching all GiaoDichTichDiem");
        return giaoDichTichDiemRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GiaoDichTichDiemDto updateGiaoDichTichDiem(Integer id, GiaoDichTichDiemDto giaoDichTichDiemDto) {
        logger.info("Updating GiaoDichTichDiem with id: {}", id);
        GiaoDichTichDiem giaoDichTichDiem = giaoDichTichDiemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("GiaoDichTichDiem not found with id: {}", id);
                    return new RuntimeException("GiaoDichTichDiem not found with id: " + id);
                });

        giaoDichTichDiem.setMaKhachHang(khachHangRepository.findById(giaoDichTichDiemDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("MaKhachHang not found with id: {}", giaoDichTichDiemDto.getMaKhachHang());
                    return new RuntimeException("MaKhachHang not found");
                }));
        giaoDichTichDiem.setSoDiem(giaoDichTichDiemDto.getSoDiem());
        giaoDichTichDiem.setLoaiGiaoDich(giaoDichTichDiemDto.getLoaiGiaoDich());
        giaoDichTichDiem.setMoTa(giaoDichTichDiemDto.getMoTa());
        giaoDichTichDiem.setTrangThai(giaoDichTichDiemDto.getTrangThai());
        giaoDichTichDiem.setNgayCapNhat(giaoDichTichDiemDto.getNgayCapNhat() != null ? giaoDichTichDiemDto.getNgayCapNhat() : LocalDateTime.now());

        if (giaoDichTichDiemDto.getNguoiCapNhatId() != null) {
            giaoDichTichDiem.setNguoiCapNhat(nhanVienRepository.findById(giaoDichTichDiemDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", giaoDichTichDiemDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }

        giaoDichTichDiem = giaoDichTichDiemRepository.save(giaoDichTichDiem);
        logger.info("GiaoDichTichDiem updated with id: {}", giaoDichTichDiem.getMaGiaoDich());
        return mapToDto(giaoDichTichDiem);
    }

    @Override
    public void deleteGiaoDichTichDiem(Integer id) {
        logger.info("Deleting GiaoDichTichDiem with id: {}", id);
        GiaoDichTichDiem giaoDichTichDiem = giaoDichTichDiemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("GiaoDichTichDiem not found with id: {}", id);
                    return new RuntimeException("GiaoDichTichDiem not found with id: " + id);
                });
        giaoDichTichDiemRepository.delete(giaoDichTichDiem);
        logger.info("GiaoDichTichDiem deleted with id: {}", id);
    }

    private GiaoDichTichDiemDto mapToDto(GiaoDichTichDiem giaoDichTichDiem) {
        return GiaoDichTichDiemDto.builder()
                .maGiaoDich(giaoDichTichDiem.getMaGiaoDich())
                .maKhachHang(giaoDichTichDiem.getMaKhachHang().getMaKhachHang())
                .soDiem(giaoDichTichDiem.getSoDiem())
                .loaiGiaoDich(giaoDichTichDiem.getLoaiGiaoDich())
                .moTa(giaoDichTichDiem.getMoTa())
                .ngayTao(giaoDichTichDiem.getNgayTao())
                .ngayCapNhat(giaoDichTichDiem.getNgayCapNhat())
                .trangThai(giaoDichTichDiem.getTrangThai())
                .nguoiTaoId(giaoDichTichDiem.getNguoiTao() != null ? giaoDichTichDiem.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(giaoDichTichDiem.getNguoiCapNhat() != null ? giaoDichTichDiem.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private GiaoDichTichDiem mapToEntity(GiaoDichTichDiemDto giaoDichTichDiemDto) {
        return GiaoDichTichDiem.builder()
                .maGiaoDich(giaoDichTichDiemDto.getMaGiaoDich())
                .soDiem(giaoDichTichDiemDto.getSoDiem())
                .loaiGiaoDich(giaoDichTichDiemDto.getLoaiGiaoDich())
                .moTa(giaoDichTichDiemDto.getMoTa())
                .ngayTao(giaoDichTichDiemDto.getNgayTao() != null ? giaoDichTichDiemDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(giaoDichTichDiemDto.getNgayCapNhat() != null ? giaoDichTichDiemDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(giaoDichTichDiemDto.getTrangThai() != null ? giaoDichTichDiemDto.getTrangThai() : true)
                .build();
    }
}
