package com.example.be.TempoTideBE.service;

import com.example.be.dto.ThongBaoDTO;
import com.example.be.entity.NhanVien;
import com.example.be.entity.ThongBao;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.ThongBaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThongBaoService {

    private final ThongBaoRepository thongBaoRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<ThongBaoDTO> getAllThongBao() {
        return thongBaoRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ThongBaoDTO getThongBaoById(Integer id) {
        ThongBao thongBao = thongBaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thông báo không tồn tại"));
        return convertToDTO(thongBao);
    }

    public ThongBaoDTO createThongBao(ThongBaoDTO dto) {
        ThongBao thongBao = new ThongBao();
        mapToEntity(dto, thongBao);
        ThongBao savedThongBao = thongBaoRepository.save(thongBao);
        return convertToDTO(savedThongBao);
    }

    public ThongBaoDTO updateThongBao(Integer id, ThongBaoDTO dto) {
        ThongBao thongBao = thongBaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thông báo không tồn tại"));
        mapToEntity(dto, thongBao);
        ThongBao updatedThongBao = thongBaoRepository.save(thongBao);
        return convertToDTO(updatedThongBao);
    }

    public void deleteThongBao(Integer id) {
        thongBaoRepository.deleteById(id);
    }

    private ThongBaoDTO convertToDTO(ThongBao thongBao) {
        ThongBaoDTO dto = new ThongBaoDTO();
        dto.setMaThongBao(thongBao.getMaThongBao());
        if (thongBao.getNhanVien() != null) {
            dto.setMaNhanVien(thongBao.getNhanVien().getMaNhanVien());
        }
        dto.setTieuDe(thongBao.getTieuDe());
        dto.setNoiDung(thongBao.getNoiDung());
        dto.setNgayTao(thongBao.getNgayTao());
        dto.setTrangThai(thongBao.getTrangThai());
        return dto;
    }

    private void mapToEntity(ThongBaoDTO dto, ThongBao thongBao) {
        if (dto.getMaNhanVien() != null) {
            NhanVien nhanVien = nhanVienRepository.findById(dto.getMaNhanVien())
                    .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));
            thongBao.setNhanVien(nhanVien);
        }
        thongBao.setTieuDe(dto.getTieuDe());
        thongBao.setNoiDung(dto.getNoiDung());
        thongBao.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        thongBao.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
    }
}