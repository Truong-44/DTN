package com.example.be.tempotide.service;


import com.example.be.tempotide.dto.lienhedathangdto;

import java.util.List;

public interface lienhedathangservice {
    lienhedathangdto createLienHeDatHang(lienhedathangdto lienHeDatHangDto);
    lienhedathangdto getLienHeDatHangById(Integer id);
    List<lienhedathangdto> getAllLienHeDatHang();
    lienhedathangdto updateLienHeDatHang(Integer id, lienhedathangdto lienHeDatHangDto);
    void deleteLienHeDatHang(Integer id);
}