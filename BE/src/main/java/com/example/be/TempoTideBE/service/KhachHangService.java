package com.example.be.TempoTideBE.service;

import com.example.be.dto.KhachHangDTO;
import com.example.be.entity.CapBacKhachHang;
import com.example.be.entity.KhachHang;
import com.example.be.entity.NhanVien;
import com.example.be.repository.CapBacKhachHangRepository;
import com.example.be.repository.KhachHangRepository;
import com.example.be.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KhachHangService {

    private final KhachHangRepository khachHangRepository;
    private final CapBacKhachHangRepository capBacKhachHangRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<KhachHangDTO> getAllKhachHang() {
        return khachHangRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public KhachHangDTO getKhachHangById(Integer id) {
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
        return convertToDTO(khachHang);
    }

    public KhachHangDTO createKhachHang(KhachHangDTO khachHangDTO) {
        KhachHang khachHang = new KhachHang();
        mapToEntity(khachHangDTO, khachHang);
        KhachHang savedKhachHang = khachHangRepository.save(khachHang);
        return convertToDTO(savedKhachHang);
    }

    public KhachHangDTO updateKhachHang(Integer id, KhachHangDTO khachHangDTO) {
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
        mapToEntity(khachHangDTO, khachHang);
        KhachHang updatedKhachHang = khachHangRepository.save(khachHang);
        return convertToDTO(updatedKhachHang);
    }

    public void deleteKhachHang(Integer id) {
        khachHangRepository.deleteById(id);
    }

    private KhachHangDTO convertToDTO(KhachHang khachHang) {
        KhachHangDTO dto = new KhachHangDTO();
        dto.setMaKhachHang(khachHang.getMaKhachHang());
        dto.setHo(khachHang.getHo());
        dto.setTen(khachHang.getTen());
        dto.setEmail(khachHang.getEmail());
        dto.setSoDienThoai(khachHang.getSoDienThoai());
        dto.setNgaySinh(khachHang.getNgaySinh());
        dto.setMatKhau(khachHang.getMatKhau());
        dto.setLaKhachVangLai(khachHang.getLaKhachVangLai());
        dto.setDiemTichLuy(khachHang.getDiemTichLuy());
        if (khachHang.getCapBac() != null) {
            dto.setMaCapBac(khachHang.getCapBac().getMaCapBac());
        }
        dto.setNgayTao(khachHang.getNgayTao());
        dto.setNgayCapNhat(khachHang.getNgayCapNhat());
        dto.setTrangThai(khachHang.getTrangThai());
        if (khachHang.getNguoiTao() != null) {
            dto.setNguoiTaoId(khachHang.getNguoiTao().getMaNhanVien());
        }
        if (khachHang.getNguoiCapNhat() != null) {
            dto.setNguoiCapNhatId(khachHang.getNguoiCapNhat().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(KhachHangDTO dto, KhachHang khachHang) {
        khachHang.setHo(dto.getHo());
        khachHang.setTen(dto.getTen());
        khachHang.setEmail(dto.getEmail());
        khachHang.setSoDienThoai(dto.getSoDienThoai());
        khachHang.setNgaySinh(dto.getNgaySinh());
        khachHang.setMatKhau(dto.getMatKhau());
        khachHang.setLaKhachVangLai(dto.getLaKhachVangLai() != null ? dto.getLaKhachVangLai() : false);
        khachHang.setDiemTichLuy(dto.getDiemTichLuy() != null ? dto.getDiemTichLuy() : 0);
        if (dto.getMaCapBac() != null) {
            CapBacKhachHang capBac = capBacKhachHangRepository.findById(dto.getMaCapBac())
                    .orElseThrow(() -> new RuntimeException("Cấp bậc khách hàng không tồn tại"));
            khachHang.setCapBac(capBac);
        }
        khachHang.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        khachHang.setNgayCapNhat(dto.getNgayCapNhat() != null ? dto.getNgayCapNhat() : LocalDateTime.now());
        khachHang.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            khachHang.setNguoiTao(nguoiTao);
        }
        if (dto.getNguoiCapNhatId() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(dto.getNguoiCapNhatId())
                    .orElseThrow(() -> new RuntimeException("Người cập nhật không tồn tại"));
            khachHang.setNguoiCapNhat(nguoiCapNhat);
        }
    }
}