package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.vaitrodto;

import java.util.List;

public interface VaiTroService {
    vaitrodto createVaiTro(vaitrodto vaiTroDto);
    vaitrodto getVaiTroById(Integer id);
    List<vaitrodto> getAllVaiTro();
    vaitrodto updateVaiTro(Integer id, vaitrodto vaiTroDto);
    void deleteVaiTro(Integer id);
}