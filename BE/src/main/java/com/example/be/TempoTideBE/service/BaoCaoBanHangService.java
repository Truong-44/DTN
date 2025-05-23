package com.example.be.TempoTideBE.service;


import com.example.be.TempoTideBE.dto.BaoCaoBanHangDTO;
import com.example.be.TempoTideBE.entity.BaoCaoBanHang;
import com.example.be.TempoTideBE.entity.NhanVien;
import com.example.be.TempoTideBE.repository.BaoCaoBanHangRepository;
import com.example.be.TempoTideBE.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaoCaoBanHangService {

    private final BaoCaoBanHangRepository baoCaoBanHangRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<BaoCaoBanHangDTO> getAllBaoCaoBanHang() {
        return baoCaoBanHangRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public BaoCaoBanHangDTO getBaoCaoBanHangById(Integer id) {
        BaoCaoBanHang baoCaoBanHang = baoCaoBanHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Báo cáo bán hàng không tồn tại"));
        return convertToDTO(baoCaoBanHang);
    }

    public BaoCaoBanHangDTO createBaoCaoBanHang(BaoCaoBanHangDTO dto) {
        if (dto.getThoiGianKetThuc().isBefore(dto.getThoiGianBatDau())) {
            throw new RuntimeException("Thời gian kết thúc phải lớn hơn thời gian bắt đầu");
        }
        BaoCaoBanHang baoCaoBanHang = new BaoCaoBanHang();
        mapToEntity(dto, baoCaoBanHang);
        BaoCaoBanHang savedBaoCaoBanHang = baoCaoBanHangRepository.save(baoCaoBanHang);
        return convertToDTO(savedBaoCaoBanHang);
    }

    public BaoCaoBanHangDTO updateBaoCaoBanHang(Integer id, BaoCaoBanHangDTO dto) {
        if (dto.getThoiGianKetThuc().isBefore(dto.getThoiGianBatDau())) {
            throw new RuntimeException("Thời gian kết thúc phải lớn hơn thời gian bắt đầu");
        }
        BaoCaoBanHang baoCaoBanHang = baoCaoBanHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Báo cáo bán hàng không tồn tại"));
        mapToEntity(dto, baoCaoBanHang);
        BaoCaoBanHang updatedBaoCaoBanHang = baoCaoBanHangRepository.save(baoCaoBanHang);
        return convertToDTO(updatedBaoCaoBanHang);
    }

    public void deleteBaoCaoBanHang(Integer id) {
        baoCaoBanHangRepository.deleteById(id);
    }

    private BaoCaoBanHangDTO convertToDTO(BaoCaoBanHang baoCaoBanHang) {
        BaoCaoBanHangDTO dto = new BaoCaoBanHangDTO();
        dto.setMaBaoCao(baoCaoBanHang.getMaBaoCao());
        dto.setThoiGianBatDau(baoCaoBanHang.getThoiGianBatDau());
        dto.setThoiGianKetThuc(baoCaoBanHang.getThoiGianKetThuc());
        dto.setTongDoanhThu(baoCaoBanHang.getTongDoanhThu());
        dto.setSoDonHang(baoCaoBanHang.getSoDonHang());
        dto.setNgayTao(baoCaoBanHang.getNgayTao());
        dto.setTrangThai(baoCaoBanHang.getTrangThai());
        if (baoCaoBanHang.getNguoiTao() != null) {
            dto.setNguoiTaoId(baoCaoBanHang.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(BaoCaoBanHangDTO dto, BaoCaoBanHang baoCaoBanHang) {
        baoCaoBanHang.setThoiGianBatDau(dto.getThoiGianBatDau());
        baoCaoBanHang.setThoiGianKetThuc(dto.getThoiGianKetThuc());
        baoCaoBanHang.setTongDoanhThu(dto.getTongDoanhThu());
        baoCaoBanHang.setSoDonHang(dto.getSoDonHang());
        baoCaoBanHang.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        baoCaoBanHang.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            baoCaoBanHang.setNguoiTao(nguoiTao);
        }
    }
}