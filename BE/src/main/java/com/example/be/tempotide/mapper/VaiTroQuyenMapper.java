package com.example.be.tempotide.mapper;

import com.example.be.tempotide.dto.VaiTroQuyenDTO;
import com.example.be.tempotide.entity.VaiTroQuyen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VaiTroQuyenMapper {
    @Mapping(target = "mavaitro", source = "mavaitro.mavaitro") // Chỉ lấy ID từ VaiTro
    @Mapping(target = "maquyen", source = "maquyen.maquyen") // Chỉ lấy ID từ Quyen
    @Mapping(target = "nguoitao", ignore = true) // Bỏ qua nguoitao để tránh vòng lặp
    VaiTroQuyenDTO toDTO(VaiTroQuyen vaiTroQuyen);

    @Mapping(target = "mavaitro", ignore = true) // Bỏ qua, sẽ set thủ công trong service
    @Mapping(target = "maquyen", ignore = true) // Bỏ qua, sẽ set thủ công trong service
    @Mapping(target = "nguoitao", ignore = true) // Bỏ qua khi tạo mới
    VaiTroQuyen toEntity(VaiTroQuyenDTO vaiTroQuyenDTO);
}