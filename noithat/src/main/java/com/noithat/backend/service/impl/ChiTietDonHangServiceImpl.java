package com.noithat.backend.service.impl;

import com.noithat.backend.dto.ChiTietDonHangDTO;
import com.noithat.backend.entity.ChiTietDonHang;
import com.noithat.backend.entity.ChiTietSanPham;
import com.noithat.backend.entity.DonHang;
import com.noithat.backend.exception.ResourceNotFoundException;
import com.noithat.backend.mapper.ChiTietDonHangMapper;
import com.noithat.backend.repository.ChiTietDonHangRepository;
import com.noithat.backend.repository.ChiTietSanPhamRepository;
import com.noithat.backend.repository.DonHangRepository;
import com.noithat.backend.service.ChiTietDonHangService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {

    private final ChiTietDonHangRepository repository;
    private final DonHangRepository donHangRepository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final ChiTietDonHangMapper mapper;

    @Override
    public ChiTietDonHangDTO create(ChiTietDonHangDTO dto) {
        log.debug("Creating new ChiTietDonHang for DonHang ID: {}", dto.getDonhangId());
        DonHang dh = donHangRepository.findById(dto.getDonhangId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng với ID: " + dto.getDonhangId()));

        ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(dto.getChitietsanphamId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi tiết sản phẩm với ID: " + dto.getChitietsanphamId()));

        ChiTietDonHang entity = mapper.toEntity(dto, dh, ctsp);
        ChiTietDonHang saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChiTietDonHangDTO> getByDonHangId(Integer donhangId) {
        log.debug("Getting ChiTietDonHang by DonHang ID: {}", donhangId);
        return repository.findByDonHang_Id(donhangId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ChiTietDonHangDTO getById(Integer id) {
        log.debug("Getting ChiTietDonHang by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi tiết đơn hàng với ID: " + id));
    }

    @Override
    public void delete(Integer id) {
        log.debug("Deleting ChiTietDonHang with ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy chi tiết đơn hàng với ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public ChiTietDonHangDTO update(Integer id, ChiTietDonHangDTO dto) {
        log.debug("Updating ChiTietDonHang with ID: {}", id);
        ChiTietDonHang existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi tiết đơn hàng với ID: " + id));

        existing.setSoluong(dto.getSoluong());
        existing.setDongia(dto.getGia());

        ChiTietDonHang updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotal(Integer donhangId) {
        log.debug("Calculating total for DonHang ID: {}", donhangId);
        BigDecimal total = repository.calculateTotalByDonHangId(donhangId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer countItems(Integer donhangId) {
        log.debug("Counting items for DonHang ID: {}", donhangId);
        Integer count = repository.countItemsByDonHangId(donhangId);
        return count != null ? count : 0;
    }
}
