package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.ThuongHieuDto;
import com.example.be.TempoTide.entity.ThuongHieu;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.repository.ThuongHieuRepository;
import com.example.be.TempoTide.service.ThuongHieuService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class ThuongHieuServiceImpl implements ThuongHieuService {

    private static final Logger logger = LoggerFactory.getLogger(ThuongHieuServiceImpl.class);

    private final ThuongHieuRepository thuongHieuRepository;
    private final NhanVienRepository nhanVienRepository;

    @Autowired
    public ThuongHieuServiceImpl(ThuongHieuRepository thuongHieuRepository, NhanVienRepository nhanVienRepository) {
        this.thuongHieuRepository = thuongHieuRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public ThuongHieuDto createThuongHieu(ThuongHieuDto thuongHieuDto) {
        logger.info("Creating new ThuongHieu: {}", thuongHieuDto.getTenThuongHieu());
        ThuongHieu thuongHieu = mapToEntity(thuongHieuDto);
        if (thuongHieuDto.getNguoiTaoId() != null) {
            thuongHieu.setNguoiTao(nhanVienRepository.findById(thuongHieuDto.getNguoiTaoId())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", thuongHieuDto.getNguoiTaoId());
                        return new RuntimeException("NguoiTao not found");
                    }));
        }
        if (thuongHieuDto.getNguoiCapNhatId() != null) {
            thuongHieu.setNguoiCapNhat(nhanVienRepository.findById(thuongHieuDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", thuongHieuDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }
        thuongHieu = thuongHieuRepository.save(thuongHieu);
        logger.info("ThuongHieu created with id: {}", thuongHieu.getMaThuongHieu());
        return mapToDto(thuongHieu);
    }

    @Override
    public ThuongHieuDto getThuongHieuById(Integer id) {
        logger.info("Fetching ThuongHieu with id: {}", id);
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThuongHieu not found with id: {}", id);
                    return new RuntimeException("ThuongHieu not found with id: " + id);
                });
        return mapToDto(thuongHieu);
    }

    @Override
    public List<ThuongHieuDto> getAllThuongHieu() {
        logger.info("Fetching all ThuongHieu");
        return thuongHieuRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ThuongHieuDto updateThuongHieu(Integer id, ThuongHieuDto thuongHieuDto) {
        logger.info("Updating ThuongHieu with id: {}", id);
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThuongHieu not found with id: {}", id);
                    return new RuntimeException("ThuongHieu not found with id: " + id);
                });

        thuongHieu.setTenThuongHieu(thuongHieuDto.getTenThuongHieu());
        thuongHieu.setMoTa(thuongHieuDto.getMoTa());
        thuongHieu.setHinhAnh(thuongHieuDto.getHinhAnh());
        thuongHieu.setTrangThai(thuongHieuDto.getTrangThai());
        thuongHieu.setNgayCapNhat(thuongHieuDto.getNgayCapNhat() != null ? thuongHieuDto.getNgayCapNhat() : LocalDateTime.now());

        if (thuongHieuDto.getNguoiCapNhatId() != null) {
            thuongHieu.setNguoiCapNhat(nhanVienRepository.findById(thuongHieuDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", thuongHieuDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    }));
        }

        thuongHieu = thuongHieuRepository.save(thuongHieu);
        logger.info("ThuongHieu updated with id: {}", thuongHieu.getMaThuongHieu());
        return mapToDto(thuongHieu);
    }

    @Override
    public void deleteThuongHieu(Integer id) {
        logger.info("Deleting ThuongHieu with id: {}", id);
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThuongHieu not found with id: {}", id);
                    return new RuntimeException("ThuongHieu not found with id: " + id);
                });
        thuongHieuRepository.delete(thuongHieu);
        logger.info("ThuongHieu deleted with id: {}", id);
    }

    private ThuongHieuDto mapToDto(ThuongHieu thuongHieu) {
        return ThuongHieuDto.builder()
                .maThuongHieu(thuongHieu.getMaThuongHieu())
                .tenThuongHieu(thuongHieu.getTenThuongHieu())
                .moTa(thuongHieu.getMoTa())
                .hinhAnh(thuongHieu.getHinhAnh())
                .ngayTao(thuongHieu.getNgayTao())
                .ngayCapNhat(thuongHieu.getNgayCapNhat())
                .trangThai(thuongHieu.getTrangThai())
                .nguoiTaoId(thuongHieu.getNguoiTao() != null ? thuongHieu.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(thuongHieu.getNguoiCapNhat() != null ? thuongHieu.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private ThuongHieu mapToEntity(ThuongHieuDto thuongHieuDto) {
        return ThuongHieu.builder()
                .maThuongHieu(thuongHieuDto.getMaThuongHieu())
                .tenThuongHieu(thuongHieuDto.getTenThuongHieu())
                .moTa(thuongHieuDto.getMoTa())
                .hinhAnh(thuongHieuDto.getHinhAnh())
                .ngayTao(thuongHieuDto.getNgayTao() != null ? thuongHieuDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(thuongHieuDto.getNgayCapNhat() != null ? thuongHieuDto.getNgayCapNhat() : LocalDateTime.now())
                .trangThai(thuongHieuDto.getTrangThai() != null ? thuongHieuDto.getTrangThai() : true)
                .build();
    }
}
