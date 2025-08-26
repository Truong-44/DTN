package com.noithat.backend.mapper;

import com.noithat.backend.dto.ChiTietGioHangDTO;
import com.noithat.backend.entity.ChiTietGioHang;
import com.noithat.backend.entity.ChiTietSanPham;
import com.noithat.backend.entity.GioHang;
import org.springframework.stereotype.Component;

@Component
public class ChiTietGioHangMapper {

    public ChiTietGioHangDTO toDTO(ChiTietGioHang entity) {
        if (entity == null) return null;

        return ChiTietGioHangDTO.builder()
                .id(entity.getId())
                .giohangId(entity.getGioHang().getId())
                .chitietsanphamId(entity.getChiTietSanPham().getId())
                .tensanpham(entity.getChiTietSanPham().getSanpham().getTensanpham())
                .soluong(entity.getSoluong())
                .dongia(entity.getDongia())
                .ngaythem(entity.getNgaythem())
                .build();
    }

    public ChiTietGioHang toEntity(ChiTietGioHangDTO dto, GioHang gioHang, ChiTietSanPham ctsp) {
        if (dto == null) return null;

        return ChiTietGioHang.builder()
                .id(dto.getId())
                .gioHang(gioHang)
                .chiTietSanPham(ctsp)
                .soluong(dto.getSoluong())
                .dongia(dto.getDongia())
                .build();
    }
}
