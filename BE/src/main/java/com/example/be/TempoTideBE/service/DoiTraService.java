package com.example.be.TempoTideBE.service;


import com.example.be.TempoTideBE.dto.DoiTraDTO;
import com.example.be.TempoTideBE.entity.*;
import com.example.be.TempoTideBE.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoiTraService {

    private final DoiTraRepository doiTraRepository;
    private final DonHangRepository donHangRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<DoiTraDTO> getAllDoiTra() {
        return doiTraRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DoiTraDTO getDoiTraById(Integer id) {
        DoiTra doiTra = doiTraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đổi trả không tồn tại"));
        return convertToDTO(doiTra);
    }

    public DoiTraDTO createDoiTra(DoiTraDTO dto) {
        DoiTra doiTra = new DoiTra();
        mapToEntity(dto, doiTra);
        DoiTra savedDoiTra = doiTraRepository.save(doiTra);
        return convertToDTO(savedDoiTra);
    }

    public DoiTraDTO updateDoiTra(Integer id, DoiTraDTO dto) {
        DoiTra doiTra = doiTraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đổi trả không tồn tại"));
        mapToEntity(dto, doiTra);
        DoiTra updatedDoiTra = doiTraRepository.save(doiTra);
        return convertToDTO(updatedDoiTra);
    }

    public void deleteDoiTra(Integer id) {
        doiTraRepository.deleteById(id);
    }

    private DoiTraDTO convertToDTO(DoiTra doiTra) {
        DoiTraDTO dto = new DoiTraDTO();
        dto.setMaDoiTra(doiTra.getMaDoiTra());
        if (doiTra.getDonHang() != null) {
            dto.setMaDonHang(doiTra.getDonHang().getMaDonHang());
        }
        if (doiTra.getChiTietSanPham() != null) {
            dto.setMaChiTietSanPham(doiTra.getChiTietSanPham().getMaChiTietSanPham());
        }
        if (doiTra.getKhachHang() != null) {
            dto.setMaKhachHang(doiTra.getKhachHang().getMaKhachHang());
        }
        dto.setLyDo(doiTra.getLyDo());
        dto.setTrangThaiDoiTra(doiTra.getTrangThaiDoiTra());
        dto.setNgayYeuCau(doiTra.getNgayYeuCau());
        dto.setNgayXuLy(doiTra.getNgayXuLy());
        dto.setGhiChu(doiTra.getGhiChu());
        dto.setTrangThai(doiTra.getTrangThai());
        if (doiTra.getNguoiXuLy() != null) {
            dto.setNguoiXuLyId(doiTra.getNguoiXuLy().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(DoiTraDTO dto, DoiTra doiTra) {
        if (dto.getMaDonHang() != null) {
            DonHang donHang = donHangRepository.findById(dto.getMaDonHang())
                    .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
            doiTra.setDonHang(donHang);
        }
        if (dto.getMaChiTietSanPham() != null) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(dto.getMaChiTietSanPham())
                    .orElseThrow(() -> new RuntimeException("Chi tiết sản phẩm không tồn tại"));
            doiTra.setChiTietSanPham(chiTietSanPham);
        }
        if (dto.getMaKhachHang() != null) {
            KhachHang khachHang = khachHangRepository.findById(dto.getMaKhachHang())
                    .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
            doiTra.setKhachHang(khachHang);
        }
        doiTra.setLyDo(dto.getLyDo());
        doiTra.setTrangThaiDoiTra(dto.getTrangThaiDoiTra());
        doiTra.setNgayYeuCau(dto.getNgayYeuCau() != null ? dto.getNgayYeuCau() : LocalDateTime.now());
        doiTra.setNgayXuLy(dto.getNgayXuLy());
        doiTra.setGhiChu(dto.getGhiChu());
        doiTra.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiXuLyId() != null) {
            NhanVien nguoiXuLy = nhanVienRepository.findById(dto.getNguoiXuLyId())
                    .orElseThrow(() -> new RuntimeException("Người xử lý không tồn tại"));
            doiTra.setNguoiXuLy(nguoiXuLy);
        }
    }
}