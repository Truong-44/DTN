package com.noithat.backend.mapper;

import com.noithat.backend.dto.KhoHangDTO;
import com.noithat.backend.entity.ChiTietSanPham;
import com.noithat.backend.entity.KhoHang;
import org.springframework.stereotype.Component;

@Component
public class KhoHangMapper {

    public KhoHangDTO toDTO(KhoHang entity) {
        return KhoHangDTO.builder()
                .id(entity.getId())
                .chitietsanphamId(entity.getChiTietSanPham().getId())
                .tensanpham(entity.getChiTietSanPham().getSanpham().getTensanpham())
                .soluongton(entity.getSoluongton())
                .ngaycapnhat(entity.getNgaycapnhat())
                .build();
    }

    public KhoHang toEntity(KhoHangDTO dto, ChiTietSanPham ctsp) {
        return KhoHang.builder()
                .id(dto.getId())
                .chiTietSanPham(ctsp)
                .soluongton(dto.getSoluongton())
                .build();
    }
}
