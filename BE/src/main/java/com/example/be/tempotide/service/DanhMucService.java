package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.DanhMucDTO;
import java.util.List;

public interface DanhMucService {
    List<DanhMucDTO> getAllActiveCategories();
    DanhMucDTO getCategoryById(Integer id);
    DanhMucDTO createCategory(DanhMucDTO danhMucDTO);
    DanhMucDTO updateCategory(Integer id, DanhMucDTO danhMucDTO);
    void deleteCategory(Integer id);
}