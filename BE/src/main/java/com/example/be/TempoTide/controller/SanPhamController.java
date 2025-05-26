package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.SanPhamDto;
import com.example.be.TempoTide.service.SanPhamService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sanpham")
public class SanPhamController {

    private static final Logger logger = LoggerFactory.getLogger(SanPhamController.class);

    private final SanPhamService sanPhamService;

    @Autowired
    public SanPhamController(SanPhamService sanPhamService) {
        this.sanPhamService = sanPhamService;
    }

    @PostMapping
    public ResponseEntity<SanPhamDto> createSanPham(@Valid @RequestBody SanPhamDto sanPhamDto) {
        logger.info("API call: POST /api/sanpham");
        SanPhamDto createdSanPham = sanPhamService.createSanPham(sanPhamDto);
        return new ResponseEntity<>(createdSanPham, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamDto> getSanPhamById(@PathVariable Integer id) {
        logger.info("API call: GET /api/sanpham/{}", id);
        SanPhamDto sanPhamDto = sanPhamService.getSanPhamById(id);
        return ResponseEntity.ok(sanPhamDto);
    }

    @GetMapping
    public ResponseEntity<List<SanPhamDto>> getAllSanPham() {
        logger.info("API call: GET /api/sanpham");
        List<SanPhamDto> sanPhamList = sanPhamService.getAllSanPham();
        return ResponseEntity.ok(sanPhamList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SanPhamDto> updateSanPham(@PathVariable Integer id, @Valid @RequestBody SanPhamDto sanPhamDto) {
        logger.info("API call: PUT /api/sanpham/{}", id);
        SanPhamDto updatedSanPham = sanPhamService.updateSanPham(id, sanPhamDto);
        return ResponseEntity.ok(updatedSanPham);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSanPham(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/sanpham/{}", id);
        sanPhamService.deleteSanPham(id);
        return ResponseEntity.noContent().build();
    }
}
