package com.example.be.TempoTideBE.service.impl;

import com.example.be.TempoTideBE.dto.SanPhamDTO;
import com.example.be.TempoTideBE.entity.DanhMuc;
import com.example.be.TempoTideBE.entity.NhaCungCap;
import com.example.be.TempoTideBE.entity.SanPham;
import com.example.be.TempoTideBE.entity.ThuongHieu;
import com.example.be.TempoTideBE.exception.ResourceNotFoundException;
import com.example.be.TempoTideBE.mapper.SanPhamMapper;
import com.example.be.TempoTideBE.repository.DanhMucRepository;
import com.example.be.TempoTideBE.repository.NhaCungCapRepository;
import com.example.be.TempoTideBE.repository.SanPhamRepository;
import com.example.be.TempoTideBE.repository.ThuongHieuRepository;
import com.example.be.TempoTideBE.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SanPhamServiceImpl implements SanPhamService {

    private final SanPhamRepository sanPhamRepository;
    private final SanPhamMapper sanPhamMapper;
    private final DanhMucRepository danhMucRepository;
    private final NhaCungCapRepository nhaCungCapRepository;
    private final ThuongHieuRepository thuongHieuRepository;

    @Override
    @Transactional
    public SanPhamDTO createSanPham(SanPhamDTO sanPhamDTO) {
        if (sanPhamDTO.getGiaNiemYet() < 0 || sanPhamDTO.getGiaBanMacDinh() < 0) {
            throw new IllegalArgumentException("Giá không thể âm.");
        }

        // Kiểm tra và lấy dữ liệu từ repository
        DanhMuc danhMuc = danhMucRepository.findById(sanPhamDTO.getMaDanhMuc())
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại: " + sanPhamDTO.getMaDanhMuc()));
        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(sanPhamDTO.getMaNhaCungCap())
                .orElseThrow(() -> new ResourceNotFoundException("Nhà cung cấp không tồn tại: " + sanPhamDTO.getMaNhaCungCap()));
        ThuongHieu thuongHieu = sanPhamDTO.getMaThuongHieu() != null ?
                thuongHieuRepository.findById(sanPhamDTO.getMaThuongHieu())
                        .orElseThrow(() -> new ResourceNotFoundException("Thương hiệu không tồn tại: " + sanPhamDTO.getMaThuongHieu())) : null;

        SanPham sanPham = sanPhamMapper.toEntity(sanPhamDTO);
        sanPham.setDanhMuc(danhMuc);
        sanPham.setNhaCungCap(nhaCungCap);
        sanPham.setThuongHieu(thuongHieu);

        SanPham savedSanPham = sanPhamRepository.save(sanPham);
        return sanPhamMapper.toDto(savedSanPham);
    }

    @Override
    @Transactional
    public SanPhamDTO updateSanPham(Integer maSanPham, SanPhamDTO sanPhamDTO) {
        SanPham existingSanPham = sanPhamRepository.findById(maSanPham)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại: " + maSanPham));
        if (sanPhamDTO.getGiaNiemYet() != null && sanPhamDTO.getGiaNiemYet() < 0) {
            throw new IllegalArgumentException("Giá niêm yết không thể âm.");
        }
        if (sanPhamDTO.getGiaBanMacDinh() != null && sanPhamDTO.getGiaBanMacDinh() < 0) {
            throw new IllegalArgumentException("Giá bán mặc định không thể âm.");
        }
        // Cập nhật từ repository thay vì tạo mới
        if (sanPhamDTO.getMaDanhMuc() != null) {
            DanhMuc danhMuc = danhMucRepository.findById(sanPhamDTO.getMaDanhMuc())
                    .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại: " + sanPhamDTO.getMaDanhMuc()));
            existingSanPham.setDanhMuc(danhMuc);
        }
        if (sanPhamDTO.getMaNhaCungCap() != null) {
            NhaCungCap nhaCungCap = nhaCungCapRepository.findById(sanPhamDTO.getMaNhaCungCap())
                    .orElseThrow(() -> new ResourceNotFoundException("Nhà cung cấp không tồn tại: " + sanPhamDTO.getMaNhaCungCap()));
            existingSanPham.setNhaCungCap(nhaCungCap);
        }
        if (sanPhamDTO.getMaThuongHieu() != null) {
            ThuongHieu thuongHieu = thuongHieuRepository.findById(sanPhamDTO.getMaThuongHieu())
                    .orElseThrow(() -> new ResourceNotFoundException("Thương hiệu không tồn tại: " + sanPhamDTO.getMaThuongHieu()));
            existingSanPham.setThuongHieu(thuongHieu);
        }

        existingSanPham.setTenSanPham(sanPhamDTO.getTenSanPham());
        existingSanPham.setMoTa(sanPhamDTO.getMoTa());
        existingSanPham.setGiaNiemYet(sanPhamDTO.getGiaNiemYet());
        existingSanPham.setGiaBanMacDinh(sanPhamDTO.getGiaBanMacDinh());
        existingSanPham.setHinhAnhMacDinh(sanPhamDTO.getHinhAnhMacDinh());
        existingSanPham.setTrangThai(sanPhamDTO.getTrangThai());
        existingSanPham.setTrangThaiBan(sanPhamDTO.getTrangThaiBan());

        SanPham updatedSanPham = sanPhamRepository.save(existingSanPham);
        return sanPhamMapper.toDto(updatedSanPham);
    }

    @Override
    @Transactional
    public void deleteSanPham(Integer maSanPham) {
        SanPham sanPham = sanPhamRepository.findById(maSanPham)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại: " + maSanPham));
        sanPhamRepository.delete(sanPham);
    }

    @Override
    @Transactional(readOnly = true)
    public SanPhamDTO getSanPhamById(Integer maSanPham) {
        SanPham sanPham = sanPhamRepository.findById(maSanPham)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại: " + maSanPham));
        return sanPhamMapper.toDto(sanPham);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SanPhamDTO> getAllSanPham() {
        return sanPhamRepository.findAll().stream()
                .map(sanPhamMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByMaDanhMuc(Integer maDanhMuc) {
        return false; // Placeholder, cần triển khai repository cho DanhMuc
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByMaNhaCungCap(Integer maNhaCungCap) {
        return false; // Placeholder, cần triển khai repository cho NhaCungCap
    }
}

