package com.example.be.tempotide.mapper;

import com.example.be.tempotide.entity.SanPham;
import com.example.tempotide.backend.dto.SanPhamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SanPhamMapper {
    SanPhamMapper INSTANCE = Mappers.getMapper(SanPhamMapper.class);

    @Mapping(target = "danhmuc", ignore = true)
    @Mapping(target = "nhacungcap", ignore = true)
    @Mapping(target = "thuonghieu", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    @Mapping(target = "nguoicapnhat", ignore = true)
    @Mapping(target = "nguoiduyet", ignore = true)
    SanPham toEntity(SanPhamDTO dto);

    @Mapping(target = "madanhmuc", expression = "java(entity.getDanhmuc() != null ? entity.getDanhmuc().getMadanhmuc() : null)")
    @Mapping(target = "manhacungcap", expression = "java(entity.getNhacungcap() != null ? entity.getNhacungcap().getManhacungcap() : null)")
    @Mapping(target = "mathuonghieu", expression = "java(entity.getThuonghieu() != null ? entity.getThuonghieu().getMathuonghieu() : null)")
    @Mapping(target = "nguoitao", expression = "java(entity.getNguoitao() != null ? entity.getNguoitao().getManhanvien() : null)")
    @Mapping(target = "nguoicapnhat", expression = "java(entity.getNguoicapnhat() != null ? entity.getNguoicapnhat().getManhanvien() : null)")
    @Mapping(target = "nguoiduyet", expression = "java(entity.getNguoiduyet() != null ? entity.getNguoiduyet().getManhanvien() : null)")
    SanPhamDTO toDTO(SanPham entity);
}