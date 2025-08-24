package com.noithat.backend.service.impl;

import com.noithat.backend.dto.ChiTietGioHangDTO;
import com.noithat.backend.entity.ChiTietGioHang;
import com.noithat.backend.entity.ChiTietSanPham;
import com.noithat.backend.entity.GioHang;
import com.noithat.backend.exception.ResourceNotFoundException;
import com.noithat.backend.mapper.ChiTietGioHangMapper;
import com.noithat.backend.repository.ChiTietGioHangRepository;
import com.noithat.backend.repository.ChiTietSanPhamRepository;
import com.noithat.backend.repository.GioHangRepository;
import com.noithat.backend.service.ChiTietGioHangService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService {

    private final ChiTietGioHangRepository repository;
    private final GioHangRepository gioHangRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final ChiTietGioHangMapper mapper;

    @Override
    public ChiTietGioHangDTO create(ChiTietGioHangDTO dto) {
        log.debug("Creating new ChiTietGioHang for GioHang ID: {}", dto.getGiohangId());
        GioHang gioHang = gioHangRepository.findById(dto.getGiohangId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng với ID: " + dto.getGiohangId()));

        ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(dto.getChitietsanphamId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi tiết sản phẩm với ID: " + dto.getChitietsanphamId()));

        ChiTietGioHang entity = mapper.toEntity(dto, gioHang, ctsp);
        ChiTietGioHang saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public ChiTietGioHangDTO update(Integer id, ChiTietGioHangDTO dto) {
        log.debug("Updating ChiTietGioHang with ID: {}", id);
        ChiTietGioHang existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi tiết giỏ hàng với ID: " + id));

        existing.setSoluong(dto.getSoluong());
        existing.setDongia(dto.getDongia());

        ChiTietGioHang updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    public ChiTietGioHangDTO updateSoLuong(Integer id, Integer soLuong) {
        log.debug("Updating quantity for ChiTietGioHang ID: {} to {}", id, soLuong);
        ChiTietGioHang existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi tiết giỏ hàng với ID: " + id));

        existing.setSoluong(soLuong);
        ChiTietGioHang updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Deleting ChiTietGioHang with ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy chi tiết giỏ hàng với ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public void clearGioHang(Integer giohangId) {
        log.debug("Clearing all items from GioHang ID: {}", giohangId);
        if (!gioHangRepository.existsById(giohangId)) {
            throw new ResourceNotFoundException("Không tìm thấy giỏ hàng với ID: " + giohangId);
        }
        repository.deleteByGioHangId(giohangId);
    }

    @Override
    @Transactional(readOnly = true)
    public ChiTietGioHangDTO getById(Integer id) {
        log.debug("Getting ChiTietGioHang by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi tiết giỏ hàng với ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChiTietGioHangDTO> getByGioHangId(Integer giohangId) {
        log.debug("Getting ChiTietGioHang by GioHang ID: {}", giohangId);
        return repository.findByGioHang_Id(giohangId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
