package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.baohanhdto;

import java.util.List;

public interface baohanhservice {
    baohanhdto createbaohanh(baohanhdto baohanhdto);
    baohanhdto getbaohanhbyid(Integer id);
    List<baohanhdto> getallbaohanh();
    baohanhdto updatebaohanh(Integer id, baohanhdto baohanhdto);
    void deletebaohanh(Integer id);
}