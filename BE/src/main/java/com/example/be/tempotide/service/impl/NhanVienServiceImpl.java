package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.NhanVienDTO;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.NhanVienMapper;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.NhanVienService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NhanVienServiceImpl implements NhanVienService {
    private final NhanVienRepository nhanVienRepository;
    private final NhanVienMapper nhanVienMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<NhanVienDTO> getAllActiveEmployees() {
        return nhanVienRepository.findByTrangthaiTrue()
                .stream()
                .map(nhanVienMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NhanVienDTO getEmployeeById(Integer id) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        return nhanVienMapper.toDTO(nhanVien);
    }

    @Override
    @Transactional
    public NhanVienDTO createEmployee(NhanVienDTO nhanVienDTO) {
        // Kiểm tra trùng email
        if (nhanVienRepository.findByEmail(nhanVienDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + nhanVienDTO.getEmail());
        }

        // Kiểm tra định dạng số điện thoại
        if (nhanVienDTO.getSodienthoai() != null && !nhanVienDTO.getSodienthoai().matches("^[0-9]{10}$")) {
            throw new RuntimeException("Invalid phone number format");
        }

        NhanVien nhanVien = nhanVienMapper.toEntity(nhanVienDTO);
        // Băm mật khẩu
        nhanVien.setMatkhau(passwordEncoder.encode(nhanVienDTO.getMatkhau()));
        // Gán nguoitao từ người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        nhanVien.setNguoitao(nguoitao);
        nhanVien.setNguoicapnhat(nguoitao);

        nhanVien = nhanVienRepository.save(nhanVien);
        return nhanVienMapper.toDTO(nhanVien);
    }

    @Override
    @Transactional
    public NhanVienDTO updateEmployee(Integer id, NhanVienDTO nhanVienDTO) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        // Kiểm tra trùng email
        Optional<NhanVien> existingNhanVien = nhanVienRepository.findByEmail(nhanVienDTO.getEmail());
        if (existingNhanVien.isPresent() && !existingNhanVien.get().getManhanvien().equals(id)) {
            throw new RuntimeException("Email already exists: " + nhanVienDTO.getEmail());
        }

        // Kiểm tra định dạng số điện thoại
        if (nhanVienDTO.getSodienthoai() != null && !nhanVienDTO.getSodienthoai().matches("^[0-9]{10}$")) {
            throw new RuntimeException("Invalid phone number format");
        }

        nhanVien.setHo(nhanVienDTO.getHo());
        nhanVien.setTen(nhanVienDTO.getTen());
        nhanVien.setEmail(nhanVienDTO.getEmail());
        nhanVien.setSodienthoai(nhanVienDTO.getSodienthoai());
        nhanVien.setNgaytuyendung(nhanVienDTO.getNgaytuyendung());
        nhanVien.setTrangthai(nhanVienDTO.getTrangthai());
        // Cập nhật nguoicapnhat
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoicapnhat = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        nhanVien.setNguoicapnhat(nguoicapnhat);
        nhanVien.setNgaycapnhat(LocalDateTime.now());

        nhanVien = nhanVienRepository.save(nhanVien);
        return nhanVienMapper.toDTO(nhanVien);
    }

    @Override
    @Transactional
    public void deleteEmployee(Integer id) {
        NhanVien nhanVien = nhanVienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        nhanVien.setTrangthai(false);
        nhanVienRepository.save(nhanVien);
    }
}