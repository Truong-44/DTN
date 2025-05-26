package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.CauHinhHeThongDto;
import com.example.be.TempoTide.entity.CauHinhHeThong;
import com.example.be.TempoTide.repository.CauHinhHeThongRepository;
import com.example.be.TempoTide.service.CauHinhHeThongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CauHinhHeThongServiceImpl implements CauHinhHeThongService {

    private static final Logger logger = LoggerFactory.getLogger(CauHinhHeThongServiceImpl.class);

    private final CauHinhHeThongRepository cauHinhHeThongRepository;

    @Autowired
    public CauHinhHeThongServiceImpl(CauHinhHeThongRepository cauHinhHeThongRepository) {
        this.cauHinhHeThongRepository = cauHinhHeThongRepository;
    }

    @Override
    public CauHinhHeThongDto createCauHinhHeThong(CauHinhHeThongDto cauHinhHeThongDto) {
        logger.info("Creating new CauHinhHeThong for TenCauHinh: {}", cauHinhHeThongDto.getTenCauHinh());
        CauHinhHeThong cauHinhHeThong = mapToEntity(cauHinhHeThongDto);
        cauHinhHeThong = cauHinhHeThongRepository.save(cauHinhHeThong);
        logger.info("CauHinhHeThong created with id: {}", cauHinhHeThong.getMaCauHinh());
        return mapToDto(cauHinhHeThong);
    }

    @Override
    public CauHinhHeThongDto getCauHinhHeThongById(Integer id) {
        logger.info("Fetching CauHinhHeThong with id: {}", id);
        CauHinhHeThong cauHinhHeThong = cauHinhHeThongRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("CauHinhHeThong not found with id: {}", id);
                    return new RuntimeException("CauHinhHeThong not found with id: " + id);
                });
        return mapToDto(cauHinhHeThong);
    }

    @Override
    public List<CauHinhHeThongDto> getAllCauHinhHeThong() {
        logger.info("Fetching all CauHinhHeThong");
        return cauHinhHeThongRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CauHinhHeThongDto updateCauHinhHeThong(Integer id, CauHinhHeThongDto cauHinhHeThongDto) {
        logger.info("Updating CauHinhHeThong with id: {}", id);
        CauHinhHeThong cauHinhHeThong = cauHinhHeThongRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("CauHinhHeThong not found with id: {}", id);
                    return new RuntimeException("CauHinhHeThong not found with id: " + id);
                });

        cauHinhHeThong.setTenCauHinh(cauHinhHeThongDto.getTenCauHinh());
        cauHinhHeThong.setGiaTri(cauHinhHeThongDto.getGiaTri());
        cauHinhHeThong.setMoTa(cauHinhHeThongDto.getMoTa());
        cauHinhHeThong.setTrangThai(cauHinhHeThongDto.getTrangThai());

        cauHinhHeThong = cauHinhHeThongRepository.save(cauHinhHeThong);
        logger.info("CauHinhHeThong updated with id: {}", cauHinhHeThong.getMaCauHinh());
        return mapToDto(cauHinhHeThong);
    }

    @Override
    public void deleteCauHinhHeThong(Integer id) {
        logger.info("Deleting CauHinhHeThong with id: {}", id);
        CauHinhHeThong cauHinhHeThong = cauHinhHeThongRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("CauHinhHeThong not found with id: {}", id);
                    return new RuntimeException("CauHinhHeThong not found with id: " + id);
                });
        cauHinhHeThongRepository.delete(cauHinhHeThong);
        logger.info("CauHinhHeThong deleted with id: {}", id);
    }

    private CauHinhHeThongDto mapToDto(CauHinhHeThong cauHinhHeThong) {
        return CauHinhHeThongDto.builder()
                .maCauHinh(cauHinhHeThong.getMaCauHinh())
                .tenCauHinh(cauHinhHeThong.getTenCauHinh())
                .giaTri(cauHinhHeThong.getGiaTri())
                .moTa(cauHinhHeThong.getMoTa())
                .trangThai(cauHinhHeThong.getTrangThai())
                .build();
    }

    private CauHinhHeThong mapToEntity(CauHinhHeThongDto cauHinhHeThongDto) {
        return CauHinhHeThong.builder()
                .maCauHinh(cauHinhHeThongDto.getMaCauHinh())
                .tenCauHinh(cauHinhHeThongDto.getTenCauHinh())
                .giaTri(cauHinhHeThongDto.getGiaTri())
                .moTa(cauHinhHeThongDto.getMoTa())
                .trangThai(cauHinhHeThongDto.getTrangThai())
                .build();
    }
}