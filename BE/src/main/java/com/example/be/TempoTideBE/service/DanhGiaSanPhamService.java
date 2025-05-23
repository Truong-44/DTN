package com.example.be.TempoTideBE.service;


import com.example.be.TempoTideBE.dto.DanhGiaSanPhamDTO;
import com.example.be.TempoTideBE.entity.DanhGiaSanPham;
import com.example.be.TempoTideBE.entity.KhachHang;
import com.example.be.TempoTideBE.entity.NhanVien;
import com.example.be.TempoTideBE.entity.SanPham;
import com.example.be.TempoTideBE.repository.DanhGiaSanPhamRepository;
import com.example.be.TempoTideBE.repository.KhachHangRepository;
import com.example.be.TempoTideBE.repository.NhanVienRepository;
import com.example.be.TempoTideBE.repository.SanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DanhGiaSanPhamService {

    private final DanhGiaSanPhamRepository danhGiaSanPhamRepository;
    private final SanPhamRepository sanPhamRepository;
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<DanhGiaSanPhamDTO> getAllDanhGiaSanPham() {
        return danhGiaSanPhamRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DanhGiaSanPhamDTO getDanhGiaSanPhamById(Integer id) {
        DanhGiaSanPham danhGiaSanPham = danhGiaSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đánh giá sản phẩm không tồn tại"));
        return convertToDTO(danhGiaSanPham);
    }

    public DanhGiaSanPhamDTO createDanhGiaSanPham(DanhGiaSanPhamDTO dto) {
        DanhGiaSanPham danhGiaSanPham = new DanhGiaSanPham();
        mapToEntity(dto, danhGiaSanPham);
        DanhGiaSanPham savedDanhGiaSanPham = danhGiaSanPhamRepository.save(danhGiaSanPham);
        return convertToDTO(savedDanhGiaSanPham);
    }

    public DanhGiaSanPhamDTO updateDanhGiaSanPham(Integer id, DanhGiaSanPhamDTO dto) {
        DanhGiaSanPham danhGiaSanPham = danhGiaSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đánh giá sản phẩm không tồn tại"));
        mapToEntity(dto, danhGiaSanPham);
        DanhGiaSanPham updatedDanhGiaSanPham = danhGiaSanPhamRepository.save(danhGiaSanPham);
        return convertToDTO(updatedDanhGiaSanPham);
    }

    public void deleteDanhGiaSanPham(Integer id) {
        danhGiaSanPhamRepository.deleteById(id);
    }

    private DanhGiaSanPhamDTO convertToDTO(DanhGiaSanPham danhGiaSanPham) {
        DanhGiaSanPhamDTO dto = new DanhGiaSanPhamDTO();
        dto.setMaDanhGia(danhGiaSanPham.getMaDanhGia());
        if (danhGiaSanPham.getSanPham() != null) {
            dto.setMaSanPham(danhGiaSanPham.getSanPham().getMaSanPham());
        }
        if (danhGiaSanPham.getKhachHang() != null) {
            dto.setMaKhachHang(danhGiaSanPham.getKhachHang().getMaKhachHang());
        }
        dto.setDiemDanhGia(danhGiaSanPham.getDiemDanhGia());
        dto.setBinhLuan(danhGiaSanPham.getBinhLuan());
        dto.setHinhAnhDanhGia(danhGiaSanPham.getHinhAnhDanhGia());
        dto.setNgayDanhGia(danhGiaSanPham.getNgayDanhGia());
        dto.setTrangThai(danhGiaSanPham.getTrangThai());
        if (danhGiaSanPham.getNguoiTao() != null) {
            dto.setNguoiTaoId(danhGiaSanPham.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(DanhGiaSanPhamDTO dto, DanhGiaSanPham danhGiaSanPham) {
        if (dto.getMaSanPham() != null) {
            SanPham sanPham = sanPhamRepository.findById(dto.getMaSanPham())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
            danhGiaSanPham.setSanPham(sanPham);
        }
        if (dto.getMaKhachHang() != null) {
            KhachHang khachHang = khachHangRepository.findById(dto.getMaKhachHang())
                    .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
            danhGiaSanPham.setKhachHang(khachHang);
        }
        danhGiaSanPham.setDiemDanhGia(dto.getDiemDanhGia());
        danhGiaSanPham.setBinhLuan(dto.getBinhLuan());
        danhGiaSanPham.setHinhAnhDanhGia(dto.getHinhAnhDanhGia());
        danhGiaSanPham.setNgayDanhGia(dto.getNgayDanhGia() != null ? dto.getNgayDanhGia() : LocalDateTime.now());
        danhGiaSanPham.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            danhGiaSanPham.setNguoiTao(nguoiTao);
        }
    }
}