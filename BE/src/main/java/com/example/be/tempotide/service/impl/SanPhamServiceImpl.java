package com.example.tempotide.service.impl;

import com.example.tempotide.dto.SanPhamDTO;
import com.example.tempotide.entity.DanhMuc;
import com.example.tempotide.entity.NhanVien;
import com.example.tempotide.entity.SanPham;
import com.example.tempotide.mapper.SanPhamMapper;
import com.example.tempotide.repository.DanhMucRepository;
import com.example.tempotide.repository.NhanVienRepository;
import com.example.tempotide.repository.SanPhamRepository;
import com.example.tempotide.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SanPhamServiceImpl implements SanPhamService {
    private final SanPhamRepository sanPhamRepository;
    private final DanhMucRepository danhMucRepository;
    private final NhanVienRepository nhanVienRepository;
    private final SanPhamMapper sanPhamMapper;

    @Override
    public List<SanPhamDTO> getAllActiveProducts() {
        return sanPhamRepository.findByTrangthaiTrue()
                .stream()
                .map(sanPhamMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SanPhamDTO getProductById(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return sanPhamMapper.toDTO(sanPham);
    }

    @Override
    @Transactional
    public SanPhamDTO createProduct(SanPhamDTO sanPhamDTO) {
        // Kiểm tra trùng tensanpham
        if (sanPhamRepository.findByTensanpham(sanPhamDTO.getTensanpham()).isPresent()) {
            throw new RuntimeException("Product name already exists: " + sanPhamDTO.getTensanpham());
        }

        // Kiểm tra danh mục
        DanhMuc danhMuc = danhMucRepository.findById(sanPhamDTO.getMadanhmuc())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + sanPhamDTO.getMadanhmuc()));

        SanPham sanPham = sanPhamMapper.toEntity(sanPhamDTO);
        sanPham.setDanhMuc(danhMuc);

        // Gán nguoitao từ người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        sanPham.setNguoitao(nguoitao);
        sanPham.setNguoicapnhat(nguoitao);

        sanPham = sanPhamRepository.save(sanPham);
        return sanPhamMapper.toDTO(sanPham);
    }

    @Override
    @Transactional
    public SanPhamDTO updateProduct(Integer id, SanPhamDTO sanPhamDTO) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        // Kiểm tra trùng tensanpham
        Optional<SanPham> existingSanPham = sanPhamRepository.findByTensanpham(sanPhamDTO.getTensanpham());
        if (existingSanPham.isPresent() && !existingSanPham.get().getMasanpham().equals(id)) {
            throw new RuntimeException("Product name already exists: " + sanPhamDTO.getTensanpham());
        }

        // Kiểm tra danh mục
        DanhMuc danhMuc = danhMucRepository.findById(sanPhamDTO.getMadanhmuc())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + sanPhamDTO.getMadanhmuc()));

        sanPham.setTensanpham(sanPhamDTO.getTensanpham());
        sanPham.setMota(sanPhamDTO.getMota());
        sanPham.setGia(sanPhamDTO.getGia());
        sanPham.setTrangthai(sanPhamDTO.getTrangthai());
        sanPham.setDanhMuc(danhMuc);

        // Cập nhật nguoicapnhat
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoicapnhat = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        sanPham.setNguoicapnhat(nguoicapnhat);
        sanPham.setNgaycapnhat(LocalDateTime.now());

        sanPham = sanPhamRepository.save(sanPham);
        return sanPhamMapper.toDTO(sanPham);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        sanPham.setTrangthai(false);
        sanPhamRepository.save(sanPham);
    }
}