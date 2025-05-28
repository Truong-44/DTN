package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.VaiTroQuyenDTO;
import com.example.be.tempotide.entity.VaiTroQuyen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VaiTroQuyenMapper {
    @Mapping(source = "mavaitro.mavaitro", target = "mavaitro")
    @Mapping(source = "maquyen.maquyen", target = "maquyen")
    @Mapping(source = "nguoitao.manhanvien", target = "nguoitao")
    VaiTroQuyenDTO toDTO(VaiTroQuyen vaiTroQuyen);

    @Mapping(target = "mavaitro", ignore = true)
    @Mapping(target = "maquyen", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    VaiTroQuyen toEntity(VaiTroQuyenDTO vaiTroQuyenDTO);
}