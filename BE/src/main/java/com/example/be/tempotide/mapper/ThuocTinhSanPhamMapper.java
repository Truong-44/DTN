package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ThuocTinhSanPhamDTO;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.entity.ThuocTinhSanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ThuocTinhSanPhamMapper {
    @Mapping(target = "nguoitao", ignore = true)
    ThuocTinhSanPhamDTO toDTO(ThuocTinhSanPham thuocTinhSanPham);

    @Mapping(target = "nguoitao", ignore = true)
    ThuocTinhSanPham toEntity(ThuocTinhSanPhamDTO thuocTinhSanPhamDTO);

    default Integer mapNhanVienToId(NhanVien nhanVien) {
        return nhanVien != null ? nhanVien.getManhanvien() : null;
    }
}