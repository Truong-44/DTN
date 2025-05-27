package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.KhachHangDTO;
import java.util.List;

public interface KhachHangService {
    List<KhachHangDTO> getAllActiveCustomers();
    KhachHangDTO getCustomerById(Integer id);
    KhachHangDTO createCustomer(KhachHangDTO khachHangDTO);
    KhachHangDTO updateCustomer(Integer id, KhachHangDTO khachHangDTO);
    void deleteCustomer(Integer id);
}