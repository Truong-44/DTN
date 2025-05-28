package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.KhachHangDTO;
import com.example.be.tempotide.entity.KhachHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KhachHangMapper {
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    @Mapping(source = "nguoicapnhat.manhanvien", target = "nguoicapnhat")
    KhachHangDTO toDTO(KhachHang khachHang);

    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    KhachHang toEntity(KhachHangDTO khachHangDTO);
}