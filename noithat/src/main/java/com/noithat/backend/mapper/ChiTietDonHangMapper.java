package com.noithat.backend.mapper;

import com.noithat.backend.dto.ChiTietDonHangDTO;
import com.noithat.backend.entity.ChiTietDonHang;
import com.noithat.backend.entity.ChiTietSanPham;
import com.noithat.backend.entity.DonHang;
import org.springframework.stereotype.Component;

@Component
public class ChiTietDonHangMapper {

    public ChiTietDonHangDTO toDTO(ChiTietDonHang entity) {
        if (entity == null) return null;

        return ChiTietDonHangDTO.builder()
                .id(entity.getId())
                .donhangId(entity.getDonHang().getId())
                .chitietsanphamId(entity.getChiTietSanPham().getId())
                .tensanpham(entity.getChiTietSanPham().getSanpham().getTensanpham())
                .soluong(entity.getSoluong())
                .dongia(entity.getDongia())
                .build();
    }

    public ChiTietDonHang toEntity(ChiTietDonHangDTO dto, DonHang donHang, ChiTietSanPham ctsp) {
        return ChiTietDonHang.builder()
                .id(dto.getId())
                .donHang(donHang)
                .chiTietSanPham(ctsp)
                .soluong(dto.getSoluong())
                .dongia(dto.getDongia())
                .build();
    }
}
