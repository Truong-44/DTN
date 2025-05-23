package com.example.be.TempoTideBE.service;

import com.example.be.dto.LichSuThanhToanDTO;
import com.example.be.entity.LichSuThanhToan;
import com.example.be.entity.NhanVien;
import com.example.be.entity.ThanhToan;
import com.example.be.repository.LichSuThanhToanRepository;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.ThanhToanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LichSuThanhToanService {

    private final LichSuThanhToanRepository lichSuThanhToanRepository;
    private final ThanhToanRepository thanhToanRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<LichSuThanhToanDTO> getAllLichSuThanhToan() {
        return lichSuThanhToanRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public LichSuThanhToanDTO getLichSuThanhToanById(Integer id) {
        LichSuThanhToan lichSuThanhToan = lichSuThanhToanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lịch sử thanh toán không tồn tại"));
        return convertToDTO(lichSuThanhToan);
    }

    public LichSuThanhToanDTO createLichSuThanhToan(LichSuThanhToanDTO dto) {
        LichSuThanhToan lichSuThanhToan = new LichSuThanhToan();
        mapToEntity(dto, lichSuThanhToan);
        LichSuThanhToan savedLichSuThanhToan = lichSuThanhToanRepository.save(lichSuThanhToan);
        return convertToDTO(savedLichSuThanhToan);
    }

    public LichSuThanhToanDTO updateLichSuThanhToan(Integer id, LichSuThanhToanDTO dto) {
        LichSuThanhToan lichSuThanhToan = lichSuThanhToanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lịch sử thanh toán không tồn tại"));
        mapToEntity(dto, lichSuThanhToan);
        LichSuThanhToan updatedLichSuThanhToan = lichSuThanhToanRepository.save(lichSuThanhToan);
        return convertToDTO(updatedLichSuThanhToan);
    }

    public void deleteLichSuThanhToan(Integer id) {
        lichSuThanhToanRepository.deleteById(id);
    }

    private LichSuThanhToanDTO convertToDTO(LichSuThanhToan lichSuThanhToan) {
        LichSuThanhToanDTO dto = new LichSuThanhToanDTO();
        dto.setMaLichSu(lichSuThanhToan.getMaLichSu());
        if (lichSuThanhToan.getThanhToan() != null) {
            dto.setMaThanhToan(lichSuThanhToan.getThanhToan().getMaThanhToan());
        }
        dto.setTrangThai(lichSuThanhToan.getTrangThai());
        dto.setSoTien(lichSuThanhToan.getSoTien());
        dto.setNgayCapNhat(lichSuThanhToan.getNgayCapNhat());
        dto.setGhiChu(lichSuThanhToan.getGhiChu());
        dto.setTrangThai(lichSuThanhToan.getTrangThai());
        if (lichSuThanhToan.getNguoiCapNhat() != null) {
            dto.setNguoiCapNhatId(lichSuThanhToan.getNguoiCapNhat().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(LichSuThanhToanDTO dto, LichSuThanhToan lichSuThanhToan) {
        if (dto.getMaThanhToan() != null) {
            ThanhToan thanhToan = thanhToanRepository.findById(dto.getMaThanhToan())
                    .orElseThrow(() -> new RuntimeException("Thanh toán không tồn tại"));
            lichSuThanhToan.setThanhToan(thanhToan);
        }
        lichSuThanhToan.setTrangThai(dto.getTrangThai());
        lichSuThanhToan.setSoTien(dto.getSoTien());
        lichSuThanhToan.setNgayCapNhat(dto.getNgayCapNhat() != null ? dto.getNgayCapNhat() : LocalDateTime.now());
        lichSuThanhToan.setGhiChu(dto.getGhiChu());
        lichSuThanhToan.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiCapNhatId() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(dto.getNguoiCapNhatId())
                    .orElseThrow(() -> new RuntimeException("Người cập nhật không tồn tại"));
            lichSuThanhToan.setNguoiCapNhat(nguoiCapNhat);
        }
    }
}