package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.VaiTroDTO;
import com.example.be.tempotide.entity.VaiTro;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VaiTroMapper {
    VaiTroDTO toDTO(VaiTro vaiTro);
    VaiTro toEntity(VaiTroDTO vaiTroDTO);
}