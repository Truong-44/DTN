package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.cauhinhhethongdto;
import java.util.List;

public interface cauhinhhethongservice {
    cauhinhhethongdto createCauHinhHeThong(cauhinhhethongdto cauHinhHeThongDto);
    cauhinhhethongdto getCauHinhHeThongById(Integer id);
    List<cauhinhhethongdto> getAllCauHinhHeThong();
    cauhinhhethongdto updateCauHinhHeThong(Integer id, cauhinhhethongdto cauHinhHeThongDto);
    void deleteCauHinhHeThong(Integer id);
}