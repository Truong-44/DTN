package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.DonHangDTO;
import com.example.be.tempotide.entity.DonHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DonHangMapper {
    @Mapping(source = "makhachhang.makhachhang", target = "makhachhang")
    @Mapping(source = "manhanvienxuly.manhanvien", target = "manhanvienxuly")
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    @Mapping(source = "nguoicapnhat.manhanvien", target = "nguoicapnhat")
    DonHangDTO toDTO(DonHang donHang);

    @Mapping(target = "makhachhang", ignore = true)
    @Mapping(target = "manhanvienxuly", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    DonHang toEntity(DonHangDTO donHangDTO);
}