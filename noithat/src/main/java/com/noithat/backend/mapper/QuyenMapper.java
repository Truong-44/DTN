package com.noithat.backend.mapper;

import com.noithat.backend.dto.QuyenDTO;
import com.noithat.backend.entity.Quyen;
import org.springframework.stereotype.Component;

@Component
public class QuyenMapper {

    public QuyenDTO toDTO(Quyen entity) {
        if (entity == null) return null;
        return QuyenDTO.builder()
                .id(entity.getId())
                .ten(entity.getTen())
                .mota(entity.getMota())
                .build();
    }

    public Quyen toEntity(QuyenDTO dto) {
        if (dto == null) return null;
        return Quyen.builder()
                .id(dto.getId())
                .ten(dto.getTen())
                .mota(dto.getMota())
                .build();
    }
}
