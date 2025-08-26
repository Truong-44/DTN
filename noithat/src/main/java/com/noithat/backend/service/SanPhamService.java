package com.noithat.backend.service;

import com.noithat.backend.dto.ChiTietSanPhamDTO;
import com.noithat.backend.dto.SanPhamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SanPhamService {
    SanPhamDTO create(SanPhamDTO dto);
    SanPhamDTO update(Integer id, SanPhamDTO dto);
    void delete(Integer id);
    SanPhamDTO getById(Integer id);
    List<SanPhamDTO> getAll();
    Page<SanPhamDTO> getAllPaged(Pageable pageable);
    List<SanPhamDTO> getByDanhMucId(Integer danhMucId);
    List<SanPhamDTO> searchByName(String name);
    Page<SanPhamDTO> searchByNamePaged(String name, Pageable pageable);
    List<ChiTietSanPhamDTO> getChiTietSanPhamById(Integer id);
    SanPhamDTO getFullInfoById(Integer id);
    List<String> getAvailableColors(Integer sanPhamId);
    List<String> getAvailableSizes(Integer sanPhamId);
    SanPhamDTO updateStatus(Integer id, Boolean status);
}
