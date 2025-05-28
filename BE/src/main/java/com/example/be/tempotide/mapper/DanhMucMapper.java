package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.DanhMucDTO;
import com.example.be.tempotide.entity.DanhMuc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DanhMucMapper {
    @Mapping(target = "danhmucchaId", source = "danhmuccha.madanhmuc")
    DanhMucDTO toDTO(DanhMuc danhMuc);
    @Mapping(target = "danhmuccha", ignore = true)
    DanhMuc toEntity(DanhMucDTO danhMucDTO);
}