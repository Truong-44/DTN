package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ChiTietDonHangDTO;
import com.example.be.tempotide.entity.ChiTietDonHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietDonHangMapper {
    @Mapping(target = "madonhang", source = "madonhang.madonhang")
    @Mapping(target = "machitietsanpham", source = "machitietsanpham.machitietsanpham")
    @Mapping(target = "nguoitao", ignore = true)
    ChiTietDonHangDTO toDTO(ChiTietDonHang chiTietDonHang);

    @Mapping(target = "madonhang", ignore = true)
    @Mapping(target = "machitietsanpham", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    ChiTietDonHang toEntity(ChiTietDonHangDTO chiTietDonHangDTO);
}