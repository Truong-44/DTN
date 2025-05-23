package com.example.be.TempoTideBE.service;

import com.example.be.dto.NhanVienDTO;
import com.example.be.entity.NhanVien;
import com.example.be.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NhanVienService {

    private final NhanVienRepository nhanVienRepository;

    // GET all
    public List<NhanVienDTO> getAllNhanVien() {
        return nhanVienRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // GET by ID
    public NhanVienDTO getNhanVienById(Integer id) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));
        return convertToDTO(nhanVien);
    }

    // POST
    public NhanVienDTO createNhanVien(NhanVienDTO nhanVienDTO) {
        NhanVien nhanVien = new NhanVien();
        mapToEntity(nhanVienDTO, nhanVien);
        NhanVien savedNhanVien = nhanVienRepository.save(nhanVien);
        return convertToDTO(savedNhanVien);
    }

    // PUT
    public NhanVienDTO updateNhanVien(Integer id, NhanVienDTO nhanVienDTO) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));
        mapToEntity(nhanVienDTO, nhanVien);
        NhanVien updatedNhanVien = nhanVienRepository.save(nhanVien);
        return convertToDTO(updatedNhanVien);
    }

    // DELETE
    public void deleteNhanVien(Integer id) {
        nhanVienRepository.deleteById(id);
    }

    // Helper: Convert Entity to DTO
    private NhanVienDTO convertToDTO(NhanVien nhanVien) {
        NhanVienDTO dto = new NhanVienDTO();
        dto.setMaNhanVien(nhanVien.getMaNhanVien());
        dto.setHo(nhanVien.getHo());
        dto.setTen(nhanVien.getTen());
        dto.setEmail(nhanVien.getEmail());
        dto.setSoDienThoai(nhanVien.getSoDienThoai());
        dto.setNgayTuyenDung(nhanVien.getNgayTuyenDung());
        dto.setMaSoThue(nhanVien.getMaSoThue());
        dto.setNgayNghiViec(nhanVien.getNgayNghiViec());
        dto.setMatKhau(nhanVien.getMatKhau());
        dto.setNgayTao(nhanVien.getNgayTao());
        dto.setNgayCapNhat(nhanVien.getNgayCapNhat());
        dto.setTrangThai(nhanVien.getTrangThai());
        if (nhanVien.getNguoiTao() != null) {
            dto.setNguoiTaoId(nhanVien.getNguoiTao().getMaNhanVien());
        }
        if (nhanVien.getNguoiCapNhat() != null) {
            dto.setNguoiCapNhatId(nhanVien.getNguoiCapNhat().getMaNhanVien());
        }
        return dto;
    }

    // Helper: Map DTO to Entity
    private void mapToEntity(NhanVienDTO dto, NhanVien nhanVien) {
        nhanVien.setHo(dto.getHo());
        nhanVien.setTen(dto.getTen());
        nhanVien.setEmail(dto.getEmail());
        nhanVien.setSoDienThoai(dto.getSoDienThoai());
        nhanVien.setNgayTuyenDung(dto.getNgayTuyenDung());
        nhanVien.setMaSoThue(dto.getMaSoThue());
        nhanVien.setNgayNghiViec(dto.getNgayNghiViec());
        nhanVien.setMatKhau(dto.getMatKhau());
        nhanVien.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        nhanVien.setNgayCapNhat(dto.getNgayCapNhat() != null ? dto.getNgayCapNhat() : LocalDateTime.now());
        nhanVien.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            nhanVien.setNguoiTao(nguoiTao);
        }
        if (dto.getNguoiCapNhatId() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(dto.getNguoiCapNhatId())
                    .orElseThrow(() -> new RuntimeException("Người cập nhật không tồn tại"));
            nhanVien.setNguoiCapNhat(nguoiCapNhat);
        }
    }
}