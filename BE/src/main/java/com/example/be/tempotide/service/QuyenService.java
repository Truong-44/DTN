package com.example.tempotide.service;

import com.example.tempotide.dto.QuyenDTO;

import java.util.List;

public interface QuyenService {
    List<QuyenDTO> getAllQuyens();
    QuyenDTO getQuyenById(Integer id);
    QuyenDTO createQuyen(QuyenDTO quyenDTO);
    QuyenDTO updateQuyen(Integer id, QuyenDTO quyenDTO);
    void deleteQuyen(Integer id);
}