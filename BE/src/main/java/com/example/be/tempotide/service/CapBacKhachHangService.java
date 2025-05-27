package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.capbackhachhangdto;

import java.util.List;

public interface capbackhachhangservice {
    capbackhachhangdto createcapbackhachhang(capbackhachhangdto capbackhachhangdto);
    capbackhachhangdto getcapbackhachhangbyid(Integer id);
    List<capbackhachhangdto> getallcapbackhachhang();
    capbackhachhangdto updatecapbackhachhang(Integer id, capbackhachhangdto capbackhachhangdto);
    void deletecapbackhachhang(Integer id);
}