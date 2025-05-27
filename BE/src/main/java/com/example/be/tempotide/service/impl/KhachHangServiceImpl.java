package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.KhachHangDTO;
import com.example.be.tempotide.entity.KhachHang;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.KhachHangMapper;
import com.example.be.tempotide.repository.KhachHangRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.KhachHangService;
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
public class KhachHangServiceImpl implements KhachHangService {
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;
    private final KhachHangMapper khachHangMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<KhachHangDTO> getAllActiveCustomers() {
        return khachHangRepository.findByTrangthaiTrue()
                .stream()
                .map(khachHangMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public KhachHangDTO getCustomerById(Integer id) {
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
        return khachHangMapper.toDTO(khachHang);
    }

    @Override
    @Transactional
    public KhachHangDTO createCustomer(KhachHangDTO khachHangDTO) {
        // Kiểm tra trùng email
        if (khachHangRepository.findByEmail(khachHangDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + khachHangDTO.getEmail());
        }

        // Kiểm tra định dạng số điện thoại
        if (khachHangDTO.getSodienthoai() != null && !khachHangDTO.getSodienthoai().matches("^[0-9]{10}$")) {
            throw new RuntimeException("Invalid phone number format");
        }

        KhachHang khachHang = khachHangMapper.toEntity(khachHangDTO);
        // Băm mật khẩu
        khachHang.setMatkhau(passwordEncoder.encode(khachHangDTO.getMatkhau()));
        // Gán nguoitao từ người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        khachHang.setNguoitao(nguoitao);
        khachHang.setNguoicapnhat(nguoitao);

        khachHang = khachHangRepository.save(khachHang);
        return khachHangMapper.toDTO(khachHang);
    }

    @Override
    @Transactional
    public KhachHangDTO updateCustomer(Integer id, KhachHangDTO khachHangDTO) {
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));

        // Kiểm tra trùng email
        Optional<KhachHang> existingKhachHang = khachHangRepository.findByEmail(khachHangDTO.getEmail());
        if (existingKhachHang.isPresent() && !existingKhachHang.get().getMakhachhang().equals(id)) {
            throw new RuntimeException("Email already exists: " + khachHangDTO.getEmail());
        }

        // Kiểm tra định dạng số điện thoại
        if (khachHangDTO.getSodienthoai() != null && !khachHangDTO.getSodienthoai().matches("^[0-9]{10}$")) {
            throw new RuntimeException("Invalid phone number format");
        }

        khachHang.setHo(khachHangDTO.getHo());
        khachHang.setTen(khachHangDTO.getTen());
        khachHang.setEmail(khachHangDTO.getEmail());
        khachHang.setSodienthoai(khachHangDTO.getSodienthoai());
        khachHang.setDiachi(khachHangDTO.getDiachi());
        khachHang.setTrangthai(khachHangDTO.getTrangthai());
        // Cập nhật nguoicapnhat
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoicapnhat = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        khachHang.setNguoicapnhat(nguoicapnhat);
        khachHang.setNgaycapnhat(LocalDateTime.now());

        khachHang = khachHangRepository.save(khachHang);
        return khachHangMapper.toDTO(khachHang);
    }

    @Override
    @Transactional
    public void deleteCustomer(Integer id) {
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
        khachHang.setTrangthai(false);
        khachHangRepository.save(khachHang);
    }
}