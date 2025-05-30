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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final SanPhamRepository sanPhamRepository;
    private final ThuocTinhSanPhamRepository thuocTinhSanPhamRepository;
    private final NhanVienRepository nhanVienRepository;
    private final ChiTietSanPhamMapper chiTietSanPhamMapper;

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
        ChiTietSanPham chiTietSanPham = chiTietSanPhamMapper.toEntity(chiTietSanPhamDTO);

        SanPham sanPham = sanPhamRepository.findById(chiTietSanPhamDTO.getMasanpham())
                .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + chiTietSanPhamDTO.getMasanpham()));
        chiTietSanPham.setMasanpham(sanPham);

        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(chiTietSanPhamDTO.getMathuoctinh())
                .orElseThrow(() -> new RuntimeException("ThuocTinhSanPham not found with ID: " + chiTietSanPhamDTO.getMathuoctinh()));
        chiTietSanPham.setMathuoctinh(thuocTinhSanPham);

        if (chiTietSanPhamDTO.getNguoitao() != null) {
            NhanVien nguoitao = nhanVienRepository.findById(chiTietSanPhamDTO.getNguoitao())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + chiTietSanPhamDTO.getNguoitao()));
            chiTietSanPham.setNguoitao(nguoitao);
        }

        if (chiTietSanPhamDTO.getNguoicapnhat() != null) {
            NhanVien nguoicapnhat = nhanVienRepository.findById(chiTietSanPhamDTO.getNguoicapnhat())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + chiTietSanPhamDTO.getNguoicapnhat()));
            chiTietSanPham.setNguoicapnhat(nguoicapnhat);
        }

        chiTietSanPham.setNgaytao(LocalDateTime.now());
        chiTietSanPham.setNgaycapnhat(LocalDateTime.now());
        ChiTietSanPham savedChiTietSanPham = chiTietSanPhamRepository.save(chiTietSanPham);
        return chiTietSanPhamMapper.toDTO(savedChiTietSanPham);
    }

    @Override
    @Transactional
    public ChiTietSanPhamDTO updateChiTietSanPham(Integer id, ChiTietSanPhamDTO chiTietSanPhamDTO) {
        ChiTietSanPham existingChiTietSanPham = chiTietSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChiTietSanPham not found with ID: " + id));

        existingChiTietSanPham.setGiatri(chiTietSanPhamDTO.getGiatri());
        existingChiTietSanPham.setGia(chiTietSanPhamDTO.getGia());
        existingChiTietSanPham.setSoluongton(chiTietSanPhamDTO.getSoluongton());
        existingChiTietSanPham.setSku(chiTietSanPhamDTO.getSku());
        existingChiTietSanPham.setDuongdanhinhanh(chiTietSanPhamDTO.getDuongdanhinhanh());
        existingChiTietSanPham.setLahinhchinh(chiTietSanPhamDTO.getLahinhchinh());
        existingChiTietSanPham.setTrangthai(chiTietSanPhamDTO.getTrangthai());
        existingChiTietSanPham.setNgaycapnhat(LocalDateTime.now());

        SanPham sanPham = sanPhamRepository.findById(chiTietSanPhamDTO.getMasanpham())
                .orElseThrow(() -> new RuntimeException("SanPham not found with ID: " + chiTietSanPhamDTO.getMasanpham()));
        existingChiTietSanPham.setMasanpham(sanPham);

        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(chiTietSanPhamDTO.getMathuoctinh())
                .orElseThrow(() -> new RuntimeException("ThuocTinhSanPham not found with ID: " + chiTietSanPhamDTO.getMathuoctinh()));
        existingChiTietSanPham.setMathuoctinh(thuocTinhSanPham);

        if (chiTietSanPhamDTO.getNguoicapnhat() != null) {
            NhanVien nguoicapnhat = nhanVienRepository.findById(chiTietSanPhamDTO.getNguoicapnhat())
                    .orElseThrow(() -> new RuntimeException("NhanVien not found with ID: " + chiTietSanPhamDTO.getNguoicapnhat()));
            existingChiTietSanPham.setNguoicapnhat(nguoicapnhat);
        }

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
}