package com.example.be.TempoTide.controller;


import com.example.be.TempoTide.dto.PhieuNhapHangDto;
import com.example.be.TempoTide.service.PhieuNhapHangService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phieunhaphang")
public class PhieuNhapHangController {

    private static final Logger logger = LoggerFactory.getLogger(PhieuNhapHangController.class);

    private final PhieuNhapHangService phieuNhapHangService;

    @Autowired
    public PhieuNhapHangController(PhieuNhapHangService phieuNhapHangService) {
        this.phieuNhapHangService = phieuNhapHangService;
    }

    @PostMapping
    public ResponseEntity<PhieuNhapHangDto> createPhieuNhapHang(@Valid @RequestBody PhieuNhapHangDto phieuNhapHangDto) {
        logger.info("API call: POST /api/phieunhaphang");
        PhieuNhapHangDto createdPhieuNhapHang = phieuNhapHangService.createPhieuNhapHang(phieuNhapHangDto);
        return new ResponseEntity<>(createdPhieuNhapHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhieuNhapHangDto> getPhieuNhapHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/phieunhaphang/{}", id);
        PhieuNhapHangDto phieuNhapHangDto = phieuNhapHangService.getPhieuNhapHangById(id);
        return ResponseEntity.ok(phieuNhapHangDto);
    }

    @GetMapping
    public ResponseEntity<List<PhieuNhapHangDto>> getAllPhieuNhapHang() {
        logger.info("API call: GET /api/phieunhaphang");
        List<PhieuNhapHangDto> phieuNhapHangList = phieuNhapHangService.getAllPhieuNhapHang();
        return ResponseEntity.ok(phieuNhapHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhieuNhapHangDto> updatePhieuNhapHang(@PathVariable Integer id, @Valid @RequestBody PhieuNhapHangDto phieuNhapHangDto) {
        logger.info("API call: PUT /api/phieunhaphang/{}", id);
        PhieuNhapHangDto updatedPhieuNhapHang = phieuNhapHangService.updatePhieuNhapHang(id, phieuNhapHangDto);
        return ResponseEntity.ok(updatedPhieuNhapHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhieuNhapHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/phieunhaphang/{}", id);
        phieuNhapHangService.deletePhieuNhapHang(id);
        return ResponseEntity.noContent().build();
    }
}