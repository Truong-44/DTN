package com.example.be.TempoTide.controller;


import com.example.be.TempoTide.dto.GioHangDto;
import com.example.be.TempoTide.service.GioHangService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/giohang")
public class GioHangController {

    private static final Logger logger = LoggerFactory.getLogger(GioHangController.class);

    private final GioHangService gioHangService;

    @Autowired
    public GioHangController(GioHangService gioHangService) {
        this.gioHangService = gioHangService;
    }

    @PostMapping
    public ResponseEntity<GioHangDto> createGioHang(@Valid @RequestBody GioHangDto gioHangDto) {
        logger.info("API call: POST /api/giohang");
        GioHangDto createdGioHang = gioHangService.createGioHang(gioHangDto);
        return new ResponseEntity<>(createdGioHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GioHangDto> getGioHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/giohang/{}", id);
        GioHangDto gioHangDto = gioHangService.getGioHangById(id);
        return ResponseEntity.ok(gioHangDto);
    }

    @GetMapping
    public ResponseEntity<List<GioHangDto>> getAllGioHang() {
        logger.info("API call: GET /api/giohang");
        List<GioHangDto> gioHangList = gioHangService.getAllGioHang();
        return ResponseEntity.ok(gioHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GioHangDto> updateGioHang(@PathVariable Integer id, @Valid @RequestBody GioHangDto gioHangDto) {
        logger.info("API call: PUT /api/giohang/{}", id);
        GioHangDto updatedGioHang = gioHangService.updateGioHang(id, gioHangDto);
        return ResponseEntity.ok(updatedGioHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGioHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/giohang/{}", id);
        gioHangService.deleteGioHang(id);
        return ResponseEntity.noContent().build();
    }
}