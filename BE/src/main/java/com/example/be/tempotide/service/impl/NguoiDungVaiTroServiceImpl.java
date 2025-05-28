package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.NguoiDungVaiTroDTO;
import com.example.be.tempotide.entity.NguoiDungVaiTro;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.entity.VaiTro;
import com.example.be.tempotide.mapper.NguoiDungVaiTroMapper;
import com.example.be.tempotide.repository.NguoiDungVaiTroRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.repository.VaiTroRepository;
import com.example.be.tempotide.service.NguoiDungVaiTroService;
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
    private final NguoiDungVaiTroMapper nguoiDungVaiTroMapper;
    private final NhanVienRepository nhanVienRepository;
    private final VaiTroRepository vaiTroRepository;

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
        nguoiDungVaiTro.setNgaytao(LocalDateTime.now());

        NhanVien nhanVien = nhanVienRepository.findById(nguoiDungVaiTroDTO.getManhanvien())
                .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + nguoiDungVaiTroDTO.getManhanvien()));
        nguoiDungVaiTro.setManhanvien(nhanVien);

        VaiTro vaiTro = vaiTroRepository.findById(nguoiDungVaiTroDTO.getMavaitro())
                .orElseThrow(() -> new RuntimeException("VaiTro not found with ID: " + nguoiDungVaiTroDTO.getMavaitro()));
        nguoiDungVaiTro.setMavaitro(vaiTro);

        NguoiDungVaiTro savedNguoiDungVaiTro = nguoiDungVaiTroRepository.save(nguoiDungVaiTro);
        return nguoiDungVaiTroMapper.toDTO(savedNguoiDungVaiTro);
    }

    @Override
    @Transactional
    public NguoiDungVaiTroDTO updateNguoiDungVaiTro(Integer id, NguoiDungVaiTroDTO nguoiDungVaiTroDTO) {
        NguoiDungVaiTro existingNguoiDungVaiTro = nguoiDungVaiTroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NguoiDungVaiTro not found with ID: " + id));

        existingNguoiDungVaiTro.setTrangthai(nguoiDungVaiTroDTO.getTrangthai());

        NhanVien nhanVien = nhanVienRepository.findById(nguoiDungVaiTroDTO.getManhanvien())
                .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + nguoiDungVaiTroDTO.getManhanvien()));
        existingNguoiDungVaiTro.setManhanvien(nhanVien);

        VaiTro vaiTro = vaiTroRepository.findById(nguoiDungVaiTroDTO.getMavaitro())
                .orElseThrow(() -> new RuntimeException("VaiTro not found with ID: " + nguoiDungVaiTroDTO.getMavaitro()));
        existingNguoiDungVaiTro.setMavaitro(vaiTro);

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

    @Override
    public List<NguoiDungVaiTroDTO> getNguoiDungVaiTroByManhanvien(Integer manhanvien) {
        return nguoiDungVaiTroRepository.findByManhanvien_Manhanvien(manhanvien)
                .stream()
                .map(nguoiDungVaiTroMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NguoiDungVaiTroDTO> getNguoiDungVaiTroByMavaitro(Integer mavaitro) {
        return nguoiDungVaiTroRepository.findByMavaitro_Mavaitro(mavaitro)
                .stream()
                .map(nguoiDungVaiTroMapper::toDTO)
                .collect(Collectors.toList());
    }
}