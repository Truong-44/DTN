package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.danhmucdto;
import com.example.be.tempotide.entity.danhmuc;
import com.example.be.tempotide.repository.danhmucrepository;
import com.example.be.tempotide.repository.nhanvienrepository;
import com.example.be.tempotide.service.DanhMucService;
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
public class DanhMucServiceImpl implements DanhMucService {

    private static final Logger logger = LoggerFactory.getLogger(DanhMucServiceImpl.class);

    private final danhmucrepository danhMucRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public DanhMucServiceImpl(danhmucrepository danhMucRepository, nhanvienrepository nhanVienRepository) {
        this.danhMucRepository = danhMucRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public danhmucdto createDanhMuc(danhmucdto danhMucDto) {
        logger.info("Creating new DanhMuc: {}", danhMucDto.getTenDanhMuc());
        danhmuc danhMuc = mapToEntity(danhMucDto);
        if (danhMucDto.getMaDanhMucChaId() != null) {
            danhMuc.setMaDanhmucCha(danhMucRepository.findById(danhMucDto.getMaDanhMucChaId())
                    .orElseThrow(() -> {
                        logger.error("MaDanhMucCha not found with id: {}", danhMucDto.getMaDanhMucChaId());
                        return new RuntimeException("MaDanhMucCha not found");
                    }));
        }
        if (danhMucDto.getNguoiTaoId() != null) {
            danhMuc.setNguoiTao(nhanVienRepository.findById(danhMucDto.getNguoiTaoId())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", danhMucDto.getNguoiTaoId());
                        return new RuntimeException("NguoiTao not found");
                    }));
        }
        if (danhMucDto.getNguoiCapNhatId() != null) {
            danhMuc.setNguoiCapNhat(nhanVienRepository.findById(danhMucDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", danhMucDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }
        danhMuc = danhMucRepository.save(danhMuc);
        logger.info("DanhMuc created with id: {}", danhMuc.getMaDanhMuc());
        return mapToDto(danhMuc);
    }

    @Override
    public danhmucdto getDanhMucById(Integer id) {
        logger.info("Fetching DanhMuc with id: {}", id);
        danhmuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DanhMuc not found with id: {}", id);
                    return new RuntimeException("DanhMuc not found with id: " + id);
                });
        return mapToDto(danhMuc);
    }

    @Override
    public List<danhmucdto> getAllDanhMuc() {
        logger.info("Fetching all DanhMuc");
        return danhMucRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public danhmucdto updateDanhMuc(Integer id, danhmucdto danhMucDto) {
        logger.info("Updating DanhMuc with id: {}", id);
        danhmuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DanhMuc not found with id: {}", id);
                    return new RuntimeException("DanhMuc not found with id: " + id);
                });

        danhMuc.setTenDanhMuc(danhMucDto.getTenDanhMuc());
        danhMuc.setMaDanhmucCha(danhMucDto.getMaDanhMucChaId() != null ?
                danhMucRepository.findById(danhMucDto.getMaDanhMucChaId())
                        .orElseThrow(() -> {
                            logger.error("MaDanhMucCha not found with id: {}", danhMucDto.getMaDanhMucChaId());
                            return new RuntimeException("MaDanhMucCha not found");
                        }) : null);
        danhMuc.setTrangThai(danhMucDto.getTrangThai());
        danhMuc.setNgayCapNhat(danhMucDto.getNgayCapNhat() != null ? danhMucDto.getNgayCapNhat() : LocalDateTime.now());

        if (danhMucDto.getNguoiCapNhatId() != null) {
            danhMuc.setNguoiCapNhat(nhanVienRepository.findById(danhMucDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", danhMucDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }

        danhMuc = danhMucRepository.save(danhMuc);
        logger.info("DanhMuc updated with id: {}", danhMuc.getMaDanhMuc());
        return mapToDto(danhMuc);
    }

    @Override
    public void deleteDanhMuc(Integer id) {
        logger.info("Deleting DanhMuc with id: {}", id);
        danhmuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DanhMuc not found with id: {}", id);
                    return new RuntimeException("DanhMuc not found with id: " + id);
                });
        danhMucRepository.delete(danhMuc);
        logger.info("DanhMuc deleted with id: {}", id);
    }

    private danhmucdto mapToDto(danhmuc danhMuc) {
        return danhmucdto.builder()
                .maDanhMuc(danhMuc.getMaDanhMuc())
                .tenDanhMuc(danhMuc.getTenDanhMuc())
                .maDanhMucChaId(danhMuc.getMaDanhmucCha() != null ? danhMuc.getMaDanhmucCha().getMaDanhMuc() : null)
                .ngayTao(danhMuc.getNgayTao())
                .ngayCapNhat(danhMuc.getNgayCapNhat())
                .trangThai(danhMuc.getTrangThai())
                .nguoiTaoId(danhMuc.getNguoiTao() != null ? danhMuc.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(danhMuc.getNguoiCapNhat() != null ? danhMuc.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private danhmuc mapToEntity(danhmucdto danhMucDto) {
        return danhmuc.builder()
                .maDanhMuc(danhMucDto.getMaDanhMuc())
                .tenDanhMuc(danhMucDto.getTenDanhMuc())
                .ngayTao(danhMucDto.getNgayTao() != null ? danhMucDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(danhMucDto.getNgayCapNhat() != null ? danhMucDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(danhMucDto.getTrangThai() != null ? danhMucDto.getTrangThai() : true)
                .build();
    }
}
