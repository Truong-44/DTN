package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.DiaChiDto;
import com.example.be.TempoTide.entity.DiaChi;
import com.example.be.TempoTide.entity.KhachHang;
import com.example.be.TempoTide.repository.DiaChiRepository;
import com.example.be.TempoTide.repository.KhachHangRepository;
import com.example.be.TempoTide.service.DiaChiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiaChiServiceImpl implements DiaChiService {

    private static final Logger logger = LoggerFactory.getLogger(DiaChiServiceImpl.class);

    private final DiaChiRepository diaChiRepository;
    private final KhachHangRepository khachHangRepository;

    @Autowired
    public DiaChiServiceImpl(DiaChiRepository diaChiRepository,
                             KhachHangRepository khachHangRepository) {
        this.diaChiRepository = diaChiRepository;
        this.khachHangRepository = khachHangRepository;
    }

    @Override
    public DiaChiDto createDiaChi(DiaChiDto diaChiDto) {
        logger.info("Creating new DiaChi for MaKhachHang: {}", diaChiDto.getMaKhachHang());
        DiaChi diaChi = mapToEntity(diaChiDto);

        KhachHang khachHang = khachHangRepository.findById(diaChiDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("KhachHang not found with id: {}", diaChiDto.getMaKhachHang());
                    return new RuntimeException("KhachHang not found");
                });
        diaChi.setKhachHang(khachHang);

        diaChi = diaChiRepository.save(diaChi);
        logger.info("DiaChi created with id: {}", diaChi.getMaDiaChi());
        return mapToDto(diaChi);
    }

    @Override
    public DiaChiDto getDiaChiById(Integer id) {
        logger.info("Fetching DiaChi with id: {}", id);
        DiaChi diaChi = diaChiRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DiaChi not found with id: {}", id);
                    return new RuntimeException("DiaChi not found with id: " + id);
                });
        return mapToDto(diaChi);
    }

    @Override
    public List<DiaChiDto> getAllDiaChi() {
        logger.info("Fetching all DiaChi");
        return diaChiRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DiaChiDto updateDiaChi(Integer id, DiaChiDto diaChiDto) {
        logger.info("Updating DiaChi with id: {}", id);
        DiaChi diaChi = diaChiRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DiaChi not found with id: {}", id);
                    return new RuntimeException("DiaChi not found with id: " + id);
                });

        diaChi.setMaKhachHang(diaChiDto.getMaKhachHang());
        diaChi.setDiaChiCuThe(diaChiDto.getDiaChiCuThe());
        diaChi.setPhuongXa(diaChiDto.getPhuongXa());
        diaChi.setQuanHuyen(diaChiDto.getQuanHuyen());
        diaChi.setTinhThanh(diaChiDto.getTinhThanh());
        diaChi.setLaDiaChiMacDinh(diaChiDto.getLaDiaChiMacDinh());
        diaChi.setTrangThai(diaChiDto.getTrangThai());

        KhachHang khachHang = khachHangRepository.findById(diaChiDto.getMaKhachHang())
                .orElseThrow(() -> {
                    logger.error("KhachHang not found with id: {}", diaChiDto.getMaKhachHang());
                    return new RuntimeException("KhachHang not found");
                });
        diaChi.setKhachHang(khachHang);

        diaChi = diaChiRepository.save(diaChi);
        logger.info("DiaChi updated with id: {}", diaChi.getMaDiaChi());
        return mapToDto(diaChi);
    }

    @Override
    public void deleteDiaChi(Integer id) {
        logger.info("Deleting DiaChi with id: {}", id);
        DiaChi diaChi = diaChiRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("DiaChi not found with id: {}", id);
                    return new RuntimeException("DiaChi not found with id: " + id);
                });
        diaChiRepository.delete(diaChi);
        logger.info("DiaChi deleted with id: {}", id);
    }

    private DiaChiDto mapToDto(DiaChi diaChi) {
        return DiaChiDto.builder()
                .maDiaChi(diaChi.getMaDiaChi())
                .maKhachHang(diaChi.getMaKhachHang())
                .diaChiCuThe(diaChi.getDiaChiCuThe())
                .phuongXa(diaChi.getPhuongXa())
                .quanHuyen(diaChi.getQuanHuyen())
                .tinhThanh(diaChi.getTinhThanh())
                .laDiaChiMacDinh(diaChi.getLaDiaChiMacDinh())
                .trangThai(diaChi.getTrangThai())
                .build();
    }

    private DiaChi mapToEntity(DiaChiDto diaChiDto) {
        return DiaChi.builder()
                .maDiaChi(diaChiDto.getMaDiaChi())
                .maKhachHang(diaChiDto.getMaKhachHang())
                .diaChiCuThe(diaChiDto.getDiaChiCuThe())
                .phuongXa(diaChiDto.getPhuongXa())
                .quanHuyen(diaChiDto.getQuanHuyen())
                .tinhThanh(diaChiDto.getTinhThanh())
                .laDiaChiMacDinh(diaChiDto.getLaDiaChiMacDinh())
                .trangThai(diaChiDto.getTrangThai())
                .build();
    }
}