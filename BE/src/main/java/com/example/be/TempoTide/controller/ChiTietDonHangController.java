package com.example.be.TempoTide.controller;


import com.example.be.TempoTide.dto.ChiTietDonHangDto;
import com.example.be.TempoTide.service.ChiTietDonHangService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietdonhang")
public class ChiTietDonHangController {

    private static final Logger logger = LoggerFactory.getLogger(ChiTietDonHangController.class);

    private final ChiTietDonHangService chiTietDonHangService;

    @Autowired
    public ChiTietDonHangController(ChiTietDonHangService chiTietDonHangService) {
        this.chiTietDonHangService = chiTietDonHangService;
    }

    @PostMapping
    public ResponseEntity<ChiTietDonHangDto> createChiTietDonHang(@Valid @RequestBody ChiTietDonHangDto chiTietDonHangDto) {
        logger.info("API call: POST /api/chitietdonhang");
        ChiTietDonHangDto createdChiTietDonHang = chiTietDonHangService.createChiTietDonHang(chiTietDonHangDto);
        return new ResponseEntity<>(createdChiTietDonHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietDonHangDto> getChiTietDonHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/chitietdonhang/{}", id);
        ChiTietDonHangDto chiTietDonHangDto = chiTietDonHangService.getChiTietDonHangById(id);
        return ResponseEntity.ok(chiTietDonHangDto);
    }

    @GetMapping
    public ResponseEntity<List<ChiTietDonHangDto>> getAllChiTietDonHang() {
        logger.info("API call: GET /api/chitietdonhang");
        List<ChiTietDonHangDto> chiTietDonHangList = chiTietDonHangService.getAllChiTietDonHang();
        return ResponseEntity.ok(chiTietDonHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChiTietDonHangDto> updateChiTietDonHang(@PathVariable Integer id, @Valid @RequestBody ChiTietDonHangDto chiTietDonHangDto) {
        logger.info("API call: PUT /api/chitietdonhang/{}", id);
        ChiTietDonHangDto updatedChiTietDonHang = chiTietDonHangService.updateChiTietDonHang(id, chiTietDonHangDto);
        return ResponseEntity.ok(updatedChiTietDonHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChiTietDonHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/chitietdonhang/{}", id);
        chiTietDonHangService.deleteChiTietDonHang(id);
        return ResponseEntity.noContent().build();
    }
}