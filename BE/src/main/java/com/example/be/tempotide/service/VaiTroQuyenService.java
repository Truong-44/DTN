package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.QuyenDTO;
import com.example.be.tempotide.dto.VaiTroQuyenRequestDTO;

import java.util.List;

public interface VaiTroQuyenService {
    List<QuyenDTO> getPermissionsByRoleId(Integer roleId);
    void assignPermissionToRole(VaiTroQuyenRequestDTO requestDTO);
    void removePermissionFromRole(Integer roleId, Integer permissionId);
}