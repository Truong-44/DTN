package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.KhachHangDTO;
import com.example.be.tempotide.entity.KhachHang;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.KhachHangMapper;
import com.example.be.tempotide.repository.KhachHangRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {
    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;
    private final KhachHangMapper khachHangMapper;

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
        KhachHang khachHang = khachHangMapper.toEntity(khachHangDTO);

        if (khachHangDTO.getNguoitao() != null) {
            NhanVien nguoitao = nhanVienRepository.findById(khachHangDTO.getNguoitao())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + khachHangDTO.getNguoitao()));
            khachHang.setNguoitao(nguoitao);
        }

        if (khachHangDTO.getNguoicapnhat() != null) {
            NhanVien nguoicapnhat = nhanVienRepository.findById(khachHangDTO.getNguoicapnhat())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + khachHangDTO.getNguoicapnhat()));
            khachHang.setNguoicapnhat(nguoicapnhat);
        }

        khachHang.setNgaytao(LocalDateTime.now());
        KhachHang savedKhachHang = khachHangRepository.save(khachHang);
        return khachHangMapper.toDTO(savedKhachHang);
    }

    @Override
    @Transactional
    public KhachHangDTO updateKhachHang(Integer id, KhachHangDTO khachHangDTO) {
        KhachHang existingKhachHang = khachHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + id));

        existingKhachHang.setHo(khachHangDTO.getHo());
        existingKhachHang.setTen(khachHangDTO.getTen());
        existingKhachHang.setEmail(khachHangDTO.getEmail());
        existingKhachHang.setSodienthoai(khachHangDTO.getSodienthoai());
        existingKhachHang.setMatkhau(khachHangDTO.getMatkhau());
        existingKhachHang.setDiemtichluy(khachHangDTO.getDiemtichluy());
        existingKhachHang.setNhanthongbao(khachHangDTO.getNhanthongbao());
        existingKhachHang.setTrangthai(khachHangDTO.getTrangthai());

        if (khachHangDTO.getNguoicapnhat() != null) {
            NhanVien nguoicapnhat = nhanVienRepository.findById(khachHangDTO.getNguoicapnhat())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + khachHangDTO.getNguoicapnhat()));
            existingKhachHang.setNguoicapnhat(nguoicapnhat);
        }

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