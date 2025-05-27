package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.donhangdto;

import java.util.List;

public interface donhangservice {
    donhangdto createDonHang(donhangdto donHangDto);
    donhangdto getDonHangById(Integer id);
    List<donhangdto> getAllDonHang();
    donhangdto updateDonHang(Integer id, donhangdto donHangDto);
    void deleteDonHang(Integer id);
}
