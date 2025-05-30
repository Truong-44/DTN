package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.GioHangDTO;
import com.example.be.tempotide.entity.GioHang;
import com.example.be.tempotide.entity.KhachHang;
import com.example.be.tempotide.entity.NhanVien;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GioHangMapper {
    @Mapping(target = "nguoitao", ignore = true)
    GioHangDTO toDTO(GioHang gioHang);

    @Mapping(target = "makhachhang", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    GioHang toEntity(GioHangDTO gioHangDTO);

    default Integer mapKhachHangToId(KhachHang khachHang) {
        return khachHang != null ? khachHang.getMakhachhang() : null;
    }

    default Integer mapNhanVienToId(NhanVien nhanVien) {
        return nhanVien != null ? nhanVien.getManhanvien() : null;
    }
}