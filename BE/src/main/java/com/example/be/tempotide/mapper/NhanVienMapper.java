package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.NhanVienDTO;
import com.example.be.tempotide.entity.NhanVien;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NhanVienMapper {
    @Mapping(target = "nguoitao", ignore = true) // Bỏ qua nguoitao để tránh vòng lặp
    @Mapping(target = "nguoicapnhat", ignore = true) // Bỏ qua nguoicapnhat để tránh vòng lặp
    NhanVienDTO toDTO(NhanVien nhanVien);

    @Mapping(target = "nguoitao", ignore = true) // Bỏ qua khi tạo mới
    @Mapping(target = "nguoicapnhat", ignore = true) // Bỏ qua khi tạo mới
    NhanVien toEntity(NhanVienDTO nhanVienDTO);
}