package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.VaiTroQuyenDTO;
import com.example.be.tempotide.entity.VaiTroQuyen;
import com.example.be.tempotide.entity.VaiTro;
import com.example.be.tempotide.entity.Quyen;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.VaiTroQuyenMapper;
import com.example.be.tempotide.repository.VaiTroQuyenRepository;
import com.example.be.tempotide.repository.VaiTroRepository;
import com.example.be.tempotide.repository.QuyenRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.VaiTroQuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VaiTroQuyenServiceImpl implements VaiTroQuyenService {
    private final VaiTroQuyenRepository vaiTroQuyenRepository;
    private final VaiTroQuyenMapper vaiTroQuyenMapper;
    private final VaiTroRepository vaiTroRepository;
    private final QuyenRepository quyenRepository;
    private final NhanVienRepository nhanVienRepository;

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
        vaiTroQuyen.setNgaytao(LocalDateTime.now());

        // Gán mavaitro
        VaiTro vaiTro = vaiTroRepository.findById(vaiTroQuyenDTO.getMavaitro())
                .orElseThrow(() -> new RuntimeException("VaiTro not found with ID: " + vaiTroQuyenDTO.getMavaitro()));
        vaiTroQuyen.setMavaitro(vaiTro);

        // Gán maquyen
        Quyen quyen = quyenRepository.findById(vaiTroQuyenDTO.getMaquyen())
                .orElseThrow(() -> new RuntimeException("Quyen not found with ID: " + vaiTroQuyenDTO.getMaquyen()));
        vaiTroQuyen.setMaquyen(quyen);

        // Gán nguoitao từ thông tin người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        vaiTroQuyen.setNguoitao(nguoitao);

        VaiTroQuyen savedVaiTroQuyen = vaiTroQuyenRepository.save(vaiTroQuyen);
        return vaiTroQuyenMapper.toDTO(savedVaiTroQuyen);
    }

    @Override
    @Transactional
    public VaiTroQuyenDTO updateVaiTroQuyen(Integer id, VaiTroQuyenDTO vaiTroQuyenDTO) {
        VaiTroQuyen existingVaiTroQuyen = vaiTroQuyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VaiTroQuyen not found with ID: " + id));

        // Cập nhật mavaitro
        VaiTro vaiTro = vaiTroRepository.findById(vaiTroQuyenDTO.getMavaitro())
                .orElseThrow(() -> new RuntimeException("VaiTro not found with ID: " + vaiTroQuyenDTO.getMavaitro()));
        existingVaiTroQuyen.setMavaitro(vaiTro);

        // Cập nhật maquyen
        Quyen quyen = quyenRepository.findById(vaiTroQuyenDTO.getMaquyen())
                .orElseThrow(() -> new RuntimeException("Quyen not found with ID: " + vaiTroQuyenDTO.getMaquyen()));
        existingVaiTroQuyen.setMaquyen(quyen);

        existingVaiTroQuyen.setNgaytao(vaiTroQuyenDTO.getNgaytao());
        existingVaiTroQuyen.setTrangthai(vaiTroQuyenDTO.getTrangthai());

        // Gán nguoitao từ thông tin người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        existingVaiTroQuyen.setNguoitao(nguoitao);

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