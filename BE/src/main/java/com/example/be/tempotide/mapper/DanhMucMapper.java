package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.DanhMucDTO;
import com.example.be.tempotide.entity.DanhMuc;
import com.example.be.tempotide.entity.NhanVien;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DanhMucMapper {
    @Mapping(target = "nguoitao", ignore = true)
    DanhMucDTO toDTO(DanhMuc danhMuc);

    @Mapping(target = "madanhmuccha", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    DanhMuc toEntity(DanhMucDTO danhMucDTO);

    default Integer mapDanhMucToId(DanhMuc danhMuc) {
        return danhMuc != null ? danhMuc.getMadanhmuc() : null;
    }

    default Integer mapNhanVienToId(NhanVien nhanVien) {
        return nhanVien != null ? nhanVien.getManhanvien() : null;
    }
}