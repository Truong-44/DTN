package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ChiTietGioHangDTO;
import com.example.be.tempotide.entity.ChiTietGioHang;
import com.example.be.tempotide.entity.ChiTietSanPham;
import com.example.be.tempotide.entity.GioHang;
import com.example.be.tempotide.entity.NhanVien;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietGioHangMapper {
    @Mapping(target = "nguoitao", ignore = true)
    ChiTietGioHangDTO toDTO(ChiTietGioHang chiTietGioHang);

    @Mapping(target = "magiohang", ignore = true)
    @Mapping(target = "machitietsanpham", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    ChiTietGioHang toEntity(ChiTietGioHangDTO chiTietGioHangDTO);

    default Integer mapGioHangToId(GioHang gioHang) {
        return gioHang != null ? gioHang.getMagiohang() : null;
    }

    default Integer mapChiTietSanPhamToId(ChiTietSanPham chiTietSanPham) {
        return chiTietSanPham != null ? chiTietSanPham.getMachitietsanpham() : null;
    }

    default Integer mapNhanVienToId(NhanVien nhanVien) {
        return nhanVien != null ? nhanVien.getManhanvien() : null;
    }
}