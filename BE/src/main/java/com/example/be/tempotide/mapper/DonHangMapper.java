package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.DonHangDTO;
import com.example.be.tempotide.entity.DonHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DonHangMapper {
    @Mapping(target = "makhachhang", source = "makhachhang.makhachhang")
    @Mapping(target = "manhanvienxuly", source = "manhanvienxuly.manhanvien")
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    DonHangDTO toDTO(DonHang donHang);

    @Mapping(target = "makhachhang", ignore = true)
    @Mapping(target = "manhanvienxuly", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    DonHang toEntity(DonHangDTO donHangDTO);
}