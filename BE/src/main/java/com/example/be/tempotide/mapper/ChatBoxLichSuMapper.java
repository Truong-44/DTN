package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ChatBoxLichSuDTO;
import com.example.be.tempotide.entity.ChatBoxLichSu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatBoxLichSuMapper {
    @Mapping(source = "makhachhang.makhachhang", target = "makhachhang")
    @Mapping(source = "giosanpham.masanpham", target = "giosanpham")
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    ChatBoxLichSuDTO toDTO(ChatBoxLichSu chatBoxLichSu);

    @Mapping(target = "makhachhang", ignore = true)
    @Mapping(target = "giosanpham", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    ChatBoxLichSu toEntity(ChatBoxLichSuDTO chatBoxLichSuDTO);
}