package com.noithat.backend.mapper;

import com.noithat.backend.dto.ChiTietSanPhamDTO;
import com.noithat.backend.entity.ChiTietSanPham;
import com.noithat.backend.entity.SanPham;
import org.springframework.stereotype.Component;

@Component
public class ChiTietSanPhamMapper {

    public ChiTietSanPhamDTO toDTO(ChiTietSanPham entity) {
        if (entity == null) return null;

        Long sanphamId = null;
        String tensanpham = null;
        
        // Safely handle SanPham relationship
        if (entity.getSanpham() != null) {
            sanphamId = entity.getSanpham().getId();
            tensanpham = entity.getSanpham().getTensanpham();
        }

        return ChiTietSanPhamDTO.builder()
                .id(entity.getId())
                .sanphamId(sanphamId)
                .tensanpham(tensanpham)
                .tenmau(entity.getTenmau())
                .mamau(entity.getMamau())
                .chatlieu(entity.getChatlieu())
                .kichthuoc(entity.getKichthuoc())
                .trongluong(entity.getTrongluong())
                .soluong(entity.getSoluong())
                .hinhchinh(entity.getHinhchinh())
                .hinhphu(entity.getHinhphu())
                .build();
    }

    public ChiTietSanPham toEntity(ChiTietSanPhamDTO dto, SanPham sp) {
        if (dto == null) return null;

        return ChiTietSanPham.builder()
                .id(dto.getId())
                .sanpham(sp)
                .tenmau(dto.getTenmau())
                .mamau(dto.getMamau())
                .chatlieu(dto.getChatlieu())
                .kichthuoc(dto.getKichthuoc())
                .trongluong(dto.getTrongluong())
                .soluong(dto.getSoluong())
                .hinhchinh(dto.getHinhchinh())
                .hinhphu(dto.getHinhphu())
                .build();
    }
}
