package com.example.be.tempotide.service.impl;

import com.example.be.tempotide.dto.ChiTietSanPhamDTO;
import com.example.be.tempotide.entity.ChiTietSanPham;
import com.example.be.tempotide.entity.SanPham;
import com.example.be.tempotide.entity.ThuocTinhSanPham;
import com.example.be.tempotide.entity.NhanVien;
import com.example.be.tempotide.mapper.ChiTietSanPhamMapper;
import com.example.be.tempotide.repository.ChiTietSanPhamRepository;
import com.example.be.tempotide.repository.SanPhamRepository;
import com.example.be.tempotide.repository.ThuocTinhSanPhamRepository;
import com.example.be.tempotide.repository.NhanVienRepository;
import com.example.be.tempotide.service.ChiTietSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final ChiTietSanPhamMapper chiTietSanPhamMapper;
    private final SanPhamRepository sanPhamRepository;
    private final ThuocTinhSanPhamRepository thuocTinhSanPhamRepository;
    private final NhanVienRepository nhanVienRepository;

    @Override
    public List<ChiTietSanPhamDTO> getAllChiTietSanPhams() {
        return chiTietSanPhamRepository.findAll()
                .stream()
                .map(chiTietSanPhamMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ChiTietSanPhamDTO getChiTietSanPhamById(Integer id) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChiTietSanPham not found with ID: " + id));
        return chiTietSanPhamMapper.toDTO(chiTietSanPham);
    }

    @Override
    @Transactional
    public ChiTietSanPhamDTO createChiTietSanPham(ChiTietSanPhamDTO chiTietSanPhamDTO) {
        if (chiTietSanPhamDTO.getSku() != null && chiTietSanPhamRepository.findBySku(chiTietSanPhamDTO.getSku()).isPresent()) {
            throw new RuntimeException("SKU đã tồn tại: " + chiTietSanPhamDTO.getSku());
        }

        ChiTietSanPham chiTietSanPham = chiTietSanPhamMapper.toEntity(chiTietSanPhamDTO);
        chiTietSanPham.setNgaytao(LocalDateTime.now());
        chiTietSanPham.setNgaycapnhat(LocalDateTime.now());

        // Gán masanpham
        SanPham sanPham = sanPhamRepository.findById(chiTietSanPhamDTO.getMasanpham())
                .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + chiTietSanPhamDTO.getMasanpham()));
        chiTietSanPham.setMasanpham(sanPham);

        // Gán mathuoctinh
        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(chiTietSanPhamDTO.getMathuoctinh())
                .orElseThrow(() -> new RuntimeException("ThuocTinhSanPham not found with ID: " + chiTietSanPhamDTO.getMathuoctinh()));
        chiTietSanPham.setMathuoctinh(thuocTinhSanPham);

        // Gán nguoitao và nguoicapnhat từ thông tin người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        chiTietSanPham.setNguoitao(nguoitao);
        chiTietSanPham.setNguoicapnhat(nguoitao);

        ChiTietSanPham savedChiTietSanPham = chiTietSanPhamRepository.save(chiTietSanPham);
        return chiTietSanPhamMapper.toDTO(savedChiTietSanPham);
    }

    @Override
    @Transactional
    public ChiTietSanPhamDTO updateChiTietSanPham(Integer id, ChiTietSanPhamDTO chiTietSanPhamDTO) {
        ChiTietSanPham existingChiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChiTietSanPham not found with ID: " + id));

        if (chiTietSanPhamDTO.getSku() != null && !chiTietSanPhamDTO.getSku().equals(existingChiTietSanPham.getSku()) &&
                chiTietSanPhamRepository.findBySku(chiTietSanPhamDTO.getSku()).isPresent()) {
            throw new RuntimeException("SKU đã tồn tại: " + chiTietSanPhamDTO.getSku());
        }

        existingChiTietSanPham.setGiatri(chiTietSanPhamDTO.getGiatri());
        existingChiTietSanPham.setGia(chiTietSanPhamDTO.getGia());
        existingChiTietSanPham.setSoluongton(chiTietSanPhamDTO.getSoluongton());
        existingChiTietSanPham.setSku(chiTietSanPhamDTO.getSku());
        existingChiTietSanPham.setDuongdanhinhanh(chiTietSanPhamDTO.getDuongdanhinhanh());
        existingChiTietSanPham.setLahinhchinh(chiTietSanPhamDTO.getLahinhchinh());
        existingChiTietSanPham.setNgaytao(chiTietSanPhamDTO.getNgaytao());
        existingChiTietSanPham.setNgaycapnhat(LocalDateTime.now());
        existingChiTietSanPham.setTrangthai(chiTietSanPhamDTO.getTrangthai());

        // Cập nhật masanpham
        SanPham sanPham = sanPhamRepository.findById(chiTietSanPhamDTO.getMasanpham())
                .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + chiTietSanPhamDTO.getMasanpham()));
        existingChiTietSanPham.setMasanpham(sanPham);

        // Cập nhật mathuoctinh
        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(chiTietSanPhamDTO.getMathuoctinh())
                .orElseThrow(() -> new RuntimeException("ThuocTinhSanPham not found with ID: " + chiTietSanPhamDTO.getMathuoctinh()));
        existingChiTietSanPham.setMathuoctinh(thuocTinhSanPham);

        // Gán nguoicapnhat từ thông tin người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoicapnhat = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        existingChiTietSanPham.setNguoicapnhat(nguoicapnhat);

        ChiTietSanPham updatedChiTietSanPham = chiTietSanPhamRepository.save(existingChiTietSanPham);
        return chiTietSanPhamMapper.toDTO(updatedChiTietSanPham);
    }

    @Override
    @Transactional
    public void deleteChiTietSanPham(Integer id) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChiTietSanPham not found with ID: " + id));
        chiTietSanPham.setTrangthai(false);
        chiTietSanPhamRepository.save(chiTietSanPham);
    }

    @Override
    public List<ChiTietSanPhamDTO> getChiTietSanPhamBySanPhamId(Integer masanpham) {
        return chiTietSanPhamRepository.findByMasanpham_Masanpham(masanpham)
                .stream()
                .map(chiTietSanPhamMapper::toDTO)
                .collect(Collectors.toList());
    }
}