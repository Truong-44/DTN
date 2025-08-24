package com.noithat.backend.mapper;

import com.noithat.backend.dto.DonHangDTO;
import com.noithat.backend.entity.DonHang;
import com.noithat.backend.entity.KhachHang;
import org.springframework.stereotype.Component;

@Component
public class DonHangMapper {

    public DonHangDTO toDTO(DonHang entity) {
        if (entity == null) return null;

        return DonHangDTO.builder()
                .id(entity.getId())
                .khachHangId(entity.getKhachHang().getId())
                .khachHangHoTen(entity.getKhachHang().getHoten())
                .ngaydat(entity.getNgaydat())
                .diachinhan(entity.getDiachinhan())
                .trangthaidonhang(entity.getTrangthaidonhang())
                .tongtien(entity.getTongtien())
                .phuongthucthanhtoan(entity.getPhuongthucthanhtoan())
                .trangthaithanhtoan(entity.getTrangthaithanhtoan())
                .ngaythanhtoan(entity.getNgaythanhtoan())
                .ghichu(entity.getGhichu())
                .build();
    }

    public DonHang toEntity(DonHangDTO dto, KhachHang khachHang) {
        if (dto == null) return null;

        return DonHang.builder()
                .id(dto.getId())
                .khachHang(khachHang)
                .diachinhan(dto.getDiachinhan())
                .trangthaidonhang(dto.getTrangthaidonhang())
                .tongtien(dto.getTongtien())
                .phuongthucthanhtoan(dto.getPhuongthucthanhtoan())
                .trangthaithanhtoan(dto.getTrangthaithanhtoan())
                .ngaythanhtoan(dto.getNgaythanhtoan())
                .ghichu(dto.getGhichu())
                .build();
    }
}
