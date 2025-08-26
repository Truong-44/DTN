package com.noithat.backend.service.impl;

import com.noithat.backend.dto.KhoHangDTO;
import com.noithat.backend.entity.ChiTietSanPham;
import com.noithat.backend.entity.KhoHang;
import com.noithat.backend.exception.ResourceNotFoundException;
import com.noithat.backend.mapper.KhoHangMapper;
import com.noithat.backend.repository.ChiTietSanPhamRepository;
import com.noithat.backend.repository.KhoHangRepository;
import com.noithat.backend.service.KhoHangService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class KhoHangServiceImpl implements KhoHangService {

    private final KhoHangRepository repository;
    private final ChiTietSanPhamRepository chiTietSanPhamRepository;
    private final KhoHangMapper mapper;

    @Override
    public KhoHangDTO create(KhoHangDTO dto) {
        log.debug("Creating new KhoHang for ChiTietSanPham ID: {}", dto.getChitietsanphamId());
        ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(dto.getChitietsanphamId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi tiết sản phẩm với ID: " + dto.getChitietsanphamId()));

        KhoHang entity = mapper.toEntity(dto, ctsp);
        KhoHang saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public KhoHangDTO update(Integer id, KhoHangDTO dto) {
        log.debug("Updating KhoHang with ID: {}", id);
        KhoHang existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho hàng với ID: " + id));

        existing.setSoluongton(dto.getSoluongton());
        KhoHang updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    public KhoHangDTO createOrUpdate(KhoHangDTO dto) {
        log.debug("Creating or updating KhoHang for ChiTietSanPham ID: {}", dto.getChitietsanphamId());
        ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(dto.getChitietsanphamId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi tiết sản phẩm với ID: " + dto.getChitietsanphamId()));

        KhoHang kho = repository.findByChiTietSanPham_Id(dto.getChitietsanphamId())
                .orElse(new KhoHang());

        kho.setChiTietSanPham(ctsp);
        kho.setSoluongton(dto.getSoluongton());

        KhoHang saved = repository.save(kho);
        return mapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public KhoHangDTO getById(Integer id) {
        log.debug("Getting KhoHang by ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho hàng với ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public KhoHangDTO getByChiTietSanPhamId(Integer chitietSanphamId) {
        log.debug("Getting KhoHang by ChiTietSanPham ID: {}", chitietSanphamId);
        return repository.findByChiTietSanPham_Id(chitietSanphamId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho hàng cho chi tiết sản phẩm với ID: " + chitietSanphamId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<KhoHangDTO> getAll() {
        log.debug("Getting all KhoHang");
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KhoHangDTO> getAllPaged(Pageable pageable) {
        log.debug("Getting paged KhoHang");
        return repository.findAll(pageable)
                .map(mapper::toDTO);
    }

    @Override
    public KhoHangDTO updateSoLuong(Integer id, Integer soLuong) {
        log.debug("Updating quantity for KhoHang ID: {} to {}", id, soLuong);
        KhoHang existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho hàng với ID: " + id));

        existing.setSoluongton(soLuong);
        KhoHang updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    public KhoHangDTO increaseStock(Integer id, Integer soLuong) {
        log.debug("Increasing stock for KhoHang ID: {} by {}", id, soLuong);
        KhoHang existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho hàng với ID: " + id));

        existing.setSoluongton(existing.getSoluongton() + soLuong);
        KhoHang updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    public KhoHangDTO decreaseStock(Integer id, Integer soLuong) {
        log.debug("Decreasing stock for KhoHang ID: {} by {}", id, soLuong);
        KhoHang existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy kho hàng với ID: " + id));

        int newQuantity = existing.getSoluongton() - soLuong;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Không thể giảm số lượng tồn kho xuống dưới 0");
        }

        existing.setSoluongton(newQuantity);
        KhoHang updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KhoHangDTO> getLowStockItems(Integer threshold) {
        log.debug("Getting low stock items with threshold: {}", threshold);
        return repository.findLowStockItems(threshold).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        log.debug("Deleting KhoHang with ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy kho hàng với ID: " + id);
        }
        repository.deleteById(id);
    }
}
