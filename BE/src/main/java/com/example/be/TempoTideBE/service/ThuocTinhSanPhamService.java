package com.example.be.TempoTideBE.service;

import com.example.be.dto.ThuocTinhSanPhamDTO;
import com.example.be.entity.NhanVien;
import com.example.be.entity.ThuocTinhSanPham;
import com.example.be.repository.NhanVienRepository;
import com.example.be.repository.ThuocTinhSanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThuocTinhSanPhamService {

    private final ThuocTinhSanPhamRepository thuocTinhSanPhamRepository;
    private final NhanVienRepository nhanVienRepository;

    public List<ThuocTinhSanPhamDTO> getAllThuocTinhSanPham() {
        return thuocTinhSanPhamRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ThuocTinhSanPhamDTO getThuocTinhSanPhamById(Integer id) {
        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thuộc tính sản phẩm không tồn tại"));
        return convertToDTO(thuocTinhSanPham);
    }

    public ThuocTinhSanPhamDTO createThuocTinhSanPham(ThuocTinhSanPhamDTO dto) {
        ThuocTinhSanPham thuocTinhSanPham = new ThuocTinhSanPham();
        mapToEntity(dto, thuocTinhSanPham);
        ThuocTinhSanPham savedThuocTinhSanPham = thuocTinhSanPhamRepository.save(thuocTinhSanPham);
        return convertToDTO(savedThuocTinhSanPham);
    }

    public ThuocTinhSanPhamDTO updateThuocTinhSanPham(Integer id, ThuocTinhSanPhamDTO dto) {
        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thuộc tính sản phẩm không tồn tại"));
        mapToEntity(dto, thuocTinhSanPham);
        ThuocTinhSanPham updatedThuocTinhSanPham = thuocTinhSanPhamRepository.save(thuocTinhSanPham);
        return convertToDTO(updatedThuocTinhSanPham);
    }

    public void deleteThuocTinhSanPham(Integer id) {
        thuocTinhSanPhamRepository.deleteById(id);
    }

    private ThuocTinhSanPhamDTO convertToDTO(ThuocTinhSanPham thuocTinhSanPham) {
        ThuocTinhSanPhamDTO dto = new ThuocTinhSanPhamDTO();
        dto.setMaThuocTinh(thuocTinhSanPham.getMaThuocTinh());
        dto.setTenThuocTinh(thuocTinhSanPham.getTenThuocTinh());
        dto.setMoTa(thuocTinhSanPham.getMoTa());
        dto.setLoaiGiaTri(thuocTinhSanPham.getLoaiGiaTri());
        dto.setNgayTao(thuocTinhSanPham.getNgayTao());
        dto.setTrangThai(thuocTinhSanPham.getTrangThai());
        if (thuocTinhSanPham.getNguoiTao() != null) {
            dto.setNguoiTaoId(thuocTinhSanPham.getNguoiTao().getMaNhanVien());
        }
        return dto;
    }

    private void mapToEntity(ThuocTinhSanPhamDTO dto, ThuocTinhSanPham thuocTinhSanPham) {
        thuocTinhSanPham.setTenThuocTinh(dto.getTenThuocTinh());
        thuocTinhSanPham.setMoTa(dto.getMoTa());
        thuocTinhSanPham.setLoaiGiaTri(dto.getLoaiGiaTri());
        thuocTinhSanPham.setNgayTao(dto.getNgayTao() != null ? dto.getNgayTao() : LocalDateTime.now());
        thuocTinhSanPham.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        if (dto.getNguoiTaoId() != null) {
            NhanVien nguoiTao = nhanVienRepository.findById(dto.getNguoiTaoId())
                    .orElseThrow(() -> new RuntimeException("Người tạo không tồn tại"));
            thuocTinhSanPham.setNguoiTao(nguoiTao);
        }
    }
}