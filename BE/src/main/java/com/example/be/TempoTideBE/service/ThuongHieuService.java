package com.example.be.TempoTideBE.service;

import com.example.be.dto.ThuongHieuDTO;
import com.example.be.entity.ThuongHieu;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.ThuongHieuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThuongHieuService {

    private final ThuongHieuRepository thuongHieuRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<ThuongHieuDTO> getAllThuongHieu() {
        return thuongHieuRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ThuongHieuDTO getThuongHieuById(Integer id) {
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thương hiệu không tồn tại"));
        return convertToDTO(thuongHieu);
    }

    public ThuongHieuDTO createThuongHieu(ThuongHieuDTO thuongHieuDTO) {
        ThuongHieu thuongHieu = new ThuongHieu();
        mapToEntity(thuongHieuDTO, thuongHieu);
        ThuongHieu savedThuongHieu = thuongHieuRepository.save(thuongHieu);
        return convertToDTO(savedThuongHieu);
    }

    public ThuongHieuDTO updateThuongHieu(Integer id, ThuongHieuDTO thuongHieuDTO) {
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thương hiệu không tồn tại"));
        mapToEntity(thuongHieuDTO, thuongHieu);
        ThuongHieu updatedThuongHieu = thuongHieuRepository.save(thuongHieu);
        return convertToDTO(updatedThuongHieu);
    }

    public void deleteThuongHieu(Integer id) {
        thuongHieuRepository.deleteById(id);
    }

    private ThuongHieuDTO convertToDTO(ThuongHieu thuongHieu) {
        ThuongHieuDTO dto = new ThuongHieuDTO();
        dto.setMaThuongHieu(thuongHieu.getMaThuongHieu());
        dto.setTenThuongHieu(thuongHieu.getTenThuongHieu());
        dto.setMoTa(thuongHieu.getMoTa());
        dto.setHinhAnh(thuongHieu.getHinhAnh());
        dto.setNgayTao(thuongHieu.getNgayTao());
        dto.setTrangThai(thuongHieu.getTrangThai());
        if (thuongHieu.getNguoiTao() != null) {
            dto.setNguoiTaoId(thuongHieu.getNguoiTao().getMaNhanVien());
        }
        if (thuongHieu.getNguoiCapNhat() != null) {
            dto.setNguoiCapNhatId(thuongHieu.getNguoiCapNhat().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(ThuongHieuDTO dto, ThuongHieu thuongHieu) {
        thuongHieu.setTenThuongHieu(dto.getTenThuongHieu());
        thuongHieu.setMoTa(dto.getMoTa());
        thuongHieu.setHinhAnh(dto.getHinhAnh());
        thuongHieu.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        thuongHieu.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            thuongHieu.setNguoiTao(nguoiTao);
        }
        if (dto.getNguoiCapNhatId() != null) {
            NhanVien nguoiCapNhat = nhanVienRepository.findById(dto.getNguoiCapNhatId())
                    .orElseThrow(() -> new RuntimeException("Người cập nhật không tồn tại"));
            thuongHieu.setNguoiCapNhat(nguoiCapNhat);
        }
    }
}