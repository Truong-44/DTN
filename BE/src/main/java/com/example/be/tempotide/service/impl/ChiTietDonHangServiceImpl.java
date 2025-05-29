package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.ChiTietDonHangDTO;
import com.example.be.tempotide.entity.ChiTietDonHang;
import com.example.be.tempotide.entity.DonHang;
import com.example.be.tempotide.entity.SanPham;
import com.example.be.tempotide.mapper.ChiTietDonHangMapper;
import com.example.be.tempotide.repository.ChiTietDonHangRepository;
import com.example.be.tempotide.repository.DonHangRepository;
import com.example.be.tempotide.repository.SanPhamRepository;
import com.example.be.tempotide.service.ChiTietDonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {
    private final ChiTietDonHangRepository chiTietDonHangRepository;
    private final DonHangRepository donHangRepository;
    private final SanPhamRepository sanPhamRepository;
    private final ChiTietDonHangMapper chiTietDonHangMapper;

    @Override
    public List<ChiTietDonHangDTO> getAllChiTietDonHangs() {
        return chiTietDonHangRepository.findAll()
                .stream()
                .map(chiTietDonHangMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ChiTietDonHangDTO getChiTietDonHangById(Integer id) {
        ChiTietDonHang chiTietDonHang = chiTietDonHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChiTietDonHang not found with ID: " + id));
        return chiTietDonHangMapper.toDTO(chiTietDonHang);
    }

    @Override
    @Transactional
    public ChiTietDonHangDTO createChiTietDonHang(ChiTietDonHangDTO chiTietDonHangDTO) {
        ChiTietDonHang chiTietDonHang = chiTietDonHangMapper.toEntity(chiTietDonHangDTO);

        DonHang donHang = donHangRepository.findById(chiTietDonHangDTO.getMadonhang())
                .orElseThrow(() -> new RuntimeException("DonHang not found with ID: " + chiTietDonHangDTO.getMadonhang()));
        chiTietDonHang.setMadonhang(donHang);

        SanPham sanPham = sanPhamRepository.findById(chiTietDonHangDTO.getMasanpham())
                .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + chiTietDonHangDTO.getMasanpham()));
        chiTietDonHang.setMasanpham(sanPham);

        chiTietDonHang.setNgaytao(LocalDateTime.now());
        ChiTietDonHang savedChiTietDonHang = chiTietDonHangRepository.save(chiTietDonHang);
        return chiTietDonHangMapper.toDTO(savedChiTietDonHang);
    }

    @Override
    @Transactional
    public ChiTietDonHangDTO updateChiTietDonHang(Integer id, ChiTietDonHangDTO chiTietDonHangDTO) {
        ChiTietDonHang existingChiTietDonHang = chiTietDonHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChiTietDonHang not found with ID: " + id));

        existingChiTietDonHang.setSoluong(chiTietDonHangDTO.getSoluong());
        existingChiTietDonHang.setDongia(chiTietDonHangDTO.getDongia());
        existingChiTietDonHang.setGhichu(chiTietDonHangDTO.getGhichu());
        existingChiTietDonHang.setNgaycapnhat(LocalDateTime.now());
        existingChiTietDonHang.setTrangthai(chiTietDonHangDTO.getTrangthai());

        if (chiTietDonHangDTO.getMadonhang() != null) {
            DonHang donHang = donHangRepository.findById(chiTietDonHangDTO.getMadonhang())
                    .orElseThrow(() -> new RuntimeException("DonHang not found with ID: " + chiTietDonHangDTO.getMadonhang()));
            existingChiTietDonHang.setMadonhang(donHang);
        }

        if (chiTietDonHangDTO.getMasanpham() != null) {
            SanPham sanPham = sanPhamRepository.findById(chiTietDonHangDTO.getMasanpham())
                    .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + chiTietDonHangDTO.getMasanpham()));
            existingChiTietDonHang.setMasanpham(sanPham);
        }

        ChiTietDonHang updatedChiTietDonHang = chiTietDonHangRepository.save(existingChiTietDonHang);
        return chiTietDonHangMapper.toDTO(updatedChiTietDonHang);
    }

    @Override
    @Transactional
    public void deleteChiTietDonHang(Integer id) {
        ChiTietDonHang chiTietDonHang = chiTietDonHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChiTietDonHang not found with ID: " + id));
        chiTietDonHang.setTrangthai(false);
        chiTietDonHangRepository.save(chiTietDonHang);
    }

    @Override
    public List<ChiTietDonHangDTO> getChiTietDonHangByDonHangId(Integer madonhang) {
        List<ChiTietDonHang> chiTietDonHangs = chiTietDonHangRepository.findByMadonhangMadonhang(madonhang);
        return chiTietDonHangs.stream()
                .map(chiTietDonHangMapper::toDTO)
                .collect(Collectors.toList());
    }
}