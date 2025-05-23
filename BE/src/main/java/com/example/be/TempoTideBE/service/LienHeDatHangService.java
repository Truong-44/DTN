package com.example.be.TempoTideBE.service;

import com.example.be.dto.LienHeDatHangDTO;
import com.example.be.entity.*;
import com.example.be.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LienHeDatHangService {

    private final LienHeDatHangRepository lienHeDatHangRepository;
    private final SanPhamRepository sanPhamRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final DonHangRepository donHangRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<LienHeDatHangDTO> getAllLienHeDatHang() {
        return lienHeDatHangRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public LienHeDatHangDTO getLienHeDatHangById(Integer id) {
        LienHeDatHang lienHeDatHang = lienHeDatHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Liên hệ đặt hàng không tồn tại"));
        return convertToDTO(lienHeDatHang);
    }

    public LienHeDatHangDTO createLienHeDatHang(LienHeDatHangDTO dto) {
        LienHeDatHang lienHeDatHang = new LienHeDatHang();
        mapToEntity(dto, lienHeDatHang);
        LienHeDatHang savedLienHeDatHang = lienHeDatHangRepository.save(lienHeDatHang);
        return convertToDTO(savedLienHeDatHang);
    }

    public LienHeDatHangDTO updateLienHeDatHang(Integer id, LienHeDatHangDTO dto) {
        LienHeDatHang lienHeDatHang = lienHeDatHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Liên hệ đặt hàng không tồn tại"));
        mapToEntity(dto, lienHeDatHang);
        LienHeDatHang updatedLienHeDatHang = lienHeDatHangRepository.save(lienHeDatHang);
        return convertToDTO(updatedLienHeDatHang);
    }

    public void deleteLienHeDatHang(Integer id) {
        lienHeDatHangRepository.deleteById(id);
    }

    private LienHeDatHangDTO convertToDTO(LienHeDatHang lienHeDatHang) {
        LienHeDatHangDTO dto = new LienHeDatHangDTO();
        dto.setMaLienHe(lienHeDatHang.getMaLienHe());
        dto.setHoTen(lienHeDatHang.getHoTen());
        dto.setSoDienThoai(lienHeDatHang.getSoDienThoai());
        dto.setEmail(lienHeDatHang.getEmail());
        if (lienHeDatHang.getSanPham() != null) {
            dto.setMaSanPham(lienHeDatHang.getSanPham().getMaSanPham());
        }
        if (lienHeDatHang.getChiTietSanPham() != null) {
            dto.setMaChiTietSanPham(lienHeDatHang.getChiTietSanPham().getMaChiTietSanPham());
        }
        dto.setSoLuong(lienHeDatHang.getSoLuong());
        dto.setGhiChu(lienHeDatHang.getGhiChu());
        if (lienHeDatHang.getDonHang() != null) {
            dto.setMaDonHang(lienHeDatHang.getDonHang().getMaDonHang());
        }
        dto.setNgayLienHe(lienHeDatHang.getNgayLienHe());
        dto.setTrangThai(lienHeDatHang.getTrangThai());
        if (lienHeDatHang.getNguoiTao() != null) {
            dto.setNguoiTaoId(lienHeDatHang.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(LienHeDatHangDTO dto, LienHeDatHang lienHeDatHang) {
        lienHeDatHang.setHoTen(dto.getHoTen());
        lienHeDatHang.setSoDienThoai(dto.getSoDienThoai());
        lienHeDatHang.setEmail(dto.getEmail());
        if (dto.getMaSanPham() != null) {
            SanPham sanPham = sanPhamRepository.findById(dto.getMaSanPham())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
            lienHeDatHang.setSanPham(sanPham);
        }
        if (dto.getMaChiTietSanPham() != null) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(dto.getMaChiTietSanPham())
                    .orElseThrow(() -> new RuntimeException("Chi tiết sản phẩm không tồn tại"));
            lienHeDatHang.setChiTietSanPham(chiTietSanPham);
        }
        lienHeDatHang.setSoLuong(dto.getSoLuong());
        lienHeDatHang.setGhiChu(dto.getGhiChu());
        if (dto.getMaDonHang()