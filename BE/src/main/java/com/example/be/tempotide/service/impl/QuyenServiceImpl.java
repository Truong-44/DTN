package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.QuyenDTO;
import com.example.be.tempotide.entity.Quyen;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.QuyenMapper;
import com.example.be.tempotide.repository.QuyenRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.QuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuyenServiceImpl implements QuyenService {
    private final QuyenRepository quyenRepository;
    private final QuyenMapper quyenMapper;
    private final NhanVienRepository nhanVienRepository;

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
        if (quyenRepository.findByTenquyen(quyenDTO.getTenquyen()).isPresent()) {
            throw new RuntimeException("Quyền với tên " + quyenDTO.getTenquyen() + " đã tồn tại");
        }

        Quyen quyen = quyenMapper.toEntity(quyenDTO);
        quyen.setNgaytao(LocalDateTime.now()); // Đặt giá trị mặc định là thời gian hiện tại

        if (quyenDTO.getNguoitao() != null) {
            NhanVien nguoitao = nhanVienRepository.findById(quyenDTO.getNguoitao())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + quyenDTO.getNguoitao()));
            quyen.setNguoitao(nguoitao);
        } else {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("User not found: " + username));
            quyen.setNguoitao(nguoitao);
        }

        Quyen savedQuyen = quyenRepository.save(quyen);
        return quyenMapper.toDTO(savedQuyen);
    }

    @Override
    @Transactional
    public QuyenDTO updateQuyen(Integer id, QuyenDTO quyenDTO) {
        Quyen existingQuyen = quyenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quyen not found with ID: " + id));

        if (!existingQuyen.getTenquyen().equals(quyenDTO.getTenquyen()) &&
                quyenRepository.findByTenquyen(quyenDTO.getTenquyen()).isPresent()) {
            throw new RuntimeException("Quyền với tên " + quyenDTO.getTenquyen() + " đã tồn tại");
        }

        existingQuyen.setTenquyen(quyenDTO.getTenquyen());
        existingQuyen.setMota(quyenDTO.getMota());
        existingQuyen.setTrangthai(quyenDTO.getTrangthai());

        if (quyenDTO.getNguoitao() != null) {
            NhanVien nguoitao = nhanVienRepository.findById(quyenDTO.getNguoitao())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + quyenDTO.getNguoitao()));
            existingQuyen.setNguoitao(nguoitao);
        }

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