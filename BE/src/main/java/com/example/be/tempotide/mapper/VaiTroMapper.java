package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.VaiTroDTO;
import com.example.be.tempotide.entity.VaiTro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VaiTroMapper {
    @Mapping(target = "nguoitao", ignore = true) // Bỏ qua nguoitao để tránh vòng lặp
    VaiTroDTO toDTO(VaiTro vaiTro);

    @Mapping(target = "nguoitao", ignore = true) // Bỏ qua khi tạo mới
    VaiTro toEntity(VaiTroDTO vaiTroDTO);
}