package com.example.be.TempoTideBE.service;

import com.example.be.dto.PhieuNhapKhoDTO;
import com.example.be.entity.NhaCungCap;
import com.example.be.entity.NhanVien;
import com.example.be.entity.PhieuNhapKho;
import com.example.be.repository.NhaCungCapRepository;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.PhieuNhapKhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhieuNhapKhoService {

    private final PhieuNhapKhoRepository phieuNhapKhoRepository;
    private final NhaCungCapRepository nhaCungCapRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<PhieuNhapKhoDTO> getAllPhieuNhapKho() {
        return phieuNhapKhoRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PhieuNhapKhoDTO getPhieuNhapKhoById(Integer id) {
        PhieuNhapKho phieuNhapKho = phieuNhapKhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phiếu nhập kho không tồn tại"));
        return convertToDTO(phieuNhapKho);
    }

    public PhieuNhapKhoDTO createPhieuNhapKho(PhieuNhapKhoDTO dto) {
        PhieuNhapKho phieuNhapKho = new PhieuNhapKho();
        mapToEntity(dto, phieuNhapKho);
        PhieuNhapKho savedPhieuNhapKho = phieuNhapKhoRepository.save(phieuNhapKho);
        return convertToDTO(savedPhieuNhapKho);
    }

    public PhieuNhapKhoDTO updatePhieuNhapKho(Integer id, PhieuNhapKhoDTO dto) {
        PhieuNhapKho phieuNhapKho = phieuNhapKhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phiếu nhập kho không tồn tại"));
        mapToEntity(dto, phieuNhapKho);
        PhieuNhapKho updatedPhieuNhapKho = phieuNhapKhoRepository.save(phieuNhapKho);
        return convertToDTO(updatedPhieuNhapKho);
    }

    public void deletePhieuNhapKho(Integer id) {
        phieuNhapKhoRepository.deleteById(id);
    }

    private PhieuNhapKhoDTO convertToDTO(PhieuNhapKho phieuNhapKho) {
        PhieuNhapKhoDTO dto = new PhieuNhapKhoDTO();
        dto.setMaPhieuNhapKho(phieuNhapKho.getMaPhieuNhapKho());
        if (phieuNhapKho.getNhaCungCap() != null) {
            dto.setMaNhaCungCap(phieuNhapKho.getNhaCungCap().getMaNhaCungCap());
        }
        dto.setNgayNhapKho(phieuNhapKho.getNgayNhapKho());
        dto.setTongGiaTri(phieuNhapKho.getTongGiaTri());
        dto.setGhiChu(phieuNhapKho.getGhiChu());
        dto.setNgayTao(phieuNhapKho.getNgayTao());
        dto.setTrangThai(phieuNhapKho.getTrangThai());
        if (phieuNhapKho.getNguoiTao() != null) {
            dto.setNguoiTaoId(phieuNhapKho.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(PhieuNhapKhoDTO dto, PhieuNhapKho phieuNhapKho) {
        if (dto.getMaNhaCungCap() != null) {
            NhaCungCap nhaCungCap = nhaCungCapRepository.findById(dto.getMaNhaCungCap())
                    .orElseThrow(() -> new RuntimeException("Nhà cung cấp không tồn tại"));
            phieuNhapKho.setNhaCungCap(nhaCungCap);
        }
        phieuNhapKho.setNgayNhapKho(dto.getNgayNhapKho() != null ? dto.getNgayNhapKho() : LocalDateTime.now());
        phieuNhapKho.setTongGiaTri(dto.getTongGiaTri());
        phieuNhapKho.setGhiChu(dto.getGhiChu());
        phieuNhapKho.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        phieuNhapKho.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            phieuNhapKho.setNguoiTao(nguoiTao);
        }
    }
}