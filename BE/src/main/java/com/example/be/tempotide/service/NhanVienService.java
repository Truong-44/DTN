package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.NhanVienDTO;
import java.util.List;

public interface NhanVienService {
    List<NhanVienDTO> getAllActiveEmployees();
    NhanVienDTO getEmployeeById(Integer id);
    NhanVienDTO createEmployee(NhanVienDTO nhanVienDTO);
    NhanVienDTO updateEmployee(Integer id, NhanVienDTO nhanVienDTO);
    void deleteEmployee(Integer id);
}