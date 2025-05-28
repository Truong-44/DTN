package com.example.tempotide.mapper;

import com.example.tempotide.dto.GioHangDTO;
import com.example.tempotide.entity.GioHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GioHangMapper {
    @Mapping(target = "makhachhang", source = "khachhang.makhachhang")
    GioHangDTO toDTO(GioHang gioHang);

    @Mapping(target = "khachhang", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    GioHang toEntity(GioHangDTO gioHangDTO);
}