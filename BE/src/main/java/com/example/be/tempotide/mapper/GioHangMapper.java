package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.GioHangDTO;
import com.example.be.tempotide.entity.GioHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GioHangMapper {
    @Mapping(target = "makhachhang", source = "makhachhang.makhachhang")
    @Mapping(target = "nguoitao", ignore = true)
    GioHangDTO toDTO(GioHang gioHang);

    @Mapping(target = "makhachhang", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    GioHang toEntity(GioHangDTO gioHangDTO);
}