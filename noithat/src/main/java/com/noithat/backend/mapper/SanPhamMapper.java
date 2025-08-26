package com.noithat.backend.mapper;

import com.noithat.backend.dto.SanPhamDTO;
import com.noithat.backend.entity.DanhMuc;
import com.noithat.backend.entity.SanPham;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SanPhamMapper {

    private final ChiTietSanPhamMapper chiTietSanPhamMapper;

    public SanPhamDTO toDTO(SanPham entity) {
        if (entity == null) return null;

        SanPhamDTO.SanPhamDTOBuilder builder = SanPhamDTO.builder()
                .id(entity.getId())
                .tensanpham(entity.getTensanpham())
                .mota(entity.getMota())
                .tenloai(entity.getTenloai())
                .giacu(entity.getGiacu())
                .giamoi(entity.getGiamoi())
                .trangthai(entity.getTrangthai());

        // Safely handle danhmuc
        if (entity.getDanhmuc() != null) {
            builder.danhmucId(entity.getDanhmuc().getId())
                   .danhmucTen(entity.getDanhmuc().getTendanhmuc());
        }

        // Safely handle chiTietSanPhams collection
        if (entity.getChiTietSanPhams() != null && !entity.getChiTietSanPhams().isEmpty()) {
            builder.chitietsanpham(entity.getChiTietSanPhams().stream()
                    .map(chiTietSanPhamMapper::toDTO)
                    .collect(Collectors.toList()));
        } else {
            builder.chitietsanpham(Collections.emptyList());
        }

        return builder.build();
    }

    public SanPham toEntity(SanPhamDTO dto, DanhMuc danhmuc) {
        if (dto == null) return null;
        SanPham.SanPhamBuilder builder = SanPham.builder()
                .tensanpham(dto.getTensanpham())
                .mota(dto.getMota())
                .danhmuc(danhmuc)
                .tenloai(dto.getTenloai())
                .giacu(dto.getGiacu())
                .giamoi(dto.getGiamoi())
                .trangthai(dto.getTrangthai());
        if (dto.getId() != null) {
            builder.id(dto.getId());
        }
        return builder.build();
    }
}
