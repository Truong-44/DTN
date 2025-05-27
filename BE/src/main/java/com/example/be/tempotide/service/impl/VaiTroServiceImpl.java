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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VaiTroServiceImpl implements VaiTroService {
    private final VaiTroRepository vaiTroRepository;
    private final VaiTroMapper vaiTroMapper;
    private final NhanVienRepository nhanVienRepository;

    @Override
    public List<VaiTroDTO> getAllActiveRoles() {
        return vaiTroRepository.findByTrangthaiTrue()
                .stream()
                .map(vaiTroMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VaiTroDTO getRoleById(Integer id) {
        VaiTro vaiTro = vaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));
        return vaiTroMapper.toDTO(vaiTro);
    }

    @Override
    @Transactional
    public VaiTroDTO createRole(VaiTroDTO vaiTroDTO) {
        // Kiểm tra trùng tenvaitro
        if (vaiTroRepository.findByTenvaitro(vaiTroDTO.getTenvaitro()).isPresent()) {
            throw new RuntimeException("Role name already exists: " + vaiTroDTO.getTenvaitro());
        }

        VaiTro vaiTro = vaiTroMapper.toEntity(vaiTroDTO);
        // Gán nguoitao từ người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        vaiTro.setNguoitao(nguoitao);

        vaiTro = vaiTroRepository.save(vaiTro);
        return vaiTroMapper.toDTO(vaiTro);
    }

    @Override
    @Transactional
    public VaiTroDTO updateRole(Integer id, VaiTroDTO vaiTroDTO) {
        VaiTro vaiTro = vaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));

        // Kiểm tra trùng tenvaitro
        Optional<VaiTro> existingVaiTro = vaiTroRepository.findByTenvaitro(vaiTroDTO.getTenvaitro());
        if (existingVaiTro.isPresent() && !existingVaiTro.get().getMavaitro().equals(id)) {
            throw new RuntimeException("Role name already exists: " + vaiTroDTO.getTenvaitro());
        }

        vaiTro.setTenvaitro(vaiTroDTO.getTenvaitro());
        vaiTro.setMota(vaiTroDTO.getMota());
        vaiTro.setTrangthai(vaiTroDTO.getTrangthai());
        vaiTro = vaiTroRepository.save(vaiTro);
        return vaiTroMapper.toDTO(vaiTro);
    }

    @Override
    @Transactional
    public void deleteRole(Integer id) {
        VaiTro vaiTro = vaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));
        vaiTro.setTrangthai(false);
        vaiTroRepository.save(vaiTro);
    }
}