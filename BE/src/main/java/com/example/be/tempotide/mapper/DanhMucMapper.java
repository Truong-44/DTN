package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.DanhMucDTO;
import com.example.be.tempotide.entity.DanhMuc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DanhMucMapper {
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    DanhMucDTO toDTO(DanhMuc danhMuc);

    @Mapping(target = "nguoitao", ignore = true)
    DanhMuc toEntity(DanhMucDTO danhMucDTO);
}