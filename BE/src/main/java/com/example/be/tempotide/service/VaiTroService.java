package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.VaiTroDTO;
import java.util.List;

public interface VaiTroService {
    List<VaiTroDTO> getAllActiveRoles();
    VaiTroDTO getRoleById(Integer id);
    VaiTroDTO createRole(VaiTroDTO vaiTroDTO);
    VaiTroDTO updateRole(Integer id, VaiTroDTO vaiTroDTO);
    void deleteRole(Integer id);
}