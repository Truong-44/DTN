package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.thuonghieudto;
import com.example.be.tempotide.entity.thuonghieu;
import com.example.be.tempotide.repository.nhanvienrepository;
import com.example.be.tempotide.repository.thuonghieurepository;
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
public class thuonghieuserviceImpl implements com.example.be.tempotide.service.thuonghieuservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.thuonghieuserviceImpl.class);

    private final thuonghieurepository thuongHieuRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public thuonghieuserviceImpl(thuonghieurepository thuongHieuRepository, nhanvienrepository nhanVienRepository) {
        this.thuongHieuRepository = thuongHieuRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public thuonghieudto createThuongHieu(thuonghieudto thuongHieuDto) {
        logger.info("Creating new ThuongHieu: {}", thuongHieuDto.getTenThuongHieu());
        thuonghieu thuongHieu = mapToEntity(thuongHieuDto);
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
    public thuonghieudto getThuongHieuById(Integer id) {
        logger.info("Fetching ThuongHieu with id: {}", id);
        thuonghieu thuongHieu = thuongHieuRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThuongHieu not found with id: {}", id);
                    return new RuntimeException("ThuongHieu not found with id: " + id);
                });
        return mapToDto(thuongHieu);
    }

    @Override
    public List<thuonghieudto> getAllThuongHieu() {
        logger.info("Fetching all ThuongHieu");
        return thuongHieuRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public thuonghieudto updateThuongHieu(Integer id, thuonghieudto thuongHieuDto) {
        logger.info("Updating ThuongHieu with id: {}", id);
        thuonghieu thuongHieu = thuongHieuRepository.findById(id)
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
        thuonghieu thuongHieu = thuongHieuRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ThuongHieu not found with id: {}", id);
                    return new RuntimeException("ThuongHieu not found with id: " + id);
                });
        thuongHieuRepository.delete(thuongHieu);
        logger.info("ThuongHieu deleted with id: {}", id);
    }

    private thuonghieudto mapToDto(thuonghieu thuongHieu) {
        return thuonghieudto.builder()
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

    private thuonghieu mapToEntity(thuonghieudto thuongHieuDto) {
        return thuonghieu.builder()
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
