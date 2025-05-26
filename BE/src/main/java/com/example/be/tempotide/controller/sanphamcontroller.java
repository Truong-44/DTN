package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.sanphamdto;
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
public class sanphamcontroller {

    private static final Logger logger = LoggerFactory.getLogger(sanphamcontroller.class);

    private final com.example.be.tempotide.service.sanphamservice sanPhamService;

    @Autowired
    public sanphamcontroller(com.example.be.tempotide.service.sanphamservice sanPhamService) {
        this.sanPhamService = sanPhamService;
    }

    @PostMapping
    public ResponseEntity<sanphamdto> createSanPham(@Valid @RequestBody sanphamdto sanPhamDto) {
        logger.info("API call: POST /api/sanpham");
        sanphamdto createdSanPham = sanPhamService.createSanPham(sanPhamDto);
        return new ResponseEntity<>(createdSanPham, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<sanphamdto> getSanPhamById(@PathVariable Integer id) {
        logger.info("API call: GET /api/sanpham/{}", id);
        sanphamdto sanPhamDto = sanPhamService.getSanPhamById(id);
        return ResponseEntity.ok(sanPhamDto);
    }

    @GetMapping
    public ResponseEntity<List<sanphamdto>> getAllSanPham() {
        logger.info("API call: GET /api/sanpham");
        List<sanphamdto> sanPhamList = sanPhamService.getAllSanPham();
        return ResponseEntity.ok(sanPhamList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<sanphamdto> updateSanPham(@PathVariable Integer id, @Valid @RequestBody sanphamdto sanPhamDto) {
        logger.info("API call: PUT /api/sanpham/{}", id);
        sanphamdto updatedSanPham = sanPhamService.updateSanPham(id, sanPhamDto);
        return ResponseEntity.ok(updatedSanPham);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSanPham(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/sanpham/{}", id);
        sanPhamService.deleteSanPham(id);
        return ResponseEntity.noContent().build();
    }
}
