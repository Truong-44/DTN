package com.example.be.TempoTideBE.service;


import com.example.be.TempoTideBE.dto.CauHinhHeThongDTO;
import com.example.be.TempoTideBE.entity.CauHinhHeThong;
import com.example.be.TempoTideBE.entity.NhanVien;
import com.example.be.TempoTideBE.repository.CauHinhHeThongRepository;
import com.example.be.TempoTideBE.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CauHinhHeThongService {

    private final CauHinhHeThongRepository cauHinhHeThongRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<CauHinhHeThongDTO> getAllCauHinhHeThong() {
        return cauHinhHeThongRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CauHinhHeThongDTO getCauHinhHeThongById(Integer id) {
        CauHinhHeThong cauHinhHeThong = cauHinhHeThongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cấu hình hệ thống không tồn tại"));
        return convertToDTO(cauHinhHeThong);
    }

    public CauHinhHeThongDTO createCauHinhHeThong(CauHinhHeThongDTO dto) {
        CauHinhHeThong cauHinhHeThong = new CauHinhHeThong();
        mapToEntity(dto, cauHinhHeThong);
        CauHinhHeThong savedCauHinhHeThong = cauHinhHeThongRepository.save(cauHinhHeThong);
        return convertToDTO(savedCauHinhHeThong);
    }

    public CauHinhHeThongDTO updateCauHinhHeThong(Integer id, CauHinhHeThongDTO dto) {
        CauHinhHeThong cauHinhHeThong = cauHinhHeThongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cấu hình hệ thống không tồn tại"));
        mapToEntity(dto, cauHinhHeThong);
        CauHinhHeThong updatedCauHinhHeThong = cauHinhHeThongRepository.save(cauHinhHeThong);
        return convertToDTO(updatedCauHinhHeThong);
    }

    public void deleteCauHinhHeThong(Integer id) {
        cauHinhHeThongRepository.deleteById(id);
    }

    private CauHinhHeThongDTO convertToDTO(CauHinhHeThong cauHinhHeThong) {
        CauHinhHeThongDTO dto = new CauHinhHeThongDTO();
        dto.setMaCauHinh(cauHinhHeThong.getMaCauHinh());
        dto.setTenCauHinh(cauHinhHeThong.getTenCauHinh());
        dto.setGiaTri(cauHinhHeThong.getGiaTri());
        dto.setMoTa(cauHinhHeThong.getMoTa());
        dto.setNgayTao(cauHinhHeThong.getNgayTao());
        dto.setTrangThai(cauHinhHeThong.getTrangThai());
        if (cauHinhHeThong.getNguoiTao() != null) {
            dto.setNguoiTaoId(cauHinhHeThong.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(CauHinhHeThongDTO dto, CauHinhHeThong cauHinhHeThong) {
        cauHinhHeThong.setTenCauHinh(dto.getTenCauHinh());
        cauHinhHeThong.setGiaTri(dto.getGiaTri());
        cauHinhHeThong.setMoTa(dto.getMoTa());
        cauHinhHeThong.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        cauHinhHeThong.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            cauHinhHeThong.setNguoiTao(nguoiTao);
        }
    }
}