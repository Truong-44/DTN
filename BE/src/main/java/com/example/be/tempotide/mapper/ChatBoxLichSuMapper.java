package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ChatBoxLichSuDTO;
import com.example.be.tempotide.entity.ChatBoxLichSu;
import com.example.be.tempotide.entity.KhachHang;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.entity.SanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatBoxLichSuMapper {
    @Mapping(target = "nguoitao", ignore = true)
    ChatBoxLichSuDTO toDTO(ChatBoxLichSu chatBoxLichSu);

    @Mapping(target = "makhachhang", ignore = true)
    @Mapping(target = "giosanpham", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    ChatBoxLichSu toEntity(ChatBoxLichSuDTO chatBoxLichSuDTO);

    default Integer mapKhachHangToId(KhachHang khachHang) {
        return khachHang != null ? khachHang.getMakhachhang() : null;
    }

    default Integer mapSanPhamToId(SanPham sanPham) {
        return sanPham != null ? sanPham.getMasanpham() : null;
    }

    default Integer mapNhanVienToId(NhanVien nhanVien) {
        return nhanVien != null ? nhanVien.getManhanvien() : null;
    }
}