package com.example.tempotide.service.impl;

import com.example.tempotide.dto.DanhMucDTO;
import com.example.tempotide.entity.DanhMuc;
import com.example.tempotide.entity.NhanVien;
import com.example.tempotide.mapper.DanhMucMapper;
import com.example.tempotide.repository.DanhMucRepository;
import com.example.tempotide.repository.NhanVienRepository;
import com.example.tempotide.service.DanhMucService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DanhMucServiceImpl implements DanhMucService {
    private final DanhMucRepository danhMucRepository;
    private final NhanVienRepository nhanVienRepository;
    private final DanhMucMapper danhMucMapper;

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

        if (danhMucDTO.getMadanhmuccha() != null) {
            DanhMuc madanhmuccha = danhMucRepository.findById(danhMucDTO.getMadanhmuccha())
                    .orElseThrow(() -> new RuntimeException("DanhMuc not found with ID: " + danhMucDTO.getMadanhmuccha()));
            danhMuc.setMadanhmuccha(madanhmuccha);
        }

        if (danhMucDTO.getNguoitao() != null) {
            NhanVien nguoitao = nhanVienRepository.findById(danhMucDTO.getNguoitao())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + danhMucDTO.getNguoitao()));
            danhMuc.setNguoitao(nguoitao);
        }

        danhMuc.setNgaytao(LocalDateTime.now());
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
        existingDanhMuc.setTrangthai(danhMucDTO.getTrangthai());

        if (danhMucDTO.getMadanhmuccha() != null) {
            DanhMuc madanhmuccha = danhMucRepository.findById(danhMucDTO.getMadanhmuccha())
                    .orElseThrow(() -> new RuntimeException("DanhMuc not found with ID: " + danhMucDTO.getMadanhmuccha()));
            existingDanhMuc.setMadanhmuccha(madanhmuccha);
        } else {
            existingDanhMuc.setMadanhmuccha(null);
        }

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