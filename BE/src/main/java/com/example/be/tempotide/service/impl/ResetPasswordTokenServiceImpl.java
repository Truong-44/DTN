package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.resetpasswordtokendto;
import com.example.be.tempotide.entity.nhanvien;
import com.example.be.tempotide.entity.resetpasswordtoken;
import com.example.be.tempotide.repository.nhanvienrepository;
import com.example.be.tempotide.repository.resetpasswordtokenrepository;
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
public class resetpasswordtokenserviceImpl implements com.example.be.tempotide.service.resetpasswordtokenservice {

    private static final Logger logger = LoggerFactory.getLogger(com.example.be.tempotide.service.impl.resetpasswordtokenserviceImpl.class);

    private final resetpasswordtokenrepository resetPasswordTokenRepository;
    private final nhanvienrepository nhanVienRepository;

    @Autowired
    public resetpasswordtokenserviceImpl(resetpasswordtokenrepository resetPasswordTokenRepository,
                                         nhanvienrepository nhanVienRepository) {
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public resetpasswordtokendto createResetPasswordToken(resetpasswordtokendto resetPasswordTokenDto) {
        logger.info("Creating new ResetPasswordToken for MaNhanVien: {}, MaKhachHang: {}",
                resetPasswordTokenDto.getMaNhanVien(), resetPasswordTokenDto.getMaKhachHang());
        resetpasswordtoken resetPasswordToken = mapToEntity(resetPasswordTokenDto);

        nhanvien nhanVien = nhanVienRepository.findById(resetPasswordTokenDto.getMaNhanVien())
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", resetPasswordTokenDto.getMaNhanVien());
                    return new RuntimeException("NhanVien not found");
                });
        resetPasswordToken.setNhanVien(nhanVien);

        if (resetPasswordTokenDto.getNguoiTaoId() != null) {
            nhanvien nguoiTao = nhanVienRepository.findById(resetPasswordTokenDto.getNguoiTaoId())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", resetPasswordTokenDto.getNguoiTaoId());
                        return new RuntimeException("NguoiTao not found");
                    });
            resetPasswordToken.setNguoiTao(nguoiTao);
        }

        resetPasswordToken.setNgayTao(LocalDateTime.now());
        resetPasswordToken.setTrangThai(true);
        resetPasswordToken = resetPasswordTokenRepository.save(resetPasswordToken);
        logger.info("ResetPasswordToken created with id: {}", resetPasswordToken.getMaToken());
        return mapToDto(resetPasswordToken);
    }

    @Override
    public resetpasswordtokendto getResetPasswordTokenById(Integer id) {
        logger.info("Fetching ResetPasswordToken with id: {}", id);
        resetpasswordtoken resetPasswordToken = resetPasswordTokenRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ResetPasswordToken not found with id: {}", id);
                    return new RuntimeException("ResetPasswordToken not found with id: " + id);
                });
        return mapToDto(resetPasswordToken);
    }

    @Override
    public List<resetpasswordtokendto> getAllResetPasswordTokens() {
        logger.info("Fetching all ResetPasswordTokens");
        return resetPasswordTokenRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public resetpasswordtokendto updateResetPasswordToken(Integer id, resetpasswordtokendto resetPasswordTokenDto) {
        logger.info("Updating ResetPasswordToken with id: {}", id);
        resetpasswordtoken resetPasswordToken = resetPasswordTokenRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ResetPasswordToken not found with id: {}", id);
                    return new RuntimeException("ResetPasswordToken not found with id: " + id);
                });

        resetPasswordToken.setMaNhanVien(resetPasswordTokenDto.getMaNhanVien());
        resetPasswordToken.setMaKhachHang(resetPasswordTokenDto.getMaKhachHang());
        resetPasswordToken.setToken(resetPasswordTokenDto.getToken());
        resetPasswordToken.setNgayHetHan(resetPasswordTokenDto.getNgayHetHan());
        resetPasswordToken.setDaSuDung(resetPasswordTokenDto.getDaSuDung());
        resetPasswordToken.setTrangThai(resetPasswordTokenDto.getTrangThai());

        nhanvien nhanVien = nhanVienRepository.findById(resetPasswordTokenDto.getMaNhanVien())
                .orElseThrow(() -> {
                    logger.error("NhanVien not found with id: {}", resetPasswordTokenDto.getMaNhanVien());
                    return new RuntimeException("NhanVien not found");
                });
        resetPasswordToken.setNhanVien(nhanVien);

        if (resetPasswordTokenDto.getNguoiTaoId() != null) {
            nhanvien nguoiTao = nhanVienRepository.findById(resetPasswordTokenDto.getNguoiTaoId())
                    .orElseThrow(() -> {
                        logger.error("NguoiTao not found with id: {}", resetPasswordTokenDto.getNguoiTaoId());
                        return new RuntimeException("NguoiTao not found");
                    });
            resetPasswordToken.setNguoiTao(nguoiTao);
        }

        resetPasswordToken = resetPasswordTokenRepository.save(resetPasswordToken);
        logger.info("ResetPasswordToken updated with id: {}", resetPasswordToken.getMaToken());
        return mapToDto(resetPasswordToken);
    }

    @Override
    public void deleteResetPasswordToken(Integer id) {
        logger.info("Deleting ResetPasswordToken with id: {}", id);
        resetpasswordtoken resetPasswordToken = resetPasswordTokenRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("ResetPasswordToken not found with id: {}", id);
                    return new RuntimeException("ResetPasswordToken not found with id: " + id);
                });
        resetPasswordTokenRepository.delete(resetPasswordToken);
        logger.info("ResetPasswordToken deleted with id: {}", id);
    }

    private resetpasswordtokendto mapToDto(resetpasswordtoken resetPasswordToken) {
        return resetpasswordtokendto.builder()
                .maToken(resetPasswordToken.getMaToken())
                .maNhanVien(resetPasswordToken.getMaNhanVien())
                .maKhachHang(resetPasswordToken.getMaKhachHang())
                .token(resetPasswordToken.getToken())
                .ngayTao(resetPasswordToken.getNgayTao())
                .ngayHetHan(resetPasswordToken.getNgayHetHan())
                .trangThai(resetPasswordToken.getTrangThai())
                .daSuDung(resetPasswordToken.getDaSuDung())
                .nguoiTaoId(resetPasswordToken.getNguoiTao() != null ? resetPasswordToken.getNguoiTao().getMaNhanVien() : null)
                .build();
    }

    private resetpasswordtoken mapToEntity(resetpasswordtokendto resetPasswordTokenDto) {
        return resetpasswordtoken.builder()
                .maToken(resetPasswordTokenDto.getMaToken())
                .maNhanVien(resetPasswordTokenDto.getMaNhanVien())
                .maKhachHang(resetPasswordTokenDto.getMaKhachHang())
                .token(resetPasswordTokenDto.getToken())
                .ngayTao(resetPasswordTokenDto.getNgayTao() != null ? resetPasswordTokenDto.getNgayTao() : LocalDateTime.now())
                .ngayHetHan(resetPasswordTokenDto.getNgayHetHan())
                .trangThai(resetPasswordTokenDto.getTrangThai() != null ? resetPasswordTokenDto.getTrangThai() : true)
                .daSuDung(resetPasswordTokenDto.getDaSuDung())
                .build();
    }
}