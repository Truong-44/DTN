package com.noithat.backend.service;

import com.noithat.backend.dto.DonHangDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DonHangService {
    DonHangDTO create(DonHangDTO dto);
    DonHangDTO update(Integer id, DonHangDTO dto);
    DonHangDTO getById(Integer id);
    List<DonHangDTO> getAll();
    Page<DonHangDTO> getAllPaged(Pageable pageable);
    List<DonHangDTO> getByKhachHangId(Integer khachHangId);
    List<DonHangDTO> getByStatus(String status);
    DonHangDTO updateStatus(Integer id, String status);
    DonHangDTO cancel(Integer id, String reason);
    void delete(Integer id);
}
