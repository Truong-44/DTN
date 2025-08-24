package com.noithat.backend.service;

import com.noithat.backend.dto.ChiTietGioHangDTO;

import java.util.List;

public interface ChiTietGioHangService {
    ChiTietGioHangDTO create(ChiTietGioHangDTO dto);
    ChiTietGioHangDTO update(Integer id, ChiTietGioHangDTO dto);
    ChiTietGioHangDTO updateSoLuong(Integer id, Integer soLuong);
    void delete(Integer id);
    void clearGioHang(Integer giohangId);
    ChiTietGioHangDTO getById(Integer id);
    List<ChiTietGioHangDTO> getByGioHangId(Integer giohangId);
}
