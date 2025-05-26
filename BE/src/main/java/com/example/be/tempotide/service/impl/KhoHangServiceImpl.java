package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.khohangdto;
import com.example.be.tempotide.entity.khohang;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.repository.khohangrepository;
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
public class khohangserviceImpl implements com.example.be.tempotide.service.khohangservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.khohangserviceImpl.class);

    private final khohangrepository khoHangRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public khohangserviceImpl(khohangrepository khoHangRepository,
                              nhanvienrepository nhanVienRepository) {
        this.khoHangRepository = khoHangRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public khohangdto createKhoHang(khohangdto khoHangDto) {
        logger.info("Creating new KhoHang: {}", khoHangDto.getTenKho());
        khohang khoHang = mapToEntity(khoHangDto);

        if (khoHangDto.getNguoiTao() != null) {
            nhanvien nguoiTao = nhanVienRepository.findById(khoHangDto.getNguoiTao())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", khoHangDto.getNguoiTao());
                        return new RuntimeException("NhanVien not found");
                    });
            khoHang.setNguoiTao(nguoiTao);
        }
        if (khoHangDto.getNguoiCapNhat() != null) {
            nhanvien nguoiCapNhat = nhanVienRepository.findById(khoHangDto.getNguoiCapNhat())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", khoHangDto.getNguoiCapNhat());
                        return new RuntimeException("NhanVien not found");
                    });
            khoHang.setNguoiCapNhat(nguoiCapNhat);
        }

        khoHang.setNgayTao(LocalDateTime.now());
        khoHang = khoHangRepository.save(khoHang);
        logger.info("KhoHang created with id: {}", khoHang.getMaKho());
        return mapToDto(khoHang);
    }

    @Override
    public khohangdto getKhoHangById(Integer id) {
        logger.info("Fetching KhoHang with id: {}", id);
        khohang khoHang = khoHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhoHang not found with id: {}", id);
                    return new RuntimeException("KhoHang not found with id: " + id);
                });
        return mapToDto(khoHang);
    }

    @Override
    public List<khohangdto> getAllKhoHang() {
        logger.info("Fetching all KhoHang");
        return khoHangRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public khohangdto updateKhoHang(Integer id, khohangdto khoHangDto) {
        logger.info("Updating KhoHang with id: {}", id);
        khohang khoHang = khoHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhoHang not found with id: {}", id);
                    return new RuntimeException("KhoHang not found with id: " + id);
                });

        khoHang.setTenKho(khoHangDto.getTenKho());
        khoHang.setDiaChi(khoHangDto.getDiaChi());
        khoHang.setSoDienThoai(khoHangDto.getSoDienThoai());
        khoHang.setDungLuongToiDa(khoHangDto.getDungLuongToiDa());
        khoHang.setDungLuongHienTai(khoHangDto.getDungLuongHienTai());
        khoHang.setTrangThai(khoHangDto.getTrangThai());

        if (khoHangDto.getNguoiCapNhat() != null) {
            nhanvien nguoiCapNhat = nhanVienRepository.findById(khoHangDto.getNguoiCapNhat())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", khoHangDto.getNguoiCapNhat());
                        return new RuntimeException("NhanVien not found");
                    });
            khoHang.setNguoiCapNhat(nguoiCapNhat);
        }

        khoHang = khoHangRepository.save(khoHang);
        logger.info("KhoHang updated with id: {}", khoHang.getMaKho());
        return mapToDto(khoHang);
    }

    @Override
    public void deleteKhoHang(Integer id) {
        logger.info("Deleting KhoHang with id: {}", id);
        khohang khoHang = khoHangRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("KhoHang not found with id: {}", id);
                    return new RuntimeException("KhoHang not found with id: " + id);
                });
        khoHangRepository.delete(khoHang);
        logger.info("KhoHang deleted with id: {}", id);
    }

    private khohangdto mapToDto(khohang khoHang) {
        return khohangdto.builder()
                .maKho(khoHang.getMaKho())
                .tenKho(khoHang.getTenKho())
                .diaChi(khoHang.getDiaChi())
                .soDienThoai(khoHang.getSoDienThoai())
                .dungLuongToiDa(khoHang.getDungLuongToiDa())
                .dungLuongHienTai(khoHang.getDungLuongHienTai())
                .ngayTao(khoHang.getNgayTao())
                .trangThai(khoHang.getTrangThai())
                .nguoiTao(khoHang.getNguoiTao() != null ? khoHang.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhat(khoHang.getNguoiCapNhat() != null ? khoHang.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private khohang mapToEntity(khohangdto khoHangDto) {
        return khohang.builder()
                .maKho(khoHangDto.getMaKho())
                .tenKho(khoHangDto.getTenKho())
                .diaChi(khoHangDto.getDiaChi())
                .soDienThoai(khoHangDto.getSoDienThoai())
                .dungLuongToiDa(khoHangDto.getDungLuongToiDa())
                .dungLuongHienTai(khoHangDto.getDungLuongHienTai())
                .ngayTao(khoHangDto.getNgayTao() != null ? khoHangDto.getNgayTao() : LocalDateTime.now())
                .trangThai(khoHangDto.getTrangThai())
                .build();
    }
}