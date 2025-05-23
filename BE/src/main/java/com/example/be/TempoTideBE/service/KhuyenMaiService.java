package com.example.be.TempoTideBE.service;


import com.example.be.TempoTideBE.dto.KhuyenMaiDTO;
import com.example.be.TempoTideBE.entity.KhuyenMai;
import com.example.be.TempoTideBE.repository.KhuyenMaiRepository;
import com.example.be.TempoTideBE.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KhuyenMaiService {

    private final KhuyenMaiRepository khuyenMaiRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<KhuyenMaiDTO> getAllKhuyenMai() {
        return khuyenMaiRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public KhuyenMaiDTO getKhuyenMaiById(Integer id) {
        KhuyenMai khuyenMai = khuyenMaiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khuyến mãi không tồn tại"));
        return convertToDTO(khuyenMai);
    }

    public KhuyenMaiDTO createKhuyenMai(KhuyenMaiDTO dto) {
        KhuyenMai khuyenMai = new KhuyenMai();
        mapToEntity(dto, khuyenMai);
        KhuyenMai savedKhuyenMai = khuyenMaiRepository.save(khuyenMai);
        return convertToDTO(savedKhuyenMai);
    }

    public KhuyenMaiDTO updateKhuyenMai(Integer id, KhuyenMaiDTO dto) {
        KhuyenMai khuyenMai = khuyenMaiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khuyến mãi không tồn tại"));
        mapToEntity(dto, khuyenMai);
        KhuyenMai updatedKhuyenMai = khuyenMaiRepository.save(khuyenMai);
        return convertToDTO(updatedKhuyenMai);
    }

    public void deleteKhuyenMai(Integer id) {
        khuyenMaiRepository.deleteById(id);
    }

    private KhuyenMaiDTO convertToDTO(KhuyenMai khuyenMai) {
        KhuyenMaiDTO dto = new KhuyenMaiDTO();
        dto.setMaKhuyenMai(khuyenMai.getMaKhuyenMai());
        dto.setTenKhuyenMai(khuyenMai.getTenKhuyenMai());
        dto.setMoTa(khuyenMai.getMoTa());
        dto.setPhanTramGiamGia(khuyenMai.getPhanTramGiamGia());
        dto.setDieuKienApDung(khuyenMai.getDieuKienApDung());
        dto.setSoLuongApDung(khuyenMai.getSoLuongApDung());
        dto.setApDungChoDatTruoc(khuyenMai.getApDungChoDatTruoc());
        dto.setNgayBatDau(khuyenMai.getNgayBatDau());
        dto.setNgayKetThuc(khuyenMai.getNgayKetThuc());
        dto.setNgayTao(khuyenMai.getNgayTao());
        dto.setTrangThai(khuyenMai.getTrangThai());
        if (khuyenMai.getNguoiTao() != null) {
            dto.setNguoiTaoId(khuyenMai.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(KhuyenMaiDTO dto, KhuyenMai khuyenMai) {
        khuyenMai.setTenKhuyenMai(dto.getTenKhuyenMai());
        khuyenMai.setMoTa(dto.getMoTa());
        khuyenMai.setPhanTramGiamGia(dto.getPhanTramGiamGia());
        khuyenMai.setDieuKienApDung(dto.getDieuKienApDung());
        khuyenMai.setSoLuongApDung(dto.getSoLuongApDung() != null ? dto.getSoLuongApDung() : 0);
        khuyenMai.setApDungChoDatTruoc(dto.getApDungChoDatTruoc() != null ? dto.getApDungChoDatTruoc() : false);
        khuyenMai.setNgayBatDau(dto.getNgayBatDau());
        khuyenMai.setNgayKetThuc(dto.getNgayKetThuc());
        khuyenMai.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        khuyenMai.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            khuyenMai.setNguoiTao(nguoiTao);
        }
    }
}