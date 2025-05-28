package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.SanPhamDTO;
import com.example.be.tempotide.entity.SanPham;
import com.example.be.tempotide.entity.DanhMuc;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.SanPhamMapper;
import com.example.be.tempotide.repository.SanPhamRepository;
import com.example.be.tempotide.repository.DanhMucRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.SanPhamService;
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
    private final SanPhamMapper sanPhamMapper;
    private final DanhMucRepository danhMucRepository;
    private final NhanVienRepository nhanVienRepository;

    @Override
    public List<SanPhamDTO> getAllSanPhams() {
        return sanPhamRepository.findAll()
                .stream()
                .map(sanPhamMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SanPhamDTO getSanPhamById(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + id));
        return sanPhamMapper.toDTO(sanPham);
    }

    @Override
    @Transactional
    public SanPhamDTO createSanPham(SanPhamDTO sanPhamDTO) {
        // Kiểm tra tên sản phẩm đã tồn tại
        if (sanPhamRepository.findByTensanpham(sanPhamDTO.getTensanpham()).isPresent()) {
            throw new RuntimeException("Tên sản phẩm đã tồn tại: " + sanPhamDTO.getTensanpham());
        }

        SanPham sanPham = sanPhamMapper.toEntity(sanPhamDTO);
        sanPham.setNgaytao(LocalDateTime.now());

        // Gán madanhmuc
        DanhMuc danhMuc = danhMucRepository.findById(sanPhamDTO.getMadanhmuc())
                .orElseThrow(() -> new RuntimeException("DanhMuc not found with ID: " + sanPhamDTO.getMadanhmuc()));
        sanPham.setMadanhmuc(danhMuc);

        // Gán nguoitao từ thông tin người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        sanPham.setNguoitao(nguoitao);
        sanPham.setNguoicapnhat(nguoitao);

        SanPham savedSanPham = sanPhamRepository.save(sanPham);
        return sanPhamMapper.toDTO(savedSanPham);
    }

    @Override
    @Transactional
    public SanPhamDTO updateSanPham(Integer id, SanPhamDTO sanPhamDTO) {
        SanPham existingSanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + id));

        // Kiểm tra tên sản phẩm (trừ chính bản ghi đang cập nhật)
        if (!sanPhamDTO.getTensanpham().equals(existingSanPham.getTensanpham()) &&
                sanPhamRepository.findByTensanpham(sanPhamDTO.getTensanpham()).isPresent()) {
            throw new RuntimeException("Tên sản phẩm đã tồn tại: " + sanPhamDTO.getTensanpham());
        }

        existingSanPham.setTensanpham(sanPhamDTO.getTensanpham());
        existingSanPham.setMota(sanPhamDTO.getMota());
        existingSanPham.setGia(sanPhamDTO.getGia());
        existingSanPham.setNgaytao(sanPhamDTO.getNgaytao());
        existingSanPham.setTrangthai(sanPhamDTO.getTrangthai());

        // Cập nhật madanhmuc
        DanhMuc danhMuc = danhMucRepository.findById(sanPhamDTO.getMadanhmuc())
                .orElseThrow(() -> new RuntimeException("DanhMuc not found with ID: " + sanPhamDTO.getMadanhmuc()));
        existingSanPham.setMadanhmuc(danhMuc);

        // Gán nguoicapnhat từ thông tin người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoicapnhat = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        existingSanPham.setNguoicapnhat(nguoicapnhat);

        SanPham updatedSanPham = sanPhamRepository.save(existingSanPham);
        return sanPhamMapper.toDTO(updatedSanPham);
    }

    @Override
    @Transactional
    public void deleteSanPham(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + id));
        sanPham.setTrangthai(false);
        sanPhamRepository.save(sanPham);
    }
}