package com.example.be.TempoTideBE.service;

import com.example.be.dto.PhienDangNhapDTO;
import com.example.be.entity.KhachHang;
import com.example.be.entity.NhanVien;
import com.example.be.entity.PhienDangNhap;
import com.example.be.repository.KhachHangRepository;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.PhienDangNhapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhienDangNhapService {

    private final PhienDangNhapRepository phienDangNhapRepository;
    private final NhanVienRepository nhanVienRepository;
    private final KhachHangRepository khachHangRepository;

    public List<PhienDangNhapDTO> getAllPhienDangNhap() {
        return phienDangNhapRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PhienDangNhapDTO getPhienDangNhapById(Integer id) {
        PhienDangNhap phienDangNhap = phienDangNhapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phiên đăng nhập không tồn tại"));
        return convertToDTO(phienDangNhap);
    }

    public PhienDangNhapDTO createPhienDangNhap(PhienDangNhapDTO dto) {
        PhienDangNhap phienDangNhap = new PhienDangNhap();
        mapToEntity(dto, phienDangNhap);
        PhienDangNhap savedPhienDangNhap = phienDangNhapRepository.save(phienDangNhap);
        return convertToDTO(savedPhienDangNhap);
    }

    public PhienDangNhapDTO updatePhienDangNhap(Integer id, PhienDangNhapDTO dto) {
        PhienDangNhap phienDangNhap = phienDangNhapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phiên đăng nhập không tồn tại"));
        mapToEntity(dto, phienDangNhap);
        PhienDangNhap updatedPhienDangNhap = phienDangNhapRepository.save(phienDangNhap);
        return convertToDTO(updatedPhienDangNhap);
    }

    public void deletePhienDangNhap(Integer id) {
        phienDangNhapRepository.deleteById(id);
    }

    private PhienDangNhapDTO convertToDTO(PhienDangNhap phienDangNhap) {
        PhienDangNhapDTO dto = new PhienDangNhapDTO();
        dto.setMaPhien(phienDangNhap.getMaPhien());
        if (phienDangNhap.getNhanVien() != null) {
            dto.setMaNhanVien(phienDangNhap.getNhanVien().getMaNhanVien());
        }
        if (phienDangNhap.getKhachHang() != null) {
            dto.setMaKhachHang(phienDangNhap.getKhachHang().getMaKhachHang());
        }
        dto.setToken(phienDangNhap.getToken());
        dto.setNgayDangNhap(phienDangNhap.getNgayDangNhap());
        dto.setNgayHetHan(phienDangNhap.getNgayHetHan());
        dto.setTrangThai(phienDangNhap.getTrangThai());
        if (phienDangNhap.getNguoiTao() != null) {
            dto.setNguoiTaoId(phienDangNhap.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(PhienDangNhapDTO dto, PhienDangNhap phienDangNhap) {
        if (dto.getMaNhanVien() != null) {
            NhanVien nhanVien = nhanVienRepository.findById(dto.getMaNhanVien())
                    .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));
            phienDangNhap.setNhanVien(nhanVien);
        }
        if (dto.getMaKhachHang() != null) {
            KhachHang khachHang = khachHangRepository.findById(dto.getMaKhachHang())
                    .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
            phienDangNhap.setKhachHang(khachHang);
        }
        phienDangNhap.setToken(dto.getToken());
        phienDangNhap.setNgayDangNhap(dto.getNgayDangNhap() != null ? dto.getNgayDangNhap() : LocalDateTime.now());
        phienDangNhap.setNgayHetHan(dto.getNgayHetHan());
        phienDangNhap.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            phienDangNhap.setNguoiTao(nguoiTao);
        }
    }
}