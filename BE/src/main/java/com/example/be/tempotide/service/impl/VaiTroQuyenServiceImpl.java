package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.QuyenDTO;
import com.example.be.tempotide.dto.VaiTroQuyenRequestDTO;
import com.example.be.tempotide.entity.Quyen;
import com.example.be.tempotide.entity.VaiTro;
import com.example.be.tempotide.entity.VaiTroQuyen;
import com.example.be.tempotide.entity.VaiTroQuyenId;
import com.example.be.tempotide.mapper.QuyenMapper;
import com.example.be.tempotide.repository.QuyenRepository;
import com.example.be.tempotide.repository.VaiTroQuyenRepository;
import com.example.be.tempotide.repository.VaiTroRepository;
import com.example.be.tempotide.service.VaiTroQuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VaiTroQuyenServiceImpl implements VaiTroQuyenService {
    private final VaiTroQuyenRepository vaiTroQuyenRepository;
    private final VaiTroRepository vaiTroRepository;
    private final QuyenRepository quyenRepository;
    private final QuyenMapper quyenMapper;

    @Override
    public List<QuyenDTO> getPermissionsByRoleId(Integer roleId) {
        vaiTroRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));
        return vaiTroQuyenRepository.findByVaiTroMavaitro(roleId)
                .stream()
                .map(VaiTroQuyen::getQuyen)
                .map(quyenMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void assignPermissionToRole(VaiTroQuyenRequestDTO requestDTO) {
        VaiTro vaiTro = vaiTroRepository.findById(requestDTO.getMavaitro())
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + requestDTO.getMavaitro()));
        Quyen quyen = quyenRepository.findById(requestDTO.getMaquyen())
                .orElseThrow(() -> new RuntimeException("Permission not found with ID: " + requestDTO.getMaquyen()));

        // Sửa lỗi: sử dụng vaiTroQuyenRepository thay vì vaiTroQuyenService
        if (vaiTroQuyenRepository.existsByVaiTroMavaitroAndQuyenMaquyen(requestDTO.getMavaitro(), requestDTO.getMaquyen())) {
            throw new RuntimeException("Permission already assigned to role");
        }

        VaiTroQuyen vaiTroQuyen = new VaiTroQuyen();
        VaiTroQuyenId id = new VaiTroQuyenId();
        id.setMavaitro(requestDTO.getMavaitro());
        id.setMaquyen(requestDTO.getMaquyen());
        vaiTroQuyen.setId(id);
        vaiTroQuyen.setVaiTro(vaiTro);
        vaiTroQuyen.setQuyen(quyen);

        vaiTroQuyenRepository.save(vaiTroQuyen);
    }

    @Override
    @Transactional
    public void removePermissionFromRole(Integer roleId, Integer permissionId) {
        VaiTroQuyenId id = new VaiTroQuyenId();
        id.setMavaitro(roleId);
        id.setMaquyen(permissionId);

        VaiTroQuyen vaiTroQuyen = vaiTroQuyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role-Permission mapping not found"));
        vaiTroQuyenRepository.delete(vaiTroQuyen);
    }
}