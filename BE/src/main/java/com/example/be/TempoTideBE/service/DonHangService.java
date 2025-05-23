package com.example.be.TempoTideBE.service;


import com.example.be.TempoTideBE.dto.DonHangDTO;
import com.example.be.TempoTideBE.entity.*;
import com.example.be.TempoTideBE.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonHangService {

    private final DonHangRepository donHangRepository;
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;
    private final PhuongThucVanChuyenRepository phuongThucVanChuyenRepository;
    private final DiaChiRepository diaChiRepository;
    private final KhuyenMaiRepository khuyenMaiRepository;

    public List<DonHangDTO> getAllDonHang() {
        return donHangRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DonHangDTO getDonHangById(Integer id) {
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
        return convertToDTO(donHang);
    }

    public DonHangDTO createDonHang(DonHangDTO dto) {
        DonHang donHang = new DonHang();
        mapToEntity(dto, donHang);
        DonHang savedDonHang = donHangRepository.save(donHang);
        return convertToDTO(savedDonHang);
    }

    public DonHangDTO updateDonHang(Integer id, DonHangDTO dto) {
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
        mapToEntity(dto, donHang);
        DonHang updatedDonHang = donHangRepository.save(donHang);
        return convertToDTO(updatedDonHang);
    }

    public void deleteDonHang(Integer id) {
        donHangRepository.deleteById(id);
    }

    private DonHangDTO convertToDTO(DonHang donHang) {
        DonHangDTO dto = new DonHangDTO();
        dto.setMaDonHang(donHang.getMaDonHang());
        if (donHang.getKhachHang() != null) {
            dto.setMaKhachHang(donHang.getKhachHang().getMaKhachHang());
        }
        if (donHang.getNhanVienXuLy() != null) {
            dto.setMaNhanVienXuLy(donHang.getNhanVienXuLy().getMaNhanVien());
        }
        dto.setNgayDatHang(donHang.getNgayDatHang());
        dto.setTongTien(donHang.getTongTien());
        if (donHang.getPhuongThucVanChuyen() != null) {
            dto.setMaPhuongThucVanChuyen(donHang.getPhuongThucVanChuyen().getMaPhuongThucVanChuyen());
        }
        if (donHang.getDiaChiGiaoHang() != null) {
            dto.setMaDiaChiGiaoHang(donHang.getDiaChiGiaoHang().getMaDiaChi());
        }
        dto.setTrangThaiDonHang(donHang.getTrangThaiDonHang());
        if (donHang.getKhuyenMai() != null) {
            dto.setMaKhuyenMai(donHang.getKhuyenMai().getMaKhuyenMai());
        }
        dto.setGhiChu(donHang.getGhiChu());
        dto.setLaDonHangVangLai(donHang.getLaDonHangVangLai());
        dto.setNgayTao(donHang.getNgayTao());
        dto.setNgayCapNhat(donHang.getNgayCapNhat());
        dto.setTrangThai(donHang.getTrangThai());
        if (donHang.getNguoiTao() != null) {
            dto.setNguoiTaoId(donHang.getNguoiTao().getMaNhanVien());
        }
        if (donHang.getNguoiCapNhat() != null) {
            dto.setNguoiCapNhatId(donHang.getNguoiCapNhat().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(DonHangDTO dto, DonHang donHang) {
        if (dto.getMaKhachHang() != null) {
            KhachHang khachHang = khachHangRepository.findById(dto.getMaKhachHang())
                    .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
            donHang.setKhachHang(khachHang);
        }
        if (dto.getMaNhanVienXuLy() != null) {
            NhanVien nhanVienXuLy = nhanVienRepository.findById(dto.getMaNhanVienXuLy())
                    .orElseThrow(() -> new RuntimeException("Nhân viên xử lý không tồn tại"));
            donHang.setNhanVienXuLy(nhanVienXuLy);
        }
        donHang.setNgayDatHang(dto.getNgayDatHang() != null ? dto.getNgayDatHang() : LocalDateTime.now());
        donHang.setTongTien(dto.getTongTien());
        if (dto.getMaPhuongThucVanChuyen() != null) {
            PhuongThucVanChuyen phuongThucVanChuyen = phuongThucVanChuyenRepository.findById(dto.getMaPhuongThucVanChuyen())
                    .orElseThrow(() -> new RuntimeException("Phương thức vận chuyển không tồn tại"));
            donHang.setPhuongThucVanChuyen(phuongThucVanChuyen);
        }
        if (dto.getMaDiaChiGiaoHang() != null) {
            DiaChi diaChiGiaoHang = diaChiRepository.findById(dto.getMaDiaChiGiaoHang())
                    .orElseThrow(() -> new RuntimeException("Địa chỉ giao hàng không tồn tại"));
            donHang.setDiaChiGiaoHang(diaChiGiaoHang);
        }
        donHang.setTrangThaiDonHang(dto.getTrangThaiDonHang());
        if (dto.getMaKhuyenMai() != null) {
            KhuyenMai khuyenMai = khuyenMaiRepository.findById(dto.getMaKhuyenMai())
                    .orElseThrow(() -> new RuntimeException("Khuyến mãi không tồn tại"));
            donHang.setKhuyenMai(khuyenMai);
        }
        donHang.setGhiChu(dto.getGhiChu());
        donHang.setLaDonHangVangLai(dto.getLaDonHangVangLai() != null ? dto.getLaDonHangVangLai() : false);
        donHang.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        donHang.setNgayCapNhat(dto.getNgayCapNhat() != null ? dto.getNgayCapNhat() : LocalDateTime.now());
        donHang.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            donHang.setNguoiTao(nguoiTao);
        }
        if (dto.getNguoiCapNhatId() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(dto.getNguoiCapNhatId())
                    .orElseThrow(() -> new RuntimeException("Người cập nhật không tồn tại"));
            donHang.setNguoiCapNhat(nguoiCapNhat);
        }
    }
}