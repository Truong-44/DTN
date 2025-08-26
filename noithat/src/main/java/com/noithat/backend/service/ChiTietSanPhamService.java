package com.noithat.backend.service;

import com.noithat.backend.dto.ChiTietSanPhamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChiTietSanPhamService {
    ChiTietSanPhamDTO create(ChiTietSanPhamDTO dto);
    ChiTietSanPhamDTO update(Integer id, ChiTietSanPhamDTO dto);
    void delete(Integer id);
    ChiTietSanPhamDTO getById(Integer id);
    List<ChiTietSanPhamDTO> getAll();
    Page<ChiTietSanPhamDTO> getAllPaged(Pageable pageable);
    List<ChiTietSanPhamDTO> getBySanPhamId(Integer sanPhamId);
    List<ChiTietSanPhamDTO> getBySanPhamIdAndMau(Integer sanPhamId, String mau);
    List<ChiTietSanPhamDTO> getBySanPhamIdAndKichThuoc(Integer sanPhamId, String kichThuoc);
    List<ChiTietSanPhamDTO> getBySanPhamIdWithFilter(Integer sanPhamId, String mau, String kichThuoc, String sortBy);
    List<ChiTietSanPhamDTO> getAvailableStock();
}
