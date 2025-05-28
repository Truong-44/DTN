package com.example.tempotide.mapper;

import com.example.tempotide.dto.ChiTietDonHangDTO;
import com.example.tempotide.entity.ChiTietDonHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietDonHangMapper {
    @Mapping(target = "madonhang", source = "donhang.madonhang")
    @Mapping(target = "machitietsanpham", source = "chitietsanpham.machitietsanpham")
    ChiTietDonHangDTO toDTO(ChiTietDonHang chiTietDonHang);

    @Mapping(target = "donhang", ignore = true)
    @Mapping(target = "chitietsanpham", ignore = true)
    ChiTietDonHang toEntity(ChiTietDonHangDTO chiTietDonHangDTO);
}