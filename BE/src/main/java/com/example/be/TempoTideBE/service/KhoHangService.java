package com.example.be.TempoTideBE.service;


import com.example.be.TempoTideBE.dto.KhoHangDTO;
import com.example.be.TempoTideBE.entity.ChiTietSanPham;
import com.example.be.TempoTideBE.entity.KhoHang;
import com.example.be.TempoTideBE.entity.NhanVien;
import com.example.be.TempoTideBE.repository.ChiTietSanPhamRepository;
import com.example.be.TempoTideBE.repository.KhoHangRepository;
import com.example.be.TempoTideBE.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KhoHangService {

    private final KhoHangRepository khoHangRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<KhoHangDTO> getAllKhoHang() {
        return khoHangRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public KhoHangDTO getKhoHangById(Integer id) {
        KhoHang khoHang = khoHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kho hàng không tồn tại"));
        return convertToDTO(khoHang);
    }

    public KhoHangDTO createKhoHang(KhoHangDTO dto) {
        KhoHang khoHang = new KhoHang();
        mapToEntity(dto, khoHang);
        KhoHang savedKhoHang = khoHangRepository.save(khoHang);
        return convertToDTO(savedKhoHang);
    }

    public KhoHangDTO updateKhoHang(Integer id, KhoHangDTO dto) {
        KhoHang khoHang = khoHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kho hàng không tồn tại"));
        mapToEntity(dto, khoHang);
        KhoHang updatedKhoHang = khoHangRepository.save(khoHang);
        return convertToDTO(updatedKhoHang);
    }

    public void deleteKhoHang(Integer id) {
        khoHangRepository.deleteById(id);
    }

    private KhoHangDTO convertToDTO(KhoHang khoHang) {
        KhoHangDTO dto = new KhoHangDTO();
        dto.setMaKhoHang(khoHang.getMaKhoHang());
        if (khoHang.getChiTietSanPham() != null) {
            dto.setMaChiTietSanPham(khoHang.getChiTietSanPham().getMaChiTietSanPham());
        }
        dto.setSoLuong(khoHang.getSoLuong());
        dto.setViTriKho(khoHang.getViTriKho());
        dto.setNgayNhapKho(khoHang.getNgayNhapKho());
        dto.setNgayXuatKho(khoHang.getNgayXuatKho());
        dto.setNgayCapNhat(khoHang.getNgayCapNhat());
        dto.setTrangThai(khoHang.getTrangThai());
        if (khoHang.getNguoiCapNhat() != null) {
            dto.setNguoiCapNhatId(khoHang.getNguoiCapNhat().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(KhoHangDTO dto, KhoHang khoHang) {
        if (dto.getMaChiTietSanPham() != null) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(dto.getMaChiTietSanPham())
                    .orElseThrow(() -> new RuntimeException("Chi tiết sản phẩm không tồn tại"));
            khoHang.setChiTietSanPham(chiTietSanPham);
        }
        khoHang.setSoLuong(dto.getSoLuong());
        khoHang.setViTriKho(dto.getViTriKho());
        khoHang.setNgayNhapKho(dto.getNgayNhapKho());
        khoHang.setNgayXuatKho(dto.getNgayXuatKho());
        khoHang.setNgayCapNhat(dto.getNgayCapNhat() != null ? dto.getNgayCapNhat() : LocalDateTime.now());
        khoHang.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiCapNhatId() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(dto.getNguoiCapNhatId())
                    .orElseThrow(() -> new RuntimeException("Người cập nhật không tồn tại"));
            khoHang.setNguoiCapNhat(nguoiCapNhat);
        }
    }
}