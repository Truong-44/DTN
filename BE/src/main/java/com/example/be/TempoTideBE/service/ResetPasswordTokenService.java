package com.example.be.TempoTideBE.service;

import com.example.be.dto.ResetPasswordTokenDTO;
import com.example.be.entity.KhachHang;
import com.example.be.entity.NhanVien;
import com.example.be.entity.ResetPasswordToken;
import com.example.be.repository.KhachHangRepository;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.ResetPasswordTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResetPasswordTokenService {

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final NhanVienRepository nhanVienRepository;
    private final KhachHangRepository khachHangRepository;

    public List<ResetPasswordTokenDTO> getAllResetPasswordToken() {
        return resetPasswordTokenRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ResetPasswordTokenDTO getResetPasswordTokenById(Integer id) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Token khôi phục mật khẩu không tồn tại"));
        return convertToDTO(resetPasswordToken);
    }

    public ResetPasswordTokenDTO createResetPasswordToken(ResetPasswordTokenDTO dto) {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        mapToEntity(dto, resetPasswordToken);
        ResetPasswordToken savedResetPasswordToken = resetPasswordTokenRepository.save(resetPasswordToken);
        return convertToDTO(savedResetPasswordToken);
    }

    public ResetPasswordTokenDTO updateResetPasswordToken(Integer id, ResetPasswordTokenDTO dto) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Token khôi phục mật khẩu không tồn tại"));
        mapToEntity(dto, resetPasswordToken);
        ResetPasswordToken updatedResetPasswordToken = resetPasswordTokenRepository.save(resetPasswordToken);
        return convertToDTO(updatedResetPasswordToken);
    }

    public void deleteResetPasswordToken(Integer id) {
        resetPasswordTokenRepository.deleteById(id);
    }

    private ResetPasswordTokenDTO convertToDTO(ResetPasswordToken resetPasswordToken) {
        ResetPasswordTokenDTO dto = new ResetPasswordTokenDTO();
        dto.setMaToken(resetPasswordToken.getMaToken());
        if (resetPasswordToken.getNhanVien() != null) {
            dto.setMaNhanVien(resetPasswordToken.getNhanVien().getMaNhanVien());
        }
        if (resetPasswordToken.getKhachHang() != null) {
            dto.setMaKhachHang(resetPasswordToken.getKhachHang().getMaKhachHang());
        }
        dto.setToken(resetPasswordToken.getToken());
        dto.setNgayTao(resetPasswordToken.getNgayTao());
        dto.setNgayHetHan(resetPasswordToken.getNgayHetHan());
        dto.setTrangThai(resetPasswordToken.getTrangThai());
        if (resetPasswordToken.getNguoiTao() != null) {
            dto.setNguoiTaoId(resetPasswordToken.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(ResetPasswordTokenDTO dto, ResetPasswordToken resetPasswordToken) {
        if (dto.getMaNhanVien() != null) {
            NhanVien nhanVien = nhanVienRepository.findById(dto.getMaNhanVien())
                    .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));
            resetPasswordToken.setNhanVien(nhanVien);
        }
        if (dto.getMaKhachHang() != null) {
            KhachHang khachHang = khachHangRepository.findById(dto.getMaKhachHang())
                    .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
            resetPasswordToken.setKhachHang(khachHang);
        }
        resetPasswordToken.setToken(dto.getToken());
        resetPasswordToken.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        resetPasswordToken.setNgayHetHan(dto.getNgayHetHan());
        resetPasswordToken.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            resetPasswordToken.setNguoiTao(nguoiTao);
        }
    }
}