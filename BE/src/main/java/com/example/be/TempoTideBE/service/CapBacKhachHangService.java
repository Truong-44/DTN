package com.example.be.TempoTideBE.service;


import com.example.be.TempoTideBE.dto.CapBacKhachHangDTO;
import com.example.be.TempoTideBE.entity.CapBacKhachHang;
import com.example.be.TempoTideBE.entity.NhanVien;
import com.example.be.TempoTideBE.repository.CapBacKhachHangRepository;
import com.example.be.TempoTideBE.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CapBacKhachHangService {

    private final CapBacKhachHangRepository capBacKhachHangRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<CapBacKhachHangDTO> getAllCapBacKhachHang() {
        return capBacKhachHangRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CapBacKhachHangDTO getCapBacKhachHangById(Integer id) {
        CapBacKhachHang capBacKhachHang = capBacKhachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cấp bậc khách hàng không tồn tại"));
        return convertToDTO(capBacKhachHang);
    }

    public CapBacKhachHangDTO createCapBacKhachHang(CapBacKhachHangDTO dto) {
        CapBacKhachHang capBacKhachHang = new CapBacKhachHang();
        mapToEntity(dto, capBacKhachHang);
        CapBacKhachHang savedCapBacKhachHang = capBacKhachHangRepository.save(capBacKhachHang);
        return convertToDTO(savedCapBacKhachHang);
    }

    public CapBacKhachHangDTO updateCapBacKhachHang(Integer id, CapBacKhachHangDTO dto) {
        CapBacKhachHang capBacKhachHang = capBacKhachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cấp bậc khách hàng không tồn tại"));
        mapToEntity(dto, capBacKhachHang);
        CapBacKhachHang updatedCapBacKhachHang = capBacKhachHangRepository.save(capBacKhachHang);
        return convertToDTO(updatedCapBacKhachHang);
    }

    public void deleteCapBacKhachHang(Integer id) {
        capBacKhachHangRepository.deleteById(id);
    }

    private CapBacKhachHangDTO convertToDTO(CapBacKhachHang capBacKhachHang) {
        CapBacKhachHangDTO dto = new CapBacKhachHangDTO();
        dto.setMaCapBac(capBacKhachHang.getMaCapBac());
        dto.setTenCapBac(capBacKhachHang.getTenCapBac());
        dto.setDiemToiThieu(capBacKhachHang.getDiemToiThieu());
        dto.setGiamGiaMacDinh(capBacKhachHang.getGiamGiaMacDinh());
        dto.setNgayTao(capBacKhachHang.getNgayTao());
        dto.setTrangThai(capBacKhachHang.getTrangThai());
        if (capBacKhachHang.getNguoiTao() != null) {
            dto.setNguoiTaoId(capBacKhachHang.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(CapBacKhachHangDTO dto, CapBacKhachHang capBacKhachHang) {
        capBacKhachHang.setTenCapBac(dto.getTenCapBac());
        capBacKhachHang.setDiemToiThieu(dto.getDiemToiThieu());
        capBacKhachHang.setGiamGiaMacDinh(dto.getGiamGiaMacDinh());
        capBacKhachHang.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        capBacKhachHang.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            capBacKhachHang.setNguoiTao(nguoiTao);
        }
    }
}