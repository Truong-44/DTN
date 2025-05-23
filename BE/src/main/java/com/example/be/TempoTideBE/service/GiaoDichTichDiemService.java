package com.example.be.TempoTideBE.service;

import com.example.be.dto.GiaoDichTichDiemDTO;
import com.example.be.entity.DonHang;
import com.example.be.entity.GiaoDichTichDiem;
import com.example.be.entity.KhachHang;
import com.example.be.entity.NhanVien;
import com.example.be.repository.DonHangRepository;
import com.example.be.repository.GiaoDichTichDiemRepository;
import com.example.be.repository.KhachHangRepository;
import com.example.be.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiaoDichTichDiemService {

    private final GiaoDichTichDiemRepository giaoDichTichDiemRepository;
    private final KhachHangRepository khachHangRepository;
    private final DonHangRepository donHangRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<GiaoDichTichDiemDTO> getAllGiaoDichTichDiem() {
        return giaoDichTichDiemRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public GiaoDichTichDiemDTO getGiaoDichTichDiemById(Integer id) {
        GiaoDichTichDiem giaoDichTichDiem = giaoDichTichDiemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Giao dịch tích điểm không tồn tại"));
        return convertToDTO(giaoDichTichDiem);
    }

    public GiaoDichTichDiemDTO createGiaoDichTichDiem(GiaoDichTichDiemDTO dto) {
        GiaoDichTichDiem giaoDichTichDiem = new GiaoDichTichDiem();
        mapToEntity(dto, giaoDichTichDiem);
        GiaoDichTichDiem savedGiaoDichTichDiem = giaoDichTichDiemRepository.save(giaoDichTichDiem);
        return convertToDTO(savedGiaoDichTichDiem);
    }

    public GiaoDichTichDiemDTO updateGiaoDichTichDiem(Integer id, GiaoDichTichDiemDTO dto) {
        GiaoDichTichDiem giaoDichTichDiem = giaoDichTichDiemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Giao dịch tích điểm không tồn tại"));
        mapToEntity(dto, giaoDichTichDiem);
        GiaoDichTichDiem updatedGiaoDichTichDiem = giaoDichTichDiemRepository.save(giaoDichTichDiem);
        return convertToDTO(updatedGiaoDichTichDiem);
    }

    public void deleteGiaoDichTichDiem(Integer id) {
        giaoDichTichDiemRepository.deleteById(id);
    }

    private GiaoDichTichDiemDTO convertToDTO(GiaoDichTichDiem giaoDichTichDiem) {
        GiaoDichTichDiemDTO dto = new GiaoDichTichDiemDTO();
        dto.setMaGiaoDich(giaoDichTichDiem.getMaGiaoDich());
        if (giaoDichTichDiem.getKhachHang() != null) {
            dto.setMaKhachHang(giaoDichTichDiem.getKhachHang().getMaKhachHang());
        }
        dto.setSoDiem(giaoDichTichDiem.getSoDiem());
        dto.setLoaiGiaoDich(giaoDichTichDiem.getLoaiGiaoDich());
        if (giaoDichTichDiem.getDonHang() != null) {
            dto.setMaDonHang(giaoDichTichDiem.getDonHang().getMaDonHang());
        }
        dto.setNgayTao(giaoDichTichDiem.getNgayTao());
        dto.setTrangThai(giaoDichTichDiem.getTrangThai());
        if (giaoDichTichDiem.getNguoiTao() != null) {
            dto.setNguoiTaoId(giaoDichTichDiem.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(GiaoDichTichDiemDTO dto, GiaoDichTichDiem giaoDichTichDiem) {
        if (dto.getMaKhachHang() != null) {
            KhachHang khachHang = khachHangRepository.findById(dto.getMaKhachHang())
                    .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
            giaoDichTichDiem.setKhachHang(khachHang);
        }
        giaoDichTichDiem.setSoDiem(dto.getSoDiem());
        giaoDichTichDiem.setLoaiGiaoDich(dto.getLoaiGiaoDich());
        if (dto.getMaDonHang() != null) {
            DonHang donHang = donHangRepository.findById(dto.getMaDonHang())
                    .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
            giaoDichTichDiem.setDonHang(donHang);
        }
        giaoDichTichDiem.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        giaoDichTichDiem.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            giaoDichTichDiem.setNguoiTao(nguoiTao);
        }
    }
}