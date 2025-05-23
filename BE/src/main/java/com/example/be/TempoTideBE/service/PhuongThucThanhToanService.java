package com.example.be.TempoTideBE.service;

import com.example.be.dto.PhuongThucThanhToanDTO;
import com.example.be.entity.NhanVien;
import com.example.be.entity.PhuongThucThanhToan;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.PhuongThucThanhToanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhuongThucThanhToanService {

    private final PhuongThucThanhToanRepository phuongThucThanhToanRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<PhuongThucThanhToanDTO> getAllPhuongThucThanhToan() {
        return phuongThucThanhToanRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PhuongThucThanhToanDTO getPhuongThucThanhToanById(Integer id) {
        PhuongThucThanhToan phuongThucThanhToan = phuongThucThanhToanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phương thức thanh toán không tồn tại"));
        return convertToDTO(phuongThucThanhToan);
    }

    public PhuongThucThanhToanDTO createPhuongThucThanhToan(PhuongThucThanhToanDTO dto) {
        PhuongThucThanhToan phuongThucThanhToan = new PhuongThucThanhToan();
        mapToEntity(dto, phuongThucThanhToan);
        PhuongThucThanhToan savedPhuongThucThanhToan = phuongThucThanhToanRepository.save(phuongThucThanhToan);
        return convertToDTO(savedPhuongThucThanhToan);
    }

    public PhuongThucThanhToanDTO updatePhuongThucThanhToan(Integer id, PhuongThucThanhToanDTO dto) {
        PhuongThucThanhToan phuongThucThanhToan = phuongThucThanhToanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phương thức thanh toán không tồn tại"));
        mapToEntity(dto, phuongThucThanhToan);
        PhuongThucThanhToan updatedPhuongThucThanhToan = phuongThucThanhToanRepository.save(phuongThucThanhToan);
        return convertToDTO(updatedPhuongThucThanhToan);
    }

    public void deletePhuongThucThanhToan(Integer id) {
        phuongThucThanhToanRepository.deleteById(id);
    }

    private PhuongThucThanhToanDTO convertToDTO(PhuongThucThanhToan phuongThucThanhToan) {
        PhuongThucThanhToanDTO dto = new PhuongThucThanhToanDTO();
        dto.setMaPhuongThucThanhToan(phuongThucThanhToan.getMaPhuongThucThanhToan());
        dto.setTenPhuongThuc(phuongThucThanhToan.getTenPhuongThuc());
        dto.setMoTa(phuongThucThanhToan.getMoTa());
        dto.setNgayTao(phuongThucThanhToan.getNgayTao());
        dto.setTrangThai(phuongThucThanhToan.getTrangThai());
        if (phuongThucThanhToan.getNguoiTao() != null) {
            dto.setNguoiTaoId(phuongThucThanhToan.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(PhuongThucThanhToanDTO dto, PhuongThucThanhToan phuongThucThanhToan) {
        phuongThucThanhToan.setTenPhuongThuc(dto.getTenPhuongThuc());
        phuongThucThanhToan.setMoTa(dto.getMoTa());
        phuongThucThanhToan.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        phuongThucThanhToan.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            phuongThucThanhToan.setNguoiTao(nguoiTao);
        }
    }
}