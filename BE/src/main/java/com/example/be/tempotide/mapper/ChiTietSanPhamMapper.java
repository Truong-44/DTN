package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ChiTietSanPhamDTO;
import com.example.be.tempotide.entity.ChiTietSanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietSanPhamMapper {
    @Mapping(target = "masanpham", source = "masanpham.masanpham")
    @Mapping(target = "mathuoctinh", source = "mathuoctinh.mathuoctinh")
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    ChiTietSanPhamDTO toDTO(ChiTietSanPham chiTietSanPham);

    @Mapping(target = "masanpham", ignore = true)
    @Mapping(target = "mathuoctinh", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    ChiTietSanPham toEntity(ChiTietSanPhamDTO chiTietSanPhamDTO);
}