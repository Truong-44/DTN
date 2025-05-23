package com.example.be.TempoTideBE.service;


import com.example.be.TempoTideBE.dto.DanhGiaDTO;
import com.example.be.TempoTideBE.entity.ChiTietSanPham;
import com.example.be.TempoTideBE.entity.DanhGia;
import com.example.be.TempoTideBE.entity.KhachHang;
import com.example.be.TempoTideBE.repository.ChiTietSanPhamRepository;
import com.example.be.TempoTideBE.repository.DanhGiaRepository;
import com.example.be.TempoTideBE.repository.KhachHangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DanhGiaService {

    private final DanhGiaRepository danhGiaRepository;
    private final KhachHangRepository khachHangRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;

    public List<DanhGiaDTO> getAllDanhGia() {
        return danhGiaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DanhGiaDTO getDanhGiaById(Integer id) {
        DanhGia danhGia = danhGiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đánh giá không tồn tại"));
        return convertToDTO(danhGia);
    }

    public DanhGiaDTO createDanhGia(DanhGiaDTO dto) {
        DanhGia danhGia = new DanhGia();
        mapToEntity(dto, danhGia);
        DanhGia savedDanhGia = danhGiaRepository.save(danhGia);
        return convertToDTO(savedDanhGia);
    }

    public DanhGiaDTO updateDanhGia(Integer id, DanhGiaDTO dto) {
        DanhGia danhGia = danhGiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đánh giá không tồn tại"));
        mapToEntity(dto, danhGia);
        DanhGia updatedDanhGia = danhGiaRepository.save(danhGia);
        return convertToDTO(updatedDanhGia);
    }

    public void deleteDanhGia(Integer id) {
        danhGiaRepository.deleteById(id);
    }

    private DanhGiaDTO convertToDTO(DanhGia danhGia) {
        DanhGiaDTO dto = new DanhGiaDTO();
        dto.setMaDanhGia(danhGia.getMaDanhGia());
        if (danhGia.getKhachHang() != null) {
            dto.setMaKhachHang(danhGia.getKhachHang().getMaKhachHang());
        }
        if (danhGia.getChiTietSanPham() != null) {
            dto.setMaChiTietSanPham(danhGia.getChiTietSanPham().getMaChiTietSanPham());
        }
        dto.setDanhDiem(danhGia.getDanhDiem());
        dto.setBinhLuan(danhGia.getBinhLuan());
        dto.setNgayDanhGia(danhGia.getNgayDanhGia());
        dto.setTrangThai(danhGia.getTrangThai());
        return dto;
    }

    private void mapToEntity(DanhGiaDTO dto, DanhGia danhGia) {
        if (dto.getMaKhachHang() != null) {
            KhachHang khachHang = khachHangRepository.findById(dto.getMaKhachHang())
                    .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
            danhGia.setKhachHang(khachHang);
        }
        if (dto.getMaChiTietSanPham() != null) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(dto.getMaChiTietSanPham())
                    .orElseThrow(() -> new RuntimeException("Chi tiết sản phẩm không tồn tại"));
            danhGia.setChiTietSanPham(chiTietSanPham);
        }
        danhGia.setDanhDiem(dto.getDanhDiem());
        danhGia.setBinhLuan(dto.getBinhLuan());
        danhGia.setNgayDanhGia(dto.getNgayDanhGia() != null ? dto.getNgayDanhGia() : LocalDateTime.now());
        danhGia.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
    }
}