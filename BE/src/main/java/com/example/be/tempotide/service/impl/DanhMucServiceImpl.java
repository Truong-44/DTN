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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DanhMucServiceImpl implements DanhMucService {
    private final DanhMucRepository danhMucRepository;
    private final DanhMucMapper danhMucMapper;
    private final NhanVienRepository nhanVienRepository;

    @Override
    public List<DanhMucDTO> getAllDanhMucs() {
        return danhMucRepository.findAll()
                .stream()
                .map(danhMucMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DanhMucDTO getDanhMucById(Integer id) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DanhMuc not found with ID: " + id));
        return danhMucMapper.toDTO(danhMuc);
    }

    @Override
    @Transactional
    public DanhMucDTO createDanhMuc(DanhMucDTO danhMucDTO) {
        DanhMuc danhMuc = danhMucMapper.toEntity(danhMucDTO);
        danhMuc.setNgaytao(LocalDateTime.now());

        // Gán madanhmuccha (nếu có)
        if (danhMucDTO.getMadanhmuccha() != null) {
            DanhMuc danhMucCha = danhMucRepository.findById(danhMucDTO.getMadanhmuccha())
                    .orElseThrow(() -> new RuntimeException("DanhMucCha not found with ID: " + danhMucDTO.getMadanhmuccha()));
            danhMuc.setMadanhmuccha(danhMucCha);
        }

        // Gán nguoitao từ thông tin người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        danhMuc.setNguoitao(nguoitao);

        DanhMuc savedDanhMuc = danhMucRepository.save(danhMuc);
        return danhMucMapper.toDTO(savedDanhMuc);
    }

    @Override
    @Transactional
    public DanhMucDTO updateDanhMuc(Integer id, DanhMucDTO danhMucDTO) {
        DanhMuc existingDanhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DanhMuc not found with ID: " + id));

        existingDanhMuc.setTendanhmuc(danhMucDTO.getTendanhmuc());
        existingDanhMuc.setMota(danhMucDTO.getMota());

        // Cập nhật madanhmuccha (nếu có)
        if (danhMucDTO.getMadanhmuccha() != null) {
            DanhMuc danhMucCha = danhMucRepository.findById(danhMucDTO.getMadanhmuccha())
                    .orElseThrow(() -> new RuntimeException("DanhMucCha not found with ID: " + danhMucDTO.getMadanhmuccha()));
            existingDanhMuc.setMadanhmuccha(danhMucCha);
        } else {
            existingDanhMuc.setMadanhmuccha(null);
        }

        existingDanhMuc.setNgaytao(danhMucDTO.getNgaytao());
        existingDanhMuc.setTrangthai(danhMucDTO.getTrangthai());

        // Gán nguoitao từ thông tin người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        existingDanhMuc.setNguoitao(nguoitao);

        DanhMuc updatedDanhMuc = danhMucRepository.save(existingDanhMuc);
        return danhMucMapper.toDTO(updatedDanhMuc);
    }

    @Override
    @Transactional
    public void deleteDanhMuc(Integer id) {
        DanhMuc danhMuc = danhMucRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DanhMuc not found with ID: " + id));
        danhMuc.setTrangthai(false);
        danhMucRepository.save(danhMuc);
    }
}