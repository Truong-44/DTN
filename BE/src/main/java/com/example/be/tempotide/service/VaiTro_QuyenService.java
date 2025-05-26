package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.vaitro_quyendto;

import java.util.List;

public interface VaiTro_QuyenService {
    vaitro_quyendto createVaiTro_Quyen(vaitro_quyendto vaiTro_QuyenDto);
    vaitro_quyendto getVaiTro_QuyenById(Integer maVaiTro, Integer maQuyen);
    List<vaitro_quyendto> getAllVaiTro_Quyen();
    vaitro_quyendto updateVaiTro_Quyen(Integer maVaiTro, Integer maQuyen, vaitro_quyendto vaiTro_QuyenDto);
    void deleteVaiTro_Quyen(Integer maVaiTro, Integer maQuyen);
}