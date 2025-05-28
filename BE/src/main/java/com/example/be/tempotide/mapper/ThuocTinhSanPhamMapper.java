package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ThuocTinhSanPhamDTO;
import com.example.be.tempotide.entity.ThuocTinhSanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ThuocTinhSanPhamMapper {
    @Mapping(target = "masanpham", source = "masanpham.masanpham") // Chỉ lấy ID
    @Mapping(target = "nguoitao", ignore = true) // Bỏ qua nguoitao để tránh vòng lặp
    ThuocTinhSanPhamDTO toDTO(ThuocTinhSanPham thuocTinhSanPham);

    @Mapping(target = "masanpham", ignore = true) // Bỏ qua, sẽ set thủ công trong service
    @Mapping(target = "nguoitao", ignore = true) // Bỏ qua khi tạo mới
    ThuocTinhSanPham toEntity(ThuocTinhSanPhamDTO thuocTinhSanPhamDTO);
}