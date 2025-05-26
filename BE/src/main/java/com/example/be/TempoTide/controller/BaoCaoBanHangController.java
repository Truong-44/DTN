package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.BaoCaoBanHangDto;
import com.example.be.TempoTide.service.BaoCaoBanHangService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baocaobanhang")
public class BaoCaoBanHangController {

    private static final Logger logger = LoggerFactory.getLogger(BaoCaoBanHangController.class);

    private final BaoCaoBanHangService baoCaoBanHangService;

    @Autowired
    public BaoCaoBanHangController(BaoCaoBanHangService baoCaoBanHangService) {
        this.baoCaoBanHangService = baoCaoBanHangService;
    }

    @PostMapping
    public ResponseEntity<BaoCaoBanHangDto> createBaoCaoBanHang(@Valid @RequestBody BaoCaoBanHangDto baoCaoBanHangDto) {
        logger.info("API call: POST /api/baocaobanhang");
        BaoCaoBanHangDto createdBaoCaoBanHang = baoCaoBanHangService.createBaoCaoBanHang(baoCaoBanHangDto);
        return new ResponseEntity<>(createdBaoCaoBanHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaoCaoBanHangDto> getBaoCaoBanHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/baocaobanhang/{}", id);
        BaoCaoBanHangDto baoCaoBanHangDto = baoCaoBanHangService.getBaoCaoBanHangById(id);
        return ResponseEntity.ok(baoCaoBanHangDto);
    }

    @GetMapping
    public ResponseEntity<List<BaoCaoBanHangDto>> getAllBaoCaoBanHang() {
        logger.info("API call: GET /api/baocaobanhang");
        List<BaoCaoBanHangDto> baoCaoBanHangList = baoCaoBanHangService.getAllBaoCaoBanHang();
        return ResponseEntity.ok(baoCaoBanHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaoCaoBanHangDto> updateBaoCaoBanHang(@PathVariable Integer id, @Valid @RequestBody BaoCaoBanHangDto baoCaoBanHangDto) {
        logger.info("API call: PUT /api/baocaobanhang/{}", id);
        BaoCaoBanHangDto updatedBaoCaoBanHang = baoCaoBanHangService.updateBaoCaoBanHang(id, baoCaoBanHangDto);
        return ResponseEntity.ok(updatedBaoCaoBanHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaoCaoBanHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/baocaobanhang/{}", id);
        baoCaoBanHangService.deleteBaoCaoBanHang(id);
        return ResponseEntity.noContent().build();
    }
}