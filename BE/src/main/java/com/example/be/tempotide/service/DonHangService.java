package com.example.tempotide.service;

import com.example.tempotide.dto.DonHangDTO;

import java.util.List;

public interface DonHangService {
    List<DonHangDTO> getAllOrders();
    List<DonHangDTO> getUserOrders();
    DonHangDTO getOrderById(Integer id);
    DonHangDTO createOrder(DonHangDTO donHangDTO, Integer cartId);
    DonHangDTO updateOrder(Integer id, DonHangDTO donHangDTO);
    void cancelOrder(Integer id);
}