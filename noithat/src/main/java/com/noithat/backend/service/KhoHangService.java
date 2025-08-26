package com.noithat.backend.service;

import com.noithat.backend.dto.KhoHangDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KhoHangService {
    KhoHangDTO create(KhoHangDTO dto);
    KhoHangDTO update(Integer id, KhoHangDTO dto);
    KhoHangDTO createOrUpdate(KhoHangDTO dto);
    KhoHangDTO getById(Integer id);
    KhoHangDTO getByChiTietSanPhamId(Integer chitietSanphamId);
    List<KhoHangDTO> getAll();
    Page<KhoHangDTO> getAllPaged(Pageable pageable);
    KhoHangDTO updateSoLuong(Integer id, Integer soLuong);
    KhoHangDTO increaseStock(Integer id, Integer soLuong);
    KhoHangDTO decreaseStock(Integer id, Integer soLuong);
    List<KhoHangDTO> getLowStockItems(Integer threshold);
    void delete(Integer id);
}
