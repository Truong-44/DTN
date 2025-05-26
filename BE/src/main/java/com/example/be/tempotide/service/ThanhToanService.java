package com.example.be.tempotide.service;


import com.example.be.tempotide.dto.thanhtoandto;

import java.util.List;

public interface ThanhToanService {
    thanhtoandto createThanhToan(thanhtoandto thanhToanDto);
    thanhtoandto getThanhToanById(Integer id);
    List<thanhtoandto> getAllThanhToan();
    thanhtoandto updateThanhToan(Integer id, thanhtoandto thanhToanDto);
    void deleteThanhToan(Integer id);
}