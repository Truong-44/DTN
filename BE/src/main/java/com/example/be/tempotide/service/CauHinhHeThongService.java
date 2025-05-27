package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.cauhinhhethongdto;

import java.util.List;

public interface cauhinhhethongservice {
    cauhinhhethongdto createcauhinhhethong(cauhinhhethongdto cauhinhhethongdto);
    cauhinhhethongdto getcauhinhhethongbyid(Integer id);
    List<cauhinhhethongdto> getallcauhinhhethong();
    cauhinhhethongdto updatecauhinhhethong(Integer id, cauhinhhethongdto cauhinhhethongdto);
    void deletecauhinhhethong(Integer id);
}