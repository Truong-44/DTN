package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.ChiTietSanPhamDto;
import com.example.be.TempoTide.service.ChiTietSanPhamService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietsanpham")
public class ChiTietSanPhamController {

    private static final Logger logger = LoggerFactory.getLogger(ChiTietSanPhamController.class);

    private final ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    public ChiTietSanPhamController(ChiTietSanPhamService chiTietSanPhamService) {
        this.chiTietSanPhamService = chiTietSanPhamService;
    }

    @PostMapping
    public ResponseEntity<ChiTietSanPhamDto> createChiTietSanPham(@Valid @RequestBody ChiTietSanPhamDto chiTietSanPhamDto) {
        logger.info("API call: POST /api/chitietsanpham");
        ChiTietSanPhamDto createdChiTietSanPham = chiTietSanPhamService.createChiTietSanPham(chiTietSanPhamDto);
        return new ResponseEntity<>(createdChiTietSanPham, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietSanPhamDto> getChiTietSanPhamById(@PathVariable Integer id) {
        logger.info("API call: GET /api/chitietsanpham/{}", id);
        ChiTietSanPhamDto chiTietSanPhamDto = chiTietSanPhamService.getChiTietSanPhamById(id);
        return ResponseEntity.ok(chiTietSanPhamDto);
    }

    @GetMapping
    public ResponseEntity<List<ChiTietSanPhamDto>> getAllChiTietSanPham() {
        logger.info("API call: GET /api/chitietsanpham");
        List<ChiTietSanPhamDto> chiTietSanPhamList = chiTietSanPhamService.getAllChiTietSanPham();
        return ResponseEntity.ok(chiTietSanPhamList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChiTietSanPhamDto> updateChiTietSanPham(@PathVariable Integer id, @Valid @RequestBody ChiTietSanPhamDto chiTietSanPhamDto) {
        logger.info("API call: PUT /api/chitietsanpham/{}", id);
        ChiTietSanPhamDto updatedChiTietSanPham = chiTietSanPhamService.updateChiTietSanPham(id, chiTietSanPhamDto);
        return ResponseEntity.ok(updatedChiTietSanPham);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChiTietSanPham(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/chitietsanpham/{}", id);
        chiTietSanPhamService.deleteChiTietSanPham(id);
        return ResponseEntity.noContent().build();
    }
}