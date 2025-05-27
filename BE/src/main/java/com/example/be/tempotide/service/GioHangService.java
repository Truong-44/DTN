package com.example.be.tempotide.service;


import com.example.be.tempotide.dto.giohangdto;

import java.util.List;

public interface giohangservice {
    giohangdto createGioHang(giohangdto gioHangDto);
    giohangdto getGioHangById(Integer id);
    List<giohangdto> getAllGioHang();
    giohangdto updateGioHang(Integer id, giohangdto gioHangDto);
    void deleteGioHang(Integer id);
}