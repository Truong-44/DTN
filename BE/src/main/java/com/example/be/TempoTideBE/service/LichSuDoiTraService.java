package com.example.be.TempoTideBE.service;

import com.example.be.dto.LichSuDoiTraDTO;
import com.example.be.entity.DoiTra;
import com.example.be.entity.LichSuDoiTra;
import com.example.be.entity.NhanVien;
import com.example.be.repository.DoiTraRepository;
import com.example.be.repository.LichSuDoiTraRepository;
import com.example.be.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LichSuDoiTraService {

    private final LichSuDoiTraRepository lichSuDoiTraRepository;
    private final DoiTraRepository doiTraRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<LichSuDoiTraDTO> getAllLichSuDoiTra() {
        return lichSuDoiTraRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public LichSuDoiTraDTO getLichSuDoiTraById(Integer id) {
        LichSuDoiTra lichSuDoiTra = lichSuDoiTraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lịch sử đổi trả không tồn tại"));
        return convertToDTO(lichSuDoiTra);
    }

    public LichSuDoiTraDTO createLichSuDoiTra(LichSuDoiTraDTO dto) {
        LichSuDoiTra lichSuDoiTra = new LichSuDoiTra();
        mapToEntity(dto, lichSuDoiTra);
        LichSuDoiTra savedLichSuDoiTra = lichSuDoiTraRepository.save(lichSuDoiTra);
        return convertToDTO(savedLichSuDoiTra);
    }

    public LichSuDoiTraDTO updateLichSuDoiTra(Integer id, LichSuDoiTraDTO dto) {
        LichSuDoiTra lichSuDoiTra = lichSuDoiTraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lịch sử đổi trả không tồn tại"));
        mapToEntity(dto, lichSuDoiTra);
        LichSuDoiTra updatedLichSuDoiTra = lichSuDoiTraRepository.save(lichSuDoiTra);
        return convertToDTO(updatedLichSuDoiTra);
    }

    public void deleteLichSuDoiTra(Integer id) {
        lichSuDoiTraRepository.deleteById(id);
    }

    private LichSuDoiTraDTO convertToDTO(LichSuDoiTra lichSuDoiTra) {
        LichSuDoiTraDTO dto = new LichSuDoiTraDTO();
        dto.setMaLichSu(lichSuDoiTra.getMaLichSu());
        if (lichSuDoiTra.getDoiTra() != null) {
            dto.setMaDoiTra(lichSuDoiTra.getDoiTra().getMaDoiTra());
        }
        dto.setTrangThai(lichSuDoiTra.getTrangThai());
        dto.setMoTa(lichSuDoiTra.getMoTa());
        dto.setNgayCapNhat(lichSuDoiTra.getNgayCapNhat());
        dto.setGhiChu(lichSuDoiTra.getGhiChu());
        dto.setTrangThai(lichSuDoiTra.getTrangThai());
        if (lichSuDoiTra.getNguoiCapNhat() != null) {
            dto.setNguoiCapNhatId(lichSuDoiTra.getNguoiCapNhat().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(LichSuDoiTraDTO dto, LichSuDoiTra lichSuDoiTra) {
        if (dto.getMaDoiTra() != null) {
            DoiTra doiTra = doiTraRepository.findById(dto.getMaDoiTra())
                    .orElseThrow(() -> new RuntimeException("Đổi trả không tồn tại"));
            lichSuDoiTra.setDoiTra(doiTra);
        }
        lichSuDoiTra.setTrangThai(dto.getTrangThai());
        lichSuDoiTra.setMoTa(dto.getMoTa());
        lichSuDoiTra.setNgayCapNhat(dto.getNgayCapNhat() != null ? dto.getNgayCapNhat() : LocalDateTime.now());
        lichSuDoiTra.setGhiChu(dto.getGhiChu());
        lichSuDoiTra.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiCapNhatId() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(dto.getNguoiCapNhatId())
                    .orElseThrow(() -> new RuntimeException("Người cập nhật không tồn tại"));
            lichSuDoiTra.setNguoiCapNhat(nguoiCapNhat);
        }
    }
}