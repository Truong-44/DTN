package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.khuyenmaidto;
import com.example.be.tempotide.entity.khuyenmai;
import com.example.be.tempotide.repository.khuyenmairepository;
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
public class khuyenmaiserviceImpl implements com.example.be.tempotide.service.khuyenmaiservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.khuyenmaiserviceImpl.class);

    private final khuyenmairepository khuyenMaiRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public khuyenmaiserviceImpl(khuyenmairepository khuyenMaiRepository, nhanvienrepository nhanVienRepository) {
        this.khuyenMaiRepository = khuyenMaiRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public khuyenmaidto createKhuyenMai(khuyenmaidto khuyenMaiDto) {
        logger.info("Creating new KhuyenMai: {}", khuyenMaiDto.getTenKhuyenMai());
        khuyenmai khuyenMai = mapToEntity(khuyenMaiDto);
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
    public khuyenmaidto getKhuyenMaiById(Integer id) {
        logger.info("Fetching KhuyenMai with id: {}", id);
        khuyenmai khuyenMai = khuyenMaiRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhuyenMai not found with id: {}", id);
                    return new RuntimeException("KhuyenMai not found with id: " + id);
                });
        return mapToDto(khuyenMai);
    }

    @Override
    public List<khuyenmaidto> getAllKhuyenMai() {
        logger.info("Fetching all KhuyenMai");
        return khuyenMaiRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public khuyenmaidto updateKhuyenMai(Integer id, khuyenmaidto khuyenMaiDto) {
        logger.info("Updating KhuyenMai with id: {}", id);
        khuyenmai khuyenMai = khuyenMaiRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhuyenMai not found with id: {}", id);
                    return new RuntimeException("KhuyenMai not found with id: " + id);
                });

        khuyenMai.setTenKhuyenMai(khuyenMaiDto.getTenKhuyenMai());
        khuyenMai.setMoTa(khuyenMaiDto.getMoTa());
        khuyenMai.setPhanTramGiamGia(khuyenMaiDto.getPhanTramGiamGia());
        khuyenMai.setDieuKienApDung(khuyenMaiDto.getDieuKienApDung());
        khuyenMai.setSoLuongApDung(khuyenMaiDto.getSoLuongApDung());
        khuyenMai.setApDungChoDatTruoc(khuyenMaiDto.getApDungChoDatTruoc());
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
        khuyenmai khuyenMai = khuyenMaiRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhuyenMai not found with id: {}", id);
                    return new RuntimeException("KhuyenMai not found with id: " + id);
                });
        khuyenMaiRepository.delete(khuyenMai);
        logger.info("KhuyenMai deleted with id: {}", id);
    }

    private khuyenmaidto mapToDto(khuyenmai khuyenMai) {
        return khuyenmaidto.builder()
                .maKhuyenMai(khuyenMai.getMaKhuyenMai())
                .tenKhuyenMai(khuyenMai.getTenKhuyenMai())
                .moTa(khuyenMai.getMoTa())
                .phanTramGiamGia(khuyenMai.getPhanTramGiamGia())
                .dieuKienApDung(khuyenMai.getDieuKienApDung())
                .soLuongApDung(khuyenMai.getSoLuongApDung())
                .apDungChoDatTruoc(khuyenMai.getApDungChoDatTruoc())
                .ngayBatDau(khuyenMai.getNgayBatDau())
                .ngayKetThuc(khuyenMai.getNgayKetThuc())
                .ngayTao(khuyenMai.getNgayTao())
                .ngayCapNhat(khuyenMai.getNgayCapNhat())
                .trangThai(khuyenMai.getTrangThai())
                .nguoiTaoId(khuyenMai.getNguoiTao() != null ? khuyenMai.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(khuyenMai.getNguoiCapNhat() != null ? khuyenMai.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private khuyenmai mapToEntity(khuyenmaidto khuyenMaiDto) {
        return khuyenmai.builder()
                .maKhuyenMai(khuyenMaiDto.getMaKhuyenMai())
                .tenKhuyenMai(khuyenMaiDto.getTenKhuyenMai())
                .moTa(khuyenMaiDto.getMoTa())
                .phanTramGiamGia(khuyenMaiDto.getPhanTramGiamGia())
                .dieuKienApDung(khuyenMaiDto.getDieuKienApDung())
                .soLuongApDung(khuyenMaiDto.getSoLuongApDung() != null ? khuyenMaiDto.getSoLuongApDung() : 0)
                .apDungChoDatTruoc(khuyenMaiDto.getApDungChoDatTruoc() != null ? khuyenMaiDto.getApDungChoDatTruoc() : false)
                .ngayBatDau(khuyenMaiDto.getNgayBatDau())
                .ngayKetThuc(khuyenMaiDto.getNgayKetThuc())
                .ngayTao(khuyenMaiDto.getNgayTao() != null ? khuyenMaiDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(khuyenMaiDto.getNgayCapNhat() != null ? khuyenMaiDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(khuyenMaiDto.getTrangThai() != null ? khuyenMaiDto.getTrangThai() : true)
                .build();
    }
}