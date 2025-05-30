package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.entity.SanPham;
import com.example.be.tempotide.entity.DanhMuc;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.SanPhamMapper;
import com.example.be.tempotide.dto.SanPhamDTO;
import com.example.be.tempotide.repository.SanPhamRepository;
import com.example.be.tempotide.repository.DanhMucRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SanPhamServiceImpl implements SanPhamService {
    private final SanPhamRepository sanPhamRepository;
    private final DanhMucRepository danhMucRepository;
    private final NhanVienRepository nhanVienRepository;
    private final SanPhamMapper sanPhamMapper;

    @Override
    public List<SanPhamDTO> getAllSanPhams() {
        return sanPhamRepository.findAll()
                .stream()
                .map(sanPhamMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SanPhamDTO getSanPhamById(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + id));
        return sanPhamMapper.toDTO(sanPham);
    }

    @Override
    @Transactional
    public SanPhamDTO createSanPham(SanPhamDTO sanPhamDTO) {
        SanPham sanPham = sanPhamMapper.toEntity(sanPhamDTO);

        DanhMuc danhMuc = danhMucRepository.findById(sanPhamDTO.getMadanhmuc())
                .orElseThrow(() -> new RuntimeException("DanhMuc not found with ID: " + sanPhamDTO.getMadanhmuc()));
        sanPham.setMadanhmuc(danhMuc);

        if (sanPhamDTO.getNguoitao() != null) {
            NhanVien nguoitao = nhanVienRepository.findById(sanPhamDTO.getNguoitao())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + sanPhamDTO.getNguoitao()));
            sanPham.setNguoitao(nguoitao);
        }

        if (sanPhamDTO.getNguoicapnhat() != null) {
            NhanVien nguoicapnhat = nhanVienRepository.findById(sanPhamDTO.getNguoicapnhat())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + sanPhamDTO.getNguoicapnhat()));
            sanPham.setNguoicapnhat(nguoicapnhat);
        }

        sanPham.setNgaytao(LocalDateTime.now());
        SanPham savedSanPham = sanPhamRepository.save(sanPham);
        return sanPhamMapper.toDTO(savedSanPham);
    }

    @Override
    @Transactional
    public SanPhamDTO updateSanPham(Integer id, SanPhamDTO sanPhamDTO) {
        SanPham existingSanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + id));

        existingSanPham.setTensanpham(sanPhamDTO.getTensanpham());
        existingSanPham.setMota(sanPhamDTO.getMota());
        existingSanPham.setGia(sanPhamDTO.getGia());
        existingSanPham.setTrangthai(sanPhamDTO.getTrangthai());

        DanhMuc danhMuc = danhMucRepository.findById(sanPhamDTO.getMadanhmuc())
                .orElseThrow(() -> new RuntimeException("DanhMuc not found with ID: " + sanPhamDTO.getMadanhmuc()));
        existingSanPham.setMadanhmuc(danhMuc);

        if (sanPhamDTO.getNguoicapnhat() != null) {
            NhanVien nguoicapnhat = nhanVienRepository.findById(sanPhamDTO.getNguoicapnhat())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + sanPhamDTO.getNguoicapnhat()));
            existingSanPham.setNguoicapnhat(nguoicapnhat);
        }

        SanPham updatedSanPham = sanPhamRepository.save(existingSanPham);
        return sanPhamMapper.toDTO(updatedSanPham);
    }

    @Override
    @Transactional
    public void deleteSanPham(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + id));
        sanPham.setTrangthai(false);
        sanPhamRepository.save(sanPham);
    }
}