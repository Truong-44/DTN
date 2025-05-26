package com.example.be.TempoTide.controller;


import com.example.be.TempoTide.dto.LichSuDonHangDto;
import com.example.be.TempoTide.service.LichSuDonHangService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lichsudonhang")
public class LichSuDonHangController {

    private static final Logger logger = LoggerFactory.getLogger(LichSuDonHangController.class);

    private final LichSuDonHangService lichSuDonHangService;

    @Autowired
    public LichSuDonHangController(LichSuDonHangService lichSuDonHangService) {
        this.lichSuDonHangService = lichSuDonHangService;
    }

    @PostMapping
    public ResponseEntity<LichSuDonHangDto> createLichSuDonHang(@Valid @RequestBody LichSuDonHangDto lichSuDonHangDto) {
        logger.info("API call: POST /api/lichsudonhang");
        LichSuDonHangDto createdLichSuDonHang = lichSuDonHangService.createLichSuDonHang(lichSuDonHangDto);
        return new ResponseEntity<>(createdLichSuDonHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichSuDonHangDto> getLichSuDonHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/lichsudonhang/{}", id);
        LichSuDonHangDto lichSuDonHangDto = lichSuDonHangService.getLichSuDonHangById(id);
        return ResponseEntity.ok(lichSuDonHangDto);
    }

    @GetMapping
    public ResponseEntity<List<LichSuDonHangDto>> getAllLichSuDonHang() {
        logger.info("API call: GET /api/lichsudonhang");
        List<LichSuDonHangDto> lichSuDonHangList = lichSuDonHangService.getAllLichSuDonHang();
        return ResponseEntity.ok(lichSuDonHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LichSuDonHangDto> updateLichSuDonHang(@PathVariable Integer id, @Valid @RequestBody LichSuDonHangDto lichSuDonHangDto) {
        logger.info("API call: PUT /api/lichsudonhang/{}", id);
        LichSuDonHangDto updatedLichSuDonHang = lichSuDonHangService.updateLichSuDonHang(id, lichSuDonHangDto);
        return ResponseEntity.ok(updatedLichSuDonHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLichSuDonHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/lichsudonhang/{}", id);
        lichSuDonHangService.deleteLichSuDonHang(id);
        return ResponseEntity.noContent().build();
    }
}