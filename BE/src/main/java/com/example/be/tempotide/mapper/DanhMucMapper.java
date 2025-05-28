package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.DanhMucDTO;
import com.example.be.tempotide.entity.DanhMuc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DanhMucMapper {
    @Mapping(target = "madanhmuccha", source = "madanhmuccha.madanhmuc") // Chỉ lấy ID
    @Mapping(target = "nguoitao", ignore = true) // Bỏ qua nguoitao để tránh vòng lặp
    DanhMucDTO toDTO(DanhMuc danhMuc);

    @Mapping(target = "madanhmuccha", ignore = true) // Bỏ qua, sẽ set thủ công trong service
    @Mapping(target = "nguoitao", ignore = true) // Bỏ qua khi tạo mới
    DanhMuc toEntity(DanhMucDTO danhMucDTO);
}