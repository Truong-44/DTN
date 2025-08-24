package com.noithat.backend.mapper;

import com.noithat.backend.dto.ChiTietHoaDonDTO;
import com.noithat.backend.entity.ChiTietHoaDon;
import com.noithat.backend.entity.HoaDon;
import org.springframework.stereotype.Component;

@Component
public class ChiTietHoaDonMapper {

    public ChiTietHoaDonDTO toDTO(ChiTietHoaDon entity) {
        return ChiTietHoaDonDTO.builder()
                .id(entity.getId())
                .hoadonId(entity.getHoaDon().getId())
                .tensanpham(entity.getTensanpham())
                .tenmau(entity.getTenmau())
                .chatlieu(entity.getChatlieu())
                .kichthuoc(entity.getKichthuoc())
                .soluong(entity.getSoluong())
                .dongia(entity.getDongia())
                .build();
    }

    public ChiTietHoaDon toEntity(ChiTietHoaDonDTO dto, HoaDon hoaDon) {
        return ChiTietHoaDon.builder()
                .id(dto.getId())
                .hoaDon(hoaDon)
                .tensanpham(dto.getTensanpham())
                .tenmau(dto.getTenmau())
                .chatlieu(dto.getChatlieu())
                .kichthuoc(dto.getKichthuoc())
                .soluong(dto.getSoluong())
                .dongia(dto.getDongia())
                .build();
    }
}
