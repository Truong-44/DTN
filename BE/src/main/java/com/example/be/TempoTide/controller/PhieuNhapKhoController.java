package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.PhieuNhapKhoDto;
import com.example.be.TempoTide.service.PhieuNhapKhoService;
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
public class PhieuNhapKhoController {

    private static final Logger logger = LoggerFactory.getLogger(PhieuNhapKhoController.class);

    private final PhieuNhapKhoService phieuNhapKhoService;

    @Autowired
    public PhieuNhapKhoController(PhieuNhapKhoService phieuNhapKhoService) {
        this.phieuNhapKhoService = phieuNhapKhoService;
    }

    @PostMapping
    public ResponseEntity<PhieuNhapKhoDto> createPhieuNhapKho(@Valid @RequestBody PhieuNhapKhoDto phieuNhapKhoDto) {
        logger.info("API call: POST /api/phieunhapkho");
        PhieuNhapKhoDto createdPhieuNhapKho = phieuNhapKhoService.createPhieuNhapKho(phieuNhapKhoDto);
        return new ResponseEntity<>(createdPhieuNhapKho, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhieuNhapKhoDto> getPhieuNhapKhoById(@PathVariable Integer id) {
        logger.info("API call: GET /api/phieunhapkho/{}", id);
        PhieuNhapKhoDto phieuNhapKhoDto = phieuNhapKhoService.getPhieuNhapKhoById(id);
        return ResponseEntity.ok(phieuNhapKhoDto);
    }

    @GetMapping
    public ResponseEntity<List<PhieuNhapKhoDto>> getAllPhieuNhapKho() {
        logger.info("API call: GET /api/phieunhapkho");
        List<PhieuNhapKhoDto> phieuNhapKhoList = phieuNhapKhoService.getAllPhieuNhapKho();
        return ResponseEntity.ok(phieuNhapKhoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhieuNhapKhoDto> updatePhieuNhapKho(@PathVariable Integer id, @Valid @RequestBody PhieuNhapKhoDto phieuNhapKhoDto) {
        logger.info("API call: PUT /api/phieunhapkho/{}", id);
        PhieuNhapKhoDto updatedPhieuNhapKho = phieuNhapKhoService.updatePhieuNhapKho(id, phieuNhapKhoDto);
        return ResponseEntity.ok(updatedPhieuNhapKho);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhieuNhapKho(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/phieunhapkho/{}", id);
        phieuNhapKhoService.deletePhieuNhapKho(id);
        return ResponseEntity.noContent().build();
    }
}