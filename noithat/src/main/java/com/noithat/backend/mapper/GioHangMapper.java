package com.noithat.backend.mapper;

import com.noithat.backend.dto.GioHangDTO;
import com.noithat.backend.entity.GioHang;
import com.noithat.backend.entity.KhachHang;
import org.springframework.stereotype.Component;

@Component
public class GioHangMapper {

    public GioHangDTO toDTO(GioHang entity) {
        if (entity == null) return null;

        return GioHangDTO.builder()
                .id(entity.getId())
                .khachHangId(entity.getKhachHang().getId())
                .khachHangHoTen(entity.getKhachHang().getHoten())
                .ngayCapNhat(entity.getNgaycapnhat())
                .build();
    }

    public GioHang toEntity(GioHangDTO dto, KhachHang khachHang) {
        if (dto == null) return null;

        return GioHang.builder()
                .id(dto.getId())
                .khachHang(khachHang)
                .build(); // ngày cập nhật để @UpdateTimestamp tự xử lý
    }
}
