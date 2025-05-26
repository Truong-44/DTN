package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.NguoiDung_VaiTroDto;
import com.example.be.TempoTide.entity.NguoiDung_VaiTro;
import com.example.be.TempoTide.entity.NhanVien;
import com.example.be.TempoTide.entity.VaiTro;
import com.example.be.TempoTide.repository.NguoiDung_VaiTroRepository;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.repository.VaiTroRepository;
import com.example.be.TempoTide.service.NguoiDung_VaiTroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NguoiDung_VaiTroServiceImpl implements NguoiDung_VaiTroService {

    private static final Logger logger = LoggerFactory.getLogger(NguoiDung_VaiTroServiceImpl.class);

    private final NguoiDung_VaiTroRepository nguoiDung_VaiTroRepository;
    private final NhanVienRepository nhanVienRepository;
    private final VaiTroRepository vaiTroRepository;

    @Autowired
    public NguoiDung_VaiTroServiceImpl(NguoiDung_VaiTroRepository nguoiDung_VaiTroRepository,
                                       NhanVienRepository nhanVienRepository,
                                       VaiTroRepository vaiTroRepository) {
        this.nguoiDung_VaiTroRepository = nguoiDung_VaiTroRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.vaiTroRepository = vaiTroRepository;
    }

    @Override
    public NguoiDung_VaiTroDto createNguoiDung_VaiTro(NguoiDung_VaiTroDto nguoiDung_VaiTroDto) {
        logger.info("Creating new NguoiDung_VaiTro for MaNguoiDung: {} and MaVaiTro: {}", nguoiDung_VaiTroDto.getMaNguoiDung(), nguoiDung_VaiTroDto.getMaVaiTro());
        NguoiDung_VaiTro nguoiDung_VaiTro = mapToEntity(nguoiDung_VaiTroDto);

        NhanVien nguoiDung = nhanVienRepository.findById(nguoiDung_VaiTroDto.getMaNguoiDung())
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", nguoiDung_VaiTroDto.getMaNguoiDung());
                    return new RuntimeException("NhanVien not found");
                });
        nguoiDung_VaiTro.setNguoiDung(nguoiDung);

        VaiTro vaiTro = vaiTroRepository.findById(nguoiDung_VaiTroDto.getMaVaiTro())
                .orElseThrow(() -> {
                    logger.error("VaiTro not found with id: {}", nguoiDung_VaiTroDto.getMaVaiTro());
                    return new RuntimeException("VaiTro not found");
                });
        nguoiDung_VaiTro.setVaiTro(vaiTro);

        nguoiDung_VaiTro = nguoiDung_VaiTroRepository.save(nguoiDung_VaiTro);
        logger.info("NguoiDung_VaiTro created with MaNguoiDung: {} and MaVaiTro: {}", nguoiDung_VaiTro.getId().getMaNguoiDung(), nguoiDung_VaiTro.getId().getMaVaiTro());
        return mapToDto(nguoiDung_VaiTro);
    }

    @Override
    public NguoiDung_VaiTroDto getNguoiDung_VaiTroById(Integer maNguoiDung, Integer maVaiTro) {
        logger.info("Fetching NguoiDung_VaiTro with MaNguoiDung: {} and MaVaiTro: {}", maNguoiDung, maVaiTro);
        NguoiDung_VaiTro nguoiDung_VaiTro = nguoiDung_VaiTroRepository.findById(new NguoiDung_VaiTro.NguoiDung_VaiTroId(maNguoiDung, maVaiTro))
                .orElseThrow(() -> {
                    logger.error("NguoiDung_VaiTro not found with MaNguoiDung: {} and MaVaiTro: {}", maNguoiDung, maVaiTro);
                    return new RuntimeException("NguoiDung_VaiTro not found with MaNguoiDung: " + maNguoiDung + " and MaVaiTro: " + maVaiTro);
                });
        return mapToDto(nguoiDung_VaiTro);
    }

    @Override
    public List<NguoiDung_VaiTroDto> getAllNguoiDung_VaiTro() {
        logger.info("Fetching all NguoiDung_VaiTro");
        return nguoiDung_VaiTroRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NguoiDung_VaiTroDto updateNguoiDung_VaiTro(Integer maNguoiDung, Integer maVaiTro, NguoiDung_VaiTroDto nguoiDung_VaiTroDto) {
        logger.info("Updating NguoiDung_VaiTro with MaNguoiDung: {} and MaVaiTro: {}", maNguoiDung, maVaiTro);
        NguoiDung_VaiTro nguoiDung_VaiTro = nguoiDung_VaiTroRepository.findById(new NguoiDung_VaiTro.NguoiDung_VaiTroId(maNguoiDung, maVaiTro))
                .orElseThrow(() -> {
                    logger.error("NguoiDung_VaiTro not found with MaNguoiDung: {} and MaVaiTro: {}", maNguoiDung, maVaiTro);
                    return new RuntimeException("NguoiDung_VaiTro not found with MaNguoiDung: " + maNguoiDung + " and MaVaiTro: " + maVaiTro);
                });

        nguoiDung_VaiTro.setTrangThai(nguoiDung_VaiTroDto.getTrangThai());

        NhanVien nguoiDung = nhanVienRepository.findById(nguoiDung_VaiTroDto.getMaNguoiDung())
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", nguoiDung_VaiTroDto.getMaNguoiDung());
                    return new RuntimeException("NhanVien not found");
                });
        nguoiDung_VaiTro.setNguoiDung(nguoiDung);

        VaiTro vaiTro = vaiTroRepository.findById(nguoiDung_VaiTroDto.getMaVaiTro())
                .orElseThrow(() -> {
                    logger.error("VaiTro not found with id: {}", nguoiDung_VaiTroDto.getMaVaiTro());
                    return new RuntimeException("VaiTro not found");
                });
        nguoiDung_VaiTro.setVaiTro(vaiTro);

        nguoiDung_VaiTro = nguoiDung_VaiTroRepository.save(nguoiDung_VaiTro);
        logger.info("NguoiDung_VaiTro updated with MaNguoiDung: {} and MaVaiTro: {}", nguoiDung_VaiTro.getId().getMaNguoiDung(), nguoiDung_VaiTro.getId().getMaVaiTro());
        return mapToDto(nguoiDung_VaiTro);
    }

    @Override
    public void deleteNguoiDung_VaiTro(Integer maNguoiDung, Integer maVaiTro) {
        logger.info("Deleting NguoiDung_VaiTro with MaNguoiDung: {} and MaVaiTro: {}", maNguoiDung, maVaiTro);
        NguoiDung_VaiTro nguoiDung_VaiTro = nguoiDung_VaiTroRepository.findById(new NguoiDung_VaiTro.NguoiDung_VaiTroId(maNguoiDung, maVaiTro))
                .orElseThrow(() -> {
                    logger.error("NguoiDung_VaiTro not found with MaNguoiDung: {} and MaVaiTro: {}", maNguoiDung, maVaiTro);
                    return new RuntimeException("NguoiDung_VaiTro not found with MaNguoiDung: " + maNguoiDung + " and MaVaiTro: " + maVaiTro);
                });
        nguoiDung_VaiTroRepository.delete(nguoiDung_VaiTro);
        logger.info("NguoiDung_VaiTro deleted with MaNguoiDung: {} and MaVaiTro: {}", maNguoiDung, maVaiTro);
    }

    private NguoiDung_VaiTroDto mapToDto(NguoiDung_VaiTro nguoiDung_VaiTro) {
        return NguoiDung_VaiTroDto.builder()
                .maNguoiDung(nguoiDung_VaiTro.getId().getMaNguoiDung())
                .maVaiTro(nguoiDung_VaiTro.getId().getMaVaiTro())
                .trangThai(nguoiDung_VaiTro.getTrangThai())
                .build();
    }

    private NguoiDung_VaiTro mapToEntity(NguoiDung_VaiTroDto nguoiDung_VaiTroDto) {
        return NguoiDung_VaiTro.builder()
                .id(new NguoiDung_VaiTro.NguoiDung_VaiTroId(nguoiDung_VaiTroDto.getMaNguoiDung(), nguoiDung_VaiTroDto.getMaVaiTro()))
                .trangThai(nguoiDung_VaiTroDto.getTrangThai())
                .build();
    }
}