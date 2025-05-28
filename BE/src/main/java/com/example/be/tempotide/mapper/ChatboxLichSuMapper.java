package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ChatboxLichSuDTO;
import com.example.be.tempotide.entity.ChatboxLichSu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatboxLichSuMapper {
    @Mapping(source = "makhachhang.makhachhang", target = "makhachhang")
    @Mapping(source = "giosanpham.masanpham", target = "giosanpham")
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    ChatboxLichSuDTO toDTO(ChatboxLichSu chatboxLichSu);

    @Mapping(target = "makhachhang", ignore = true)
    @Mapping(target = "giosanpham", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    ChatboxLichSu toEntity(ChatboxLichSuDTO chatboxLichSuDTO);
}