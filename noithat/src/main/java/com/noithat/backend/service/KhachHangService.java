package com.noithat.backend.service;

import com.noithat.backend.dto.KhachHangDTO;

import java.util.List;

public interface KhachHangService {
    KhachHangDTO create(KhachHangDTO dto);
    KhachHangDTO update(Integer id, KhachHangDTO dto);
    void delete(Integer id);
    KhachHangDTO getById(Integer id);
    List<KhachHangDTO> getAll();
    List<KhachHangDTO> searchByName(String name);
}
