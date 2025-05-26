package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.cauhinhhethongdto;
import com.example.be.tempotide.entity.cauhinhhethong;
import com.example.be.tempotide.repository.cauhinhhethongrepository;
import com.example.be.tempotide.service.CauHinhHeThongService;
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

    private final cauhinhhethongrepository cauHinhHeThongRepository;

    @Autowired
    public CauHinhHeThongServiceImpl(cauhinhhethongrepository cauHinhHeThongRepository) {
        this.cauHinhHeThongRepository = cauHinhHeThongRepository;
    }

    @Override
    public cauhinhhethongdto createCauHinhHeThong(cauhinhhethongdto cauHinhHeThongDto) {
        logger.info("Creating new CauHinhHeThong for TenCauHinh: {}", cauHinhHeThongDto.getTenCauHinh());
        cauhinhhethong cauHinhHeThong = mapToEntity(cauHinhHeThongDto);
        cauHinhHeThong = cauHinhHeThongRepository.save(cauHinhHeThong);
        logger.info("CauHinhHeThong created with id: {}", cauHinhHeThong.getMaCauHinh());
        return mapToDto(cauHinhHeThong);
    }

    @Override
    public cauhinhhethongdto getCauHinhHeThongById(Integer id) {
        logger.info("Fetching CauHinhHeThong with id: {}", id);
        cauhinhhethong cauHinhHeThong = cauHinhHeThongRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("CauHinhHeThong not found with id: {}", id);
                    return new RuntimeException("CauHinhHeThong not found with id: " + id);
                });
        return mapToDto(cauHinhHeThong);
    }

    @Override
    public List<cauhinhhethongdto> getAllCauHinhHeThong() {
        logger.info("Fetching all CauHinhHeThong");
        return cauHinhHeThongRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public cauhinhhethongdto updateCauHinhHeThong(Integer id, cauhinhhethongdto cauHinhHeThongDto) {
        logger.info("Updating CauHinhHeThong with id: {}", id);
        cauhinhhethong cauHinhHeThong = cauHinhHeThongRepository.findById(id)
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
        cauhinhhethong cauHinhHeThong = cauHinhHeThongRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("CauHinhHeThong not found with id: {}", id);
                    return new RuntimeException("CauHinhHeThong not found with id: " + id);
                });
        cauHinhHeThongRepository.delete(cauHinhHeThong);
        logger.info("CauHinhHeThong deleted with id: {}", id);
    }

    private cauhinhhethongdto mapToDto(cauhinhhethong cauHinhHeThong) {
        return cauhinhhethongdto.builder()
                .maCauHinh(cauHinhHeThong.getMaCauHinh())
                .tenCauHinh(cauHinhHeThong.getTenCauHinh())
                .giaTri(cauHinhHeThong.getGiaTri())
                .moTa(cauHinhHeThong.getMoTa())
                .trangThai(cauHinhHeThong.getTrangThai())
                .build();
    }

    private cauhinhhethong mapToEntity(cauhinhhethongdto cauHinhHeThongDto) {
        return cauhinhhethong.builder()
                .maCauHinh(cauHinhHeThongDto.getMaCauHinh())
                .tenCauHinh(cauHinhHeThongDto.getTenCauHinh())
                .giaTri(cauHinhHeThongDto.getGiaTri())
                .moTa(cauHinhHeThongDto.getMoTa())
                .trangThai(cauHinhHeThongDto.getTrangThai())
                .build();
    }
}