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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {
    private final KhachHangRepository khachHangRepository;
    private final KhachHangMapper khachHangMapper;
    private final NhanVienRepository nhanVienRepository;

    @Override
    public List<KhachHangDTO> getAllKhachHangs() {
        return khachHangRepository.findAll()
                .stream()
                .map(khachHangMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public KhachHangDTO getKhachHangById(Integer id) {
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + id));
        return khachHangMapper.toDTO(khachHang);
    }

    @Override
    @Transactional
    public KhachHangDTO createKhachHang(KhachHangDTO khachHangDTO) {
        // Kiểm tra email hoặc số điện thoại (theo constraint trong SQL)
        if (khachHangDTO.getEmail() == null && khachHangDTO.getSodienthoai() == null) {
            throw new IllegalArgumentException("Email hoặc số điện thoại phải có ít nhất một giá trị");
        }

        // Kiểm tra email đã tồn tại
        if (khachHangDTO.getEmail() != null && khachHangRepository.findByEmail(khachHangDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại: " + khachHangDTO.getEmail());
        }

        // Kiểm tra số điện thoại đã tồn tại
        if (khachHangDTO.getSodienthoai() != null && khachHangRepository.findBySodienthoai(khachHangDTO.getSodienthoai()).isPresent()) {
            throw new RuntimeException("Số điện thoại đã tồn tại: " + khachHangDTO.getSodienthoai());
        }

        KhachHang khachHang = khachHangMapper.toEntity(khachHangDTO);
        khachHang.setNgaytao(LocalDateTime.now());
        khachHang.setDiemtichluy(khachHangDTO.getDiemtichluy() != null ? khachHangDTO.getDiemtichluy() : 0);
        khachHang.setNhanthongbao(khachHangDTO.getNhanthongbao() != null ? khachHangDTO.getNhanthongbao() : false);

        // Gán nguoitao từ thông tin người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        khachHang.setNguoitao(nguoitao);
        khachHang.setNguoicapnhat(nguoitao);

        KhachHang savedKhachHang = khachHangRepository.save(khachHang);
        return khachHangMapper.toDTO(savedKhachHang);
    }

    @Override
    @Transactional
    public KhachHangDTO updateKhachHang(Integer id, KhachHangDTO khachHangDTO) {
        KhachHang existingKhachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + id));

        // Kiểm tra email hoặc số điện thoại
        if (khachHangDTO.getEmail() == null && khachHangDTO.getSodienthoai() == null) {
            throw new IllegalArgumentException("Email hoặc số điện thoại phải có ít nhất một giá trị");
        }

        // Kiểm tra email đã tồn tại (trừ chính bản ghi đang cập nhật)
        if (khachHangDTO.getEmail() != null && !khachHangDTO.getEmail().equals(existingKhachHang.getEmail())) {
            if (khachHangRepository.findByEmail(khachHangDTO.getEmail()).isPresent()) {
                throw new RuntimeException("Email đã tồn tại: " + khachHangDTO.getEmail());
            }
        }

        // Kiểm tra số điện thoại đã tồn tại (trừ chính bản ghi đang cập nhật)
        if (khachHangDTO.getSodienthoai() != null && !khachHangDTO.getSodienthoai().equals(existingKhachHang.getSodienthoai())) {
            if (khachHangRepository.findBySodienthoai(khachHangDTO.getSodienthoai()).isPresent()) {
                throw new RuntimeException("Số điện thoại đã tồn tại: " + khachHangDTO.getSodienthoai());
            }
        }

        existingKhachHang.setHo(khachHangDTO.getHo());
        existingKhachHang.setTen(khachHangDTO.getTen());
        existingKhachHang.setEmail(khachHangDTO.getEmail());
        existingKhachHang.setSodienthoai(khachHangDTO.getSodienthoai());
        existingKhachHang.setMatkhau(khachHangDTO.getMatkhau());
        existingKhachHang.setDiemtichluy(khachHangDTO.getDiemtichluy());
        existingKhachHang.setNhanthongbao(khachHangDTO.getNhanthongbao());
        existingKhachHang.setNgaytao(khachHangDTO.getNgaytao());
        existingKhachHang.setTrangthai(khachHangDTO.getTrangthai());

        // Gán nguoicapnhat từ thông tin người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoicapnhat = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        existingKhachHang.setNguoicapnhat(nguoicapnhat);

        KhachHang updatedKhachHang = khachHangRepository.save(existingKhachHang);
        return khachHangMapper.toDTO(updatedKhachHang);
    }

    @Override
    @Transactional
    public void deleteKhachHang(Integer id) {
        KhachHang khachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + id));
        khachHang.setTrangthai(false);
        khachHangRepository.save(khachHang);
    }
}