package com.example.tempotide.service.impl;

import com.example.tempotide.dto.NguoiDungVaiTroDTO;
import com.example.tempotide.entity.KhachHang;
import com.example.tempotide.entity.NhanVien;
import com.example.tempotide.entity.NguoiDungVaiTro;
import com.example.tempotide.entity.VaiTro;
import com.example.tempotide.mapper.NguoiDungVaiTroMapper;
import com.example.tempotide.repository.KhachHangRepository;
import com.example.tempotide.repository.NhanVienRepository;
import com.example.tempotide.repository.NguoiDungVaiTroRepository;
import com.example.tempotide.repository.VaiTroRepository;
import com.example.tempotide.service.NguoiDungVaiTroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NguoiDungVaiTroServiceImpl implements NguoiDungVaiTroService {
    private final NguoiDungVaiTroRepository nguoiDungVaiTroRepository;
    private final NhanVienRepository nhanVienRepository;
    private final KhachHangRepository khachHangRepository;
    private final VaiTroRepository vaiTroRepository;
    private final NguoiDungVaiTroMapper nguoiDungVaiTroMapper;

    @Override
    public List<NguoiDungVaiTroDTO> getAllNguoiDungVaiTros() {
        return nguoiDungVaiTroRepository.findAll()
                .stream()
                .map(nguoiDungVaiTroMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NguoiDungVaiTroDTO getNguoiDungVaiTroById(Integer id) {
        NguoiDungVaiTro nguoiDungVaiTro = nguoiDungVaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NguoiDungVaiTro not found with ID: " + id));
        return nguoiDungVaiTroMapper.toDTO(nguoiDungVaiTro);
    }

    @Override
    @Transactional
    public NguoiDungVaiTroDTO createNguoiDungVaiTro(NguoiDungVaiTroDTO nguoiDungVaiTroDTO) {
        NguoiDungVaiTro nguoiDungVaiTro = nguoiDungVaiTroMapper.toEntity(nguoiDungVaiTroDTO);

        if (nguoiDungVaiTroDTO.getManhanvien() != null) {
            NhanVien nhanVien = nhanVienRepository.findById(nguoiDungVaiTroDTO.getManhanvien())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + nguoiDungVaiTroDTO.getManhanvien()));
            nguoiDungVaiTro.setManhanvien(nhanVien);
        }

        if (nguoiDungVaiTroDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(nguoiDungVaiTroDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + nguoiDungVaiTroDTO.getMakhachhang()));
            nguoiDungVaiTro.setMakhachhang(khachHang);
        }

        VaiTro vaiTro = vaiTroRepository.findById(nguoiDungVaiTroDTO.getMavaitro())
                .orElseThrow(() -> new RuntimeException("VaiTro not found with ID: " + nguoiDungVaiTroDTO.getMavaitro()));
        nguoiDungVaiTro.setMavaitro(vaiTro);

        if (nguoiDungVaiTroDTO.getNguoitao() != null) {
            NhanVien nguoitao = nhanVienRepository.findById(nguoiDungVaiTroDTO.getNguoitao())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + nguoiDungVaiTroDTO.getNguoitao()));
            nguoiDungVaiTro.setNguoitao(nguoitao);
        }

        nguoiDungVaiTro.setNgaytao(LocalDateTime.now());
        NguoiDungVaiTro savedNguoiDungVaiTro = nguoiDungVaiTroRepository.save(nguoiDungVaiTro);
        return nguoiDungVaiTroMapper.toDTO(savedNguoiDungVaiTro);
    }

    @Override
    @Transactional
    public NguoiDungVaiTroDTO updateNguoiDungVaiTro(Integer id, NguoiDungVaiTroDTO nguoiDungVaiTroDTO) {
        NguoiDungVaiTro existingNguoiDungVaiTro = nguoiDungVaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NguoiDungVaiTro not found with ID: " + id));

        if (nguoiDungVaiTroDTO.getManhanvien() != null) {
            NhanVien nhanVien = nhanVienRepository.findById(nguoiDungVaiTroDTO.getManhanvien())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + nguoiDungVaiTroDTO.getManhanvien()));
            existingNguoiDungVaiTro.setManhanvien(nhanVien);
        } else {
            existingNguoiDungVaiTro.setManhanvien(null);
        }

        if (nguoiDungVaiTroDTO.getMakhachhang() != null) {
            KhachHang khachHang = khachHangRepository.findById(nguoiDungVaiTroDTO.getMakhachhang())
                    .orElseThrow(() -> new RuntimeException("KhachHang not found with ID: " + nguoiDungVaiTroDTO.getMakhachhang()));
            existingNguoiDungVaiTro.setMakhachhang(khachHang);
        } else {
            existingNguoiDungVaiTro.setMakhachhang(null);
        }

        VaiTro vaiTro = vaiTroRepository.findById(nguoiDungVaiTroDTO.getMavaitro())
                .orElseThrow(() -> new RuntimeException("VaiTro not found with ID: " + nguoiDungVaiTroDTO.getMavaitro()));
        existingNguoiDungVaiTro.setMavaitro(vaiTro);

        existingNguoiDungVaiTro.setTrangthai(nguoiDungVaiTroDTO.getTrangthai());

        NguoiDungVaiTro updatedNguoiDungVaiTro = nguoiDungVaiTroRepository.save(existingNguoiDungVaiTro);
        return nguoiDungVaiTroMapper.toDTO(updatedNguoiDungVaiTro);
    }

    @Override
    @Transactional
    public void deleteNguoiDungVaiTro(Integer id) {
        NguoiDungVaiTro nguoiDungVaiTro = nguoiDungVaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NguoiDungVaiTro not found with ID: " + id));
        nguoiDungVaiTro.setTrangthai(false);
        nguoiDungVaiTroRepository.save(nguoiDungVaiTro);
    }
}