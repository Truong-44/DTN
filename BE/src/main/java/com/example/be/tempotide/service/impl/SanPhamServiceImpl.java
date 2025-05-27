package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.entity.SanPham;
import com.example.be.tempotide.repository.SanPhamRepository;
import com.example.be.tempotide.service.SanPhamService;
import com.example.tempotide.backend.dto.SanPhamDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SanPhamServiceImpl implements SanPhamService {
    private final SanPhamRepository sanPhamRepository;
    private final SanPhamMapper sanPhamMapper;
    private final DanhMucRepository danhMucRepository;
    private final NhaCungCapRepository nhaCungCapRepository;
    private final ThuongHieuRepository thuongHieuRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<SanPhamDTO> getAllSanPham(Pageable pageable) {
        log.info("Fetching all products with pagination: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return sanPhamRepository.findAll(pageable).map(sanPhamMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public SanPhamDTO getSanPhamById(Integer id) {
        log.info("Fetching product with ID: {}", id);
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại với ID: " + id));
        return sanPhamMapper.toDTO(sanPham);
    }

    @Override
    @Transactional
    public SanPhamDTO createSanPham(SanPhamDTO dto) {
        log.info("Creating new product: {}", dto.getTensanpham());
        if (sanPhamRepository.existsByTensanpham(dto.getTensanpham())) {
            throw new IllegalArgumentException("Tên sản phẩm đã tồn tại: " + dto.getTensanpham());
        }

        SanPham sanPham = sanPhamMapper.toEntity(dto);
        sanPham.setDanhmuc(danhMucRepository.findById(dto.getMadanhmuc())
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại với ID: " + dto.getMadanhmuc())));
        sanPham.setNhacungcap(nhaCungCapRepository.findById(dto.getManhacungcap())
                .orElseThrow(() -> new ResourceNotFoundException("Nhà cung cấp không tồn tại với ID: " + dto.getManhacungcap())));
        if (dto.getMathuonghieu() != null) {
            sanPham.setThuonghieu(thuongHieuRepository.findById(dto.getMathuonghieu())
                    .orElseThrow(() -> new ResourceNotFoundException("Thương hiệu không tồn tại với ID: " + dto.getMathuonghieu())));
        }

        sanPham.setNgaytao(LocalDateTime.now());
        sanPham.setNgaycapnhat(LocalDateTime.now());
        sanPham.setTrangthai(true);
        sanPham.setTrangthaiban(true);
        sanPham = sanPhamRepository.save(sanPham);
        log.info("Product created successfully with ID: {}", sanPham.getMasanpham());
        return sanPhamMapper.toDTO(sanPham);
    }

    @Override
    @Transactional
    public SanPhamDTO updateSanPham(Integer id, SanPhamDTO dto) {
        log.info("Updating product with ID: {}", id);
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại với ID: " + id));

        sanPham = sanPhamMapper.toEntity(dto);
        sanPham.setMasanpham(id);
        sanPham.setDanhmuc(danhMucRepository.findById(dto.getMadanhmuc())
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại với ID: " + dto.getMadanhmuc())));
        sanPham.setNhacungcap(nhaCungCapRepository.findById(dto.getManhacungcap())
                .orElseThrow(() -> new ResourceNotFoundException("Nhà cung cấp không tồn tại với ID: " + dto.getManhacungcap())));
        sanPham.setThuonghieu(dto.getMathuonghieu() != null ? thuongHieuRepository.findById(dto.getMathuonghieu())
                .orElseThrow(() -> new ResourceNotFoundException("Thương hiệu không tồn tại với ID: " + dto.getMathuonghieu())) : null);
        sanPham.setNgaycapnhat(LocalDateTime.now());
        sanPham = sanPhamRepository.save(sanPham);
        log.info("Product updated successfully with ID: {}", id);
        return sanPhamMapper.toDTO(sanPham);
    }

    @Override
    @Transactional
    public void deleteSanPham(Integer id) {
        log.info("Deleting product with ID: {}", id);
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại với ID: " + id));
        sanPham.setTrangthai(false);
        sanPhamRepository.save(sanPham);
        log.info("Product deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SanPhamDTO> searchSanPham(String keyword, Pageable pageable) {
        log.info("Searching products with keyword: {}, page={}, size={}", keyword, pageable.getPageNumber(), pageable.getPageSize());
        return sanPhamRepository.findByTensanphamContainingIgnoreCase(keyword, pageable)
                .map(sanPhamMapper::toDTO);
    }
}