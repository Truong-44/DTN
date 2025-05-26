package com.example.be.tempotide.controller;


import com.example.be.tempotide.dto.chitietdonhangdto;
import com.example.be.tempotide.service.ChiTietDonHangService;
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
public class chitietdonhangcontroller {

    private static final Logger logger = LoggerFactory.getLogger(chitietdonhangcontroller.class);

    private final ChiTietDonHangService chiTietDonHangService;

    @Autowired
    public chitietdonhangcontroller(ChiTietDonHangService chiTietDonHangService) {
        this.chiTietDonHangService = chiTietDonHangService;
    }

    @PostMapping
    public ResponseEntity<chitietdonhangdto> createChiTietDonHang(@Valid @RequestBody chitietdonhangdto chiTietDonHangDto) {
        logger.info("API call: POST /api/chitietdonhang");
        chitietdonhangdto createdChiTietDonHang = chiTietDonHangService.createChiTietDonHang(chiTietDonHangDto);
        return new ResponseEntity<>(createdChiTietDonHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<chitietdonhangdto> getChiTietDonHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/chitietdonhang/{}", id);
        chitietdonhangdto chiTietDonHangDto = chiTietDonHangService.getChiTietDonHangById(id);
        return ResponseEntity.ok(chiTietDonHangDto);
    }

    @GetMapping
    public ResponseEntity<List<chitietdonhangdto>> getAllChiTietDonHang() {
        logger.info("API call: GET /api/chitietdonhang");
        List<chitietdonhangdto> chiTietDonHangList = chiTietDonHangService.getAllChiTietDonHang();
        return ResponseEntity.ok(chiTietDonHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<chitietdonhangdto> updateChiTietDonHang(@PathVariable Integer id, @Valid @RequestBody chitietdonhangdto chiTietDonHangDto) {
        logger.info("API call: PUT /api/chitietdonhang/{}", id);
        chitietdonhangdto updatedChiTietDonHang = chiTietDonHangService.updateChiTietDonHang(id, chiTietDonHangDto);
        return ResponseEntity.ok(updatedChiTietDonHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChiTietDonHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/chitietdonhang/{}", id);
        chiTietDonHangService.deleteChiTietDonHang(id);
        return ResponseEntity.noContent().build();
    }
}