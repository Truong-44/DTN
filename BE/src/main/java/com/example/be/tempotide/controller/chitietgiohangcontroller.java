package com.example.be.tempotide.controller;


import com.example.be.tempotide.dto.chitietgiohangdto;
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
public class chitietgiohangcontroller {

    private static final Logger logger = LoggerFactory.getLogger(chitietgiohangcontroller.class);

    private final com.example.be.tempotide.service.chitietgiohangservice chiTietGioHangService;

    @Autowired
    public chitietgiohangcontroller(com.example.be.tempotide.service.chitietgiohangservice chiTietGioHangService) {
        this.chiTietGioHangService = chiTietGioHangService;
    }

    @PostMapping
    public ResponseEntity<chitietgiohangdto> createChiTietGioHang(@Valid @RequestBody chitietgiohangdto chiTietGioHangDto) {
        logger.info("API call: POST /api/chitietgiohang");
        chitietgiohangdto createdChiTietGioHang = chiTietGioHangService.createChiTietGioHang(chiTietGioHangDto);
        return new ResponseEntity<>(createdChiTietGioHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<chitietgiohangdto> getChiTietGioHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/chitietgiohang/{}", id);
        chitietgiohangdto chiTietGioHangDto = chiTietGioHangService.getChiTietGioHangById(id);
        return ResponseEntity.ok(chiTietGioHangDto);
    }

    @GetMapping
    public ResponseEntity<List<chitietgiohangdto>> getAllChiTietGioHang() {
        logger.info("API call: GET /api/chitietgiohang");
        List<chitietgiohangdto> chiTietGioHangList = chiTietGioHangService.getAllChiTietGioHang();
        return ResponseEntity.ok(chiTietGioHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<chitietgiohangdto> updateChiTietGioHang(@PathVariable Integer id, @Valid @RequestBody chitietgiohangdto chiTietGioHangDto) {
        logger.info("API call: PUT /api/chitietgiohang/{}", id);
        chitietgiohangdto updatedChiTietGioHang = chiTietGioHangService.updateChiTietGioHang(id, chiTietGioHangDto);
        return ResponseEntity.ok(updatedChiTietGioHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChiTietGioHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/chitietgiohang/{}", id);
        chiTietGioHangService.deleteChiTietGioHang(id);
        return ResponseEntity.noContent().build();
    }
}