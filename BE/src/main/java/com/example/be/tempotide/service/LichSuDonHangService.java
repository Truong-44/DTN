package com.example.be.tempotide.service;


import com.example.be.tempotide.dto.lichsudonhangdto;

import java.util.List;

public interface LichSuDonHangService {
    lichsudonhangdto createLichSuDonHang(lichsudonhangdto lichSuDonHangDto);
    lichsudonhangdto getLichSuDonHangById(Integer id);
    List<lichsudonhangdto> getAllLichSuDonHang();
    lichsudonhangdto updateLichSuDonHang(Integer id, lichsudonhangdto lichSuDonHangDto);
    void deleteLichSuDonHang(Integer id);
}