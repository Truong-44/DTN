package com.noithat.backend.service;

import com.noithat.backend.dto.NhanVienDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NhanVienService {
    NhanVienDTO create(NhanVienDTO dto);
    NhanVienDTO update(Integer id, NhanVienDTO dto);
    void delete(Integer id);
    NhanVienDTO getById(Integer id);
    List<NhanVienDTO> getAll();
    Page<NhanVienDTO> getAllPaged(Pageable pageable);
    List<NhanVienDTO> searchByName(String name);
    List<NhanVienDTO> search(String keyword);
}
