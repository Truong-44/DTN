package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.vaitro_quyendto;
import com.example.be.tempotide.entity.quyen;
import com.example.be.tempotide.entity.vaitro;
import com.example.be.tempotide.entity.vaitro_quyen;
import com.example.be.tempotide.repository.quyenrepository;
import com.example.be.tempotide.repository.vaitrorepository;
import com.example.be.tempotide.repository.vaitro_quyenrepository;
import com.example.be.tempotide.service.VaiTro_QuyenService;
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

    private final vaitro_quyenrepository vaiTro_QuyenRepository;
    private final vaitrorepository vaiTroRepository;
    private final quyenrepository quyenRepository;

    @Autowired
    public VaiTro_QuyenServiceImpl(vaitro_quyenrepository vaiTro_QuyenRepository,
                                   vaitrorepository vaiTroRepository,
                                   quyenrepository quyenRepository) {
        this.vaiTro_QuyenRepository = vaiTro_QuyenRepository;
        this.vaiTroRepository = vaiTroRepository;
        this.quyenRepository = quyenRepository;
    }

    @Override
    public vaitro_quyendto createVaiTro_Quyen(vaitro_quyendto vaiTro_QuyenDto) {
        logger.info("Creating new VaiTro_Quyen for MaVaiTro: {} and MaQuyen: {}", vaiTro_QuyenDto.getMaVaiTro(), vaiTro_QuyenDto.getMaQuyen());
        vaitro_quyen vaiTro_Quyen = mapToEntity(vaiTro_QuyenDto);

        vaitro vaiTro = vaiTroRepository.findById(vaiTro_QuyenDto.getMaVaiTro())
                .orElseThrow(() -> {
                    logger.error("VaiTro not found with id: {}", vaiTro_QuyenDto.getMaVaiTro());
                    return new RuntimeException("VaiTro not found");
                });
        vaiTro_Quyen.setVaiTro(vaiTro);

        quyen quyen = quyenRepository.findById(vaiTro_QuyenDto.getMaQuyen())
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
    public vaitro_quyendto getVaiTro_QuyenById(Integer maVaiTro, Integer maQuyen) {
        logger.info("Fetching VaiTro_Quyen with MaVaiTro: {} and MaQuyen: {}", maVaiTro, maQuyen);
        vaitro_quyen vaiTro_Quyen = vaiTro_QuyenRepository.findById(new vaitro_quyen.VaiTro_QuyenId(maVaiTro, maQuyen))
                .orElseThrow(() -> {
                    logger.error("VaiTro_Quyen not found with MaVaiTro: {} and MaQuyen: {}", maVaiTro, maQuyen);
                    return new RuntimeException("VaiTro_Quyen not found with MaVaiTro: " + maVaiTro + " and MaQuyen: " + maQuyen);
                });
        return mapToDto(vaiTro_Quyen);
    }

    @Override
    public List<vaitro_quyendto> getAllVaiTro_Quyen() {
        logger.info("Fetching all VaiTro_Quyen");
        return vaiTro_QuyenRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public vaitro_quyendto updateVaiTro_Quyen(Integer maVaiTro, Integer maQuyen, vaitro_quyendto vaiTro_QuyenDto) {
        logger.info("Updating VaiTro_Quyen with MaVaiTro: {} and MaQuyen: {}", maVaiTro, maQuyen);
        vaitro_quyen vaiTro_Quyen = vaiTro_QuyenRepository.findById(new vaitro_quyen.VaiTro_QuyenId(maVaiTro, maQuyen))
                .orElseThrow(() -> {
                    logger.error("VaiTro_Quyen not found with MaVaiTro: {} and MaQuyen: {}", maVaiTro, maQuyen);
                    return new RuntimeException("VaiTro_Quyen not found with MaVaiTro: " + maVaiTro + " and MaQuyen: " + maQuyen);
                });

        vaiTro_Quyen.setTrangThai(vaiTro_QuyenDto.getTrangThai());

        vaitro vaiTro = vaiTroRepository.findById(vaiTro_QuyenDto.getMaVaiTro())
                .orElseThrow(() -> {
                    logger.error("VaiTro not found with id: {}", vaiTro_QuyenDto.getMaVaiTro());
                    return new RuntimeException("VaiTro not found");
                });
        vaiTro_Quyen.setVaiTro(vaiTro);

        quyen quyen = quyenRepository.findById(vaiTro_QuyenDto.getMaQuyen())
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
        vaitro_quyen vaiTro_Quyen = vaiTro_QuyenRepository.findById(new vaitro_quyen.VaiTro_QuyenId(maVaiTro, maQuyen))
                .orElseThrow(() -> {
                    logger.error("VaiTro_Quyen not found with MaVaiTro: {} and MaQuyen: {}", maVaiTro, maQuyen);
                    return new RuntimeException("VaiTro_Quyen not found with MaVaiTro: " + maVaiTro + " and MaQuyen: " + maQuyen);
                });
        vaiTro_QuyenRepository.delete(vaiTro_Quyen);
        logger.info("VaiTro_Quyen deleted with MaVaiTro: {} and MaQuyen: {}", maVaiTro, maQuyen);
    }

    private vaitro_quyendto mapToDto(vaitro_quyen vaiTro_Quyen) {
        return vaitro_quyendto.builder()
                .maVaiTro(vaiTro_Quyen.getId().getMaVaiTro())
                .maQuyen(vaiTro_Quyen.getId().getMaQuyen())
                .trangThai(vaiTro_Quyen.getTrangThai())
                .build();
    }

    private vaitro_quyen mapToEntity(vaitro_quyendto vaiTro_QuyenDto) {
        return vaitro_quyen.builder()
                .id(new vaitro_quyen.VaiTro_QuyenId(vaiTro_QuyenDto.getMaVaiTro(), vaiTro_QuyenDto.getMaQuyen()))
                .trangThai(vaiTro_QuyenDto.getTrangThai())
                .build();
    }
}