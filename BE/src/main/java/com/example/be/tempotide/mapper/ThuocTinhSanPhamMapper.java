package com.example.tempotide.mapper;

import com.example.tempotide.dto.ThuocTinhSanPhamDTO;
import com.example.tempotide.entity.ThuocTinhSanPham;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ThuocTinhSanPhamMapper {
    ThuocTinhSanPhamDTO toDTO(ThuocTinhSanPham thuocTinhSanPham);

    ThuocTinhSanPham toEntity(ThuocTinhSanPhamDTO thuocTinhSanPhamDTO);
}