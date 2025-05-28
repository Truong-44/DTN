package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.VaiTroQuyenDTO;

import java.util.List;

public interface VaiTroQuyenService {
    List<VaiTroQuyenDTO> getAllVaiTroQuyens();
    VaiTroQuyenDTO getVaiTroQuyenById(Integer id);
    VaiTroQuyenDTO createVaiTroQuyen(VaiTroQuyenDTO vaiTroQuyenDTO);
    VaiTroQuyenDTO updateVaiTroQuyen(Integer id, VaiTroQuyenDTO vaiTroQuyenDTO);
    void deleteVaiTroQuyen(Integer id);
}