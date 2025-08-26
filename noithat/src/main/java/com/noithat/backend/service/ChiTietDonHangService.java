package com.noithat.backend.service;

import com.noithat.backend.dto.ChiTietDonHangDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ChiTietDonHangService {
    ChiTietDonHangDTO create(ChiTietDonHangDTO dto);
    ChiTietDonHangDTO update(Integer id, ChiTietDonHangDTO dto);
    List<ChiTietDonHangDTO> getByDonHangId(Integer donhangId);
    ChiTietDonHangDTO getById(Integer id);
    BigDecimal calculateTotal(Integer donhangId);
    Integer countItems(Integer donhangId);
    void delete(Integer id);
}
