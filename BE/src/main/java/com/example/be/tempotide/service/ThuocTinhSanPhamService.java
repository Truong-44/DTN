package com.example.tempotide.service;

import com.example.tempotide.dto.ThuocTinhSanPhamDTO;

import java.util.List;

public interface ThuocTinhSanPhamService {
    List<ThuocTinhSanPhamDTO> getAllActiveAttributes();
    ThuocTinhSanPhamDTO getAttributeById(Integer id);
    ThuocTinhSanPhamDTO createAttribute(ThuocTinhSanPhamDTO thuocTinhSanPhamDTO);
    ThuocTinhSanPhamDTO updateAttribute(Integer id, ThuocTinhSanPhamDTO thuocTinhSanPhamDTO);
    void deleteAttribute(Integer id);
}