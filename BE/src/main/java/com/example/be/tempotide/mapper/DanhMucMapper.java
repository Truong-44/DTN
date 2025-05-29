package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.DanhMucDTO;
import com.example.be.tempotide.entity.DanhMuc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DanhMucMapper {
    @Mapping(source = "madanhmuccha.madanhmuc", target = "madanhmuccha")
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    DanhMucDTO toDTO(DanhMuc danhMuc);

    @Mapping(target = "madanhmuccha", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    DanhMuc toEntity(DanhMucDTO danhMucDTO);
}