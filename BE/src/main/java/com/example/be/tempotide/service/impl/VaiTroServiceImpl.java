package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.VaiTroDTO;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.entity.VaiTro;
import com.example.be.tempotide.mapper.VaiTroMapper;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.repository.VaiTroRepository;
import com.example.be.tempotide.service.VaiTroService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VaiTroServiceImpl implements VaiTroService {
    private final VaiTroRepository vaiTroRepository;
    private final VaiTroMapper vaiTroMapper;
    private final NhanVienRepository nhanVienRepository; // Thêm để lấy nguoitao

    @Override
    public List<VaiTroDTO> getAllVaiTros() {
        return vaiTroRepository.findAll()
                .stream()
                .map(vaiTroMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VaiTroDTO getVaiTroById(Integer id) {
        VaiTro vaiTro = vaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VaiTro not found with ID: " + id));
        return vaiTroMapper.toDTO(vaiTro);
    }

    @Override
    @Transactional
    public VaiTroDTO createVaiTro(VaiTroDTO vaiTroDTO) {
        VaiTro vaiTro = vaiTroMapper.toEntity(vaiTroDTO);
        vaiTro.setNgaytao(LocalDateTime.now());

        // Gán nguoitao từ thông tin người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        vaiTro.setNguoitao(nguoitao);

        VaiTro savedVaiTro = vaiTroRepository.save(vaiTro);
        return vaiTroMapper.toDTO(savedVaiTro);
    }

    @Override
    @Transactional
    public VaiTroDTO updateVaiTro(Integer id, VaiTroDTO vaiTroDTO) {
        VaiTro existingVaiTro = vaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VaiTro not found with ID: " + id));

        existingVaiTro.setTenvaitro(vaiTroDTO.getTenvaitro());
        existingVaiTro.setMota(vaiTroDTO.getMota());
        existingVaiTro.setNgaytao(vaiTroDTO.getNgaytao());
        existingVaiTro.setTrangthai(vaiTroDTO.getTrangthai());

        // Gán nguoitao từ thông tin người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        existingVaiTro.setNguoitao(nguoitao);

        VaiTro updatedVaiTro = vaiTroRepository.save(existingVaiTro);
        return vaiTroMapper.toDTO(updatedVaiTro);
    }

    @Override
    @Transactional
    public void deleteVaiTro(Integer id) {
        VaiTro vaiTro = vaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VaiTro not found with ID: " + id));
        vaiTro.setTrangthai(false);
        vaiTroRepository.save(vaiTro);
    }
}