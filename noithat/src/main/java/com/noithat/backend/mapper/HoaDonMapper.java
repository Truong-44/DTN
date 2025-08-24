package com.noithat.backend.mapper;

import com.noithat.backend.dto.HoaDonDTO;
import com.noithat.backend.entity.DonHang;
import com.noithat.backend.entity.HoaDon;
import org.springframework.stereotype.Component;

@Component
public class HoaDonMapper {

    public HoaDonDTO toDTO(HoaDon entity) {
        if (entity == null) return null;

        return HoaDonDTO.builder()
                .id(entity.getId())
                .donhangId(entity.getDonHang().getId())
                .nguoixuathoadon(entity.getNguoixuathoadon())
                .tenkhachhang(entity.getTenkhachhang())
                .diachinguoinhan(entity.getDiachinguoinhan())
                .tongtien(entity.getTongtien())
                .ghichu(entity.getGhichu())
                .ngayxuathoadon(entity.getNgayxuathoadon())
                .build();
    }

    public HoaDon toEntity(HoaDonDTO dto, DonHang dh) {
        return HoaDon.builder()
                .id(dto.getId())
                .donHang(dh)
                .nguoixuathoadon(dto.getNguoixuathoadon())
                .tenkhachhang(dto.getTenkhachhang())
                .diachinguoinhan(dto.getDiachinguoinhan())
                .tongtien(dto.getTongtien())
                .ghichu(dto.getGhichu())
                .build();
    }
}
