package com.example.tempotide.service;

import com.example.tempotide.dto.GioHangDTO;

import java.util.List;

public interface GioHangService {
    List<GioHangDTO> getAllActiveCarts();
    GioHangDTO getUserCart();
    GioHangDTO createCart(GioHangDTO gioHangDTO);
    GioHangDTO updateCart(Integer id, GioHangDTO gioHangDTO);
    void deleteCart(Integer id);
}