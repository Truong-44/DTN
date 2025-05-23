package com.example.be.TempoTideBE.service;

import com.example.be.dto.ChiTietDonHangDTO;
import com.example.be.entity.ChiTietDonHang;
import com.example.be.entity.ChiTietSanPham;
import com.example.be.entity.DonHang;
import com.example.be.entity.NhanVien;
import com.example.be.repository.ChiTietDonHangRepository;
import com.example.be.repository.ChiTietSanPhamRepository;
import com.example.be.repository.DonHangRepository;
import com.example.be.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChiTietDonHangService {

    private final ChiTietDonHangRepository chiTietDonHangRepository;
    private final DonHangRepository donHangRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<ChiTietDonHangDTO> getAllChiTietDonHang() {
        return chiTietDonHangRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ChiTietDonHangDTO getChiTietDonHangById(Integer id) {
        ChiTietDonHang chiTietDonHang = chiTietDonHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chi tiết đơn hàng không tồn tại"));
        return convertToDTO(chiTietDonHang);
    }

    public ChiTietDonHangDTO createChiTietDonHang(ChiTietDonHangDTO dto) {
        ChiTietDonHang chiTietDonHang = new ChiTietDonHang();
        mapToEntity(dto, chiTietDonHang);
        ChiTietDonHang savedChiTietDonHang = chiTietDonHangRepository.save(chiTietDonHang);
        return convertToDTO(savedChiTietDonHang);
    }

    public ChiTietDonHangDTO updateChiTietDonHang(Integer id, ChiTietDonHangDTO dto) {
        ChiTietDonHang chiTietDonHang = chiTietDonHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chi tiết đơn hàng không tồn tại"));
        mapToEntity(dto, chiTietDonHang);
        ChiTietDonHang updatedChiTietDonHang = chiTietDonHangRepository.save(chiTietDonHang);
        return convertToDTO(updatedChiTietDonHang);
    }

    public void deleteChiTietDonHang(Integer id) {
        chiTietDonHangRepository.deleteById(id);
    }

    private ChiTietDonHangDTO convertToDTO(ChiTietDonHang chiTietDonHang) {
        ChiTietDonHangDTO dto = new ChiTietDonHangDTO();
        dto.setMaChiTietDonHang(chiTietDonHang.getMaChiTietDonHang());
        if (chiTietDonHang.getDonHang() != null) {
            dto.setMaDonHang(chiTietDonHang.getDonHang().getMaDonHang());
        }
        if (chiTietDonHang.getChiTietSanPham() != null) {
            dto.setMaChiTietSanPham(chiTietDonHang.getChiTietSanPham().getMaChiTietSanPham());
        }
        dto.setSoLuong(chiTietDonHang.getSoLuong());
        dto.setDonGia(chiTietDonHang.getDonGia());
        dto.setGiamGia(chiTietDonHang.getGiamGia());
        dto.setLaDatTruoc(chiTietDonHang.getLaDatTruoc());
        dto.setNgayTao(chiTietDonHang.getNgayTao());
        dto.setNgayCapNhat(chiTietDonHang.getNgayCapNhat());
        dto.setTrangThai(chiTietDonHang.getTrangThai());
        if (chiTietDonHang.getNguoiTao() != null) {
            dto.setNguoiTaoId(chiTietDonHang.getNguoiTao().getMaNhanVien());
        }
        if (chiTietDonHang.getNguoiCapNhat() != null) {
            dto.setNguoiCapNhatId(chiTietDonHang.getNguoiCapNhat().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(ChiTietDonHangDTO dto, ChiTietDonHang chiTietDonHang) {
        if (dto.getMaDonHang() != null) {
            DonHang donHang = donHangRepository.findById(dto.getMaDonHang())
                    .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
            chiTietDonHang.setDonHang(donHang);
        }
        if (dto.getMaChiTietSanPham() != null) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(dto.getMaChiTietSanPham())
                    .orElseThrow(() -> new RuntimeException("Chi tiết sản phẩm không tồn tại"));
            chiTietDonHang.setChiTietSanPham(chiTietSanPham);
        }
        chiTietDonHang.setSoLuong(dto.getSoLuong());
        chiTietDonHang.setDonGia(dto.getDonGia());
        chiTietDonHang.setGiamGia(dto.getGiamGia() != null ? dto.getGiamGia() : BigDecimal.ZERO);
        chiTietDonHang.setLaDatTruoc(dto.getLaDatTruoc() != null ? dto.getLaDatTruoc() : false);
        chiTietDonHang.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        chiTietDonHang.setNgayCapNhat(dto.getNgayCapNhat() != null ? dto.getNgayCapNhat() : LocalDateTime.now());
        chiTietDonHang.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            chiTietDonHang.setNguoiTao(nguoiTao);
        }
        if (dto.getNguoiCapNhatId() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(dto.getNguoiCapNhatId())
                    .orElseThrow(() -> new RuntimeException("Người cập nhật không tồn tại"));
            chiTietDonHang.setNguoiCapNhat(nguoiCapNhat);
        }
    }
}