package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.NguoiDungVaiTroDTO;
import com.example.be.tempotide.entity.NguoiDungVaiTro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NguoiDungVaiTroMapper {
    @Mapping(source = "manhanvien.manhanvien", target = "manhanvien")
    @Mapping(source = "mavaitro.mavaitro", target = "mavaitro")
    NguoiDungVaiTroDTO toDTO(NguoiDungVaiTro nguoiDungVaiTro);

    @Mapping(target = "manhanvien", ignore = true)
    @Mapping(target = "mavaitro", ignore = true)
    NguoiDungVaiTro toEntity(NguoiDungVaiTroDTO nguoiDungVaiTroDTO);
}