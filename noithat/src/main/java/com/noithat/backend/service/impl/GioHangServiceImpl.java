package com.noithat.backend.service.impl;

import com.noithat.backend.dto.GioHangDTO;
import com.noithat.backend.entity.GioHang;
import com.noithat.backend.entity.KhachHang;
import com.noithat.backend.mapper.GioHangMapper;
import com.noithat.backend.repository.GioHangRepository;
import com.noithat.backend.repository.KhachHangRepository;
import com.noithat.backend.service.GioHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GioHangServiceImpl implements GioHangService {

    private final GioHangRepository gioHangRepository;
    private final KhachHangRepository khachHangRepository;
    private final GioHangMapper mapper;

    @Override
    public GioHangDTO create(GioHangDTO dto) {
        KhachHang kh = khachHangRepository.findById(dto.getKhachHangId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng ID: " + dto.getKhachHangId()));

        GioHang entity = mapper.toEntity(dto, kh);
        return mapper.toDTO(gioHangRepository.save(entity));
    }

    @Override
    public GioHangDTO getByKhachHangId(Integer khachHangId) {
        return gioHangRepository.findByKhachHang_Id(khachHangId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng cho khách hàng ID: " + khachHangId));
    }

    @Override
    public GioHangDTO getById(Integer id) {
        return gioHangRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng ID: " + id));
    }

    @Override
    public List<GioHangDTO> getAll() {
        return gioHangRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        gioHangRepository.deleteById(id);
    }

    @Override
    public GioHangDTO update(Integer id, GioHangDTO dto) {
        GioHang existing = gioHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng với ID: " + id));
        
        // Update ngaycapnhat
        existing.setNgaycapnhat(java.time.LocalDateTime.now());
        
        return mapper.toDTO(gioHangRepository.save(existing));
    }

    @Override
    public void clearGioHang(Integer id) {
        if (!gioHangRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy giỏ hàng với ID: " + id);
        }
        // Logic to clear cart items would go here
        // This might involve calling ChiTietGioHangService to delete all items
    }

    @Override
    public void clearGioHangByKhachHang(Integer khachHangId) {
        GioHang gioHang = gioHangRepository.findByKhachHang_Id(khachHangId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng cho khách hàng ID: " + khachHangId));
        clearGioHang(gioHang.getId());
    }
}
