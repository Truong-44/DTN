package com.noithat.backend.service;

import com.noithat.backend.dto.QuyenDTO;

import java.util.List;

public interface QuyenService {
    QuyenDTO create(QuyenDTO dto);
    QuyenDTO update(Integer id, QuyenDTO dto);
    void delete(Integer id);
    QuyenDTO getById(Integer id);
    List<QuyenDTO> getAll();
}
