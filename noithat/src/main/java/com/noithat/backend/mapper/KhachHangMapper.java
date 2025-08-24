package com.noithat.backend.mapper;

import com.noithat.backend.dto.KhachHangDTO;
import com.noithat.backend.entity.KhachHang;
import com.noithat.backend.entity.TaiKhoan;
import org.springframework.stereotype.Component;

@Component
public class KhachHangMapper {

    public KhachHangDTO toDTO(KhachHang entity) {
        if (entity == null) {
            return null;
        }
        return KhachHangDTO.builder()
                .id(entity.getId())
                .hoten(entity.getHoten())
                .diachi(entity.getDiachi())
                .ngaysinh(entity.getNgaysinh())
                .gioitinh(entity.getGioitinh())
                .taikhoanId(entity.getTaikhoan() != null ? entity.getTaikhoan().getId() : null)
                .tendangnhap(entity.getTaikhoan() != null ? entity.getTaikhoan().getTendangnhap() : null)
                .build();
    }

    public KhachHang toEntity(KhachHangDTO dto) {
        if (dto == null) {
            return null;
        }
        KhachHang entity = new KhachHang();
        entity.setId(dto.getId());
        entity.setHoten(dto.getHoten());
        entity.setDiachi(dto.getDiachi());
        entity.setNgaysinh(dto.getNgaysinh());
        entity.setGioitinh(dto.getGioitinh());
        // Note: 'taikhoan' is not set here, should be handled in service layer
        return entity;
    }

    public KhachHang toEntity(KhachHangDTO dto, TaiKhoan taiKhoan) {
        if (dto == null) {
            return null;
        }
        KhachHang entity = new KhachHang();
        entity.setId(dto.getId());
        entity.setHoten(dto.getHoten());
        entity.setDiachi(dto.getDiachi());
        entity.setNgaysinh(dto.getNgaysinh());
        entity.setGioitinh(dto.getGioitinh());
        entity.setTaikhoan(taiKhoan);
        return entity;
    }
}
