package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.KhoHangDto;
import com.example.be.TempoTide.service.KhoHangService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khohang")
public class KhoHangController {

    private static final Logger logger = LoggerFactory.getLogger(KhoHangController.class);

    private final KhoHangService khoHangService;

    @Autowired
    public KhoHangController(KhoHangService khoHangService) {
        this.khoHangService = khoHangService;
    }

    @PostMapping
    public ResponseEntity<KhoHangDto> createKhoHang(@Valid @RequestBody KhoHangDto khoHangDto) {
        logger.info("API call: POST /api/kohang");
        KhoHangDto createdKhoHang = khoHangService.createKhoHang(khoHangDto);
        return new ResponseEntity<>(createdKhoHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhoHangDto> getKhoHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/kohang/{}", id);
        KhoHangDto khoHangDto = khoHangService.getKhoHangById(id);
        return ResponseEntity.ok(khoHangDto);
    }

    @GetMapping
    public ResponseEntity<List<KhoHangDto>> getAllKhoHang() {
        logger.info("API call: GET /api/kohang");
        List<KhoHangDto> khoHangList = khoHangService.getAllKhoHang();
        return ResponseEntity.ok(khoHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KhoHangDto> updateKhoHang(@PathVariable Integer id, @Valid @RequestBody KhoHangDto khoHangDto) {
        logger.info("API call: PUT /api/kohang/{}", id);
        KhoHangDto updatedKhoHang = khoHangService.updateKhoHang(id, khoHangDto);
        return ResponseEntity.ok(updatedKhoHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKhoHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/kohang/{}", id);
        khoHangService.deleteKhoHang(id);
        return ResponseEntity.noContent().build();
    }
}