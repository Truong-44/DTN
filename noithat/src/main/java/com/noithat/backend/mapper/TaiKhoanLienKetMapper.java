package com.noithat.backend.mapper;

import com.noithat.backend.dto.TaiKhoanLienKetDTO;
import com.noithat.backend.entity.TaiKhoanLienKet;
import com.noithat.backend.entity.TaiKhoan;

public class TaiKhoanLienKetMapper {

    public static TaiKhoanLienKetDTO toDTO(TaiKhoanLienKet entity) {
        return TaiKhoanLienKetDTO.builder()
                .id(entity.getId())
                .loaidangnhap(entity.getLoaidangnhap())
                .giatridangnhap(entity.getGiatridangnhap())
                .taikhoanid(entity.getTaikhoan().getId())
                .build();
    }

    public static TaiKhoanLienKet toEntity(TaiKhoanLienKetDTO dto, TaiKhoan taikhoan) {
        return TaiKhoanLienKet.builder()
                .id(dto.getId())
                .loaidangnhap(dto.getLoaidangnhap())
                .giatridangnhap(dto.getGiatridangnhap())
                .taikhoan(taikhoan)
                .build();
    }
}
