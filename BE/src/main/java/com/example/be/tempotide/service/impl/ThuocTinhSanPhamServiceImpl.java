package com.example.tempotide.service.impl;

import com.example.tempotide.dto.ThuocTinhSanPhamDTO;
import com.example.tempotide.entity.NhanVien;
import com.example.tempotide.entity.ThuocTinhSanPham;
import com.example.tempotide.mapper.ThuocTinhSanPhamMapper;
import com.example.tempotide.repository.NhanVienRepository;
import com.example.tempotide.repository.ThuocTinhSanPhamRepository;
import com.example.tempotide.service.ThuocTinhSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThuocTinhSanPhamServiceImpl implements ThuocTinhSanPhamService {
    private final ThuocTinhSanPhamRepository thuocTinhSanPhamRepository;
    private final NhanVienRepository nhanVienRepository;
    private final ThuocTinhSanPhamMapper thuocTinhSanPhamMapper;

    @Override
    public List<ThuocTinhSanPhamDTO> getAllActiveAttributes() {
        return thuocTinhSanPhamRepository.findByTrangthaiTrue()
                .stream()
                .map(thuocTinhSanPhamMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ThuocTinhSanPhamDTO getAttributeById(Integer id) {
        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attribute not found with ID: " + id));
        return thuocTinhSanPhamMapper.toDTO(thuocTinhSanPham);
    }

    @Override
    @Transactional
    public ThuocTinhSanPhamDTO createAttribute(ThuocTinhSanPhamDTO thuocTinhSanPhamDTO) {
        // Kiểm tra trùng tenthuoctinh
        if (thuocTinhSanPhamRepository.findByTenthuoctinh(thuocTinhSanPhamDTO.getTenthuoctinh()).isPresent()) {
            throw new RuntimeException("Attribute name already exists: " + thuocTinhSanPhamDTO.getTenthuoctinh());
        }

        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamMapper.toEntity(thuocTinhSanPhamDTO);

        // Gán nguoitao từ người dùng hiện tại
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NhanVien nguoitao = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        thuocTinhSanPham.setNguoitao(nguoitao);

        thuocTinhSanPham = thuocTinhSanPhamRepository.save(thuocTinhSanPham);
        return thuocTinhSanPhamMapper.toDTO(thuocTinhSanPham);
    }

    @Override
    @Transactional
    public ThuocTinhSanPhamDTO updateAttribute(Integer id, ThuocTinhSanPhamDTO thuocTinhSanPhamDTO) {
        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attribute not found with ID: " + id));

        // Kiểm tra trùng tenthuoctinh
        Optional<ThuocTinhSanPham> existingThuocTinh = thuocTinhSanPhamRepository.findByTenthuoctinh(thuocTinhSanPhamDTO.getTenthuoctinh());
        if (existingThuocTinh.isPresent() && !existingThuocTinh.get().getMathuoctinh().equals(id)) {
            throw new RuntimeException("Attribute name already exists: " + thuocTinhSanPhamDTO.getTenthuoctinh());
        }

        thuocTinhSanPham.setTenthuoctinh(thuocTinhSanPhamDTO.getTenthuoctinh());
        thuocTinhSanPham.setMota(thuocTinhSanPhamDTO.getMota());
        thuocTinhSanPham.setTrangthai(thuocTinhSanPhamDTO.getTrangthai());

        thuocTinhSanPham = thuocTinhSanPhamRepository.save(thuocTinhSanPham);
        return thuocTinhSanPhamMapper.toDTO(thuocTinhSanPham);
    }

    @Override
    @Transactional
    public void deleteAttribute(Integer id) {
        ThuocTinhSanPham thuocTinhSanPham = thuocTinhSanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attribute not found with ID: " + id));
        thuocTinhSanPham.setTrangthai(false);
        thuocTinhSanPhamRepository.save(thuocTinhSanPham);
    }
}