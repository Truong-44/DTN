package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.ThuocTinhSanPhamDTO;
import com.example.be.tempotide.entity.ThuocTinhSanPham;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.ThuocTinhSanPhamMapper;
import com.example.be.tempotide.repository.ThuocTinhSanPhamRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.ThuocTinhSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThuocTinhSanPhamServiceImpl implements ThuocTinhSanPhamService {
    private final ThuocTinhSanPhamRepository thuocTinhSanPhamRepository;
    private final NhanVienRepository nhanVienRepository;
    private final ThuocTinhSanPhamMapper thuocTinhSanPhamMapper;

    @Override
    public List<ThuocTinhSanPhamDTO> getAllThuocTinhSanPhams() {
        return thuocTinhSanPhamRepository.findAll()
                .stream()
                .map(thuocTinhSanPhamMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ThuocTinhSanPhamDTO getThuocTinhSanPhamById(Integer id) {
        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ThuocTinhSanPham not found with ID: " + id));
        return thuocTinhSanPhamMapper.toDTO(thuocTinhSanPham);
    }

    @Override
    @Transactional
    public ThuocTinhSanPhamDTO createThuocTinhSanPham(ThuocTinhSanPhamDTO thuocTinhSanPhamDTO) {
        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamMapper.toEntity(thuocTinhSanPhamDTO);

        if (thuocTinhSanPhamDTO.getNguoitao() != null) {
            NhanVien nguoitao = nhanVienRepository.findById(thuocTinhSanPhamDTO.getNguoitao())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + thuocTinhSanPhamDTO.getNguoitao()));
            thuocTinhSanPham.setNguoitao(nguoitao);
        }

        thuocTinhSanPham.setNgaytao(LocalDateTime.now());
        ThuocTinhSanPham savedThuocTinhSanPham = thuocTinhSanPhamRepository.save(thuocTinhSanPham);
        return thuocTinhSanPhamMapper.toDTO(savedThuocTinhSanPham);
    }

    @Override
    @Transactional
    public ThuocTinhSanPhamDTO updateThuocTinhSanPham(Integer id, ThuocTinhSanPhamDTO thuocTinhSanPhamDTO) {
        ThuocTinhSanPham existingThuocTinhSanPham = thuocTinhSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ThuocTinhSanPham not found with ID: " + id));

        existingThuocTinhSanPham.setTenthuoctinh(thuocTinhSanPhamDTO.getTenthuoctinh());
        existingThuocTinhSanPham.setMota(thuocTinhSanPhamDTO.getMota());
        existingThuocTinhSanPham.setTrangthai(thuocTinhSanPhamDTO.getTrangthai());

        ThuocTinhSanPham updatedThuocTinhSanPham = thuocTinhSanPhamRepository.save(existingThuocTinhSanPham);
        return thuocTinhSanPhamMapper.toDTO(updatedThuocTinhSanPham);
    }

    @Override
    @Transactional
    public void deleteThuocTinhSanPham(Integer id) {
        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ThuocTinhSanPham not found with ID: " + id));
        thuocTinhSanPham.setTrangthai(false);
        thuocTinhSanPhamRepository.save(thuocTinhSanPham);
    }
}