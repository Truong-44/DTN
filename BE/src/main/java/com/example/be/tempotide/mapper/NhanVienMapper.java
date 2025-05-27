package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.NhanVienDTO;
import com.example.be.tempotide.entity.NhanVien;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NhanVienMapper {
    NhanVienDTO toDTO(NhanVien nhanVien);
    NhanVien toEntity(NhanVienDTO nhanVienDTO);
}