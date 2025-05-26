package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.vaitrodto;
import com.example.be.tempotide.entity.quyen;
import com.example.be.tempotide.entity.vaitro;
import com.example.be.tempotide.repository.quyenrepository;
import com.example.be.tempotide.repository.vaitrorepository;
import com.example.be.tempotide.service.VaiTroService;
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

    private final vaitrorepository vaiTroRepository;
    private final quyenrepository quyenRepository;

    @Autowired
    public VaiTroServiceImpl(vaitrorepository vaiTroRepository, quyenrepository quyenRepository) {
        this.vaiTroRepository = vaiTroRepository;
        this.quyenRepository = quyenRepository;
    }

    @Override
    public vaitrodto createVaiTro(vaitrodto vaiTroDto) {
        logger.info("Creating new VaiTro: {}", vaiTroDto.getTenVaiTro());
        if (vaiTroRepository.existsByTenVaiTro(vaiTroDto.getTenVaiTro())) {
            logger.error("VaiTro with name {} already exists", vaiTroDto.getTenVaiTro());
            throw new RuntimeException("VaiTro with name " + vaiTroDto.getTenVaiTro() + " already exists");
        }
        vaitro vaiTro = mapToEntity(vaiTroDto);
        if (vaiTroDto.getQuyenIds() != null && !vaiTroDto.getQuyenIds().isEmpty()) {
            List<quyen> quyens = quyenRepository.findAllById(vaiTroDto.getQuyenIds());
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
    public vaitrodto getVaiTroById(Integer id) {
        logger.info("Fetching VaiTro with id: {}", id);
        vaitro vaiTro = vaiTroRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("VaiTro not found with id: {}", id);
                    return new RuntimeException("VaiTro not found with id: " + id);
                });
        return mapToDto(vaiTro);
    }

    @Override
    public List<vaitrodto> getAllVaiTro() {
        logger.info("Fetching all VaiTro");
        return vaiTroRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public vaitrodto updateVaiTro(Integer id, vaitrodto vaiTroDto) {
        logger.info("Updating VaiTro with id: {}", id);
        vaitro vaiTro = vaiTroRepository.findById(id)
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
            List<quyen> quyens = quyenRepository.findAllById(vaiTroDto.getQuyenIds());
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
        vaitro vaiTro = vaiTroRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("VaiTro not found with id: {}", id);
                    return new RuntimeException("VaiTro not found with id: " + id);
                });
        vaiTroRepository.delete(vaiTro);
        logger.info("VaiTro deleted with id: {}", id);
    }

    private vaitrodto mapToDto(vaitro vaiTro) {
        return vaitrodto.builder()
                .maVaiTro(vaiTro.getMaVaiTro())
                .tenVaiTro(vaiTro.getTenVaiTro())
                .moTa(vaiTro.getMoTa())
                .quyenIds(vaiTro.getQuyens() != null ? vaiTro.getQuyens().stream()
                        .map(quyen::getMaQuyen)
                        .collect(Collectors.toList()) : null)
                .ngayTao(vaiTro.getNgayTao())
                .ngayCapNhat(vaiTro.getNgayCapNhat())
                .build();
    }

    private vaitro mapToEntity(vaitrodto vaiTroDto) {
        return vaitro.builder()
                .maVaiTro(vaiTroDto.getMaVaiTro())
                .tenVaiTro(vaiTroDto.getTenVaiTro())
                .moTa(vaiTroDto.getMoTa())
                .ngayTao(vaiTroDto.getNgayTao() != null ? vaiTroDto.getNgayTao() : LocalDateTime.now())
                .ngayCapNhat(vaiTroDto.getNgayCapNhat() != null ? vaiTroDto.getNgayCapNhat() : LocalDateTime.now())
                .build();
    }
}