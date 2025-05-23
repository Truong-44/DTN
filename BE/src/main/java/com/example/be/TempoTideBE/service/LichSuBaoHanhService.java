package com.example.be.TempoTideBE.service;

import com.example.be.dto.LichSuBaoHanhDTO;
import com.example.be.entity.LichSuBaoHanh;
import com.example.be.entity.NhanVien;
import com.example.be.entity.YeuCauBaoHanh;
import com.example.be.repository.LichSuBaoHanhRepository;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.YeuCauBaoHanhRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LichSuBaoHanhService {

    private final LichSuBaoHanhRepository lichSuBaoHanhRepository;
    private final YeuCauBaoHanhRepository yeuCauBaoHanhRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<LichSuBaoHanhDTO> getAllLichSuBaoHanh() {
        return lichSuBaoHanhRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public LichSuBaoHanhDTO getLichSuBaoHanhById(Integer id) {
        LichSuBaoHanh lichSuBaoHanh = lichSuBaoHanhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lịch sử bảo hành không tồn tại"));
        return convertToDTO(lichSuBaoHanh);
    }

    public LichSuBaoHanhDTO createLichSuBaoHanh(LichSuBaoHanhDTO dto) {
        LichSuBaoHanh lichSuBaoHanh = new LichSuBaoHanh();
        mapToEntity(dto, lichSuBaoHanh);
        LichSuBaoHanh savedLichSuBaoHanh = lichSuBaoHanhRepository.save(lichSuBaoHanh);
        return convertToDTO(savedLichSuBaoHanh);
    }

    public LichSuBaoHanhDTO updateLichSuBaoHanh(Integer id, LichSuBaoHanhDTO dto) {
        LichSuBaoHanh lichSuBaoHanh = lichSuBaoHanhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lịch sử bảo hành không tồn tại"));
        mapToEntity(dto, lichSuBaoHanh);
        LichSuBaoHanh updatedLichSuBaoHanh = lichSuBaoHanhRepository.save(lichSuBaoHanh);
        return convertToDTO(updatedLichSuBaoHanh);
    }

    public void deleteLichSuBaoHanh(Integer id) {
        lichSuBaoHanhRepository.deleteById(id);
    }

    private LichSuBaoHanhDTO convertToDTO(LichSuBaoHanh lichSuBaoHanh) {
        LichSuBaoHanhDTO dto = new LichSuBaoHanhDTO();
        dto.setMaLichSu(lichSuBaoHanh.getMaLichSu());
        if (lichSuBaoHanh.getYeuCauBaoHanh() != null) {
            dto.setMaYeuCauBaoHanh(lichSuBaoHanh.getYeuCauBaoHanh().getMaYeuCau());
        }
        dto.setTrangThai(lichSuBaoHanh.getTrangThai());
        dto.setMoTa(lichSuBaoHanh.getMoTa());
        dto.setNgayCapNhat(lichSuBaoHanh.getNgayCapNhat());
        dto.setGhiChu(lichSuBaoHanh.getGhiChu());
        dto.setTrangThai(lichSuBaoHanh.getTrangThai());
        if (lichSuBaoHanh.getNguoiCapNhat() != null) {
            dto.setNguoiCapNhatId(lichSuBaoHanh.getNguoiCapNhat().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(LichSuBaoHanhDTO dto, LichSuBaoHanh lichSuBaoHanh) {
        if (dto.getMaYeuCauBaoHanh() != null) {
            YeuCauBaoHanh yeuCauBaoHanh = yeuCauBaoHanhRepository.findById(dto.getMaYeuCauBaoHanh())
                    .orElseThrow(() -> new RuntimeException("Yêu cầu bảo hành không tồn tại"));
            lichSuBaoHanh.setYeuCauBaoHanh(yeuCauBaoHanh);
        }
        lichSuBaoHanh.setTrangThai(dto.getTrangThai());
        lichSuBaoHanh.setMoTa(dto.getMoTa());
        lichSuBaoHanh.setNgayCapNhat(dto.getNgayCapNhat() != null ? dto.getNgayCapNhat() : LocalDateTime.now());
        lichSuBaoHanh.setGhiChu(dto.getGhiChu());
        lichSuBaoHanh.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiCapNhatId() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(dto.getNguoiCapNhatId())
                    .orElseThrow(() -> new RuntimeException("Người cập nhật không tồn tại"));
            lichSuBaoHanh.setNguoiCapNhat(nguoiCapNhat);
        }
    }
}