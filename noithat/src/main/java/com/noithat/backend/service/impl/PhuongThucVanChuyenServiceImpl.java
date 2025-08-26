package com.noithat.backend.service.impl;

import com.noithat.backend.dto.PhuongThucVanChuyenDTO;
import com.noithat.backend.entity.PhuongThucVanChuyen;
import com.noithat.backend.exception.ResourceNotFoundException;
import com.noithat.backend.mapper.PhuongThucVanChuyenMapper;
import com.noithat.backend.repository.PhuongThucVanChuyenRepository;
import com.noithat.backend.service.PhuongThucVanChuyenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PhuongThucVanChuyenServiceImpl implements PhuongThucVanChuyenService {

    private final PhuongThucVanChuyenRepository repository;
    private final PhuongThucVanChuyenMapper mapper;

    @Override
    public PhuongThucVanChuyenDTO create(PhuongThucVanChuyenDTO dto) {
        log.debug("Creating new PhuongThucVanChuyen: {}", dto.getTen());
        PhuongThucVanChuyen entity = mapper.toEntity(dto);
        entity.setTrangthai(true); // Mặc định là hoạt động
        PhuongThucVanChuyen saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public PhuongThucVanChuyenDTO update(Integer id, PhuongThucVanChuyenDTO dto) {
        log.debug("Updating PhuongThucVanChuyen with ID: {}", id);
        PhuongThucVanChuyen existingEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phương thức vận chuyển với ID: " + id));
        
        // Cập nhật các trường
        existingEntity.setTen(dto.getTen());
        existingEntity.setMota(dto.getMota());
        existingEntity.setPhivanchuyen(dto.getPhivanchuyen());
        existingEntity.setThoigiandukien(dto.getThoigiandukien());
        
        PhuongThucVanChuyen updated = repository.save(existingEntity);
        return mapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PhuongThucVanChuyenDTO> getAll() {
        log.debug("Getting all PhuongThucVanChuyen");
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PhuongThucVanChuyenDTO> getAllPaged(Pageable pageable) {
        log.debug("Getting paged PhuongThucVanChuyen");
        return repository.findAll(pageable)
                .map(mapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public PhuongThucVanChuyenDTO getById(Integer id) {
        log.debug("Getting PhuongThucVanChuyen by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phương thức vận chuyển với ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PhuongThucVanChuyenDTO> getActiveShippingMethods() {
        log.debug("Getting active shipping methods");
        return repository.findByTrangthaiTrue().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PhuongThucVanChuyenDTO> getByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        log.debug("Getting shipping methods by price range: {} - {}", minPrice, maxPrice);
        return repository.findByPriceRange(minPrice, maxPrice).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PhuongThucVanChuyenDTO> search(String keyword) {
        log.debug("Searching shipping methods with keyword: {}", keyword);
        return repository.searchByKeyword(keyword).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PhuongThucVanChuyenDTO updateStatus(Integer id, Boolean active) {
        log.debug("Updating status for PhuongThucVanChuyen ID: {} to {}", id, active);
        PhuongThucVanChuyen entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phương thức vận chuyển với ID: " + id));
        
        entity.setTrangthai(active);
        PhuongThucVanChuyen updated = repository.save(entity);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Integer id) {
        log.debug("Deleting PhuongThucVanChuyen with ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy phương thức vận chuyển với ID: " + id);
        }
        repository.deleteById(id);
    }
}
