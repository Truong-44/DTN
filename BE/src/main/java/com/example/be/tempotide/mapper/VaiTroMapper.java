package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.VaiTroDTO;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.entity.VaiTro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VaiTroMapper {
    @Mapping(target = "nguoitao", ignore = true)
    VaiTroDTO toDTO(VaiTro vaiTro);

    @Mapping(target = "nguoitao", ignore = true)
    VaiTro toEntity(VaiTroDTO vaiTroDTO);

    default Integer mapNhanVienToId(NhanVien nhanVien) {
        return nhanVien != null ? nhanVien.getManhanvien() : null;
    }
}