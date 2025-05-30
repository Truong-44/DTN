package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.NguoiDungVaiTroDTO;
import com.example.be.tempotide.entity.KhachHang;
import com.example.be.tempotide.entity.NguoiDungVaiTro;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.entity.VaiTro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NguoiDungVaiTroMapper {
    @Mapping(target = "nguoitao", ignore = true)
    NguoiDungVaiTroDTO toDTO(NguoiDungVaiTro nguoiDungVaiTro);

    @Mapping(target = "manhanvien", ignore = true)
    @Mapping(target = "makhachhang", ignore = true)
    @Mapping(target = "mavaitro", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    NguoiDungVaiTro toEntity(NguoiDungVaiTroDTO nguoiDungVaiTroDTO);

    default Integer mapNhanVienToId(NhanVien nhanVien) {
        return nhanVien != null ? nhanVien.getManhanvien() : null;
    }

    default Integer mapKhachHangToId(KhachHang khachHang) {
        return khachHang != null ? khachHang.getMakhachhang() : null;
    }

    default Integer mapVaiTroToId(VaiTro vaiTro) {
        return vaiTro != null ? vaiTro.getMavaitro() : null;
    }
}