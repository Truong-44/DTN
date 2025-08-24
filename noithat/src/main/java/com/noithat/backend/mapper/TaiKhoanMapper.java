package com.noithat.backend.mapper;

import com.noithat.backend.dto.KhachHangDTO;
import com.noithat.backend.dto.NhanVienDTO;
import com.noithat.backend.dto.TaiKhoanDTO;
import com.noithat.backend.entity.TaiKhoan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaiKhoanMapper {

    private final KhachHangMapper khachHangMapper;
    private final NhanVienMapper nhanVienMapper;

    public TaiKhoanDTO toDTO(TaiKhoan entity) {
        if (entity == null) {
            return null;
        }

        KhachHangDTO khachHangDTO = null;
        if (entity.getKhachhang() != null) {
            khachHangDTO = khachHangMapper.toDTO(entity.getKhachhang());
        }

        NhanVienDTO nhanVienDTO = null;
        if (entity.getNhanvien() != null) {
            nhanVienDTO = nhanVienMapper.toDTO(entity.getNhanvien());
        }

        return TaiKhoanDTO.builder()
                .id(entity.getId())
                .tendangnhap(entity.getTendangnhap())
                .matkhau(null) // Never send password to frontend
                .email(entity.getEmail())
                .sodienthoai(entity.getSodienthoai())
                .trangthai(entity.getTrangthai())
                .loaidangnhap(entity.getLoaidangnhap())
                .quyenId(entity.getQuyen() != null ? entity.getQuyen().getId() : null)
                .quyenTen(entity.getQuyen() != null ? entity.getQuyen().getTen() : null)
                .khachhang(khachHangDTO)
                .nhanvien(nhanVienDTO)
                .build();
    }

    public TaiKhoan toEntity(TaiKhoanDTO dto) {
        if (dto == null) {
            return null;
        }
        return TaiKhoan.builder()
                .id(dto.getId())
                .tendangnhap(dto.getTendangnhap())
                .matkhau(dto.getMatkhau())
                .email(dto.getEmail())
                .sodienthoai(dto.getSodienthoai())
                .trangthai(dto.getTrangthai())
                .loaidangnhap(dto.getLoaidangnhap())
                .build();
    }
}

