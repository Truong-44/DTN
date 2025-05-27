package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.QuyenDTO;
import com.example.be.tempotide.entity.Quyen;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuyenMapper {
    QuyenDTO toDTO(Quyen quyen);
    Quyen toEntity(QuyenDTO quyenDTO);
}