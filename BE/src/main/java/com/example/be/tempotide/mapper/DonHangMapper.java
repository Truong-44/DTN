package com.example.tempotide.mapper;

import com.example.tempotide.dto.DonHangDTO;
import com.example.tempotide.entity.DonHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DonHangMapper {
    @Mapping(target = "makhachhang", source = "khachhang.makhachhang")
    @Mapping(target = "manhanvien", source = "nhanvien.manhanvien")
    DonHangDTO toDTO(DonHang donHang);

    @Mapping(target = "khachhang", ignore = true)
    @Mapping(target = "nhanvien", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    DonHang toEntity(DonHangDTO donHangDTO);
}