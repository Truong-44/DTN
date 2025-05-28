package com.example.tempotide.mapper;

import com.example.tempotide.dto.SanPhamDTO;
import com.example.tempotide.entity.SanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SanPhamMapper {
    @Mapping(target = "madanhmuc", source = "danhMuc.madanhmuc")
    SanPhamDTO toDTO(SanPham sanPham);

    @Mapping(target = "danhMuc", ignore = true)
    SanPham toEntity(SanPhamDTO sanPhamDTO);
}