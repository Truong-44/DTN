package com.noithat.backend.service;

import com.noithat.backend.dto.DanhMucDTO;
import com.noithat.backend.dto.SanPhamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DanhMucService {
    DanhMucDTO create(DanhMucDTO dto);
    DanhMucDTO update(Integer id, DanhMucDTO dto);
    void delete(Integer id);
    DanhMucDTO getById(Integer id);
    List<DanhMucDTO> getAll();
    Page<DanhMucDTO> getAllPaged(Pageable pageable);
    List<DanhMucDTO> getRootCategories();
    List<DanhMucDTO> getSubCategories(Integer parentId);
    List<DanhMucDTO> search(String keyword);
    List<SanPhamDTO> getSanPhamByDanhMucId(Integer danhMucId);
}
