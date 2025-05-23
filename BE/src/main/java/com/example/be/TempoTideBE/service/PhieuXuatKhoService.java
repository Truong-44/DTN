package com.example.be.TempoTideBE.service;

import com.example.be.dto.PhieuXuatKhoDTO;
import com.example.be.entity.DonHang;
import com.example.be.entity.NhanVien;
import com.example.be.entity.PhieuXuatKho;
import com.example.be.repository.DonHangRepository;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.PhieuXuatKhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhieuXuatKhoService {

    private final PhieuXuatKhoRepository phieuXuatKhoRepository;
    private final DonHangRepository donHangRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<PhieuXuatKhoDTO> getAllPhieuXuatKho() {
        return phieuXuatKhoRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PhieuXuatKhoDTO getPhieuXuatKhoById(Integer id) {
        PhieuXuatKho phieuXuatKho = phieuXuatKhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phiếu xuất kho không tồn tại"));
        return convertToDTO(phieuXuatKho);
    }

    public PhieuXuatKhoDTO createPhieuXuatKho(PhieuXuatKhoDTO dto) {
        PhieuXuatKho phieuXuatKho = new PhieuXuatKho();
        mapToEntity(dto, phieuXuatKho);
        PhieuXuatKho savedPhieuXuatKho = phieuXuatKhoRepository.save(phieuXuatKho);
        return convertToDTO(savedPhieuXuatKho);
    }

    public PhieuXuatKhoDTO updatePhieuXuatKho(Integer id, PhieuXuatKhoDTO dto) {
        PhieuXuatKho phieuXuatKho = phieuXuatKhoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phiếu xuất kho không tồn tại"));
        mapToEntity(dto, phieuXuatKho);
        PhieuXuatKho updatedPhieuXuatKho = phieuXuatKhoRepository.save(phieuXuatKho);
        return convertToDTO(updatedPhieuXuatKho);
    }

    public void deletePhieuXuatKho(Integer id) {
        phieuXuatKhoRepository.deleteById(id);
    }

    private PhieuXuatKhoDTO convertToDTO(PhieuXuatKho phieuXuatKho) {
        PhieuXuatKhoDTO dto = new PhieuXuatKhoDTO();
        dto.setMaPhieuXuatKho(phieuXuatKho.getMaPhieuXuatKho());
        if (phieuXuatKho.getDonHang() != null) {
            dto.setMaDonHang(phieuXuatKho.getDonHang().getMaDonHang());
        }
        dto.setNgayXuatKho(phieuXuatKho.getNgayXuatKho());
        dto.setTongGiaTri(phieuXuatKho.getTongGiaTri());
        dto.setGhiChu(phieuXuatKho.getGhiChu());
        dto.setNgayTao(phieuXuatKho.getNgayTao());
        dto.setTrangThai(phieuXuatKho.getTrangThai());
        if (phieuXuatKho.getNguoiTao() != null) {
            dto.setNguoiTaoId(phieuXuatKho.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(PhieuXuatKhoDTO dto, PhieuXuatKho phieuXuatKho) {
        if (dto.getMaDonHang() != null) {
            DonHang donHang = donHangRepository.findById(dto.getMaDonHang())
                    .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
            phieuXuatKho.setDonHang(donHang);
        }
        phieuXuatKho.setNgayXuatKho(dto.getNgayXuatKho() != null ? dto.getNgayXuatKho() : LocalDateTime.now());
        phieuXuatKho.setTongGiaTri(dto.getTongGiaTri());
        phieuXuatKho.setGhiChu(dto.getGhiChu());
        phieuXuatKho.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        phieuXuatKho.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            phieuXuatKho.setNguoiTao(nguoiTao);
        }
    }
}