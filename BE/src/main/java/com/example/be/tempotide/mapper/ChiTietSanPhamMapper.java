package com.example.temp;

import com.example.temp.dto.ChiTietSanPhamDTO;
import com.example.temp.entity.ChiTietSanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietSanPhamMapper {
    @Mapping(target = "masanpham", source = "sanpham.masanpham")
    @Mapping(target = "mathuoctinh", source = "thuoctinh.mathuoctinh")
    ChiTietSanPhamDTO toDTO(ChiTietSanPham chiTietSanPham);

    @Mapping(target = "sanpham", ignore = true)
    @Mapping(target = "thuoctinh", ignore = true)
    ChiTietSanPham toEntity(ChiTietSanPhamDTO chiTietSanPhamDTO);
}