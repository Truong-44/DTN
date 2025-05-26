package com.example.be.TempoTide.controller;


import com.example.be.TempoTide.dto.ChiTietGioHangDto;
import com.example.be.TempoTide.service.ChiTietGioHangService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietgiohang")
public class ChiTietGioHangController {

    private static final Logger logger = LoggerFactory.getLogger(ChiTietGioHangController.class);

    private final ChiTietGioHangService chiTietGioHangService;

    @Autowired
    public ChiTietGioHangController(ChiTietGioHangService chiTietGioHangService) {
        this.chiTietGioHangService = chiTietGioHangService;
    }

    @PostMapping
    public ResponseEntity<ChiTietGioHangDto> createChiTietGioHang(@Valid @RequestBody ChiTietGioHangDto chiTietGioHangDto) {
        logger.info("API call: POST /api/chitietgiohang");
        ChiTietGioHangDto createdChiTietGioHang = chiTietGioHangService.createChiTietGioHang(chiTietGioHangDto);
        return new ResponseEntity<>(createdChiTietGioHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietGioHangDto> getChiTietGioHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/chitietgiohang/{}", id);
        ChiTietGioHangDto chiTietGioHangDto = chiTietGioHangService.getChiTietGioHangById(id);
        return ResponseEntity.ok(chiTietGioHangDto);
    }

    @GetMapping
    public ResponseEntity<List<ChiTietGioHangDto>> getAllChiTietGioHang() {
        logger.info("API call: GET /api/chitietgiohang");
        List<ChiTietGioHangDto> chiTietGioHangList = chiTietGioHangService.getAllChiTietGioHang();
        return ResponseEntity.ok(chiTietGioHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChiTietGioHangDto> updateChiTietGioHang(@PathVariable Integer id, @Valid @RequestBody ChiTietGioHangDto chiTietGioHangDto) {
        logger.info("API call: PUT /api/chitietgiohang/{}", id);
        ChiTietGioHangDto updatedChiTietGioHang = chiTietGioHangService.updateChiTietGioHang(id, chiTietGioHangDto);
        return ResponseEntity.ok(updatedChiTietGioHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChiTietGioHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/chitietgiohang/{}", id);
        chiTietGioHangService.deleteChiTietGioHang(id);
        return ResponseEntity.noContent().build();
    }
}