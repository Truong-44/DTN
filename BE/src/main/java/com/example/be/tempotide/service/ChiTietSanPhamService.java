package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.chitietsanphamdto;

import java.util.List;

public interface ChiTietSanPhamService {
    chitietsanphamdto createChiTietSanPham(chitietsanphamdto chiTietSanPhamDto);
    chitietsanphamdto getChiTietSanPhamById(Integer id);
    List<chitietsanphamdto> getAllChiTietSanPham();
    chitietsanphamdto updateChiTietSanPham(Integer id, chitietsanphamdto chiTietSanPhamDto);
    void deleteChiTietSanPham(Integer id);
}