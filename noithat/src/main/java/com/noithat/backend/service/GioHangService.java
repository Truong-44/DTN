package com.noithat.backend.service;

import com.noithat.backend.dto.GioHangDTO;

import java.util.List;

public interface GioHangService {
    GioHangDTO create(GioHangDTO dto);
    GioHangDTO update(Integer id, GioHangDTO dto);
    GioHangDTO getByKhachHangId(Integer khachHangId);
    GioHangDTO getById(Integer id);
    List<GioHangDTO> getAll();
    void clearGioHang(Integer id);
    void clearGioHangByKhachHang(Integer khachHangId);
    void delete(Integer id);
}
