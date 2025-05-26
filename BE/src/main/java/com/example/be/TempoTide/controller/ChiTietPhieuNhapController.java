package com.example.be.TempoTide.controller;


import com.example.be.TempoTide.dto.ChiTietPhieuNhapDto;
import com.example.be.TempoTide.service.ChiTietPhieuNhapService;
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
public class ChiTietPhieuNhapController {

    private static final Logger logger = LoggerFactory.getLogger(ChiTietPhieuNhapController.class);

    private final ChiTietPhieuNhapService chiTietPhieuNhapService;

    @Autowired
    public ChiTietPhieuNhapController(ChiTietPhieuNhapService chiTietPhieuNhapService) {
        this.chiTietPhieuNhapService = chiTietPhieuNhapService;
    }

    @PostMapping
    public ResponseEntity<ChiTietPhieuNhapDto> createChiTietPhieuNhap(@Valid @RequestBody ChiTietPhieuNhapDto chiTietPhieuNhapDto) {
        logger.info("API call: POST /api/chitietphieunhap");
        ChiTietPhieuNhapDto createdChiTietPhieuNhap = chiTietPhieuNhapService.createChiTietPhieuNhap(chiTietPhieuNhapDto);
        return new ResponseEntity<>(createdChiTietPhieuNhap, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietPhieuNhapDto> getChiTietPhieuNhapById(@PathVariable Integer id) {
        logger.info("API call: GET /api/chitietphieunhap/{}", id);
        ChiTietPhieuNhapDto chiTietPhieuNhapDto = chiTietPhieuNhapService.getChiTietPhieuNhapById(id);
        return ResponseEntity.ok(chiTietPhieuNhapDto);
    }

    @GetMapping
    public ResponseEntity<List<ChiTietPhieuNhapDto>> getAllChiTietPhieuNhap() {
        logger.info("API call: GET /api/chitietphieunhap");
        List<ChiTietPhieuNhapDto> chiTietPhieuNhapList = chiTietPhieuNhapService.getAllChiTietPhieuNhap();
        return ResponseEntity.ok(chiTietPhieuNhapList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChiTietPhieuNhapDto> updateChiTietPhieuNhap(@PathVariable Integer id, @Valid @RequestBody ChiTietPhieuNhapDto chiTietPhieuNhapDto) {
        logger.info("API call: PUT /api/chitietphieunhap/{}", id);
        ChiTietPhieuNhapDto updatedChiTietPhieuNhap = chiTietPhieuNhapService.updateChiTietPhieuNhap(id, chiTietPhieuNhapDto);
        return ResponseEntity.ok(updatedChiTietPhieuNhap);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChiTietPhieuNhap(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/chitietphieunhap/{}", id);
        chiTietPhieuNhapService.deleteChiTietPhieuNhap(id);
        return ResponseEntity.noContent().build();
    }
}