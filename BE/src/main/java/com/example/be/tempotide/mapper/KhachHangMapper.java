package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.KhachHangDTO;
import com.example.be.tempotide.entity.KhachHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KhachHangMapper {
    @Mapping(target = "nguoitao", ignore = true) // Bỏ qua nguoitao để tránh vòng lặp
    @Mapping(target = "nguoicapnhat", ignore = true) // Bỏ qua nguoicapnhat để tránh vòng lặp
    KhachHangDTO toDTO(KhachHang khachHang);

    @Mapping(target = "nguoitao", ignore = true) // Bỏ qua khi tạo mới
    @Mapping(target = "nguoicapnhat", ignore = true) // Bỏ qua khi tạo mới
    KhachHang toEntity(KhachHangDTO khachHangDTO);
}