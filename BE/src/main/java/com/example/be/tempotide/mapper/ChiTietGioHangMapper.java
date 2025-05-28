package com.example.tempotide.mapper;

import com.example.tempotide.dto.ChiTietGioHangDTO;
import com.example.tempotide.entity.ChiTietGioHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietGioHangMapper {
    @Mapping(target = "magiohang", source = "giohang.magiohang")
    @Mapping(target = "machitietsanpham", source = "chitietsanpham.machitietsanpham")
    ChiTietGioHangDTO toDTO(ChiTietGioHang chiTietGioHang);

    @Mapping(target = "giohang", ignore = true)
    @Mapping(target = "chitietsanpham", ignore = true)
    ChiTietGioHang toEntity(ChiTietGioHangDTO chiTietGioHangDTO);
}