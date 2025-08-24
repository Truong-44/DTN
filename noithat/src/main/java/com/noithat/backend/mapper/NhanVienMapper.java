package com.noithat.backend.mapper;

import com.noithat.backend.dto.NhanVienDTO;
import com.noithat.backend.entity.NhanVien;
import com.noithat.backend.entity.TaiKhoan;
import org.springframework.stereotype.Component;

@Component
public class NhanVienMapper {

    public NhanVienDTO toDTO(NhanVien entity) {
        if (entity == null) {
            return null;
        }
        return NhanVienDTO.builder()
                .id(entity.getId())
                .hoten(entity.getHoten())
                .email(entity.getEmail())
                .sodienthoai(entity.getSodienthoai())
                .chucvu(entity.getChucvu())
                .ngayvaolam(entity.getNgayvaolam())
                .taikhoanId(entity.getTaikhoan() != null ? entity.getTaikhoan().getId() : null)
                .tendangnhap(entity.getTaikhoan() != null ? entity.getTaikhoan().getTendangnhap() : null)
                .build();
    }

    public NhanVien toEntity(NhanVienDTO dto) {
        if (dto == null) {
            return null;
        }
        NhanVien entity = new NhanVien();
        entity.setId(dto.getId());
        entity.setHoten(dto.getHoten());
        entity.setEmail(dto.getEmail());
        entity.setSodienthoai(dto.getSodienthoai());
        entity.setChucvu(dto.getChucvu());
        entity.setNgayvaolam(dto.getNgayvaolam());
        // Note: 'taikhoan' is not set here, should be handled in service layer
        return entity;
    }

    public NhanVien toEntity(NhanVienDTO dto, TaiKhoan taiKhoan) {
        if (dto == null) {
            return null;
        }
        NhanVien entity = new NhanVien();
        entity.setId(dto.getId());
        entity.setHoten(dto.getHoten());
        entity.setEmail(dto.getEmail());
        entity.setSodienthoai(dto.getSodienthoai());
        entity.setChucvu(dto.getChucvu());
        entity.setNgayvaolam(dto.getNgayvaolam());
        entity.setTaikhoan(taiKhoan);
        return entity;
    }
}
