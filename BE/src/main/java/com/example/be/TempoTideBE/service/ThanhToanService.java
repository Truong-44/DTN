package com.example.be.TempoTideBE.service;

import com.example.be.dto.ThanhToanDTO;
import com.example.be.entity.DonHang;
import com.example.be.entity.NhanVien;
import com.example.be.entity.PhuongThucThanhToan;
import com.example.be.entity.ThanhToan;
import com.example.be.repository.DonHangRepository;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.PhuongThucThanhToanRepository;
import com.example.be.repository.ThanhToanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThanhToanService {

    private final ThanhToanRepository thanhToanRepository;
    private final DonHangRepository donHangRepository;
    private final PhuongThucThanhToanRepository phuongThucThanhToanRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<ThanhToanDTO> getAllThanhToan() {
        return thanhToanRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ThanhToanDTO getThanhToanById(Integer id) {
        ThanhToan thanhToan = thanhToanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thanh toán không tồn tại"));
        return convertToDTO(thanhToan);
    }

    public ThanhToanDTO createThanhToan(ThanhToanDTO dto) {
        ThanhToan thanhToan = new ThanhToan();
        mapToEntity(dto, thanhToan);
        ThanhToan savedThanhToan = thanhToanRepository.save(thanhToan);
        return convertToDTO(savedThanhToan);
    }

    public ThanhToanDTO updateThanhToan(Integer id, ThanhToanDTO dto) {
        ThanhToan thanhToan = thanhToanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thanh toán không tồn tại"));
        mapToEntity(dto, thanhToan);
        ThanhToan updatedThanhToan = thanhToanRepository.save(thanhToan);
        return convertToDTO(updatedThanhToan);
    }

    public void deleteThanhToan(Integer id) {
        thanhToanRepository.deleteById(id);
    }

    private ThanhToanDTO convertToDTO(ThanhToan thanhToan) {
        ThanhToanDTO dto = new ThanhToanDTO();
        dto.setMaThanhToan(thanhToan.getMaThanhToan());
        if (thanhToan.getDonHang() != null) {
            dto.setMaDonHang(thanhToan.getDonHang().getMaDonHang());
        }
        if (thanhToan.getPhuongThucThanhToan() != null) {
            dto.setMaPhuongThucThanhToan(thanhToan.getPhuongThucThanhToan().getMaPhuongThucThanhToan());
        }
        dto.setSoTien(thanhToan.getSoTien());
        dto.setNgayThanhToan(thanhToan.getNgayThanhToan());
        dto.setTrangThaiThanhToan(thanhToan.getTrangThaiThanhToan());
        dto.setMaGiaoDich(thanhToan.getMaGiaoDich());
        dto.setGhiChu(thanhToan.getGhiChu());
        dto.setTrangThai(thanhToan.getTrangThai());
        if (thanhToan.getNguoiTao() != null) {
            dto.setNguoiTaoId(thanhToan.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(ThanhToanDTO dto, ThanhToan thanhToan) {
        if (dto.getMaDonHang() != null) {
            DonHang donHang = donHangRepository.findById(dto.getMaDonHang())
                    .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
            thanhToan.setDonHang(donHang);
        }
        if (dto.getMaPhuongThucThanhToan() != null) {
            PhuongThucThanhToan phuongThucThanhToan = phuongThucThanhToanRepository.findById(dto.getMaPhuongThucThanhToan())
                    .orElseThrow(() -> new RuntimeException("Phương thức thanh toán không tồn tại"));
            thanhToan.setPhuongThucThanhToan(phuongThucThanhToan);
        }
        thanhToan.setSoTien(dto.getSoTien());
        thanhToan.setNgayThanhToan(dto.getNgayThanhToan() != null ? dto.getNgayThanhToan() : LocalDateTime.now());
        thanhToan.setTrangThaiThanhToan(dto.getTrangThaiThanhToan());
        thanhToan.setMaGiaoDich(dto.getMaGiaoDich());
        thanhToan.setGhiChu(dto.getGhiChu());
        thanhToan.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            thanhToan.setNguoiTao(nguoiTao);
        }
    }
}