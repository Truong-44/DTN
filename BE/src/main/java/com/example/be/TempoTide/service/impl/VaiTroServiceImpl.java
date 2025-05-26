package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.VaiTroDto;
import com.example.be.TempoTide.entity.Quyen;
import com.example.be.TempoTide.entity.VaiTro;
import com.example.be.TempoTide.repository.QuyenRepository;
import com.example.be.TempoTide.repository.VaiTroRepository;
import com.example.be.TempoTide.service.VaiTroService;
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
public class VaiTroServiceImpl implements VaiTroService {

    private static final Logger logger = LoggerFactory.getLogger(VaiTroServiceImpl.class);

    private final VaiTroRepository vaiTroRepository;
    private final QuyenRepository quyenRepository;

    @Autowired
    public VaiTroServiceImpl(VaiTroRepository vaiTroRepository, QuyenRepository quyenRepository) {
        this.vaiTroRepository = vaiTroRepository;
        this.quyenRepository = quyenRepository;
    }

    @Override
    public VaiTroDto createVaiTro(VaiTroDto vaiTroDto) {
        logger.info("Creating new VaiTro: {}", vaiTroDto.getTenVaiTro());
        if (vaiTroRepository.existsByTenVaiTro(vaiTroDto.getTenVaiTro())) {
            logger.error("VaiTro with name {} already exists", vaiTroDto.getTenVaiTro());
            throw new RuntimeException("VaiTro with name " + vaiTroDto.getTenVaiTro() + " already exists");
        }
        VaiTro vaiTro = mapToEntity(vaiTroDto);
        if (vaiTroDto.getQuyenIds() != null && !vaiTroDto.getQuyenIds().isEmpty()) {
            List<Quyen> quyens = quyenRepository.findAllById(vaiTroDto.getQuyenIds());
            if (quyens.size() != vaiTroDto.getQuyenIds().size()) {
                logger.error("Some Quyens not found for IDs: {}", vaiTroDto.getQuyenIds());
                throw new RuntimeException("Some Quyens not found");
            }
            vaiTro.setQuyens(quyens);
        }
        vaiTro = vaiTroRepository.save(vaiTro);
        logger.info("VaiTro created with id: {}", vaiTro.getMaVaiTro());
        return mapToDto(vaiTro);
    }

    @Override
    public VaiTroDto getVaiTroById(Integer id) {
        logger.info("Fetching VaiTro with id: {}", id);
        VaiTro vaiTro = vaiTroRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("VaiTro not found with id: {}", id);
                    return new RuntimeException("VaiTro not found with id: " + id);
                });
        return mapToDto(vaiTro);
    }

    @Override
    public List<VaiTroDto> getAllVaiTro() {
        logger.info("Fetching all VaiTro");
        return vaiTroRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VaiTroDto updateVaiTro(Integer id, VaiTroDto vaiTroDto) {
        logger.info("Updating VaiTro with id: {}", id);
        VaiTro vaiTro = vaiTroRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("VaiTro not found with id: {}", id);
                    return new RuntimeException("VaiTro not found with id: " + id);
                });

        if (!vaiTro.getTenVaiTro().equals(vaiTroDto.getTenVaiTro()) && vaiTroRepository.existsByTenVaiTro(vaiTroDto.getTenVaiTro())) {
            logger.error("VaiTro with name {} already exists", vaiTroDto.getTenVaiTro());
            throw new RuntimeException("VaiTro with name " + vaiTroDto.getTenVaiTro() + " already exists");
        }

        vaiTro.setTenVaiTro(vaiTroDto.getTenVaiTro());
        vaiTro.setMoTa(vaiTroDto.getMoTa());
        if (vaiTroDto.getQuyenIds() != null) {
            List<Quyen> quyens = quyenRepository.findAllById(vaiTroDto.getQuyenIds());
            if (quyens.size() != vaiTroDto.getQuyenIds().size()) {
                logger.error("Some Quyens not found for IDs: {}", vaiTroDto.getQuyenIds());
                throw new RuntimeException("Some Quyens not found");
            }
            vaiTro.setQuyens(quyens);
        }
        vaiTro.setNgayCapNhat(vaiTroDto.getNgayCapNhat() != null ? vaiTroDto.getNgayCapNhat() : LocalDateTime.now());

        vaiTro = vaiTroRepository.save(vaiTro);
        logger.info("VaiTro updated with id: {}", vaiTro.getMaVaiTro());
        return mapToDto(vaiTro);
    }

    @Override
    public void deleteVaiTro(Integer id) {
        logger.info("Deleting VaiTro with id: {}", id);
        VaiTro vaiTro = vaiTroRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("VaiTro not found with id: {}", id);
                    return new RuntimeException("VaiTro not found with id: " + id);
                });
        vaiTroRepository.delete(vaiTro);
        logger.info("VaiTro deleted with id: {}", id);
    }

    private VaiTroDto mapToDto(VaiTro vaiTro) {
        return VaiTroDto.builder()
                .maVaiTro(vaiTro.getMaVaiTro())
                .tenVaiTro(vaiTro.getTenVaiTro())
                .moTa(vaiTro.getMoTa())
                .quyenIds(vaiTro.getQuyens() != null ? vaiTro.getQuyens().stream()
                        .map(Quyen::getMaQuyen)
                        .collect(Collectors.toList()) : null)
                .ngayTao(vaiTro.getNgayTao())
                .ngayCapNhat(vaiTro.getNgayCapNhat())
                .build();
    }

    private VaiTro mapToEntity(VaiTroDto vaiTroDto) {
        return VaiTro.builder()
                .maVaiTro(vaiTroDto.getMaVaiTro())
                .tenVaiTro(vaiTroDto.getTenVaiTro())
                .moTa(vaiTroDto.getMoTa())
                .ngayTao(vaiTroDto.getNgayTao() != null ? vaiTroDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(vaiTroDto.getNgayCapNhat() != null ? vaiTroDto.getNgayCapNhat() : LocalDateTime.now())
                .build();
    }
}