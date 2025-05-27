package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.chitietdonhangdto;

import java.util.List;

public interface chitietdonhangservice {
    chitietdonhangdto createchitietdonhang(chitietdonhangdto chitietdonhangdto);
    chitietdonhangdto getchitietdonhangbyid(Integer id);
    List<chitietdonhangdto> getallchitietdonhang();
    chitietdonhangdto updatechitietdonhang(Integer id, chitietdonhangdto chitietdonhangdto);
    void deletechitietdonhang(Integer id);
}