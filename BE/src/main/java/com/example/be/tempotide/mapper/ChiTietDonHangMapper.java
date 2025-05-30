package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ChiTietDonHangDTO;
import com.example.be.tempotide.entity.ChiTietDonHang;
import com.example.be.tempotide.entity.ChiTietSanPham;
import com.example.be.tempotide.entity.DonHang;
import com.example.be.tempotide.entity.NhanVien;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietDonHangMapper {
    @Mapping(target = "nguoitao", ignore = true)
    ChiTietDonHangDTO toDTO(ChiTietDonHang chiTietDonHang);

    @Mapping(target = "madonhang", ignore = true)
    @Mapping(target = "machitietsanpham", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    ChiTietDonHang toEntity(ChiTietDonHangDTO chiTietDonHangDTO);

    default Integer mapDonHangToId(DonHang donHang) {
        return donHang != null ? donHang.getMadonhang() : null;
    }

    default Integer mapChiTietSanPhamToId(ChiTietSanPham chiTietSanPham) {
        return chiTietSanPham != null ? chiTietSanPham.getMachitietsanpham() : null;
    }

    default Integer mapNhanVienToId(NhanVien nhanVien) {
        return nhanVien != null ? nhanVien.getManhanvien() : null;
    }
}