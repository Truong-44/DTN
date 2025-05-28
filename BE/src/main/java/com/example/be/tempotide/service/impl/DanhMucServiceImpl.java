package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.DanhMucDTO;
import com.example.be.tempotide.entity.DanhMuc;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.DanhMucMapper;
import com.example.be.tempotide.repository.DanhMucRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.DanhMucService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DanhMucServiceImpl implements DanhMucService {
    private final DanhMucRepository danhMucRepository;
    private final NhanVienRepository nhanVienRepository;
    private final DanhMucMapper danhMucMapper;

    @Override
    public List<DanhMucDTO> getAllActiveCategories() {
        return danhMucRepository.findByTrangthaiTrue()
                .stream()
                .map(danhMucMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DanhMucDTO getCategoryById(Integer id) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
        return danhMucMapper.toDTO(danhMuc);
    }

    @Override
    @Transactional
    public DanhMucDTO createCategory(DanhMucDTO danhMucDTO) {
        // Kiểm tra trùng tendanhmuc
        if (danhMucRepository.findByTendanhmuc(danhMucDTO.getTendanhmuc()).isPresent()) {
            throw new RuntimeException("Category name already exists: " + danhMucDTO.getTendanhmuc());
        }

        DanhMuc danhMuc = danhMucMapper.toEntity(danhMucDTO);
        // Gán danh mục cha
        if (danhMucDTO.getDanhmucchaId() != null) {
            DanhMuc danhMucCha = danhMucRepository.findById(danhMucDTO.getDanhmucchaId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found with ID: " + danhMucDTO.getDanhmucchaId()));
            danhMuc.setDanhmuccha(danhMucCha);
        }

        // Gán nguoitao từ người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        danhMuc.setNguoitao(nguoitao);
        danhMuc.setNguoicapnhat(nguoitao);

        danhMuc = danhMucRepository.save(danhMuc);
        return danhMucMapper.toDTO(danhMuc);
    }

    @Override
    @Transactional
    public DanhMucDTO updateCategory(Integer id, DanhMucDTO danhMucDTO) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));

        // Kiểm tra trùng tendanhmuc
        Optional<DanhMuc> existingDanhMuc = danhMucRepository.findByTendanhmuc(danhMucDTO.getTendanhmuc());
        if (existingDanhMuc.isPresent() && !existingDanhMuc.get().getMadanhmuc().equals(id)) {
            throw new RuntimeException("Category name already exists: " + danhMucDTO.getTendanhmuc());
        }

        // Cập nhật danh mục cha
        if (danhMucDTO.getDanhmucchaId() != null) {
            DanhMuc danhMucCha = danhMucRepository.findById(danhMucDTO.getDanhmucchaId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found with ID: " + danhMucDTO.getDanhmucchaId()));
            if (danhMucCha.getMadanhmuc().equals(id)) {
                throw new RuntimeException("Category cannot be its own parent");
            }
            danhMuc.setDanhmuccha(danhMucCha);
        } else {
            danhMuc.setDanhmuccha(null);
        }

        danhMuc.setTendanhmuc(danhMucDTO.getTendanhmuc());
        danhMuc.setMota(danhMucDTO.getMota());
        danhMuc.setTrangthai(danhMucDTO.getTrangthai());

        // Cập nhật nguoicapnhat
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoicapnhat = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        danhMuc.setNguoicapnhat(nguoicapnhat);
        danhMuc.setNgaycapnhat(LocalDateTime.now());

        danhMuc = danhMucRepository.save(danhMuc);
        return danhMucMapper.toDTO(danhMuc);
    }

    @Override
    @Transactional
    public void deleteCategory(Integer id) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
        danhMuc.setTrangthai(false);
        danhMucRepository.save(danhMuc);
    }
}