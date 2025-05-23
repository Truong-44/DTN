package com.example.be.TempoTideBE.service;

import com.example.be.dto.PhuongThucVanChuyenDTO;
import com.example.be.entity.NhanVien;
import com.example.be.entity.PhuongThucVanChuyen;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.PhuongThucVanChuyenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhuongThucVanChuyenService {

    private final PhuongThucVanChuyenRepository phuongThucVanChuyenRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<PhuongThucVanChuyenDTO> getAllPhuongThucVanChuyen() {
        return phuongThucVanChuyenRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PhuongThucVanChuyenDTO getPhuongThucVanChuyenById(Integer id) {
        PhuongThucVanChuyen phuongThucVanChuyen = phuongThucVanChuyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phương thức vận chuyển không tồn tại"));
        return convertToDTO(phuongThucVanChuyen);
    }

    public PhuongThucVanChuyenDTO createPhuongThucVanChuyen(PhuongThucVanChuyenDTO dto) {
        PhuongThucVanChuyen phuongThucVanChuyen = new PhuongThucVanChuyen();
        mapToEntity(dto, phuongThucVanChuyen);
        PhuongThucVanChuyen savedPhuongThucVanChuyen = phuongThucVanChuyenRepository.save(phuongThucVanChuyen);
        return convertToDTO(savedPhuongThucVanChuyen);
    }

    public PhuongThucVanChuyenDTO updatePhuongThucVanChuyen(Integer id, PhuongThucVanChuyenDTO dto) {
        PhuongThucVanChuyen phuongThucVanChuyen = phuongThucVanChuyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phương thức vận chuyển không tồn tại"));
        mapToEntity(dto, phuongThucVanChuyen);
        PhuongThucVanChuyen updatedPhuongThucVanChuyen = phuongThucVanChuyenRepository.save(phuongThucVanChuyen);
        return convertToDTO(updatedPhuongThucVanChuyen);
    }

    public void deletePhuongThucVanChuyen(Integer id) {
        phuongThucVanChuyenRepository.deleteById(id);
    }

    private PhuongThucVanChuyenDTO convertToDTO(PhuongThucVanChuyen phuongThucVanChuyen) {
        PhuongThucVanChuyenDTO dto = new PhuongThucVanChuyenDTO();
        dto.setMaPhuongThucVanChuyen(phuongThucVanChuyen.getMaPhuongThucVanChuyen());
        dto.setTenPhuongThuc(phuongThucVanChuyen.getTenPhuongThuc());
        dto.setChiPhi(phuongThucVanChuyen.getChiPhi());
        dto.setThoiGianDuKien(phuongThucVanChuyen.getThoiGianDuKien());
        dto.setThoiGianToiDa(phuongThucVanChuyen.getThoiGianToiDa());
        dto.setMoTa(phuongThucVanChuyen.getMoTa());
        dto.setNgayTao(phuongThucVanChuyen.getNgayTao());
        dto.setTrangThai(phuongThucVanChuyen.getTrangThai());
        if (phuongThucVanChuyen.getNguoiTao() != null) {
            dto.setNguoiTaoId(phuongThucVanChuyen.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(PhuongThucVanChuyenDTO dto, PhuongThucVanChuyen phuongThucVanChuyen) {
        phuongThucVanChuyen.setTenPhuongThuc(dto.getTenPhuongThuc());
        phuongThucVanChuyen.setChiPhi(dto.getChiPhi());
        phuongThucVanChuyen.setThoiGianDuKien(dto.getThoiGianDuKien());
        phuongThucVanChuyen.setThoiGianToiDa(dto.getThoiGianToiDa());
        phuongThucVanChuyen.setMoTa(dto.getMoTa());
        phuongThucVanChuyen.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        phuongThucVanChuyen.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            phuongThucVanChuyen.setNguoiTao(nguoiTao);
        }
    }
}