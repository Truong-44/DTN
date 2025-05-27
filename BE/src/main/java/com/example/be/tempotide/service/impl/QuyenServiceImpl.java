package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.QuyenDTO;
import com.example.be.tempotide.entity.Quyen;
import com.example.be.tempotide.mapper.QuyenMapper;
import com.example.be.tempotide.repository.QuyenRepository;
import com.example.be.tempotide.service.QuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuyenServiceImpl implements QuyenService {
    private final QuyenRepository quyenRepository;
    private final QuyenMapper quyenMapper;
    private final NhanVienRepository nhanVienRepository;

    @Override
    public List<QuyenDTO> getAllActivePermissions() {
        return quyenRepository.findByTrangthaiTrue()
                .stream()
                .map(quyenMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QuyenDTO getPermissionById(Integer id) {
        Quyen quyen = quyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found with ID: " + id));
        return quyenMapper.toDTO(quyen);
    }

    @Override
    @Transactional
    public QuyenDTO createPermission(QuyenDTO quyenDTO) {
        // Kiểm tra trùng tenquyen
        if (quyenRepository.findByTenquyen(quyenDTO.getTenquyen()).isPresent()) {
            throw new RuntimeException("Permission name already exists: " + quyenDTO.getTenquyen());
        }

        Quyen quyen = quyenMapper.toEntity(quyenDTO);
        // Gán nguoitao từ người dùng hiện tại (giả định lấy từ JWT token)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        quyen.setNguoitao(nguoitao);

        quyen = quyenRepository.save(quyen);
        return quyenMapper.toDTO(quyen);
    }

    @Override
    @Transactional
    public QuyenDTO updatePermission(Integer id, QuyenDTO quyenDTO) {
        Quyen quyen = quyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found with ID: " + id));

        // Kiểm tra trùng tenquyen
        Optional<Quyen> existingQuyen = quyenRepository.findByTenquyen(quyenDTO.getTenquyen());
        if (existingQuyen.isPresent() && !existingQuyen.get().getMaquyen().equals(id)) {
            throw new RuntimeException("Permission name already exists: " + quyenDTO.getTenquyen());
        }

        quyen.setTenquyen(quyenDTO.getTenquyen());
        quyen.setMota(quyenDTO.getMota());
        quyen.setTrangthai(quyenDTO.getTrangthai());
        quyen = quyenRepository.save(quyen);
        return quyenMapper.toDTO(quyen);
    }

    @Override
    @Transactional
    public void deletePermission(Integer id) {
        Quyen quyen = quyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found with ID: " + id));
        quyen.setTrangthai(false);
        quyenRepository.save(quyen);
    }
}