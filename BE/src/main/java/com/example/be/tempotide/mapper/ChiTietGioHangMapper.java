package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ChiTietGioHangDTO;
import com.example.be.tempotide.entity.ChiTietGioHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietGioHangMapper {
    @Mapping(target = "magiohang", source = "magiohang.magiohang")
    @Mapping(target = "machitietsanpham", source = "machitietsanpham.machitietsanpham")
    @Mapping(target = "nguoitao", ignore = true)
    ChiTietGioHangDTO toDTO(ChiTietGioHang chiTietGioHang);

    @Mapping(target = "magiohang", ignore = true)
    @Mapping(target = "machitietsanpham", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    ChiTietGioHang toEntity(ChiTietGioHangDTO chiTietGioHangDTO);
}