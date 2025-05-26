package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.phuongthucvanchuyendto;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.entity.phuongthucvanchuyen;
import com.example.be.tempotide.repository.nhanvienrepository;
import com.example.be.tempotide.repository.phuongthucvanchuyenrepository;
import com.example.be.tempotide.service.PhuongThucVanChuyenService;
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
public class PhuongThucVanChuyenServiceImpl implements PhuongThucVanChuyenService {

    private static final Logger logger = LoggerFactory.getLogger(PhuongThucVanChuyenServiceImpl.class);

    private final phuongthucvanchuyenrepository phuongThucVanChuyenRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public PhuongThucVanChuyenServiceImpl(phuongthucvanchuyenrepository phuongThucVanChuyenRepository,
                                          nhanvienrepository nhanVienRepository) {
        this.phuongThucVanChuyenRepository = phuongThucVanChuyenRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public phuongthucvanchuyendto createPhuongThucVanChuyen(phuongthucvanchuyendto phuongThucVanChuyenDto) {
        logger.info("Creating new PhuongThucVanChuyen: {}", phuongThucVanChuyenDto.getTenPhuongThuc());
        phuongthucvanchuyen phuongThucVanChuyen = mapToEntity(phuongThucVanChuyenDto);

        if (phuongThucVanChuyenDto.getNguoiTao() != null) {
            nhanvien nguoiTao = nhanVienRepository.findById(phuongThucVanChuyenDto.getNguoiTao())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", phuongThucVanChuyenDto.getNguoiTao());
                        return new RuntimeException("NhanVien not found");
                    });
            phuongThucVanChuyen.setNguoiTao(nguoiTao);
        }
        if (phuongThucVanChuyenDto.getNguoiCapNhat() != null) {
            nhanvien nguoiCapNhat = nhanVienRepository.findById(phuongThucVanChuyenDto.getNguoiCapNhat())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", phuongThucVanChuyenDto.getNguoiCapNhat());
                        return new RuntimeException("NhanVien not found");
                    });
            phuongThucVanChuyen.setNguoiCapNhat(nguoiCapNhat);
        }

        phuongThucVanChuyen.setNgayTao(LocalDateTime.now());
        phuongThucVanChuyen = phuongThucVanChuyenRepository.save(phuongThucVanChuyen);
        logger.info("PhuongThucVanChuyen created with id: {}", phuongThucVanChuyen.getMaPhuongThuc());
        return mapToDto(phuongThucVanChuyen);
    }

    @Override
    public phuongthucvanchuyendto getPhuongThucVanChuyenById(Integer id) {
        logger.info("Fetching PhuongThucVanChuyen with id: {}", id);
        phuongthucvanchuyen phuongThucVanChuyen = phuongThucVanChuyenRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhuongThucVanChuyen not found with id: {}", id);
                    return new RuntimeException("PhuongThucVanChuyen not found with id: " + id);
                });
        return mapToDto(phuongThucVanChuyen);
    }

    @Override
    public List<phuongthucvanchuyendto> getAllPhuongThucVanChuyen() {
        logger.info("Fetching all PhuongThucVanChuyen");
        return phuongThucVanChuyenRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public phuongthucvanchuyendto updatePhuongThucVanChuyen(Integer id, phuongthucvanchuyendto phuongThucVanChuyenDto) {
        logger.info("Updating PhuongThucVanChuyen with id: {}", id);
        phuongthucvanchuyen phuongThucVanChuyen = phuongThucVanChuyenRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhuongThucVanChuyen not found with id: {}", id);
                    return new RuntimeException("PhuongThucVanChuyen not found with id: " + id);
                });

        phuongThucVanChuyen.setTenPhuongThuc(phuongThucVanChuyenDto.getTenPhuongThuc());
        phuongThucVanChuyen.setMoTa(phuongThucVanChuyenDto.getMoTa());
        phuongThucVanChuyen.setChiPhi(phuongThucVanChuyenDto.getChiPhi());
        phuongThucVanChuyen.setThoiGianDuKien(phuongThucVanChuyenDto.getThoiGianDuKien());
        phuongThucVanChuyen.setTrangThai(phuongThucVanChuyenDto.getTrangThai());

        if (phuongThucVanChuyenDto.getNguoiCapNhat() != null) {
            nhanvien nguoiCapNhat = nhanVienRepository.findById(phuongThucVanChuyenDto.getNguoiCapNhat())
                    .orElseThrow(() -> {
                        logger.error("NhanVien not found with id: {}", phuongThucVanChuyenDto.getNguoiCapNhat());
                        return new RuntimeException("NhanVien not found");
                    });
            phuongThucVanChuyen.setNguoiCapNhat(nguoiCapNhat);
        }

        phuongThucVanChuyen = phuongThucVanChuyenRepository.save(phuongThucVanChuyen);
        logger.info("PhuongThucVanChuyen updated with id: {}", phuongThucVanChuyen.getMaPhuongThuc());
        return mapToDto(phuongThucVanChuyen);
    }

    @Override
    public void deletePhuongThucVanChuyen(Integer id) {
        logger.info("Deleting PhuongThucVanChuyen with id: {}", id);
        phuongthucvanchuyen phuongThucVanChuyen = phuongThucVanChuyenRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("PhuongThucVanChuyen not found with id: {}", id);
                    return new RuntimeException("PhuongThucVanChuyen not found with id: " + id);
                });
        phuongThucVanChuyenRepository.delete(phuongThucVanChuyen);
        logger.info("PhuongThucVanChuyen deleted with id: {}", id);
    }

    private phuongthucvanchuyendto mapToDto(phuongthucvanchuyen phuongThucVanChuyen) {
        return phuongthucvanchuyendto.builder()
                .maPhuongThuc(phuongThucVanChuyen.getMaPhuongThuc())
                .tenPhuongThuc(phuongThucVanChuyen.getTenPhuongThuc())
                .moTa(phuongThucVanChuyen.getMoTa())
                .chiPhi(phuongThucVanChuyen.getChiPhi())
                .thoiGianDuKien(phuongThucVanChuyen.getThoiGianDuKien())
                .ngayTao(phuongThucVanChuyen.getNgayTao())
                .trangThai(phuongThucVanChuyen.getTrangThai())
                .nguoiTao(phuongThucVanChuyen.getNguoiTao() != null ? phuongThucVanChuyen.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhat(phuongThucVanChuyen.getNguoiCapNhat() != null ? phuongThucVanChuyen.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private phuongthucvanchuyen mapToEntity(phuongthucvanchuyendto phuongThucVanChuyenDto) {
        return phuongthucvanchuyen.builder()
                .maPhuongThuc(phuongThucVanChuyenDto.getMaPhuongThuc())
                .tenPhuongThuc(phuongThucVanChuyenDto.getTenPhuongThuc())
                .moTa(phuongThucVanChuyenDto.getMoTa())
                .chiPhi(phuongThucVanChuyenDto.getChiPhi())
                .thoiGianDuKien(phuongThucVanChuyenDto.getThoiGianDuKien())
                .ngayTao(phuongThucVanChuyenDto.getNgayTao() != null ? phuongThucVanChuyenDto.getNgayTao() : LocalDateTime.now())
                .trangThai(phuongThucVanChuyenDto.getTrangThai())
                .build();
    }
}