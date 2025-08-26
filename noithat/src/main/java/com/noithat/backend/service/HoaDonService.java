package com.noithat.backend.service;

import com.noithat.backend.dto.HoaDonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface HoaDonService {
    HoaDonDTO create(HoaDonDTO dto);
    HoaDonDTO update(Integer id, HoaDonDTO dto);
    HoaDonDTO getById(Integer id);
    List<HoaDonDTO> getAll();
    Page<HoaDonDTO> getAllPaged(Pageable pageable);
    List<HoaDonDTO> getByKhachHangId(Integer khachHangId);
    List<HoaDonDTO> getByNhanVienId(Integer nhanVienId);
    List<HoaDonDTO> getByNgayTao(LocalDate fromDate, LocalDate toDate);
    void delete(Integer id);
}
