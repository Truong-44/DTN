package com.noithat.backend.service;

import com.noithat.backend.dto.PhuongThucVanChuyenDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface PhuongThucVanChuyenService {
    PhuongThucVanChuyenDTO create(PhuongThucVanChuyenDTO dto);
    PhuongThucVanChuyenDTO update(Integer id, PhuongThucVanChuyenDTO dto);
    List<PhuongThucVanChuyenDTO> getAll();
    Page<PhuongThucVanChuyenDTO> getAllPaged(Pageable pageable);
    PhuongThucVanChuyenDTO getById(Integer id);
    List<PhuongThucVanChuyenDTO> getActiveShippingMethods();
    List<PhuongThucVanChuyenDTO> getByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<PhuongThucVanChuyenDTO> search(String keyword);
    PhuongThucVanChuyenDTO updateStatus(Integer id, Boolean active);
    void delete(Integer id);
}
