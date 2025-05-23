package com.example.be.TempoTideBE.service;

import com.example.be.dto.LichSuVanChuyenDTO;
import com.example.be.entity.DonHang;
import com.example.be.entity.LichSuVanChuyen;
import com.example.be.entity.NhanVien;
import com.example.be.entity.PhuongThucVanChuyen;
import com.example.be.repository.DonHangRepository;
import com.example.be.repository.LichSuVanChuyenRepository;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.PhuongThucVanChuyenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LichSuVanChuyenService {

    private final LichSuVanChuyenRepository lichSuVanChuyenRepository;
    private final DonHangRepository donHangRepository;
    private final PhuongThucVanChuyenRepository phuongThucVanChuyenRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<LichSuVanChuyenDTO> getAllLichSuVanChuyen() {
        return lichSuVanChuyenRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public LichSuVanChuyenDTO getLichSuVanChuyenById(Integer id) {
        LichSuVanChuyen lichSuVanChuyen = lichSuVanChuyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lịch sử vận chuyển không tồn tại"));
        return convertToDTO(lichSuVanChuyen);
    }

    public LichSuVanChuyenDTO createLichSuVanChuyen(LichSuVanChuyenDTO dto) {
        LichSuVanChuyen lichSuVanChuyen = new LichSuVanChuyen();
        mapToEntity(dto, lichSuVanChuyen);
        LichSuVanChuyen savedLichSuVanChuyen = lichSuVanChuyenRepository.save(lichSuVanChuyen);
        return convertToDTO(savedLichSuVanChuyen);
    }

    public LichSuVanChuyenDTO updateLichSuVanChuyen(Integer id, LichSuVanChuyenDTO dto) {
        LichSuVanChuyen lichSuVanChuyen = lichSuVanChuyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lịch sử vận chuyển không tồn tại"));
        mapToEntity(dto, lichSuVanChuyen);
        LichSuVanChuyen updatedLichSuVanChuyen = lichSuVanChuyenRepository.save(lichSuVanChuyen);
        return convertToDTO(updatedLichSuVanChuyen);
    }

    public void deleteLichSuVanChuyen(Integer id) {
        lichSuVanChuyenRepository.deleteById(id);
    }

    private LichSuVanChuyenDTO convertToDTO(LichSuVanChuyen lichSuVanChuyen) {
        LichSuVanChuyenDTO dto = new LichSuVanChuyenDTO();
        dto.setMaLichSu(lichSuVanChuyen.getMaLichSu());
        if (lichSuVanChuyen.getDonHang() != null) {
            dto.setMaDonHang(lichSuVanChuyen.getDonHang().getMaDonHang());
        }
        if (lichSuVanChuyen.getPhuongThucVanChuyen() != null) {
            dto.setMaPhuongThucVanChuyen(lichSuVanChuyen.getPhuongThucVanChuyen().getMaPhuongThucVanChuyen());
        }
        dto.setTrangThaiVanChuyen(lichSuVanChuyen.getTrangThaiVanChuyen());
        dto.setDiaDiem(lichSuVanChuyen.getDiaDiem());
        dto.setNgayCapNhat(lichSuVanChuyen.getNgayCapNhat());
        dto.setGhiChu(lichSuVanChuyen.getGhiChu());
        dto.setTrangThai(lichSuVanChuyen.getTrangThai());
        if (lichSuVanChuyen.getNguoiCapNhat() != null) {
            dto.setNguoiCapNhatId(lichSuVanChuyen.getNguoiCapNhat().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(LichSuVanChuyenDTO dto, LichSuVanChuyen lichSuVanChuyen) {
        if (dto.getMaDonHang() != null) {
            DonHang donHang = donHangRepository.findById(dto.getMaDonHang())
                    .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
            lichSuVanChuyen.setDonHang(donHang);
        }
        if (dto.getMaPhuongThucVanChuyen() != null) {
            PhuongThucVanChuyen phuongThucVanChuyen = phuongThucVanChuyenRepository.findById(dto.getMaPhuongThucVanChuyen())
                    .orElseThrow(() -> new RuntimeException("Phương thức vận chuyển không tồn tại"));
            lichSuVanChuyen.setPhuongThucVanChuyen(phuongThucVanChuyen);
        }
        lichSuVanChuyen.setTrangThaiVanChuyen(dto.getTrangThaiVanChuyen());
        lichSuVanChuyen.setDiaDiem(dto.getDiaDiem());
        lichSuVanChuyen.setNgayCapNhat(dto.getNgayCapNhat() != null ? dto.getNgayCapNhat() : LocalDateTime.now());
        lichSuVanChuyen.setGhiChu(dto.getGhiChu());
        lichSuVanChuyen.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiCapNhatId() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(dto.getNguoiCapNhatId())
                    .orElseThrow(() -> new RuntimeException("Người cập nhật không tồn tại"));
            lichSuVanChuyen.setNguoiCapNhat(nguoiCapNhat);
        }
    }
}