package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.SanPhamDTO;
import com.example.be.tempotide.entity.SanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SanPhamMapper {
    @Mapping(source = "madanhmuc.madanhmuc", target = "madanhmuc")
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    @Mapping(source = "nguoicapnhat.manhanvien", target = "nguoicapnhat")
    SanPhamDTO toDTO(SanPham sanPham);

    @Mapping(target = "madanhmuc", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    SanPham toEntity(SanPhamDTO sanPhamDTO);
}