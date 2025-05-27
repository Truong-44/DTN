package com.example.be.tempotide.controller;


import com.example.be.tempotide.dto.phieunhaphangdto;
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
public class phieunhaphangcontroller {

    private static final Logger logger = LoggerFactory.getLogger(phieunhaphangcontroller.class);

    private final com.example.be.tempotide.service.phieunhaphangservice phieuNhapHangService;

    @Autowired
    public phieunhaphangcontroller(com.example.be.tempotide.service.phieunhaphangservice phieuNhapHangService) {
        this.phieuNhapHangService = phieuNhapHangService;
    }

    @PostMapping
    public ResponseEntity<phieunhaphangdto> createPhieuNhapHang(@Valid @RequestBody phieunhaphangdto phieuNhapHangDto) {
        logger.info("API call: POST /api/phieunhaphang");
        phieunhaphangdto createdPhieuNhapHang = phieuNhapHangService.createPhieuNhapHang(phieuNhapHangDto);
        return new ResponseEntity<>(createdPhieuNhapHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<phieunhaphangdto> getPhieuNhapHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/phieunhaphang/{}", id);
        phieunhaphangdto phieuNhapHangDto = phieuNhapHangService.getPhieuNhapHangById(id);
        return ResponseEntity.ok(phieuNhapHangDto);
    }

    @GetMapping
    public ResponseEntity<List<phieunhaphangdto>> getAllPhieuNhapHang() {
        logger.info("API call: GET /api/phieunhaphang");
        List<phieunhaphangdto> phieuNhapHangList = phieuNhapHangService.getAllPhieuNhapHang();
        return ResponseEntity.ok(phieuNhapHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<phieunhaphangdto> updatePhieuNhapHang(@PathVariable Integer id, @Valid @RequestBody phieunhaphangdto phieuNhapHangDto) {
        logger.info("API call: PUT /api/phieunhaphang/{}", id);
        phieunhaphangdto updatedPhieuNhapHang = phieuNhapHangService.updatePhieuNhapHang(id, phieuNhapHangDto);
        return ResponseEntity.ok(updatedPhieuNhapHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhieuNhapHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/phieunhaphang/{}", id);
        phieuNhapHangService.deletePhieuNhapHang(id);
        return ResponseEntity.noContent().build();
    }
}