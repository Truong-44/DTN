package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.baohanhdto;

import java.util.List;

public interface baohanhservice {
    baohanhdto createBaoHanh(baohanhdto baoHanhDto);
    baohanhdto getBaoHanhById(Integer id);
    List<baohanhdto> getAllBaoHanh();
    baohanhdto updateBaoHanh(Integer id, baohanhdto baoHanhDto);
    void deleteBaoHanh(Integer id);
}