package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.NhanVienDTO;
import com.example.be.tempotide.entity.NhanVien;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NhanVienMapper {
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    @Mapping(source = "nguoicapnhat.manhanvien", target = "nguoicapnhat")
    NhanVienDTO toDTO(NhanVien nhanVien);

    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    NhanVien toEntity(NhanVienDTO nhanVienDTO);
}