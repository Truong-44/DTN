package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.VaiTro_QuyenDto;
import com.example.be.TempoTide.entity.Quyen;
import com.example.be.TempoTide.entity.VaiTro;
import com.example.be.TempoTide.entity.VaiTro_Quyen;
import com.example.be.TempoTide.repository.QuyenRepository;
import com.example.be.TempoTide.repository.VaiTroRepository;
import com.example.be.TempoTide.repository.VaiTro_QuyenRepository;
import com.example.be.TempoTide.service.VaiTro_QuyenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VaiTro_QuyenServiceImpl implements VaiTro_QuyenService {

    private static final Logger logger = LoggerFactory.getLogger(VaiTro_QuyenServiceImpl.class);

    private final VaiTro_QuyenRepository vaiTro_QuyenRepository;
    private final VaiTroRepository vaiTroRepository;
    private final QuyenRepository quyenRepository;

    @Autowired
    public VaiTro_QuyenServiceImpl(VaiTro_QuyenRepository vaiTro_QuyenRepository,
                                   VaiTroRepository vaiTroRepository,
                                   QuyenRepository quyenRepository) {
        this.vaiTro_QuyenRepository = vaiTro_QuyenRepository;
        this.vaiTroRepository = vaiTroRepository;
        this.quyenRepository = quyenRepository;
    }

    @Override
    public VaiTro_QuyenDto createVaiTro_Quyen(VaiTro_QuyenDto vaiTro_QuyenDto) {
        logger.info("Creating new VaiTro_Quyen for MaVaiTro: {} and MaQuyen: {}", vaiTro_QuyenDto.getMaVaiTro(), vaiTro_QuyenDto.getMaQuyen());
        VaiTro_Quyen vaiTro_Quyen = mapToEntity(vaiTro_QuyenDto);

        VaiTro vaiTro = vaiTroRepository.findById(vaiTro_QuyenDto.getMaVaiTro())
                .orElseThrow(() -> {
                    logger.error("VaiTro not found with id: {}", vaiTro_QuyenDto.getMaVaiTro());
                    return new RuntimeException("VaiTro not found");
                });
        vaiTro_Quyen.setVaiTro(vaiTro);

        Quyen quyen = quyenRepository.findById(vaiTro_QuyenDto.getMaQuyen())
                .orElseThrow(() -> {
                    logger.error("Quyen not found with id: {}", vaiTro_QuyenDto.getMaQuyen());
                    return new RuntimeException("Quyen not found");
                });
        vaiTro_Quyen.setQuyen(quyen);

        vaiTro_Quyen = vaiTro_QuyenRepository.save(vaiTro_Quyen);
        logger.info("VaiTro_Quyen created with MaVaiTro: {} and MaQuyen: {}", vaiTro_Quyen.getId().getMaVaiTro(), vaiTro_Quyen.getId().getMaQuyen());
        return mapToDto(vaiTro_Quyen);
    }

    @Override
    public VaiTro_QuyenDto getVaiTro_QuyenById(Integer maVaiTro, Integer maQuyen) {
        logger.info("Fetching VaiTro_Quyen with MaVaiTro: {} and MaQuyen: {}", maVaiTro, maQuyen);
        VaiTro_Quyen vaiTro_Quyen = vaiTro_QuyenRepository.findById(new VaiTro_Quyen.VaiTro_QuyenId(maVaiTro, maQuyen))
                .orElseThrow(() -> {
                    logger.error("VaiTro_Quyen not found with MaVaiTro: {} and MaQuyen: {}", maVaiTro, maQuyen);
                    return new RuntimeException("VaiTro_Quyen not found with MaVaiTro: " + maVaiTro + " and MaQuyen: " + maQuyen);
                });
        return mapToDto(vaiTro_Quyen);
    }

    @Override
    public List<VaiTro_QuyenDto> getAllVaiTro_Quyen() {
        logger.info("Fetching all VaiTro_Quyen");
        return vaiTro_QuyenRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VaiTro_QuyenDto updateVaiTro_Quyen(Integer maVaiTro, Integer maQuyen, VaiTro_QuyenDto vaiTro_QuyenDto) {
        logger.info("Updating VaiTro_Quyen with MaVaiTro: {} and MaQuyen: {}", maVaiTro, maQuyen);
        VaiTro_Quyen vaiTro_Quyen = vaiTro_QuyenRepository.findById(new VaiTro_Quyen.VaiTro_QuyenId(maVaiTro, maQuyen))
                .orElseThrow(() -> {
                    logger.error("VaiTro_Quyen not found with MaVaiTro: {} and MaQuyen: {}", maVaiTro, maQuyen);
                    return new RuntimeException("VaiTro_Quyen not found with MaVaiTro: " + maVaiTro + " and MaQuyen: " + maQuyen);
                });

        vaiTro_Quyen.setTrangThai(vaiTro_QuyenDto.getTrangThai());

        VaiTro vaiTro = vaiTroRepository.findById(vaiTro_QuyenDto.getMaVaiTro())
                .orElseThrow(() -> {
                    logger.error("VaiTro not found with id: {}", vaiTro_QuyenDto.getMaVaiTro());
                    return new RuntimeException("VaiTro not found");
                });
        vaiTro_Quyen.setVaiTro(vaiTro);

        Quyen quyen = quyenRepository.findById(vaiTro_QuyenDto.getMaQuyen())
                .orElseThrow(() -> {
                    logger.error("Quyen not found with id: {}", vaiTro_QuyenDto.getMaQuyen());
                    return new RuntimeException("Quyen not found");
                });
        vaiTro_Quyen.setQuyen(quyen);

        vaiTro_Quyen = vaiTro_QuyenRepository.save(vaiTro_Quyen);
        logger.info("VaiTro_Quyen updated with MaVaiTro: {} and MaQuyen: {}", vaiTro_Quyen.getId().getMaVaiTro(), vaiTro_Quyen.getId().getMaQuyen());
        return mapToDto(vaiTro_Quyen);
    }

    @Override
    public void deleteVaiTro_Quyen(Integer maVaiTro, Integer maQuyen) {
        logger.info("Deleting VaiTro_Quyen with MaVaiTro: {} and MaQuyen: {}", maVaiTro, maQuyen);
        VaiTro_Quyen vaiTro_Quyen = vaiTro_QuyenRepository.findById(new VaiTro_Quyen.VaiTro_QuyenId(maVaiTro, maQuyen))
                .orElseThrow(() -> {
                    logger.error("VaiTro_Quyen not found with MaVaiTro: {} and MaQuyen: {}", maVaiTro, maQuyen);
                    return new RuntimeException("VaiTro_Quyen not found with MaVaiTro: " + maVaiTro + " and MaQuyen: " + maQuyen);
                });
        vaiTro_QuyenRepository.delete(vaiTro_Quyen);
        logger.info("VaiTro_Quyen deleted with MaVaiTro: {} and MaQuyen: {}", maVaiTro, maQuyen);
    }

    private VaiTro_QuyenDto mapToDto(VaiTro_Quyen vaiTro_Quyen) {
        return VaiTro_QuyenDto.builder()
                .maVaiTro(vaiTro_Quyen.getId().getMaVaiTro())
                .maQuyen(vaiTro_Quyen.getId().getMaQuyen())
                .trangThai(vaiTro_Quyen.getTrangThai())
                .build();
    }

    private VaiTro_Quyen mapToEntity(VaiTro_QuyenDto vaiTro_QuyenDto) {
        return VaiTro_Quyen.builder()
                .id(new VaiTro_Quyen.VaiTro_QuyenId(vaiTro_QuyenDto.getMaVaiTro(), vaiTro_QuyenDto.getMaQuyen()))
                .trangThai(vaiTro_QuyenDto.getTrangThai())
                .build();
    }
}