package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.DonHangDTO;
import com.example.be.tempotide.entity.DonHang;
import com.example.be.tempotide.entity.KhachHang;
import com.example.be.tempotide.entity.NhanVien;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DonHangMapper {
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    DonHangDTO toDTO(DonHang donHang);

    @Mapping(target = "makhachhang", ignore = true)
    @Mapping(target = "manhanvienxuly", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    DonHang toEntity(DonHangDTO donHangDTO);

    default Integer mapKhachHangToId(KhachHang khachHang) {
        return khachHang != null ? khachHang.getMakhachhang() : null;
    }

    default Integer mapNhanVienToId(NhanVien nhanVien) {
        return nhanVien != null ? nhanVien.getManhanvien() : null;
    }
}