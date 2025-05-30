package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.DonHangDTO;

import java.util.List;

public interface DonHangService {
    List<DonHangDTO> getAllDonHangs();
    DonHangDTO getDonHangById(Integer id);
    DonHangDTO createDonHang(DonHangDTO donHangDTO);
    DonHangDTO updateDonHang(Integer id, DonHangDTO donHangDTO);
    void deleteDonHang(Integer id);
}