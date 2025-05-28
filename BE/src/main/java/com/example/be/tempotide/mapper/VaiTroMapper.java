package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.VaiTroDTO;
import com.example.be.tempotide.entity.VaiTro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VaiTroMapper {
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    VaiTroDTO toDTO(VaiTro vaiTro);

    @Mapping(target = "nguoitao", ignore = true)
    VaiTro toEntity(VaiTroDTO vaiTroDTO);
}