package com.example.be.TempoTideBE.mapper;

import com.example.be.TempoTideBE.dto.SanPhamDTO;
import com.example.be.TempoTideBE.entity.SanPham;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface SanPhamMapper {
    @Mapping(source = "danhMuc.maDanhMuc", target = "maDanhMuc")
    @Mapping(source = "nhaCungCap.maNhaCungCap", target = "maNhaCungCap")
    @Mapping(source = "thuongHieu.maThuongHieu", target = "maThuongHieu")
    @Mapping(source = "nguoiTao.maNhanVien", target = "nguoiTao")
    @Mapping(source = "nguoiCapNhat.maNhanVien", target = "nguoiCapNhat")
    @Mapping(source = "nguoiDuyet.maNhanVien", target = "nguoiDuyet")
    SanPhamDTO toDto(SanPham sanPham);

    @Mapping(target = "danhMuc", ignore = true)
    @Mapping(target = "nhaCungCap", ignore = true)
    @Mapping(target = "thuongHieu", ignore = true)
    @Mapping(target = "nguoiTao", ignore = true)
    @Mapping(target = "nguoiCapNhat", ignore = true)
    @Mapping(target = "nguoiDuyet", ignore = true)
    SanPham toEntity(SanPhamDTO sanPhamDTO);
}
