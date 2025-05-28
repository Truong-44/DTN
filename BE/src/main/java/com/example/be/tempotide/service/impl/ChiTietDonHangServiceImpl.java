package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.ChiTietDonHangDTO;
import com.example.be.tempotide.entity.ChiTietDonHang;
import com.example.be.tempotide.entity.DonHang;
import com.example.be.tempotide.entity.ChiTietSanPham;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.ChiTietDonHangMapper;
import com.example.be.tempotide.repository.ChiTietDonHangRepository;
import com.example.be.tempotide.repository.DonHangRepository;
import com.example.be.tempotide.repository.ChiTietSanPhamRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.ChiTietDonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {
    private final ChiTietDonHangRepository chiTietDonHangRepository;
    private final ChiTietDonHangMapper chiTietDonHangMapper;
    private final DonHangRepository donHangRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final NhanVienRepository nhanVienRepository;

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
        chiTietDonHang.setNgaytao(LocalDateTime.now());

        DonHang donHang = donHangRepository.findById(chiTietDonHangDTO.getMadonhang())
                .orElseThrow(() -> new RuntimeException("DonHang not found with ID: " + chiTietDonHangDTO.getMadonhang()));
        chiTietDonHang.setMadonhang(donHang);

        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(chiTietDonHangDTO.getMachitietsanpham())
                .orElseThrow(() -> new RuntimeException("ChiTietSanPham not found with ID: " + chiTietDonHangDTO.getMachitietsanpham()));
        if (chiTietSanPham.getSoluongton() < chiTietDonHangDTO.getSoluong()) {
            throw new RuntimeException("Số lượng tồn không đủ: " + chiTietSanPham.getSoluongton());
        }
        chiTietDonHang.setMachitietsanpham(chiTietSanPham);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        chiTietDonHang.setNguoitao(nguoitao);

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
        existingChiTietDonHang.setNgaytao(chiTietDonHangDTO.getNgaytao());
        existingChiTietDonHang.setTrangthai(chiTietDonHangDTO.getTrangthai());

        DonHang donHang = donHangRepository.findById(chiTietDonHangDTO.getMadonhang())
                .orElseThrow(() -> new RuntimeException("DonHang not found with ID: " + chiTietDonHangDTO.getMadonhang()));
        existingChiTietDonHang.setMadonhang(donHang);

        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(chiTietDonHangDTO.getMachitietsanpham())
                .orElseThrow(() -> new RuntimeException("ChiTietSanPham not found with ID: " + chiTietDonHangDTO.getMachitietsanpham()));
        if (chiTietSanPham.getSoluongton() < chiTietDonHangDTO.getSoluong()) {
            throw new RuntimeException("Số lượng tồn không đủ: " + chiTietSanPham.getSoluongton());
        }
        existingChiTietDonHang.setMachitietsanpham(chiTietSanPham);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        existingChiTietDonHang.setNguoitao(nguoitao);

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
        return chiTietDonHangRepository.findByMadonhang_Madonhang(madonhang)
                .stream()
                .map(chiTietDonHangMapper::toDTO)
                .collect(Collectors.toList());
    }
}