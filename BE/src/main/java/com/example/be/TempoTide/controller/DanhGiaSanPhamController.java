package com.example.be.TempoTide.controller;


import com.example.be.TempoTide.dto.DanhGiaSanPhamDto;
import com.example.be.TempoTide.service.DanhGiaSanPhamService;
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
public class DanhGiaSanPhamController {

    private static final Logger logger = LoggerFactory.getLogger(DanhGiaSanPhamController.class);

    private final DanhGiaSanPhamService danhGiaSanPhamService;

    @Autowired
    public DanhGiaSanPhamController(DanhGiaSanPhamService danhGiaSanPhamService) {
        this.danhGiaSanPhamService = danhGiaSanPhamService;
    }

    @PostMapping
    public ResponseEntity<DanhGiaSanPhamDto> createDanhGiaSanPham(@Valid @RequestBody DanhGiaSanPhamDto danhGiaSanPhamDto) {
        logger.info("API call: POST /api/danhgiasanpham");
        DanhGiaSanPhamDto createdDanhGiaSanPham = danhGiaSanPhamService.createDanhGiaSanPham(danhGiaSanPhamDto);
        return new ResponseEntity<>(createdDanhGiaSanPham, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DanhGiaSanPhamDto> getDanhGiaSanPhamById(@PathVariable Integer id) {
        logger.info("API call: GET /api/danhgiasanpham/{}", id);
        DanhGiaSanPhamDto danhGiaSanPhamDto = danhGiaSanPhamService.getDanhGiaSanPhamById(id);
        return ResponseEntity.ok(danhGiaSanPhamDto);
    }

    @GetMapping
    public ResponseEntity<List<DanhGiaSanPhamDto>> getAllDanhGiaSanPham() {
        logger.info("API call: GET /api/danhgiasanpham");
        List<DanhGiaSanPhamDto> danhGiaSanPhamList = danhGiaSanPhamService.getAllDanhGiaSanPham();
        return ResponseEntity.ok(danhGiaSanPhamList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DanhGiaSanPhamDto> updateDanhGiaSanPham(@PathVariable Integer id, @Valid @RequestBody DanhGiaSanPhamDto danhGiaSanPhamDto) {
        logger.info("API call: PUT /api/danhgiasanpham/{}", id);
        DanhGiaSanPhamDto updatedDanhGiaSanPham = danhGiaSanPhamService.updateDanhGiaSanPham(id, danhGiaSanPhamDto);
        return ResponseEntity.ok(updatedDanhGiaSanPham);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDanhGiaSanPham(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/danhgiasanpham/{}", id);
        danhGiaSanPhamService.deleteDanhGiaSanPham(id);
        return ResponseEntity.noContent().build();
    }
}