package com.noithat.backend.service;

import com.noithat.backend.dto.ChiTietHoaDonDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ChiTietHoaDonService {
    ChiTietHoaDonDTO create(ChiTietHoaDonDTO dto);
    ChiTietHoaDonDTO update(Integer id, ChiTietHoaDonDTO dto);
    ChiTietHoaDonDTO getById(Integer id);
    List<ChiTietHoaDonDTO> getByHoaDonId(Integer hoadonId);
    BigDecimal calculateTotal(Integer hoadonId);
    Object getInvoiceSummary(Integer hoadonId);
    void delete(Integer id);
}
