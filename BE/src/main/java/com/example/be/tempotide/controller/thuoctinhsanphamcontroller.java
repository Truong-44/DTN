package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.thuoctinhsanphamdto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thuoctinhsanpham")
public class thuoctinhsanphamcontroller {

    private static final Logger logger = LoggerFactory.getLogger(thuoctinhsanphamcontroller.class);

    private final com.example.be.tempotide.service.thuoctinhsanphamservice thuocTinhSanPhamService;

    @Autowired
    public thuoctinhsanphamcontroller(com.example.be.tempotide.service.thuoctinhsanphamservice thuocTinhSanPhamService) {
        this.thuocTinhSanPhamService = thuocTinhSanPhamService;
    }

    @PostMapping
    public ResponseEntity<thuoctinhsanphamdto> createThuocTinhSanPham(@Valid @RequestBody thuoctinhsanphamdto thuocTinhSanPhamDto) {
        logger.info("API call: POST /api/thuoctinhsanpham");
        thuoctinhsanphamdto createdThuocTinhSanPham = thuocTinhSanPhamService.createThuocTinhSanPham(thuocTinhSanPhamDto);
        return new ResponseEntity<>(createdThuocTinhSanPham, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<thuoctinhsanphamdto> getThuocTinhSanPhamById(@PathVariable Integer id) {
        logger.info("API call: GET /api/thuoctinhsanpham/{}", id);
        thuoctinhsanphamdto thuocTinhSanPhamDto = thuocTinhSanPhamService.getThuocTinhSanPhamById(id);
        return ResponseEntity.ok(thuocTinhSanPhamDto);
    }

    @GetMapping
    public ResponseEntity<List<thuoctinhsanphamdto>> getAllThuocTinhSanPham() {
        logger.info("API call: GET /api/thuoctinhsanpham");
        List<thuoctinhsanphamdto> thuocTinhSanPhamList = thuocTinhSanPhamService.getAllThuocTinhSanPham();
        return ResponseEntity.ok(thuocTinhSanPhamList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<thuoctinhsanphamdto> updateThuocTinhSanPham(@PathVariable Integer id, @Valid @RequestBody thuoctinhsanphamdto thuocTinhSanPhamDto) {
        logger.info("API call: PUT /api/thuoctinhsanpham/{}", id);
        thuoctinhsanphamdto updatedThuocTinhSanPham = thuocTinhSanPhamService.updateThuocTinhSanPham(id, thuocTinhSanPhamDto);
        return ResponseEntity.ok(updatedThuocTinhSanPham);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThuocTinhSanPham(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/thuoctinhsanpham/{}", id);
        thuocTinhSanPhamService.deleteThuocTinhSanPham(id);
        return ResponseEntity.noContent().build();
    }
}