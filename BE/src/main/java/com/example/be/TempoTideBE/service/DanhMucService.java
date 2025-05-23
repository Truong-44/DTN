package com.example.be.TempoTideBE.service;

import com.example.be.TempoTideBE.dto.DanhMucDTO;
import com.example.be.TempoTideBE.entity.DanhMuc;
import com.example.be.TempoTideBE.entity.NhanVien;
import com.example.be.TempoTideBE.repository.DanhMucRepository;
import com.example.be.TempoTideBE.repository.NhanVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DanhMucService {

    private final DanhMucRepository danhMucRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<DanhMucDTO> getAllDanhMuc() {
        return danhMucRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DanhMucDTO getDanhMucById(Integer id) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại"));
        return convertToDTO(danhMuc);
    }

    public DanhMucDTO createDanhMuc(DanhMucDTO dto) {
        DanhMuc danhMuc = new DanhMuc();
        mapToEntity(dto, danhMuc);
        DanhMuc savedDanhMuc = danhMucRepository.save(danhMuc);
        return convertToDTO(savedDanhMuc);
    }

    public DanhMucDTO updateDanhMuc(Integer id, DanhMucDTO dto) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại"));
        mapToEntity(dto, danhMuc);
        DanhMuc updatedDanhMuc = danhMucRepository.save(danhMuc);
        return convertToDTO(updatedDanhMuc);
    }

    public void deleteDanhMuc(Integer id) {
        danhMucRepository.deleteById(id);
    }

    private DanhMucDTO convertToDTO(DanhMuc danhMuc) {
        DanhMucDTO dto = new DanhMucDTO();
        dto.setMaDanhMuc(danhMuc.getMaDanhMuc());
        dto.setTenDanhMuc(danhMuc.getTenDanhMuc());
        dto.setMoTa(danhMuc.getMoTa());
        if (danhMuc.getDanhMucCha() != null) {
            dto.setMaDanhMucCha(danhMuc.getDanhMucCha().getMaDanhMuc());
        }
        dto.setThuTuHienThi(danhMuc.getThuTuHienThi());
        dto.setHinhAnh(danhMuc.getHinhAnh());
        dto.setNgayTao(danhMuc.getNgayTao());
        dto.setTrangThai(danhMuc.getTrangThai());
        if (danhMuc.getNguoiTao() != null) {
            dto.setNguoiTaoId(danhMuc.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(DanhMucDTO dto, DanhMuc danhMuc) {
        danhMuc.setTenDanhMuc(dto.getTenDanhMuc());
        danhMuc.setMoTa(dto.getMoTa());
        if (dto.getMaDanhMucCha() != null) {
            DanhMuc danhMucCha = danhMucRepository.findById(dto.getMaDanhMucCha())
                    .orElseThrow(() -> new RuntimeException("Danh mục cha không tồn tại"));
            danhMuc.setDanhMucCha(danhMucCha);
        }
        danhMuc.setThuTuHienThi(dto.getThuTuHienThi() != null ? dto.getThuTuHienThi() : 0);
        danhMuc.setHinhAnh(dto.getHinhAnh());
        danhMuc.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        danhMuc.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            danhMuc.setNguoiTao(nguoiTao);
        }
    }
}