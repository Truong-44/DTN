package com.example.be.TempoTide.service.impl;


import com.example.be.TempoTide.dto.QuyenDto;
import com.example.be.TempoTide.entity.Quyen;
import com.example.be.TempoTide.repository.QuyenRepository;
import com.example.be.TempoTide.service.QuyenService;
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

    private final QuyenRepository quyenRepository;

    @Autowired
    public QuyenServiceImpl(QuyenRepository quyenRepository) {
        this.quyenRepository = quyenRepository;
    }

    @Override
    public QuyenDto createQuyen(QuyenDto quyenDto) {
        logger.info("Creating new Quyen: {}", quyenDto.getTenQuyen());
        if (quyenRepository.existsByTenQuyen(quyenDto.getTenQuyen())) {
            logger.error("Quyen with name {} already exists", quyenDto.getTenQuyen());
            throw new RuntimeException("Quyen with name " + quyenDto.getTenQuyen() + " already exists");
        }
        Quyen quyen = mapToEntity(quyenDto);
        quyen = quyenRepository.save(quyen);
        logger.info("Quyen created with id: {}", quyen.getMaQuyen());
        return mapToDto(quyen);
    }

    @Override
    public QuyenDto getQuyenById(Integer id) {
        logger.info("Fetching Quyen with id: {}", id);
        Quyen quyen = quyenRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Quyen not found with id: {}", id);
                    return new RuntimeException("Quyen not found with id: " + id);
                });
        return mapToDto(quyen);
    }

    @Override
    public List<QuyenDto> getAllQuyen() {
        logger.info("Fetching all Quyen");
        return quyenRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuyenDto updateQuyen(Integer id, QuyenDto quyenDto) {
        logger.info("Updating Quyen with id: {}", id);
        Quyen quyen = quyenRepository.findById(id)
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
        quyen.setNgayCapNhat(quyenDto.getNgayCapNhat() != null ? quyenDto.getNgayCapNhat() : LocalDateTime.now());

        quyen = quyenRepository.save(quyen);
        logger.info("Quyen updated with id: {}", quyen.getMaQuyen());
        return mapToDto(quyen);
    }

    @Override
    public void deleteQuyen(Integer id) {
        logger.info("Deleting Quyen with id: {}", id);
        Quyen quyen = quyenRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Quyen not found with id: {}", id);
                    return new RuntimeException("Quyen not found with id: " + id);
                });
        quyenRepository.delete(quyen);
        logger.info("Quyen deleted with id: {}", id);
    }

    private QuyenDto mapToDto(Quyen quyen) {
        return QuyenDto.builder()
                .maQuyen(quyen.getMaQuyen())
                .tenQuyen(quyen.getTenQuyen())
                .moTa(quyen.getMoTa())
                .ngayTao(quyen.getNgayTao())
                .ngayCapNhat(quyen.getNgayCapNhat())
                .build();
    }

    private Quyen mapToEntity(QuyenDto quyenDto) {
        return Quyen.builder()
                .maQuyen(quyenDto.getMaQuyen())
                .tenQuyen(quyenDto.getTenQuyen())
                .moTa(quyenDto.getMoTa())
                .ngayTao(quyenDto.getNgayTao() != null ? quyenDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(quyenDto.getNgayCapNhat() != null ? quyenDto.getNgayCapNhat() : LocalDateTime.now())
                .build();
    }
}