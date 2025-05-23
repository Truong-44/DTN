package com.example.be.TempoTideBE.service;


import com.example.be.TempoTideBE.dto.BaoHanhDTO;
import com.example.be.TempoTideBE.entity.BaoHanh;
import com.example.be.TempoTideBE.repository.BaoHanhRepository;
import com.example.be.TempoTideBE.repository.DonHangRepository;
import com.example.be.TempoTideBE.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaoHanhService {

    private final BaoHanhRepository baoHanhRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final DonHangRepository donHangRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<BaoHanhDTO> getAllBaoHanh() {
        return baoHanhRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public BaoHanhDTO getBaoHanhById(Integer id) {
        BaoHanh baoHanh = baoHanhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bảo hành không tồn tại"));
        return convertToDTO(baoHanh);
    }

    public BaoHanhDTO createBaoHanh(BaoHanhDTO dto) {
        BaoHanh baoHanh = new BaoHanh();
        mapToEntity(dto, baoHanh);
        BaoHanh savedBaoHanh = baoHanhRepository.save(baoHanh);
        return convertToDTO(savedBaoHanh);
    }

    public BaoHanhDTO updateBaoHanh(Integer id, BaoHanhDTO dto) {
        BaoHanh baoHanh = baoHanhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bảo hành không tồn tại"));
        mapToEntity(dto, baoHanh);
        BaoHanh updatedBaoHanh = baoHanhRepository.save(baoHanh);
        return convertToDTO(updatedBaoHanh);
    }

    public void deleteBaoHanh(Integer id) {
        baoHanhRepository.deleteById(id);
    }

    private BaoHanhDTO convertToDTO(BaoHanh baoHanh) {
        BaoHanhDTO dto = new BaoHanhDTO();
        dto.setMaBaoHanh(baoHanh.getMaBaoHanh());
        if (baoHanh.getChiTietSanPham() != null) {
            dto.setMaChiTietSanPham(baoHanh.getChiTietSanPham().getMaChiTietSanPham());
        }
        if (baoHanh.getDonHang() != null) {
            dto.setMaDonHang(baoHanh.getDonHang().getMaDonHang());
        }
        dto.setNgayBatDau(baoHanh.getNgayBatDau());
        dto.setNgayKetThuc(baoHanh.getNgayKetThuc());
        dto.setMoTa(baoHanh.getMoTa());
        dto.setDieuKienBaoHanh(baoHanh.getDieuKienBaoHanh());
        dto.setNgayTao(baoHanh.getNgayTao());
        dto.setTrangThai(baoHanh.getTrangThai());
        if (baoHanh.getNguoiTao() != null) {
            dto.setNguoiTaoId(baoHanh.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(BaoHanhDTO dto, BaoHanh baoHanh) {
        if (dto.getMaChiTietSanPham() != null) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(dto.getMaChiTietSanPham())
                    .orElseThrow(() -> new RuntimeException("Chi tiết sản phẩm không tồn tại"));
            baoHanh.setChiTietSanPham(chiTietSanPham);
        }
        if (dto.getMaDonHang() != null) {
            DonHang donHang = donHangRepository.findById(dto.getMaDonHang())
                    .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
            baoHanh.setDonHang(donHang);
        }
        baoHanh.setNgayBatDau(dto.getNgayBatDau());
        baoHanh.setNgayKetThuc(dto.getNgayKetThuc());
        baoHanh.setMoTa(dto.getMoTa());
        baoHanh.setDieuKienBaoHanh(dto.getDieuKienBaoHanh());
        baoHanh.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        baoHanh.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            baoHanh.setNguoiTao(nguoiTao);
        }
    }
}