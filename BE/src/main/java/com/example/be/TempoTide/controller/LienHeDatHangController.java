package com.example.be.TempoTide.controller;


import com.example.be.TempoTide.dto.LienHeDatHangDto;
import com.example.be.TempoTide.service.LienHeDatHangService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lienhedathang")
public class LienHeDatHangController {

    private static final Logger logger = LoggerFactory.getLogger(LienHeDatHangController.class);

    private final LienHeDatHangService lienHeDatHangService;

    @Autowired
    public LienHeDatHangController(LienHeDatHangService lienHeDatHangService) {
        this.lienHeDatHangService = lienHeDatHangService;
    }

    @PostMapping
    public ResponseEntity<LienHeDatHangDto> createLienHeDatHang(@Valid @RequestBody LienHeDatHangDto lienHeDatHangDto) {
        logger.info("API call: POST /api/lienhedathang");
        LienHeDatHangDto createdLienHeDatHang = lienHeDatHangService.createLienHeDatHang(lienHeDatHangDto);
        return new ResponseEntity<>(createdLienHeDatHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LienHeDatHangDto> getLienHeDatHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/lienhedathang/{}", id);
        LienHeDatHangDto lienHeDatHangDto = lienHeDatHangService.getLienHeDatHangById(id);
        return ResponseEntity.ok(lienHeDatHangDto);
    }

    @GetMapping
    public ResponseEntity<List<LienHeDatHangDto>> getAllLienHeDatHang() {
        logger.info("API call: GET /api/lienhedathang");
        List<LienHeDatHangDto> lienHeDatHangList = lienHeDatHangService.getAllLienHeDatHang();
        return ResponseEntity.ok(lienHeDatHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LienHeDatHangDto> updateLienHeDatHang(@PathVariable Integer id, @Valid @RequestBody LienHeDatHangDto lienHeDatHangDto) {
        logger.info("API call: PUT /api/lienhedathang/{}", id);
        LienHeDatHangDto updatedLienHeDatHang = lienHeDatHangService.updateLienHeDatHang(id, lienHeDatHangDto);
        return ResponseEntity.ok(updatedLienHeDatHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLienHeDatHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/lienhedathang/{}", id);
        lienHeDatHangService.deleteLienHeDatHang(id);
        return ResponseEntity.noContent().build();
    }
}