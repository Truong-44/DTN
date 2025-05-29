package com.example.tempotide.service.impl;

import com.example.tempotide.dto.VaiTroQuyenDTO;
import com.example.tempotide.entity.NhanVien;
import com.example.tempotide.entity.Quyen;
import com.example.tempotide.entity.VaiTro;
import com.example.tempotide.entity.VaiTroQuyen;
import com.example.tempotide.mapper.VaiTroQuyenMapper;
import com.example.tempotide.repository.NhanVienRepository;
import com.example.tempotide.repository.QuyenRepository;
import com.example.tempotide.repository.VaiTroQuyenRepository;
import com.example.tempotide.repository.VaiTroRepository;
import com.example.tempotide.service.VaiTroQuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VaiTroQuyenServiceImpl implements VaiTroQuyenService {
    private final VaiTroQuyenRepository vaiTroQuyenRepository;
    private final VaiTroRepository vaiTroRepository;
    private final QuyenRepository quyenRepository;
    private final NhanVienRepository nhanVienRepository;
    private final VaiTroQuyenMapper vaiTroQuyenMapper;

    @Override
    public List<VaiTroQuyenDTO> getAllVaiTroQuyens() {
        return vaiTroQuyenRepository.findAll()
                .stream()
                .map(vaiTroQuyenMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VaiTroQuyenDTO getVaiTroQuyenById(Integer id) {
        VaiTroQuyen vaiTroQuyen = vaiTroQuyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VaiTroQuyen not found with ID: " + id));
        return vaiTroQuyenMapper.toDTO(vaiTroQuyen);
    }

    @Override
    @Transactional
    public VaiTroQuyenDTO createVaiTroQuyen(VaiTroQuyenDTO vaiTroQuyenDTO) {
        VaiTroQuyen vaiTroQuyen = vaiTroQuyenMapper.toEntity(vaiTroQuyenDTO);

        VaiTro vaiTro = vaiTroRepository.findById(vaiTroQuyenDTO.getMavaitro())
                .orElseThrow(() -> new RuntimeException("VaiTro not found with ID: " + vaiTroQuyenDTO.getMavaitro()));
        vaiTroQuyen.setMavaitro(vaiTro);

        Quyen quyen = quyenRepository.findById(vaiTroQuyenDTO.getMaquyen())
                .orElseThrow(() -> new RuntimeException("Quyen not found with ID: " + vaiTroQuyenDTO.getMaquyen()));
        vaiTroQuyen.setMaquyen(quyen);

        if (vaiTroQuyenDTO.getNguoitao() != null) {
            NhanVien nguoitao = nhanVienRepository.findById(vaiTroQuyenDTO.getNguoitao())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + vaiTroQuyenDTO.getNguoitao()));
            vaiTroQuyen.setNguoitao(nguoitao);
        }

        vaiTroQuyen.setNgaytao(LocalDateTime.now());
        VaiTroQuyen savedVaiTroQuyen = vaiTroQuyenRepository.save(vaiTroQuyen);
        return vaiTroQuyenMapper.toDTO(savedVaiTroQuyen);
    }

    @Override
    @Transactional
    public VaiTroQuyenDTO updateVaiTroQuyen(Integer id, VaiTroQuyenDTO vaiTroQuyenDTO) {
        VaiTroQuyen existingVaiTroQuyen = vaiTroQuyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VaiTroQuyen not found with ID: " + id));

        VaiTro vaiTro = vaiTroRepository.findById(vaiTroQuyenDTO.getMavaitro())
                .orElseThrow(() -> new RuntimeException("VaiTro not found with ID: " + vaiTroQuyenDTO.getMavaitro()));
        existingVaiTroQuyen.setMavaitro(vaiTro);

        Quyen quyen = quyenRepository.findById(vaiTroQuyenDTO.getMaquyen())
                .orElseThrow(() -> new RuntimeException("Quyen not found with ID: " + vaiTroQuyenDTO.getMaquyen()));
        existingVaiTroQuyen.setMaquyen(quyen);

        existingVaiTroQuyen.setTrangthai(vaiTroQuyenDTO.getTrangthai());

        VaiTroQuyen updatedVaiTroQuyen = vaiTroQuyenRepository.save(existingVaiTroQuyen);
        return vaiTroQuyenMapper.toDTO(updatedVaiTroQuyen);
    }

    @Override
    @Transactional
    public void deleteVaiTroQuyen(Integer id) {
        VaiTroQuyen vaiTroQuyen = vaiTroQuyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VaiTroQuyen not found with ID: " + id));
        vaiTroQuyen.setTrangthai(false);
        vaiTroQuyenRepository.save(vaiTroQuyen);
    }
}