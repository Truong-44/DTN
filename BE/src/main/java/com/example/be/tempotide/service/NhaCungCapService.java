package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.nhacungcapdto;

import java.util.List;

public interface NhaCungCapService {
    nhacungcapdto createNhaCungCap(nhacungcapdto nhaCungCapDto);
    nhacungcapdto getNhaCungCapById(Integer id);
    List<nhacungcapdto> getAllNhaCungCap();
    nhacungcapdto updateNhaCungCap(Integer id, nhacungcapdto nhaCungCapDto);
    void deleteNhaCungCap(Integer id);
}