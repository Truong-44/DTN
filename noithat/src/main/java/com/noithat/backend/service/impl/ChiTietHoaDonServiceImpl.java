package com.noithat.backend.service.impl;

import com.noithat.backend.dto.ChiTietHoaDonDTO;
import com.noithat.backend.entity.ChiTietHoaDon;
import com.noithat.backend.entity.HoaDon;
import com.noithat.backend.exception.ResourceNotFoundException;
import com.noithat.backend.mapper.ChiTietHoaDonMapper;
import com.noithat.backend.repository.ChiTietHoaDonRepository;
import com.noithat.backend.repository.HoaDonRepository;
import com.noithat.backend.service.ChiTietHoaDonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChiTietHoaDonServiceImpl implements ChiTietHoaDonService {

    private final ChiTietHoaDonRepository repository;
    private final HoaDonRepository hoaDonRepository;
    private final ChiTietHoaDonMapper mapper;

    @Override
    public ChiTietHoaDonDTO create(ChiTietHoaDonDTO dto) {
        log.debug("Creating new ChiTietHoaDon for HoaDon ID: {}", dto.getHoadonId());
        HoaDon hoaDon = hoaDonRepository.findById(dto.getHoadonId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hóa đơn với ID: " + dto.getHoadonId()));

        ChiTietHoaDon entity = mapper.toEntity(dto, hoaDon);
        ChiTietHoaDon saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public ChiTietHoaDonDTO update(Integer id, ChiTietHoaDonDTO dto) {
        log.debug("Updating ChiTietHoaDon with ID: {}", id);
        ChiTietHoaDon existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi tiết hóa đơn với ID: " + id));

        existing.setSoluong(dto.getSoluong());
        existing.setDongia(dto.getGia());

        ChiTietHoaDon updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public ChiTietHoaDonDTO getById(Integer id) {
        log.debug("Getting ChiTietHoaDon by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi tiết hóa đơn với ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChiTietHoaDonDTO> getByHoaDonId(Integer hoadonId) {
        log.debug("Getting ChiTietHoaDon by HoaDon ID: {}", hoadonId);
        return repository.findByHoaDon_Id(hoadonId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateTotal(Integer hoadonId) {
        log.debug("Calculating total for HoaDon ID: {}", hoadonId);
        BigDecimal total = repository.calculateTotalByHoaDonId(hoadonId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getInvoiceSummary(Integer hoadonId) {
        log.debug("Getting invoice summary for HoaDon ID: {}", hoadonId);
        BigDecimal total = calculateTotal(hoadonId);
        Integer itemCount = repository.countItemsByHoaDonId(hoadonId);

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalAmount", total);
        summary.put("itemCount", itemCount != null ? itemCount : 0);
        summary.put("averagePrice", itemCount != null && itemCount > 0 ? total.divide(BigDecimal.valueOf(itemCount), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO);

        return summary;
    }

    @Override
    public void delete(Integer id) {
        log.debug("Deleting ChiTietHoaDon with ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy chi tiết hóa đơn với ID: " + id);
        }
        repository.deleteById(id);
    }
}
