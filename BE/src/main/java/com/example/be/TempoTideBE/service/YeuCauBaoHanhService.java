package com.example.be.TempoTideBE.service;

import com.example.be.dto.YeuCauBaoHanhDTO;
import com.example.be.entity.BaoHanh;
import com.example.be.entity.KhachHang;
import com.example.be.entity.NhanVien;
import com.example.be.entity.YeuCauBaoHanh;
import com.example.be.repository.BaoHanhRepository;
import com.example.be.repository.KhachHangRepository;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.YeuCauBaoHanhRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class YeuCauBaoHanhService {

    private final YeuCauBaoHanhRepository yeuCauBaoHanhRepository;
    private final BaoHanhRepository baoHanhRepository;
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<YeuCauBaoHanhDTO> getAllYeuCauBaoHanh() {
        return yeuCauBaoHanhRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public YeuCauBaoHanhDTO getYeuCauBaoHanhById(Integer id) {
        YeuCauBaoHanh yeuCauBaoHanh = yeuCauBaoHanhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yêu cầu bảo hành không tồn tại"));
        return convertToDTO(yeuCauBaoHanh);
    }

    public YeuCauBaoHanhDTO createYeuCauBaoHanh(YeuCauBaoHanhDTO dto) {
        YeuCauBaoHanh yeuCauBaoHanh = new YeuCauBaoHanh();
        mapToEntity(dto, yeuCauBaoHanh);
        YeuCauBaoHanh savedYeuCauBaoHanh = yeuCauBaoHanhRepository.save(yeuCauBaoHanh);
        return convertToDTO(savedYeuCauBaoHanh);
    }

    public YeuCauBaoHanhDTO updateYeuCauBaoHanh(Integer id, YeuCauBaoHanhDTO dto) {
        YeuCauBaoHanh yeuCauBaoHanh = yeuCauBaoHanhRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yêu cầu bảo hành không tồn tại"));
        mapToEntity(dto, yeuCauBaoHanh);
        YeuCauBaoHanh updatedYeuCauBaoHanh = yeuCauBaoHanhRepository.save(yeuCauBaoHanh);
        return convertToDTO(updatedYeuCauBaoHanh);
    }

    public void deleteYeuCauBaoHanh(Integer id) {
        yeuCauBaoHanhRepository.deleteById(id);
    }

    private YeuCauBaoHanhDTO convertToDTO(YeuCauBaoHanh yeuCauBaoHanh) {
        YeuCauBaoHanhDTO dto = new YeuCauBaoHanhDTO();
        dto.setMaYeuCau(yeuCauBaoHanh.getMaYeuCau());
        if (yeuCauBaoHanh.getBaoHanh() != null) {
            dto.setMaBaoHanh(yeuCauBaoHanh.getBaoHanh().getMaBaoHanh());
        }
        if (yeuCauBaoHanh.getKhachHang() != null) {
            dto.setMaKhachHang(yeuCauBaoHanh.getKhachHang().getMaKhachHang());
        }
        dto.setMoTaLoi(yeuCauBaoHanh.getMoTaLoi());
        dto.setTrangThaiYeuCau(yeuCauBaoHanh.getTrangThaiYeuCau());
        dto.setNgayYeuCau(yeuCauBaoHanh.getNgayYeuCau());
        dto.setNgayXuLy(yeuCauBaoHanh.getNgayXuLy());
        dto.setGhiChu(yeuCauBaoHanh.getGhiChu());
        dto.setTrangThai(yeuCauBaoHanh.getTrangThai());
        if (yeuCauBaoHanh.getNguoiXuLy() != null) {
            dto.setNguoiXuLyId(yeuCauBaoHanh.getNguoiXuLy().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(YeuCauBaoHanhDTO dto, YeuCauBaoHanh yeuCauBaoHanh) {
        if (dto.getMaBaoHanh() != null) {
            BaoHanh baoHanh = baoHanhRepository.findById(dto.getMaBaoHanh())
                    .orElseThrow(() -> new RuntimeException("Bảo hành không tồn tại"));
            yeuCauBaoHanh.setBaoHanh(baoHanh);
        }
        if (dto.getMaKhachHang() != null) {
            KhachHang khachHang = khachHangRepository.findById(dto.getMaKhachHang())
                    .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
            yeuCauBaoHanh.setKhachHang(khachHang);
        }
        yeuCauBaoHanh.setMoTaLoi(dto.getMoTaLoi());
        yeuCauBaoHanh.setTrangThaiYeuCau(dto.getTrangThaiYeuCau());
        yeuCauBaoHanh.setNgayYeuCau(dto.getNgayYeuCau() != null ? dto.getNgayYeuCau() : LocalDateTime.now());
        yeuCauBaoHanh.setNgayXuLy(dto.getNgayXuLy());
        yeuCauBaoHanh.setGhiChu(dto.getGhiChu());
        yeuCauBaoHanh.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiXuLyId() != null) {
            NhanVien nguoiXuLy = nhanVienRepository.findById(dto.getNguoiXuLyId())
                    .orElseThrow(() -> new RuntimeException("Người xử lý không tồn tại"));
            yeuCauBaoHanh.setNguoiXuLy(nguoiXuLy);
        }
    }
}