package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ChiTietSanPhamDTO;
import com.example.be.tempotide.entity.ChiTietSanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietSanPhamMapper {
    @Mapping(source = "masanpham.masanpham", target = "masanpham")
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    @Mapping(source = "nguoicapnhat.manhanvien", target = "nguoicapnhat")
    ChiTietSanPhamDTO toDTO(ChiTietSanPham chiTietSanPham);

    @Mapping(target = "masanpham", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    ChiTietSanPham toEntity(ChiTietSanPhamDTO chiTietSanPhamDTO);
}