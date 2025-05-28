package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ChatboxLichSuDTO;
import com.example.be.tempotide.entity.ChatboxLichSu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatboxLichSuMapper {
    @Mapping(target = "makhachhang", source = "makhachhang.makhachhang")
    @Mapping(target = "giosanpham", source = "giosanpham.masanpham")
    @Mapping(target = "nguoitao", ignore = true)
    ChatboxLichSuDTO toDTO(ChatboxLichSu chatboxLichSu);

    @Mapping(target = "makhachhang", ignore = true)
    @Mapping(target = "giosanpham", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    ChatboxLichSu toEntity(ChatboxLichSuDTO chatboxLichSuDTO);
}