package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.VaiTroQuyenDTO;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.entity.Quyen;
import com.example.be.tempotide.entity.VaiTro;
import com.example.be.tempotide.entity.VaiTroQuyen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VaiTroQuyenMapper {
    @Mapping(target = "nguoitao", ignore = true)
    VaiTroQuyenDTO toDTO(VaiTroQuyen vaiTroQuyen);

    @Mapping(target = "mavaitro", ignore = true)
    @Mapping(target = "maquyen", ignore = true)
    @Mapping(target = "nguoitao", ignore = true)
    VaiTroQuyen toEntity(VaiTroQuyenDTO vaiTroQuyenDTO);

    default Integer mapVaiTroToId(VaiTro vaiTro) {
        return vaiTro != null ? vaiTro.getMavaitro() : null;
    }

    default Integer mapQuyenToId(Quyen quyen) {
        return quyen != null ? quyen.getMaquyen() : null;
    }

    default Integer mapNhanVienToId(NhanVien nhanVien) {
        return nhanVien != null ? nhanVien.getManhanvien() : null;
    }
}