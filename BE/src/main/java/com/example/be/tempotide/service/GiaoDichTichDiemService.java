package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.giaodichtichdiemdto;

import java.util.List;

public interface GiaoDichTichDiemService {
    giaodichtichdiemdto createGiaoDichTichDiem(giaodichtichdiemdto giaoDichTichDiemDto);
    giaodichtichdiemdto getGiaoDichTichDiemById(Integer id);
    List<giaodichtichdiemdto> getAllGiaoDichTichDiem();
    giaodichtichdiemdto updateGiaoDichTichDiem(Integer id, giaodichtichdiemdto giaoDichTichDiemDto);
    void deleteGiaoDichTichDiem(Integer id);
}
