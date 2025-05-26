package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.quyendto;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.entity.quyen;
import com.example.be.tempotide.repository.nhanvienrepository;
import com.example.be.tempotide.repository.quyenrepository;
import com.example.be.tempotide.service.QuyenService;
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
public class QuyenServiceImpl implements QuyenService {

    private static final Logger logger = LoggerFactory.getLogger(QuyenServiceImpl.class);

    private final quyenrepository quyenRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public QuyenServiceImpl(quyenrepository quyenRepository, nhanvienrepository nhanVienRepository) {
        this.quyenRepository = quyenRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public quyendto createQuyen(quyendto quyenDto) {
        logger.info("Creating new Quyen: {}", quyenDto.getTenQuyen());
        if (quyenRepository.existsByTenQuyen(quyenDto.getTenQuyen())) {
            logger.error("Quyen with name {} already exists", quyenDto.getTenQuyen());
            throw new RuntimeException("Quyen with name " + quyenDto.getTenQuyen() + " already exists");
        }
        quyen quyen = mapToEntity(quyenDto);
        if (quyenDto.getNguoiTaoId() != null) {
            nhanvien nguoiTao = nhanVienRepository.findById(quyenDto.getNguoiTaoId())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", quyenDto.getNguoiTaoId());
                        return new RuntimeException("NguoiTao not found");
                    });
            quyen.setNguoiTao(nguoiTao);
        }
        if (quyenDto.getNguoiCapNhatId() != null) {
            nhanvien nguoiCapNhat = nhanVienRepository.findById(quyenDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", quyenDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    });
            quyen.setNguoiCapNhat(nguoiCapNhat);
        }
        quyen = quyenRepository.save(quyen);
        logger.info("Quyen created with id: {}", quyen.getMaQuyen());
        return mapToDto(quyen);
    }

    @Override
    public quyendto getQuyenById(Integer id) {
        logger.info("Fetching Quyen with id: {}", id);
        quyen quyen = quyenRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Quyen not found with id: {}", id);
                    return new RuntimeException("Quyen not found with id: " + id);
                });
        return mapToDto(quyen);
    }

    @Override
    public List<quyendto> getAllQuyen() {
        logger.info("Fetching all Quyen");
        return quyenRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public quyendto updateQuyen(Integer id, quyendto quyenDto) {
        logger.info("Updating Quyen with id: {}", id);
        quyen quyen = quyenRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Quyen not found with id: {}", id);
                    return new RuntimeException("Quyen not found with id: " + id);
                });

        if (!quyen.getTenQuyen().equals(quyenDto.getTenQuyen()) && quyenRepository.existsByTenQuyen(quyenDto.getTenQuyen())) {
            logger.error("Quyen with name {} already exists", quyenDto.getTenQuyen());
            throw new RuntimeException("Quyen with name " + quyenDto.getTenQuyen() + " already exists");
        }

        quyen.setTenQuyen(quyenDto.getTenQuyen());
        quyen.setMoTa(quyenDto.getMoTa());
        quyen.setTrangThai(quyenDto.getTrangThai());
        quyen.setNgayCapNhat(quyenDto.getNgayCapNhat() != null ? quyenDto.getNgayCapNhat() : LocalDateTime.now());

        if (quyenDto.getNguoiCapNhatId() != null) {
            nhanvien nguoiCapNhat = nhanVienRepository.findById(quyenDto.getNguoiCapNhatId())
                    .orElseThrow(() -> {
                        logger.error("NguoiCapNhat not found with id: {}", quyenDto.getNguoiCapNhatId());
                        return new RuntimeException("NguoiCapNhat not found");
                    });
            quyen.setNguoiCapNhat(nguoiCapNhat);
        }

        quyen = quyenRepository.save(quyen);
        logger.info("Quyen updated with id: {}", quyen.getMaQuyen());
        return mapToDto(quyen);
    }

    @Override
    public void deleteQuyen(Integer id) {
        logger.info("Deleting Quyen with id: {}", id);
        quyen quyen = quyenRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Quyen not found with id: {}", id);
                    return new RuntimeException("Quyen not found with id: " + id);
                });
        quyenRepository.delete(quyen);
        logger.info("Quyen deleted with id: {}", id);
    }

    private quyendto mapToDto(quyen quyen) {
        return quyendto.builder()
                .maQuyen(quyen.getMaQuyen())
                .tenQuyen(quyen.getTenQuyen())
                .moTa(quyen.getMoTa())
                .trangThai(quyen.getTrangThai())
                .ngayTao(quyen.getNgayTao())
                .ngayCapNhat(quyen.getNgayCapNhat())
                .nguoiTaoId(quyen.getNguoiTao() != null ? quyen.getNguoiTao().getMaNhanVien() : null)
                .nguoiCapNhatId(quyen.getNguoiCapNhat() != null ? quyen.getNguoiCapNhat().getMaNhanVien() : null)
                .build();
    }

    private quyen mapToEntity(quyendto quyenDto) {
        return quyen.builder()
                .maQuyen(quyenDto.getMaQuyen())
                .tenQuyen(quyenDto.getTenQuyen())
                .moTa(quyenDto.getMoTa())
                .trangThai(quyenDto.getTrangThai() != null ? quyenDto.getTrangThai() : true)
                .ngayTao(quyenDto.getNgayTao() != null ? quyenDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(quyenDto.getNgayCapNhat() != null ? quyenDto.getNgayCapNhat() : LocalDateTime.now())
                .build();
    }
}