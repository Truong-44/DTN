package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.NguoiDungVaiTroRequestDTO;
import com.example.be.tempotide.dto.VaiTroDTO;

import java.util.List;

public interface NguoiDungVaiTroService {
    List<VaiTroDTO> getRolesByUserIdAndType(Integer userId, String userType);
    void assignRoleToUser(NguoiDungVaiTroRequestDTO requestDTO);
    void removeRoleFromUser(Integer userId, Integer roleId, String userType);
}