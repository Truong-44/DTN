package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ThuocTinhSanPhamDTO;
import com.example.be.tempotide.entity.ThuocTinhSanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ThuocTinhSanPhamMapper {
    @Mapping(source = "masanpham.masanpham", target = "masanpham")
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    ThuocTinhSanPhamDTO toDTO(ThuocTinhSanPham thuocTinhSanPham);

    @Mapping(target = "masanpham", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    ThuocTinhSanPham toEntity(ThuocTinhSanPhamDTO thuocTinhSanPhamDTO);
}