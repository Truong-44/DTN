package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.SanPhamDTO;
import com.example.be.tempotide.entity.DanhMuc;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.entity.SanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SanPhamMapper {
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    SanPhamDTO toDTO(SanPham sanPham);

    @Mapping(target = "madanhmuc", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    SanPham toEntity(SanPhamDTO sanPhamDTO);

    default Integer mapDanhMucToId(DanhMuc danhMuc) {
        return danhMuc != null ? danhMuc.getMadanhmuc() : null;
    }

    default Integer mapNhanVienToId(NhanVien nhanVien) {
        return nhanVien != null ? nhanVien.getManhanvien() : null;
    }
}