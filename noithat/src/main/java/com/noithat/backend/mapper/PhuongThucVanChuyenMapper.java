package com.noithat.backend.mapper;

import com.noithat.backend.dto.PhuongThucVanChuyenDTO;
import com.noithat.backend.entity.PhuongThucVanChuyen;
import org.springframework.stereotype.Component;

@Component
public class PhuongThucVanChuyenMapper {

    public PhuongThucVanChuyenDTO toDTO(PhuongThucVanChuyen entity) {
        return PhuongThucVanChuyenDTO.builder()
                .id(entity.getId())
                .ten(entity.getTen())
                .mota(entity.getMota())
                .thoigiandukien(entity.getThoigiandukien())
                .phivanchuyen(entity.getPhivanchuyen())
                .build();
    }

    public PhuongThucVanChuyen toEntity(PhuongThucVanChuyenDTO dto) {
        return PhuongThucVanChuyen.builder()
                .id(dto.getId())
                .ten(dto.getTen())
                .mota(dto.getMota())
                .thoigiandukien(dto.getThoigiandukien())
                .phivanchuyen(dto.getPhivanchuyen())
                .build();
    }
}
