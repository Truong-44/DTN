package com.example.be.tempotide.controller;


import com.example.be.tempotide.dto.danhgiasanphamdto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danhgiasanpham")
public class danhgiasanphamcontroller {

    private static final Logger logger = LoggerFactory.getLogger(danhgiasanphamcontroller.class);

    private final com.example.be.tempotide.service.danhgiasanphamservice danhGiaSanPhamService;

    @Autowired
    public danhgiasanphamcontroller(com.example.be.tempotide.service.danhgiasanphamservice danhGiaSanPhamService) {
        this.danhGiaSanPhamService = danhGiaSanPhamService;
    }

    @PostMapping
    public ResponseEntity<danhgiasanphamdto> createDanhGiaSanPham(@Valid @RequestBody danhgiasanphamdto danhGiaSanPhamDto) {
        logger.info("API call: POST /api/danhgiasanpham");
        danhgiasanphamdto createdDanhGiaSanPham = danhGiaSanPhamService.createDanhGiaSanPham(danhGiaSanPhamDto);
        return new ResponseEntity<>(createdDanhGiaSanPham, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<danhgiasanphamdto> getDanhGiaSanPhamById(@PathVariable Integer id) {
        logger.info("API call: GET /api/danhgiasanpham/{}", id);
        danhgiasanphamdto danhGiaSanPhamDto = danhGiaSanPhamService.getDanhGiaSanPhamById(id);
        return ResponseEntity.ok(danhGiaSanPhamDto);
    }

    @GetMapping
    public ResponseEntity<List<danhgiasanphamdto>> getAllDanhGiaSanPham() {
        logger.info("API call: GET /api/danhgiasanpham");
        List<danhgiasanphamdto> danhGiaSanPhamList = danhGiaSanPhamService.getAllDanhGiaSanPham();
        return ResponseEntity.ok(danhGiaSanPhamList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<danhgiasanphamdto> updateDanhGiaSanPham(@PathVariable Integer id, @Valid @RequestBody danhgiasanphamdto danhGiaSanPhamDto) {
        logger.info("API call: PUT /api/danhgiasanpham/{}", id);
        danhgiasanphamdto updatedDanhGiaSanPham = danhGiaSanPhamService.updateDanhGiaSanPham(id, danhGiaSanPhamDto);
        return ResponseEntity.ok(updatedDanhGiaSanPham);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDanhGiaSanPham(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/danhgiasanpham/{}", id);
        danhGiaSanPhamService.deleteDanhGiaSanPham(id);
        return ResponseEntity.noContent().build();
    }
}