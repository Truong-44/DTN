package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.chitietsanphamdto;

import java.util.List;

public interface chitietsanphamservice {
    chitietsanphamdto createchitietsanpham(chitietsanphamdto chitietsanphamdto);
    chitietsanphamdto getchitietsanphambyid(Integer id);
    List<chitietsanphamdto> getallchitietsanpham();
    chitietsanphamdto updatechitietsanpham(Integer id, chitietsanphamdto chitietsanphamdto);
    void deletechitietsanpham(Integer id);
}