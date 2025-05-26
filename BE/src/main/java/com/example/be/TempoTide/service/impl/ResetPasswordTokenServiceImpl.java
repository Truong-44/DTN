package com.example.be.TempoTide.service.impl;

import com.example.be.TempoTide.dto.ResetPasswordTokenDto;
import com.example.be.TempoTide.entity.NhanVien;
import com.example.be.TempoTide.entity.ResetPasswordToken;
import com.example.be.TempoTide.repository.NhanVienRepository;
import com.example.be.TempoTide.repository.ResetPasswordTokenRepository;
import com.example.be.TempoTide.service.ResetPasswordTokenService;
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
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {

    private static final Logger logger = LoggerFactory.getLogger(ResetPasswordTokenServiceImpl.class);

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final NhanVienRepository nhanVienRepository;

    @Autowired
    public ResetPasswordTokenServiceImpl(ResetPasswordTokenRepository resetPasswordTokenRepository,
                                         NhanVienRepository nhanVienRepository) {
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public ResetPasswordTokenDto createResetPasswordToken(ResetPasswordTokenDto resetPasswordTokenDto) {
        logger.info("Creating new ResetPasswordToken for MaNguoiDung: {}", resetPasswordTokenDto.getMaNguoiDung());
        ResetPasswordToken resetPasswordToken = mapToEntity(resetPasswordTokenDto);

        NhanVien nhanVien = nhanVienRepository.findById(resetPasswordTokenDto.getMaNguoiDung())
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", resetPasswordTokenDto.getMaNguoiDung());
                    return new RuntimeException("NhanVien not found");
                });
        resetPasswordToken.setNhanVien(nhanVien);

        resetPasswordToken.setNgayTao(LocalDateTime.now());
        resetPasswordToken = resetPasswordTokenRepository.save(resetPasswordToken);
        logger.info("ResetPasswordToken created with id: {}", resetPasswordToken.getMaToken());
        return mapToDto(resetPasswordToken);
    }

    @Override
    public ResetPasswordTokenDto getResetPasswordTokenById(Integer id) {
        logger.info("Fetching ResetPasswordToken with id: {}", id);
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ResetPasswordToken not found with id: {}", id);
                    return new RuntimeException("ResetPasswordToken not found with id: " + id);
                });
        return mapToDto(resetPasswordToken);
    }

    @Override
    public List<ResetPasswordTokenDto> getAllResetPasswordTokens() {
        logger.info("Fetching all ResetPasswordTokens");
        return resetPasswordTokenRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResetPasswordTokenDto updateResetPasswordToken(Integer id, ResetPasswordTokenDto resetPasswordTokenDto) {
        logger.info("Updating ResetPasswordToken with id: {}", id);
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ResetPasswordToken not found with id: {}", id);
                    return new RuntimeException("ResetPasswordToken not found with id: " + id);
                });

        resetPasswordToken.setMaNguoiDung(resetPasswordTokenDto.getMaNguoiDung());
        resetPasswordToken.setToken(resetPasswordTokenDto.getToken());
        resetPasswordToken.setNgayHetHan(resetPasswordTokenDto.getNgayHetHan());
        resetPasswordToken.setDaSuDung(resetPasswordTokenDto.getDaSuDung());

        NhanVien nhanVien = nhanVienRepository.findById(resetPasswordTokenDto.getMaNguoiDung())
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", resetPasswordTokenDto.getMaNguoiDung());
                    return new RuntimeException("NhanVien not found");
                });
        resetPasswordToken.setNhanVien(nhanVien);

        resetPasswordToken = resetPasswordTokenRepository.save(resetPasswordToken);
        logger.info("ResetPasswordToken updated with id: {}", resetPasswordToken.getMaToken());
        return mapToDto(resetPasswordToken);
    }

    @Override
    public void deleteResetPasswordToken(Integer id) {
        logger.info("Deleting ResetPasswordToken with id: {}", id);
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ResetPasswordToken not found with id: {}", id);
                    return new RuntimeException("ResetPasswordToken not found with id: " + id);
                });
        resetPasswordTokenRepository.delete(resetPasswordToken);
        logger.info("ResetPasswordToken deleted with id: {}", id);
    }

    private ResetPasswordTokenDto mapToDto(ResetPasswordToken resetPasswordToken) {
        return ResetPasswordTokenDto.builder()
                .maToken(resetPasswordToken.getMaToken())
                .maNguoiDung(resetPasswordToken.getMaNguoiDung())
                .token(resetPasswordToken.getToken())
                .ngayTao(resetPasswordToken.getNgayTao())
                .ngayHetHan(resetPasswordToken.getNgayHetHan())
                .daSuDung(resetPasswordToken.getDaSuDung())
                .build();
    }

    private ResetPasswordToken mapToEntity(ResetPasswordTokenDto resetPasswordTokenDto) {
        return ResetPasswordToken.builder()
                .maToken(resetPasswordTokenDto.getMaToken())
                .maNguoiDung(resetPasswordTokenDto.getMaNguoiDung())
                .token(resetPasswordTokenDto.getToken())
                .ngayTao(resetPasswordTokenDto.getNgayTao() != null ? resetPasswordTokenDto.getNgayTao() : LocalDateTime.now())
                .ngayHetHan(resetPasswordTokenDto.getNgayHetHan())
                .daSuDung(resetPasswordTokenDto.getDaSuDung())
                .build();
    }
}