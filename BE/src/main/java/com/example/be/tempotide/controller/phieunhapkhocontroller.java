package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.phieunhapkhodto;
import com.example.be.tempotide.service.PhieuNhapKhoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phieunhapkho")
public class phieunhapkhocontroller {

    private static final Logger logger = LoggerFactory.getLogger(phieunhapkhocontroller.class);

    private final PhieuNhapKhoService phieuNhapKhoService;

    @Autowired
    public phieunhapkhocontroller(PhieuNhapKhoService phieuNhapKhoService) {
        this.phieuNhapKhoService = phieuNhapKhoService;
    }

    @PostMapping
    public ResponseEntity<phieunhapkhodto> createPhieuNhapKho(@Valid @RequestBody phieunhapkhodto phieuNhapKhoDto) {
        logger.info("API call: POST /api/phieunhapkho");
        phieunhapkhodto createdPhieuNhapKho = phieuNhapKhoService.createPhieuNhapKho(phieuNhapKhoDto);
        return new ResponseEntity<>(createdPhieuNhapKho, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<phieunhapkhodto> getPhieuNhapKhoById(@PathVariable Integer id) {
        logger.info("API call: GET /api/phieunhapkho/{}", id);
        phieunhapkhodto phieuNhapKhoDto = phieuNhapKhoService.getPhieuNhapKhoById(id);
        return ResponseEntity.ok(phieuNhapKhoDto);
    }

    @GetMapping
    public ResponseEntity<List<phieunhapkhodto>> getAllPhieuNhapKho() {
        logger.info("API call: GET /api/phieunhapkho");
        List<phieunhapkhodto> phieuNhapKhoList = phieuNhapKhoService.getAllPhieuNhapKho();
        return ResponseEntity.ok(phieuNhapKhoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<phieunhapkhodto> updatePhieuNhapKho(@PathVariable Integer id, @Valid @RequestBody phieunhapkhodto phieuNhapKhoDto) {
        logger.info("API call: PUT /api/phieunhapkho/{}", id);
        phieunhapkhodto updatedPhieuNhapKho = phieuNhapKhoService.updatePhieuNhapKho(id, phieuNhapKhoDto);
        return ResponseEntity.ok(updatedPhieuNhapKho);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhieuNhapKho(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/phieunhapkho/{}", id);
        phieuNhapKhoService.deletePhieuNhapKho(id);
        return ResponseEntity.noContent().build();
    }
}