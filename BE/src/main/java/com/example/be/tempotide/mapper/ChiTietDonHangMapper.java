package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ChiTietDonHangDTO;
import com.example.be.tempotide.entity.ChiTietDonHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietDonHangMapper {
    @Mapping(source = "madonhang.madonhang", target = "madonhang")
    @Mapping(source = "machitietsanpham.machitietsanpham", target = "machitietsanpham")
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    ChiTietDonHangDTO toDTO(ChiTietDonHang chiTietDonHang);

    @Mapping(target = "madonhang", ignore = true)
    @Mapping(target = "machitietsanpham", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    ChiTietDonHang toEntity(ChiTietDonHangDTO chiTietDonHangDTO);
}