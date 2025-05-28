package com.example.tempotide.service;

import com.example.tempotide.dto.ChiTietGioHangDTO;

import java.util.List;

public interface ChiTietGioHangService {
    List<ChiTietGioHangDTO> getCartItems(Integer cartId);
    ChiTietGioHangDTO addCartItem(ChiTietGioHangDTO chiTietGioHangDTO);
    ChiTietGioHangDTO updateCartItem(ChiTietGioHangDTO chiTietGioHangDTO);
    void deleteCartItem(Integer cartId, Integer productDetailId);
}