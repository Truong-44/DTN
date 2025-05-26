package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.chitietsanphamdto;
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
public class chitietsanphamcontroller {

    private static final Logger logger = LoggerFactory.getLogger(chitietsanphamcontroller.class);

    private final com.example.be.tempotide.service.chitietsanphamservice chiTietSanPhamService;

    @Autowired
    public chitietsanphamcontroller(com.example.be.tempotide.service.chitietsanphamservice chiTietSanPhamService) {
        this.chiTietSanPhamService = chiTietSanPhamService;
    }

    @PostMapping
    public ResponseEntity<chitietsanphamdto> createChiTietSanPham(@Valid @RequestBody chitietsanphamdto chiTietSanPhamDto) {
        logger.info("API call: POST /api/chitietsanpham");
        chitietsanphamdto createdChiTietSanPham = chiTietSanPhamService.createChiTietSanPham(chiTietSanPhamDto);
        return new ResponseEntity<>(createdChiTietSanPham, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<chitietsanphamdto> getChiTietSanPhamById(@PathVariable Integer id) {
        logger.info("API call: GET /api/chitietsanpham/{}", id);
        chitietsanphamdto chiTietSanPhamDto = chiTietSanPhamService.getChiTietSanPhamById(id);
        return ResponseEntity.ok(chiTietSanPhamDto);
    }

    @GetMapping
    public ResponseEntity<List<chitietsanphamdto>> getAllChiTietSanPham() {
        logger.info("API call: GET /api/chitietsanpham");
        List<chitietsanphamdto> chiTietSanPhamList = chiTietSanPhamService.getAllChiTietSanPham();
        return ResponseEntity.ok(chiTietSanPhamList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<chitietsanphamdto> updateChiTietSanPham(@PathVariable Integer id, @Valid @RequestBody chitietsanphamdto chiTietSanPhamDto) {
        logger.info("API call: PUT /api/chitietsanpham/{}", id);
        chitietsanphamdto updatedChiTietSanPham = chiTietSanPhamService.updateChiTietSanPham(id, chiTietSanPhamDto);
        return ResponseEntity.ok(updatedChiTietSanPham);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChiTietSanPham(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/chitietsanpham/{}", id);
        chiTietSanPhamService.deleteChiTietSanPham(id);
        return ResponseEntity.noContent().build();
    }
}