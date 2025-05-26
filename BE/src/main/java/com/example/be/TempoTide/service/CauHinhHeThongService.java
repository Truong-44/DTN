package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.CauHinhHeThongDto;
import java.util.List;

public interface CauHinhHeThongService {
    CauHinhHeThongDto createCauHinhHeThong(CauHinhHeThongDto cauHinhHeThongDto);
    CauHinhHeThongDto getCauHinhHeThongById(Integer id);
    List<CauHinhHeThongDto> getAllCauHinhHeThong();
    CauHinhHeThongDto updateCauHinhHeThong(Integer id, CauHinhHeThongDto cauHinhHeThongDto);
    void deleteCauHinhHeThong(Integer id);
}