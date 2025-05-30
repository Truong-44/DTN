package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.NhanVienDTO;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.NhanVienMapper;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.NhanVienService;
import lombok.RequiredArgsConstructor;
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

        if (nhanVienDTO.getNguoitao() != null) {
            NhanVien nguoitao = nhanVienRepository.findById(nhanVienDTO.getNguoitao())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + nhanVienDTO.getNguoitao()));
            nhanVien.setNguoitao(nguoitao);
        }

        if (nhanVienDTO.getNguoicapnhat() != null) {
            NhanVien nguoicapnhat = nhanVienRepository.findById(nhanVienDTO.getNguoicapnhat())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + nhanVienDTO.getNguoicapnhat()));
            nhanVien.setNguoicapnhat(nguoicapnhat);
        }

        nhanVien.setNgaytao(LocalDateTime.now());
        nhanVien.setNgaycapnhat(LocalDateTime.now());
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
        existingNhanVien.setTrangthai(nhanVienDTO.getTrangthai());
        existingNhanVien.setNgaycapnhat(LocalDateTime.now());

        if (nhanVienDTO.getNguoicapnhat() != null) {
            NhanVien nguoicapnhat = nhanVienRepository.findById(nhanVienDTO.getNguoicapnhat())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + nhanVienDTO.getNguoicapnhat()));
            existingNhanVien.setNguoicapnhat(nguoicapnhat);
        }

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