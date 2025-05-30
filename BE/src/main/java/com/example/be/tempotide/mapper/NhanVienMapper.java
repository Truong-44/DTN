package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.NhanVienDTO;
import com.example.be.tempotide.entity.NhanVien;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NhanVienMapper {
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    NhanVienDTO toDTO(NhanVien nhanVien);

    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    NhanVien toEntity(NhanVienDTO nhanVienDTO);

    default Integer mapNhanVienToId(NhanVien nhanVien) {
        return nhanVien != null ? nhanVien.getManhanvien() : null;
    }
}