package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.phiendangnhapdto;
import com.example.be.tempotide.service.PhienDangNhapService;
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
public class phiendangnhapcontroller {

    private static final Logger logger = LoggerFactory.getLogger(phiendangnhapcontroller.class);

    private final PhienDangNhapService phienDangNhapService;

    @Autowired
    public phiendangnhapcontroller(PhienDangNhapService phienDangNhapService) {
        this.phienDangNhapService = phienDangNhapService;
    }

    @PostMapping
    public ResponseEntity<phiendangnhapdto> createPhienDangNhap(@Valid @RequestBody phiendangnhapdto phienDangNhapDto) {
        logger.info("API call: POST /api/phiendangnhap");
        phiendangnhapdto createdPhienDangNhap = phienDangNhapService.createPhienDangNhap(phienDangNhapDto);
        return new ResponseEntity<>(createdPhienDangNhap, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<phiendangnhapdto> getPhienDangNhapById(@PathVariable Integer id) {
        logger.info("API call: GET /api/phiendangnhap/{}", id);
        phiendangnhapdto phienDangNhapDto = phienDangNhapService.getPhienDangNhapById(id);
        return ResponseEntity.ok(phienDangNhapDto);
    }

    @GetMapping
    public ResponseEntity<List<phiendangnhapdto>> getAllPhienDangNhap() {
        logger.info("API call: GET /api/phiendangnhap");
        List<phiendangnhapdto> phienDangNhapList = phienDangNhapService.getAllPhienDangNhap();
        return ResponseEntity.ok(phienDangNhapList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<phiendangnhapdto> updatePhienDangNhap(@PathVariable Integer id, @Valid @RequestBody phiendangnhapdto phienDangNhapDto) {
        logger.info("API call: PUT /api/phiendangnhap/{}", id);
        phiendangnhapdto updatedPhienDangNhap = phienDangNhapService.updatePhienDangNhap(id, phienDangNhapDto);
        return ResponseEntity.ok(updatedPhienDangNhap);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhienDangNhap(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/phiendangnhap/{}", id);
        phienDangNhapService.deletePhienDangNhap(id);
        return ResponseEntity.noContent().build();
    }
}