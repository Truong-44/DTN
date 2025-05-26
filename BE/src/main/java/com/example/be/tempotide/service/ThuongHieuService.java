package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.thuonghieudto;

import java.util.List;

public interface ThuongHieuService {
    thuonghieudto createThuongHieu(thuonghieudto thuongHieuDto);
    thuonghieudto getThuongHieuById(Integer id);
    List<thuonghieudto> getAllThuongHieu();
    thuonghieudto updateThuongHieu(Integer id, thuonghieudto thuongHieuDto);
    void deleteThuongHieu(Integer id);
}
