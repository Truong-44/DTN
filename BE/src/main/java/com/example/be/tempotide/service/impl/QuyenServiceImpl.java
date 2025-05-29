package com.example.tempotide.service.impl;

import com.example.tempotide.dto.QuyenDTO;
import com.example.tempotide.entity.NhanVien;
import com.example.tempotide.entity.Quyen;
import com.example.tempotide.mapper.QuyenMapper;
import com.example.tempotide.repository.NhanVienRepository;
import com.example.tempotide.repository.QuyenRepository;
import com.example.tempotide.service.QuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuyenServiceImpl implements QuyenService {
    private final QuyenRepository quyenRepository;
    private final NhanVienRepository nhanVienRepository;
    private final QuyenMapper quyenMapper;

    @Override
    public List<QuyenDTO> getAllQuyens() {
        return quyenRepository.findAll()
                .stream()
                .map(quyenMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QuyenDTO getQuyenById(Integer id) {
        Quyen quyen = quyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quyen not found with ID: " + id));
        return quyenMapper.toDTO(quyen);
    }

    @Override
    @Transactional
    public QuyenDTO createQuyen(QuyenDTO quyenDTO) {
        Quyen quyen = quyenMapper.toEntity(quyenDTO);

        if (quyenDTO.getNguoitao() != null) {
            NhanVien nguoitao = nhanVienRepository.findById(quyenDTO.getNguoitao())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + quyenDTO.getNguoitao()));
            quyen.setNguoitao(nguoitao);
        }

        quyen.setNgaytao(LocalDateTime.now());
        Quyen savedQuyen = quyenRepository.save(quyen);
        return quyenMapper.toDTO(savedQuyen);
    }

    @Override
    @Transactional
    public QuyenDTO updateQuyen(Integer id, QuyenDTO quyenDTO) {
        Quyen existingQuyen = quyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quyen not found with ID: " + id));

        existingQuyen.setTenquyen(quyenDTO.getTenquyen());
        existingQuyen.setMota(quyenDTO.getMota());
        existingQuyen.setTrangthai(quyenDTO.getTrangthai());

        Quyen updatedQuyen = quyenRepository.save(existingQuyen);
        return quyenMapper.toDTO(updatedQuyen);
    }

    @Override
    @Transactional
    public void deleteQuyen(Integer id) {
        Quyen quyen = quyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quyen not found with ID: " + id));
        quyen.setTrangthai(false);
        quyenRepository.save(quyen);
    }
}