package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.ChiTietSanPhamDTO;
import com.example.be.tempotide.entity.ChiTietSanPham;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.entity.SanPham;
import com.example.be.tempotide.entity.ThuocTinhSanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiTietSanPhamMapper {
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    ChiTietSanPhamDTO toDTO(ChiTietSanPham chiTietSanPham);

    @Mapping(target = "masanpham", ignore = true)
    @Mapping(target = "mathuoctinh", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    ChiTietSanPham toEntity(ChiTietSanPhamDTO chiTietSanPhamDTO);

    default Integer mapSanPhamToId(SanPham sanPham) {
        return sanPham != null ? sanPham.getMasanpham() : null;
    }

    default Integer mapThuocTinhSanPhamToId(ThuocTinhSanPham thuocTinhSanPham) {
        return thuocTinhSanPham != null ? thuocTinhSanPham.getMathuoctinh() : null;
    }

    default Integer mapNhanVienToId(NhanVien nhanVien) {
        return nhanVien != null ? nhanVien.getManhanvien() : null;
    }
}