package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.quyendto;

import java.util.List;

public interface quyenservice {
    quyendto createQuyen(quyendto quyenDto);
    quyendto getQuyenById(Integer id);
    List<quyendto> getAllQuyen();
    quyendto updateQuyen(Integer id, quyendto quyenDto);
    void deleteQuyen(Integer id);
}