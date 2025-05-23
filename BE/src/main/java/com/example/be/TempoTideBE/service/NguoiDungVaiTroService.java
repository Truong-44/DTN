package com.example.be.TempoTideBE.service;

import com.example.be.dto.NguoiDungVaiTroDTO;
import com.example.be.entity.NguoiDungVaiTro;
import com.example.be.entity.NhanVien;
import com.example.be.entity.VaiTro;
import com.example.be.repository.NguoiDungVaiTroRepository;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.VaiTroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NguoiDungVaiTroService {

    private final NguoiDungVaiTroRepository nguoiDungVaiTroRepository;
    private final NhanVienRepository nhanVienRepository;
    private final VaiTroRepository vaiTroRepository;

    public List<NguoiDungVaiTroDTO> getAllNguoiDungVaiTro() {
        return nguoiDungVaiTroRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public NguoiDungVaiTroDTO getNguoiDungVaiTroById(Integer id) {
        NguoiDungVaiTro nguoiDungVaiTro = nguoiDungVaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Người dùng - Vai trò không tồn tại"));
        return convertToDTO(nguoiDungVaiTro);
    }

    public NguoiDungVaiTroDTO createNguoiDungVaiTro(NguoiDungVaiTroDTO dto) {
        NguoiDungVaiTro nguoiDungVaiTro = new NguoiDungVaiTro();
        mapToEntity(dto, nguoiDungVaiTro);
        NguoiDungVaiTro savedNguoiDungVaiTro = nguoiDungVaiTroRepository.save(nguoiDungVaiTro);
        return convertToDTO(savedNguoiDungVaiTro);
    }

    public NguoiDungVaiTroDTO updateNguoiDungVaiTro(Integer id, NguoiDungVaiTroDTO dto) {
        NguoiDungVaiTro nguoiDungVaiTro = nguoiDungVaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Người dùng - Vai trò không tồn tại"));
        mapToEntity(dto, nguoiDungVaiTro);
        NguoiDungVaiTro updatedNguoiDungVaiTro = nguoiDungVaiTroRepository.save(nguoiDungVaiTro);
        return convertToDTO(updatedNguoiDungVaiTro);
    }

    public void deleteNguoiDungVaiTro(Integer id) {
        nguoiDungVaiTroRepository.deleteById(id);
    }

    private NguoiDungVaiTroDTO convertToDTO(NguoiDungVaiTro nguoiDungVaiTro) {
        NguoiDungVaiTroDTO dto = new NguoiDungVaiTroDTO();
        dto.setMaNguoiDungVaiTro(nguoiDungVaiTro.getMaNguoiDungVaiTro());
        if (nguoiDungVaiTro.getNhanVien() != null) {
            dto.setMaNhanVien(nguoiDungVaiTro.getNhanVien().getMaNhanVien());
        }
        if (nguoiDungVaiTro.getVaiTro() != null) {
            dto.setMaVaiTro(nguoiDungVaiTro.getVaiTro().getMaVaiTro());
        }
        dto.setNgayTao(nguoiDungVaiTro.getNgayTao());
        dto.setTrangThai(nguoiDungVaiTro.getTrangThai());
        if (nguoiDungVaiTro.getNguoiTao() != null) {
            dto.setNguoiTaoId(nguoiDungVaiTro.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(NguoiDungVaiTroDTO dto, NguoiDungVaiTro nguoiDungVaiTro) {
        if (dto.getMaNhanVien() != null) {
            NhanVien nhanVien = nhanVienRepository.findById(dto.getMaNhanVien())
                    .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));
            nguoiDungVaiTro.setNhanVien(nhanVien);
        }
        if (dto.getMaVaiTro() != null) {
            VaiTro vaiTro = vaiTroRepository.findById(dto.getMaVaiTro())
                    .orElseThrow(() -> new RuntimeException("Vai trò không tồn tại"));
            nguoiDungVaiTro.setVaiTro(vaiTro);
        }
        nguoiDungVaiTro.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        nguoiDungVaiTro.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            nguoiDungVaiTro.setNguoiTao(nguoiTao);
        }
    }
}