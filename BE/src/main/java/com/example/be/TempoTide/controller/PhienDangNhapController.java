package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.PhienDangNhapDto;
import com.example.be.TempoTide.service.PhienDangNhapService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phiendangnhap")
public class PhienDangNhapController {

    private static final Logger logger = LoggerFactory.getLogger(PhienDangNhapController.class);

    private final PhienDangNhapService phienDangNhapService;

    @Autowired
    public PhienDangNhapController(PhienDangNhapService phienDangNhapService) {
        this.phienDangNhapService = phienDangNhapService;
    }

    @PostMapping
    public ResponseEntity<PhienDangNhapDto> createPhienDangNhap(@Valid @RequestBody PhienDangNhapDto phienDangNhapDto) {
        logger.info("API call: POST /api/phiendangnhap");
        PhienDangNhapDto createdPhienDangNhap = phienDangNhapService.createPhienDangNhap(phienDangNhapDto);
        return new ResponseEntity<>(createdPhienDangNhap, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhienDangNhapDto> getPhienDangNhapById(@PathVariable Integer id) {
        logger.info("API call: GET /api/phiendangnhap/{}", id);
        PhienDangNhapDto phienDangNhapDto = phienDangNhapService.getPhienDangNhapById(id);
        return ResponseEntity.ok(phienDangNhapDto);
    }

    @GetMapping
    public ResponseEntity<List<PhienDangNhapDto>> getAllPhienDangNhap() {
        logger.info("API call: GET /api/phiendangnhap");
        List<PhienDangNhapDto> phienDangNhapList = phienDangNhapService.getAllPhienDangNhap();
        return ResponseEntity.ok(phienDangNhapList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhienDangNhapDto> updatePhienDangNhap(@PathVariable Integer id, @Valid @RequestBody PhienDangNhapDto phienDangNhapDto) {
        logger.info("API call: PUT /api/phiendangnhap/{}", id);
        PhienDangNhapDto updatedPhienDangNhap = phienDangNhapService.updatePhienDangNhap(id, phienDangNhapDto);
        return ResponseEntity.ok(updatedPhienDangNhap);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhienDangNhap(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/phiendangnhap/{}", id);
        phienDangNhapService.deletePhienDangNhap(id);
        return ResponseEntity.noContent().build();
    }
}