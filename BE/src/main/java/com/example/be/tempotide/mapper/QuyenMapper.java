package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.QuyenDTO;
import com.example.be.tempotide.entity.Quyen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuyenMapper {
    @Mapping(target = "nguoitao", source = "nguoitao.manhanvien")
    QuyenDTO toDTO(Quyen quyen);

    @Mapping(target = "nguoitao", ignore = true)
    Quyen toEntity(QuyenDTO quyenDTO);
}