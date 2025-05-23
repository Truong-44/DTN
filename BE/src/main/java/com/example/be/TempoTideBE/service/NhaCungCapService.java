package com.example.be.TempoTideBE.service;

import com.example.be.dto.NhaCungCapDTO;
import com.example.be.entity.NhaCungCap;
import com.example.be.entity.NhanVien;
import com.example.be.repository.NhaCungCapRepository;
import com.example.be.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NhaCungCapService {

    private final NhaCungCapRepository nhaCungCapRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<NhaCungCapDTO> getAllNhaCungCap() {
        return nhaCungCapRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public NhaCungCapDTO getNhaCungCapById(Integer id) {
        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nhà cung cấp không tồn tại"));
        return convertToDTO(nhaCungCap);
    }

    public NhaCungCapDTO createNhaCungCap(NhaCungCapDTO dto) {
        NhaCungCap nhaCungCap = new NhaCungCap();
        mapToEntity(dto, nhaCungCap);
        NhaCungCap savedNhaCungCap = nhaCungCapRepository.save(nhaCungCap);
        return convertToDTO(savedNhaCungCap);
    }

    public NhaCungCapDTO updateNhaCungCap(Integer id, NhaCungCapDTO dto) {
        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nhà cung cấp không tồn tại"));
        mapToEntity(dto, nhaCungCap);
        NhaCungCap updatedNhaCungCap = nhaCungCapRepository.save(nhaCungCap);
        return convertToDTO(updatedNhaCungCap);
    }

    public void deleteNhaCungCap(Integer id) {
        nhaCungCapRepository.deleteById(id);
    }

    private NhaCungCapDTO convertToDTO(NhaCungCap nhaCungCap) {
        NhaCungCapDTO dto = new NhaCungCapDTO();
        dto.setMaNhaCungCap(nhaCungCap.getMaNhaCungCap());
        dto.setTenNhaCungCap(nhaCungCap.getTenNhaCungCap());
        dto.setNguoiLienHe(nhaCungCap.getNguoiLienHe());
        dto.setSoDienThoai(nhaCungCap.getSoDienThoai());
        dto.setEmail(nhaCungCap.getEmail());
        dto.setDiaChi(nhaCungCap.getDiaChi());
        dto.setMaTaiKhoanNganHang(nhaCungCap.getMaTaiKhoanNganHang());
        dto.setTrangWeb(nhaCungCap.getTrangWeb());
        dto.setNgayTao(nhaCungCap.getNgayTao());
        dto.setTrangThai(nhaCungCap.getTrangThai());
        if (nhaCungCap.getNguoiTao() != null) {
            dto.setNguoiTaoId(nhaCungCap.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(NhaCungCapDTO dto, NhaCungCap nhaCungCap) {
        nhaCungCap.setTenNhaCungCap(dto.getTenNhaCungCap());
        nhaCungCap.setNguoiLienHe(dto.getNguoiLienHe());
        nhaCungCap.setSoDienThoai(dto.getSoDienThoai());
        nhaCungCap.setEmail(dto.getEmail());
        nhaCungCap.setDiaChi(dto.getDiaChi());
        nhaCungCap.setMaTaiKhoanNganHang(dto.getMaTaiKhoanNganHang());
        nhaCungCap.setTrangWeb(dto.getTrangWeb());
        nhaCungCap.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        nhaCungCap.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            nhaCungCap.setNguoiTao(nguoiTao);
        }
    }
}