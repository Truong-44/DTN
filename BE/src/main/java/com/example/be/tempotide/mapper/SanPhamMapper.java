package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.SanPhamDTO;
import com.example.be.tempotide.entity.SanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SanPhamMapper {
    @Mapping(target = "madanhmuc", source = "madanhmuc.madanhmuc") // Chỉ lấy ID
    @Mapping(target = "nguoitao", ignore = true) // Bỏ qua nguoitao để tránh vòng lặp
    @Mapping(target = "nguoicapnhat", ignore = true) // Bỏ qua nguoicapnhat để tránh vòng lặp
    SanPhamDTO toDTO(SanPham sanPham);

    @Mapping(target = "madanhmuc", ignore = true) // Bỏ qua, sẽ set thủ công trong service
    @Mapping(target = "nguoitao", ignore = true) // Bỏ qua khi tạo mới
    @Mapping(target = "nguoicapnhat", ignore = true) // Bỏ qua khi tạo mới
    SanPham toEntity(SanPhamDTO sanPhamDTO);
}