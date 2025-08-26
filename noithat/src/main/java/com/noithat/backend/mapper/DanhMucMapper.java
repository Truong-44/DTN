package com.noithat.backend.mapper;

import com.noithat.backend.dto.DanhMucDTO;
import com.noithat.backend.entity.DanhMuc;
import org.springframework.stereotype.Component;

@Component
public class DanhMucMapper {

    public DanhMucDTO toDTO(DanhMuc entity) {
        if (entity == null) return null;

        return DanhMucDTO.builder()
                .id(entity.getId())
                .tendanhmuc(entity.getTendanhmuc())
                .mota(entity.getMota())
                .danhmucChaId(entity.getDanhmucCha() != null ? entity.getDanhmucCha().getId() : null)
                .danhmucChaTen(entity.getDanhmucCha() != null ? entity.getDanhmucCha().getTendanhmuc() : null)
                .build();
    }

    public DanhMuc toEntity(DanhMucDTO dto, DanhMuc parent) {
        if (dto == null) return null;

        return DanhMuc.builder()
                .id(dto.getId())
                .tendanhmuc(dto.getTendanhmuc())
                .mota(dto.getMota())
                .danhmucCha(parent)
                .build();
    }
}
