package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ChiTietGioHangDTO;
import com.example.be.tempotide.entity.ChiTietGioHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietGioHangMapper {
    @Mapping(source = "magiohang.magiohang", target = "magiohang")
    @Mapping(source = "machitietsanpham.machitietsanpham", target = "machitietsanpham")
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    ChiTietGioHangDTO toDTO(ChiTietGioHang chiTietGioHang);

    @Mapping(target = "magiohang", ignore = true)
    @Mapping(target = "machitietsanpham", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    ChiTietGioHang toEntity(ChiTietGioHangDTO chiTietGioHangDTO);
}