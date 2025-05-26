package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.nguoidung_vaitrodto;
import com.example.be.tempotide.entity.nguoidung_vaitro;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.entity.vaitro;
import com.example.be.tempotide.repository.nguoidung_vaitrorepository;
import com.example.be.tempotide.repository.nhanvienrepository;
import com.example.be.tempotide.repository.vaitrorepository;
import com.example.be.tempotide.service.NguoiDung_VaiTroService;
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

    private final nguoidung_vaitrorepository nguoiDung_VaiTroRepository;
    private final nhanvienrepository nhanVienRepository;
    private final vaitrorepository vaiTroRepository;

    @Autowired
    public NguoiDung_VaiTroServiceImpl(nguoidung_vaitrorepository nguoiDung_VaiTroRepository,
                                       nhanvienrepository nhanVienRepository,
                                       vaitrorepository vaiTroRepository) {
        this.nguoiDung_VaiTroRepository = nguoiDung_VaiTroRepository;
        this.nhanVienRepository = nhanVienRepository;
        this.vaiTroRepository = vaiTroRepository;
    }

    @Override
    public nguoidung_vaitrodto createNguoiDung_VaiTro(nguoidung_vaitrodto nguoiDung_VaiTroDto) {
        logger.info("Creating new NguoiDung_VaiTro for MaNguoiDung: {} and MaVaiTro: {}", nguoiDung_VaiTroDto.getMaNguoiDung(), nguoiDung_VaiTroDto.getMaVaiTro());
        nguoidung_vaitro nguoiDung_VaiTro = mapToEntity(nguoiDung_VaiTroDto);

        nhanvien nguoiDung = nhanVienRepository.findById(nguoiDung_VaiTroDto.getMaNguoiDung())
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", nguoiDung_VaiTroDto.getMaNguoiDung());
                    return new RuntimeException("NhanVien not found");
                });
        nguoiDung_VaiTro.setNguoiDung(nguoiDung);

        vaitro vaiTro = vaiTroRepository.findById(nguoiDung_VaiTroDto.getMaVaiTro())
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
    public nguoidung_vaitrodto getNguoiDung_VaiTroById(Integer maNguoiDung, Integer maVaiTro) {
        logger.info("Fetching NguoiDung_VaiTro with MaNguoiDung: {} and MaVaiTro: {}", maNguoiDung, maVaiTro);
        nguoidung_vaitro nguoiDung_VaiTro = nguoiDung_VaiTroRepository.findById(new nguoidung_vaitro.NguoiDung_VaiTroId(maNguoiDung, maVaiTro))
                .orElseThrow(() -> {
                    logger.error("NguoiDung_VaiTro not found with MaNguoiDung: {} and MaVaiTro: {}", maNguoiDung, maVaiTro);
                    return new RuntimeException("NguoiDung_VaiTro not found with MaNguoiDung: " + maNguoiDung + " and MaVaiTro: " + maVaiTro);
                });
        return mapToDto(nguoiDung_VaiTro);
    }

    @Override
    public List<nguoidung_vaitrodto> getAllNguoiDung_VaiTro() {
        logger.info("Fetching all NguoiDung_VaiTro");
        return nguoiDung_VaiTroRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public nguoidung_vaitrodto updateNguoiDung_VaiTro(Integer maNguoiDung, Integer maVaiTro, nguoidung_vaitrodto nguoiDung_VaiTroDto) {
        logger.info("Updating NguoiDung_VaiTro with MaNguoiDung: {} and MaVaiTro: {}", maNguoiDung, maVaiTro);
        nguoidung_vaitro nguoiDung_VaiTro = nguoiDung_VaiTroRepository.findById(new nguoidung_vaitro.NguoiDung_VaiTroId(maNguoiDung, maVaiTro))
                .orElseThrow(() -> {
                    logger.error("NguoiDung_VaiTro not found with MaNguoiDung: {} and MaVaiTro: {}", maNguoiDung, maVaiTro);
                    return new RuntimeException("NguoiDung_VaiTro not found with MaNguoiDung: " + maNguoiDung + " and MaVaiTro: " + maVaiTro);
                });

        nguoiDung_VaiTro.setTrangThai(nguoiDung_VaiTroDto.getTrangThai());

        nhanvien nguoiDung = nhanVienRepository.findById(nguoiDung_VaiTroDto.getMaNguoiDung())
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", nguoiDung_VaiTroDto.getMaNguoiDung());
                    return new RuntimeException("NhanVien not found");
                });
        nguoiDung_VaiTro.setNguoiDung(nguoiDung);

        vaitro vaiTro = vaiTroRepository.findById(nguoiDung_VaiTroDto.getMaVaiTro())
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
        nguoidung_vaitro nguoiDung_VaiTro = nguoiDung_VaiTroRepository.findById(new nguoidung_vaitro.NguoiDung_VaiTroId(maNguoiDung, maVaiTro))
                .orElseThrow(() -> {
                    logger.error("NguoiDung_VaiTro not found with MaNguoiDung: {} and MaVaiTro: {}", maNguoiDung, maVaiTro);
                    return new RuntimeException("NguoiDung_VaiTro not found with MaNguoiDung: " + maNguoiDung + " and MaVaiTro: " + maVaiTro);
                });
        nguoiDung_VaiTroRepository.delete(nguoiDung_VaiTro);
        logger.info("NguoiDung_VaiTro deleted with MaNguoiDung: {} and MaVaiTro: {}", maNguoiDung, maVaiTro);
    }

    private nguoidung_vaitrodto mapToDto(nguoidung_vaitro nguoiDung_VaiTro) {
        return nguoidung_vaitrodto.builder()
                .maNguoiDung(nguoiDung_VaiTro.getId().getMaNguoiDung())
                .maVaiTro(nguoiDung_VaiTro.getId().getMaVaiTro())
                .trangThai(nguoiDung_VaiTro.getTrangThai())
                .build();
    }

    private nguoidung_vaitro mapToEntity(nguoidung_vaitrodto nguoiDung_VaiTroDto) {
        return nguoidung_vaitro.builder()
                .id(new nguoidung_vaitro.NguoiDung_VaiTroId(nguoiDung_VaiTroDto.getMaNguoiDung(), nguoiDung_VaiTroDto.getMaVaiTro()))
                .trangThai(nguoiDung_VaiTroDto.getTrangThai())
                .build();
    }
}