package com.example.be.TempoTideBE.service;


import com.example.be.TempoTideBE.dto.ChiTietKhuyenMaiDTO;
import com.example.be.TempoTideBE.entity.ChiTietKhuyenMai;
import com.example.be.TempoTideBE.entity.KhuyenMai;
import com.example.be.TempoTideBE.entity.SanPham;
import com.example.be.TempoTideBE.repository.ChiTietKhuyenMaiRepository;
import com.example.be.TempoTideBE.repository.KhuyenMaiRepository;
import com.example.be.TempoTideBE.repository.SanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChiTietKhuyenMaiService {

    private final ChiTietKhuyenMaiRepository chiTietKhuyenMaiRepository;
    private final KhuyenMaiRepository khuyenMaiRepository;
    private final SanPhamRepository sanPhamRepository;

    public List<ChiTietKhuyenMaiDTO> getAllChiTietKhuyenMai() {
        return chiTietKhuyenMaiRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ChiTietKhuyenMaiDTO getChiTietKhuyenMaiById(Integer id) {
        ChiTietKhuyenMai chiTietKhuyenMai = chiTietKhuyenMaiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chi tiết khuyến mãi không tồn tại"));
        return convertToDTO(chiTietKhuyenMai);
    }

    public ChiTietKhuyenMaiDTO createChiTietKhuyenMai(ChiTietKhuyenMaiDTO dto) {
        ChiTietKhuyenMai chiTietKhuyenMai = new ChiTietKhuyenMai();
        mapToEntity(dto, chiTietKhuyenMai);
        ChiTietKhuyenMai savedChiTietKhuyenMai = chiTietKhuyenMaiRepository.save(chiTietKhuyenMai);
        return convertToDTO(savedChiTietKhuyenMai);
    }

    public ChiTietKhuyenMaiDTO updateChiTietKhuyenMai(Integer id, ChiTietKhuyenMaiDTO dto) {
        ChiTietKhuyenMai chiTietKhuyenMai = chiTietKhuyenMaiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chi tiết khuyến mãi không tồn tại"));
        mapToEntity(dto, chiTietKhuyenMai);
        ChiTietKhuyenMai updatedChiTietKhuyenMai = chiTietKhuyenMaiRepository.save(chiTietKhuyenMai);
        return convertToDTO(updatedChiTietKhuyenMai);
    }

    public void deleteChiTietKhuyenMai(Integer id) {
        chiTietKhuyenMaiRepository.deleteById(id);
    }

    private ChiTietKhuyenMaiDTO convertToDTO(ChiTietKhuyenMai chiTietKhuyenMai) {
        ChiTietKhuyenMaiDTO dto = new ChiTietKhuyenMaiDTO();
        dto.setMaChiTietKhuyenMai(chiTietKhuyenMai.getMaChiTietKhuyenMai());
        if (chiTietKhuyenMai.getKhuyenMai() != null) {
            dto.setMaKhuyenMai(chiTietKhuyenMai.getKhuyenMai().getMaKhuyenMai());
        }
        if (chiTietKhuyenMai.getSanPham() != null) {
            dto.setMaSanPham(chiTietKhuyenMai.getSanPham().getMaSanPham());
        }
        dto.setTrangThai(chiTietKhuyenMai.getTrangThai());
        return dto;
    }

    private void mapToEntity(ChiTietKhuyenMaiDTO dto, ChiTietKhuyenMai chiTietKhuyenMai) {
        if (dto.getMaKhuyenMai() != null) {
            KhuyenMai khuyenMai = khuyenMaiRepository.findById(dto.getMaKhuyenMai())
                    .orElseThrow(() -> new RuntimeException("Khuyến mãi không tồn tại"));
            chiTietKhuyenMai.setKhuyenMai(khuyenMai);
        }
        if (dto.getMaSanPham() != null) {
            SanPham sanPham = sanPhamRepository.findById(dto.getMaSanPham())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
            chiTietKhuyenMai.setSanPham(sanPham);
        }
        chiTietKhuyenMai.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
    }
}