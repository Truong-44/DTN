package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.PhuongThucVanChuyenDto;
import com.example.be.TempoTide.service.PhuongThucVanChuyenService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phuongthucvanchuyen")
public class PhuongThucVanChuyenController {

    private static final Logger logger = LoggerFactory.getLogger(PhuongThucVanChuyenController.class);

    private final PhuongThucVanChuyenService phuongThucVanChuyenService;

    @Autowired
    public PhuongThucVanChuyenController(PhuongThucVanChuyenService phuongThucVanChuyenService) {
        this.phuongThucVanChuyenService = phuongThucVanChuyenService;
    }

    @PostMapping
    public ResponseEntity<PhuongThucVanChuyenDto> createPhuongThucVanChuyen(@Valid @RequestBody PhuongThucVanChuyenDto phuongThucVanChuyenDto) {
        logger.info("API call: POST /api/phuongthucvanchuyen");
        PhuongThucVanChuyenDto createdPhuongThucVanChuyen = phuongThucVanChuyenService.createPhuongThucVanChuyen(phuongThucVanChuyenDto);
        return new ResponseEntity<>(createdPhuongThucVanChuyen, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhuongThucVanChuyenDto> getPhuongThucVanChuyenById(@PathVariable Integer id) {
        logger.info("API call: GET /api/phuongthucvanchuyen/{}", id);
        PhuongThucVanChuyenDto phuongThucVanChuyenDto = phuongThucVanChuyenService.getPhuongThucVanChuyenById(id);
        return ResponseEntity.ok(phuongThucVanChuyenDto);
    }

    @GetMapping
    public ResponseEntity<List<PhuongThucVanChuyenDto>> getAllPhuongThucVanChuyen() {
        logger.info("API call: GET /api/phuongthucvanchuyen");
        List<PhuongThucVanChuyenDto> phuongThucVanChuyenList = phuongThucVanChuyenService.getAllPhuongThucVanChuyen();
        return ResponseEntity.ok(phuongThucVanChuyenList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhuongThucVanChuyenDto> updatePhuongThucVanChuyen(@PathVariable Integer id, @Valid @RequestBody PhuongThucVanChuyenDto phuongThucVanChuyenDto) {
        logger.info("API call: PUT /api/phuongthucvanchuyen/{}", id);
        PhuongThucVanChuyenDto updatedPhuongThucVanChuyen = phuongThucVanChuyenService.updatePhuongThucVanChuyen(id, phuongThucVanChuyenDto);
        return ResponseEntity.ok(updatedPhuongThucVanChuyen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhuongThucVanChuyen(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/phuongthucvanchuyen/{}", id);
        phuongThucVanChuyenService.deletePhuongThucVanChuyen(id);
        return ResponseEntity.noContent().build();
    }
}