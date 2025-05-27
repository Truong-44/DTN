package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.giaodichtichdiemdto;
import com.example.be.tempotide.entity.giaodichtichdiem;
import com.example.be.tempotide.repository.giaodichtichdiemrepository;
import com.example.be.tempotide.repository.khachhangrepository;
import com.example.be.tempotide.repository.nhanvienrepository;
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
public class giaodichtichdiemserviceImpl implements com.example.be.tempotide.service.giaodichtichdiemservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.giaodichtichdiemserviceImpl.class);

    private final giaodichtichdiemrepository giaoDichTichDiemRepository;
    private final khachhangrepository khachHangRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public giaodichtichdiemserviceImpl(giaodichtichdiemrepository giaoDichTichDiemRepository,
                                       khachhangrepository khachHangRepository, nhanvienrepository nhanVienRepository) {
        this.giaoDichTichDiemRepository = giaoDichTichDiemRepository;
        this.khachHangRepository = khachHangRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public giaodichtichdiemdto createGiaoDichTichDiem(giaodichtichdiemdto giaoDichTichDiemDto) {
        logger.info("Creating new GiaoDichTichDiem for customer: {}", giaoDichTichDiemDto.getMaKhachHang());
        giaodichtichdiem giaoDichTichDiem = mapToEntity(giaoDichTichDiemDto);
        giaoDichTichDiem.setMaKhachhang(khachHangRepository.findById(giaoDichTichDiemDto.getMaKhachHang())
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
    public giaodichtichdiemdto getGiaoDichTichDiemById(Integer id) {
        logger.info("Fetching GiaoDichTichDiem with id: {}", id);
        giaodichtichdiem giaoDichTichDiem = giaoDichTichDiemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("GiaoDichTichDiem not found with id: {}", id);
                    return new RuntimeException("GiaoDichTichDiem not found with id: " + id);
                });
        return mapToDto(giaoDichTichDiem);
    }

    @Override
    public List<giaodichtichdiemdto> getAllGiaoDichTichDiem() {
        logger.info("Fetching all GiaoDichTichDiem");
        return giaoDichTichDiemRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public giaodichtichdiemdto updateGiaoDichTichDiem(Integer id, giaodichtichdiemdto giaoDichTichDiemDto) {
        logger.info("Updating GiaoDichTichDiem with id: {}", id);
        giaodichtichdiem giaoDichTichDiem = giaoDichTichDiemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("GiaoDichTichDiem not found with id: {}", id);
                    return new RuntimeException("GiaoDichTichDiem not found with id: " + id);
                });

        giaoDichTichDiem.setMaKhachhang(khachHangRepository.findById(giaoDichTichDiemDto.getMaKhachHang())
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
        giaodichtichdiem giaoDichTichDiem = giaoDichTichDiemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("GiaoDichTichDiem not found with id: {}", id);
                    return new RuntimeException("GiaoDichTichDiem not found with id: " + id);
                });
        giaoDichTichDiemRepository.delete(giaoDichTichDiem);
        logger.info("GiaoDichTichDiem deleted with id: {}", id);
    }

    private giaodichtichdiemdto mapToDto(giaodichtichdiem giaoDichTichDiem) {
        return giaodichtichdiemdto.builder()
                .maGiaoDich(giaoDichTichDiem.getMaGiaoDich())
                .maKhachHang(giaoDichTichDiem.getMaKhachhang().getMaKhachHang())
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

    private giaodichtichdiem mapToEntity(giaodichtichdiemdto giaoDichTichDiemDto) {
        return giaodichtichdiem.builder()
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
