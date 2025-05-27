package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.KhachHangDTO;
import com.example.be.tempotide.entity.KhachHang;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KhachHangMapper {
    KhachHangDTO toDTO(KhachHang khachHang);
    KhachHang toEntity(KhachHangDTO khachHangDTO);
}