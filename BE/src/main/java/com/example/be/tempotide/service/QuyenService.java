package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.QuyenDTO;

import java.util.List;

public interface QuyenService {
    List<QuyenDTO> getAllActivePermissions();
    QuyenDTO getPermissionById(Integer id);
    QuyenDTO createPermission(QuyenDTO quyenDTO);
    QuyenDTO updatePermission(Integer id, QuyenDTO quyenDTO);
    void deletePermission(Integer id);
}