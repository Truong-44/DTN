package com.example.be.tempotide.controller;


import com.example.be.tempotide.dto.chitietphieunhapdto;
import com.example.be.tempotide.service.ChiTietPhieuNhapService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietphieunhap")
public class chitietphieunhapcontroller {

    private static final Logger logger = LoggerFactory.getLogger(chitietphieunhapcontroller.class);

    private final ChiTietPhieuNhapService chiTietPhieuNhapService;

    @Autowired
    public chitietphieunhapcontroller(ChiTietPhieuNhapService chiTietPhieuNhapService) {
        this.chiTietPhieuNhapService = chiTietPhieuNhapService;
    }

    @PostMapping
    public ResponseEntity<chitietphieunhapdto> createChiTietPhieuNhap(@Valid @RequestBody chitietphieunhapdto chiTietPhieuNhapDto) {
        logger.info("API call: POST /api/chitietphieunhap");
        chitietphieunhapdto createdChiTietPhieuNhap = chiTietPhieuNhapService.createChiTietPhieuNhap(chiTietPhieuNhapDto);
        return new ResponseEntity<>(createdChiTietPhieuNhap, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<chitietphieunhapdto> getChiTietPhieuNhapById(@PathVariable Integer id) {
        logger.info("API call: GET /api/chitietphieunhap/{}", id);
        chitietphieunhapdto chiTietPhieuNhapDto = chiTietPhieuNhapService.getChiTietPhieuNhapById(id);
        return ResponseEntity.ok(chiTietPhieuNhapDto);
    }

    @GetMapping
    public ResponseEntity<List<chitietphieunhapdto>> getAllChiTietPhieuNhap() {
        logger.info("API call: GET /api/chitietphieunhap");
        List<chitietphieunhapdto> chiTietPhieuNhapList = chiTietPhieuNhapService.getAllChiTietPhieuNhap();
        return ResponseEntity.ok(chiTietPhieuNhapList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<chitietphieunhapdto> updateChiTietPhieuNhap(@PathVariable Integer id, @Valid @RequestBody chitietphieunhapdto chiTietPhieuNhapDto) {
        logger.info("API call: PUT /api/chitietphieunhap/{}", id);
        chitietphieunhapdto updatedChiTietPhieuNhap = chiTietPhieuNhapService.updateChiTietPhieuNhap(id, chiTietPhieuNhapDto);
        return ResponseEntity.ok(updatedChiTietPhieuNhap);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChiTietPhieuNhap(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/chitietphieunhap/{}", id);
        chiTietPhieuNhapService.deleteChiTietPhieuNhap(id);
        return ResponseEntity.noContent().build();
    }
}