package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ChiTietDonHangDTO;
import com.example.be.tempotide.entity.ChiTietDonHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietDonHangMapper {
    @Mapping(source = "madonhang.madonhang", target = "madonhang") // Ánh xạ từ DonHang -> Integer
    @Mapping(source = "masanpham.masanpham", target = "masanpham") // Ánh xạ từ SanPham -> Integer
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    @Mapping(source = "nguoicapnhat.manhanvien", target = "nguoicapnhat")
    ChiTietDonHangDTO toDTO(ChiTietDonHang chiTietDonHang);

    @Mapping(target = "madonhang", ignore = true) // Bỏ qua ánh xạ, sẽ xử lý trong service
    @Mapping(target = "masanpham", ignore = true) // Bỏ qua ánh xạ, sẽ xử lý trong service
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    ChiTietDonHang toEntity(ChiTietDonHangDTO chiTietDonHangDTO);
}