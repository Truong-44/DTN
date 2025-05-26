package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.DonHangDto;

import java.util.List;

public interface DonHangService {
    DonHangDto createDonHang(DonHangDto donHangDto);
    DonHangDto getDonHangById(Integer id);
    List<DonHangDto> getAllDonHang();
    DonHangDto updateDonHang(Integer id, DonHangDto donHangDto);
    void deleteDonHang(Integer id);
}
