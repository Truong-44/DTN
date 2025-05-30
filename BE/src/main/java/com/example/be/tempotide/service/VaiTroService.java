package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.VaiTroDTO;
import java.util.List;

public interface VaiTroService {
    List<VaiTroDTO> getAllVaiTros();
    VaiTroDTO getVaiTroById(Integer id);
    VaiTroDTO createVaiTro(VaiTroDTO vaiTroDTO);
    VaiTroDTO updateVaiTro(Integer id, VaiTroDTO vaiTroDTO);
    void deleteVaiTro(Integer id);
}