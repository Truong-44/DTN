package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.VaiTroDTO;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.entity.VaiTro;
import com.example.be.tempotide.mapper.VaiTroMapper;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.repository.VaiTroRepository;
import com.example.be.tempotide.service.VaiTroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VaiTroServiceImpl implements VaiTroService {
    private final VaiTroRepository vaiTroRepository;
    private final NhanVienRepository nhanVienRepository;
    private final VaiTroMapper vaiTroMapper;

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

        if (vaiTroDTO.getNguoitao() != null) {
            NhanVien nguoitao = nhanVienRepository.findById(vaiTroDTO.getNguoitao())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + vaiTroDTO.getNguoitao()));
            vaiTro.setNguoitao(nguoitao);
        }

        vaiTro.setNgaytao(LocalDateTime.now());
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
        existingVaiTro.setTrangthai(vaiTroDTO.getTrangthai());

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