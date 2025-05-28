package com.example.tempotide.service;

import com.example.tempotide.dto.ChiTietDonHangDTO;

import java.util.List;

public interface ChiTietDonHangService {
    List<ChiTietDonHangDTO> getOrderItems(Integer orderId);
    ChiTietDonHangDTO addOrderItem(ChiTietDonHangDTO chiTietDonHangDTO);
    ChiTietDonHangDTO updateOrderItem(ChiTietDonHangDTO chiTietDonHangDTO);
    void deleteOrderItem(Integer orderId, Integer productDetailId);
}