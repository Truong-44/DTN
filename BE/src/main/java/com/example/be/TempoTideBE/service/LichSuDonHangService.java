package com.example.be.TempoTideBE.service;

import com.example.be.dto.LichSuDonHangDTO;
import com.example.be.entity.DonHang;
import com.example.be.entity.LichSuDonHang;
import com.example.be.entity.NhanVien;
import com.example.be.repository.DonHangRepository;
import com.example.be.repository.LichSuDonHangRepository;
import com.example.be.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LichSuDonHangService {

    private final LichSuDonHangRepository lichSuDonHangRepository;
    private final DonHangRepository donHangRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<LichSuDonHangDTO> getAllLichSuDonHang() {
        return lichSuDonHangRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public LichSuDonHangDTO getLichSuDonHangById(Integer id) {
        LichSuDonHang lichSuDonHang = lichSuDonHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lịch sử đơn hàng không tồn tại"));
        return convertToDTO(lichSuDonHang);
    }

    public LichSuDonHangDTO createLichSuDonHang(LichSuDonHangDTO dto) {
        LichSuDonHang lichSuDonHang = new LichSuDonHang();
        mapToEntity(dto, lichSuDonHang);
        LichSuDonHang savedLichSuDonHang = lichSuDonHangRepository.save(lichSuDonHang);
        return convertToDTO(savedLichSuDonHang);
    }

    public LichSuDonHangDTO updateLichSuDonHang(Integer id, LichSuDonHangDTO dto) {
        LichSuDonHang lichSuDonHang = lichSuDonHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lịch sử đơn hàng không tồn tại"));
        mapToEntity(dto, lichSuDonHang);
        LichSuDonHang updatedLichSuDonHang = lichSuDonHangRepository.save(lichSuDonHang);
        return convertToDTO(updatedLichSuDonHang);
    }

    public void deleteLichSuDonHang(Integer id) {
        lichSuDonHangRepository.deleteById(id);
    }

    private LichSuDonHangDTO convertToDTO(LichSuDonHang lichSuDonHang) {
        LichSuDonHangDTO dto = new LichSuDonHangDTO();
        dto.setMaLichSu(lichSuDonHang.getMaLichSu());
        if (lichSuDonHang.getDonHang() != null) {
            dto.setMaDonHang(lichSuDonHang.getDonHang().getMaDonHang());
        }
        dto.setTrangThaiCu(lichSuDonHang.getTrangThaiCu());
        dto.setTrangThaiMoi(lichSuDonHang.getTrangThaiMoi());
        dto.setLyDo(lichSuDonHang.getLyDo());
        dto.setNgayCapNhat(lichSuDonHang.getNgayCapNhat());
        if (lichSuDonHang.getNguoiCapNhat() != null) {
            dto.setNguoiCapNhatId(lichSuDonHang.getNguoiCapNhat().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(LichSuDonHangDTO dto, LichSuDonHang lichSuDonHang) {
        if (dto.getMaDonHang() != null) {
            DonHang donHang = donHangRepository.findById(dto.getMaDonHang())
                    .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
            lichSuDonHang.setDonHang(donHang);
        }
        lichSuDonHang.setTrangThaiCu(dto.getTrangThaiCu());
        lichSuDonHang.setTrangThaiMoi(dto.getTrangThaiMoi());
        lichSuDonHang.setLyDo(dto.getLyDo());
        lichSuDonHang.setNgayCapNhat(dto.getNgayCapNhat() != null ? dto.getNgayCapNhat() : LocalDateTime.now());
        if (dto.getNguoiCapNhatId() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(dto.getNguoiCapNhatId())
                    .orElseThrow(() -> new RuntimeException("Người cập nhật không tồn tại"));
            lichSuDonHang.setNguoiCapNhat(nguoiCapNhat);
        }
    }
}