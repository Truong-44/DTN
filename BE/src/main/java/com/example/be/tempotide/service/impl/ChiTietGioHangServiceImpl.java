package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.ChiTietGioHangDTO;
import com.example.be.tempotide.entity.ChiTietGioHang;
import com.example.be.tempotide.entity.GioHang;
import com.example.be.tempotide.entity.ChiTietSanPham;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.ChiTietGioHangMapper;
import com.example.be.tempotide.repository.ChiTietGioHangRepository;
import com.example.be.tempotide.repository.GioHangRepository;
import com.example.be.tempotide.repository.ChiTietSanPhamRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.ChiTietGioHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService {
    private final ChiTietGioHangRepository chiTietGioHangRepository;
    private final ChiTietGioHangMapper chiTietGioHangMapper;
    private final GioHangRepository gioHangRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final NhanVienRepository nhanVienRepository;

    @Override
    public List<ChiTietGioHangDTO> getAllChiTietGioHangs() {
        return chiTietGioHangRepository.findAll()
                .stream()
                .map(chiTietGioHangMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ChiTietGioHangDTO getChiTietGioHangById(Integer id) {
        ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChiTietGioHang not found with ID: " + id));
        return chiTietGioHangMapper.toDTO(chiTietGioHang);
    }

    @Override
    @Transactional
    public ChiTietGioHangDTO createChiTietGioHang(ChiTietGioHangDTO chiTietGioHangDTO) {
        ChiTietGioHang chiTietGioHang = chiTietGioHangMapper.toEntity(chiTietGioHangDTO);
        chiTietGioHang.setNgaytao(LocalDateTime.now());

        GioHang gioHang = gioHangRepository.findById(chiTietGioHangDTO.getMagiohang())
                .orElseThrow(() -> new RuntimeException("GioHang not found with ID: " + chiTietGioHangDTO.getMagiohang()));
        chiTietGioHang.setMagiohang(gioHang);

        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(chiTietGioHangDTO.getMachitietsanpham())
                .orElseThrow(() -> new RuntimeException("ChiTietSanPham not found with ID: " + chiTietGioHangDTO.getMachitietsanpham()));
        if (chiTietSanPham.getSoluongton() < chiTietGioHangDTO.getSoluong()) {
            throw new RuntimeException("Số lượng tồn không đủ: " + chiTietSanPham.getSoluongton());
        }
        chiTietGioHang.setMachitietsanpham(chiTietSanPham);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        chiTietGioHang.setNguoitao(nguoitao);

        ChiTietGioHang savedChiTietGioHang = chiTietGioHangRepository.save(chiTietGioHang);
        return chiTietGioHangMapper.toDTO(savedChiTietGioHang);
    }

    @Override
    @Transactional
    public ChiTietGioHangDTO updateChiTietGioHang(Integer id, ChiTietGioHangDTO chiTietGioHangDTO) {
        ChiTietGioHang existingChiTietGioHang = chiTietGioHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChiTietGioHang not found with ID: " + id));

        existingChiTietGioHang.setSoluong(chiTietGioHangDTO.getSoluong());
        existingChiTietGioHang.setDongia(chiTietGioHangDTO.getDongia());
        existingChiTietGioHang.setNgaytao(chiTietGioHangDTO.getNgaytao());
        existingChiTietGioHang.setTrangthai(chiTietGioHangDTO.getTrangthai());

        GioHang gioHang = gioHangRepository.findById(chiTietGioHangDTO.getMagiohang())
                .orElseThrow(() -> new RuntimeException("GioHang not found with ID: " + chiTietGioHangDTO.getMagiohang()));
        existingChiTietGioHang.setMagiohang(gioHang);

        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(chiTietGioHangDTO.getMachitietsanpham())
                .orElseThrow(() -> new RuntimeException("ChiTietSanPham not found with ID: " + chiTietGioHangDTO.getMachitietsanpham()));
        if (chiTietSanPham.getSoluongton() < chiTietGioHangDTO.getSoluong()) {
            throw new RuntimeException("Số lượng tồn không đủ: " + chiTietSanPham.getSoluongton());
        }
        existingChiTietGioHang.setMachitietsanpham(chiTietSanPham);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        existingChiTietGioHang.setNguoitao(nguoitao);

        ChiTietGioHang updatedChiTietGioHang = chiTietGioHangRepository.save(existingChiTietGioHang);
        return chiTietGioHangMapper.toDTO(updatedChiTietGioHang);
    }

    @Override
    @Transactional
    public void deleteChiTietGioHang(Integer id) {
        ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChiTietGioHang not found with ID: " + id));
        chiTietGioHang.setTrangthai(false);
        chiTietGioHangRepository.save(chiTietGioHang);
    }

    @Override
    public List<ChiTietGioHangDTO> getChiTietGioHangByGioHangId(Integer magiohang) {
        return chiTietGioHangRepository.findByMagiohang_Magiohang(magiohang)
                .stream()
                .map(chiTietGioHangMapper::toDTO)
                .collect(Collectors.toList());
    }
}