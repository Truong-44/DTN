package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.NhanVienDTO;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.NhanVienMapper;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.NhanVienService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NhanVienServiceImpl implements NhanVienService {
    private final NhanVienRepository nhanVienRepository;
    private final NhanVienMapper nhanVienMapper;

    @Override
    public List<NhanVienDTO> getAllNhanViens() {
        return nhanVienRepository.findAll()
                .stream()
                .map(nhanVienMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NhanVienDTO getNhanVienById(Integer id) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + id));
        return nhanVienMapper.toDTO(nhanVien);
    }

    @Override
    @Transactional
    public NhanVienDTO createNhanVien(NhanVienDTO nhanVienDTO) {
        NhanVien nhanVien = nhanVienMapper.toEntity(nhanVienDTO);
        nhanVien.setNgaytao(LocalDateTime.now());
        nhanVien.setNgaycapnhat(LocalDateTime.now());

        // Gán nguoitao từ thông tin người dùng hiện tại (giả sử dùng Spring Security)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        nhanVien.setNguoitao(nguoitao);
        nhanVien.setNguoicapnhat(nguoitao);

        NhanVien savedNhanVien = nhanVienRepository.save(nhanVien);
        return nhanVienMapper.toDTO(savedNhanVien);
    }

    @Override
    @Transactional
    public NhanVienDTO updateNhanVien(Integer id, NhanVienDTO nhanVienDTO) {
        NhanVien existingNhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + id));

        existingNhanVien.setHo(nhanVienDTO.getHo());
        existingNhanVien.setTen(nhanVienDTO.getTen());
        existingNhanVien.setEmail(nhanVienDTO.getEmail());
        existingNhanVien.setSodienthoai(nhanVienDTO.getSodienthoai());
        existingNhanVien.setNgaytuyendung(nhanVienDTO.getNgaytuyendung());
        existingNhanVien.setMatkhau(nhanVienDTO.getMatkhau());
        existingNhanVien.setNgaycapnhat(LocalDateTime.now());
        existingNhanVien.setTrangthai(nhanVienDTO.getTrangthai());

        // Gán nguoicapnhat từ thông tin người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoicapnhat = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        existingNhanVien.setNguoicapnhat(nguoicapnhat);

        NhanVien updatedNhanVien = nhanVienRepository.save(existingNhanVien);
        return nhanVienMapper.toDTO(updatedNhanVien);
    }

    @Override
    @Transactional
    public void deleteNhanVien(Integer id) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + id));
        nhanVien.setTrangthai(false);
        nhanVienRepository.save(nhanVien);
    }
}