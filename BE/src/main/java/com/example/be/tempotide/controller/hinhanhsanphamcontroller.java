package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.hinhanhsanphamdto;
import com.example.be.tempotide.service.HinhAnhSanPhamService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hinhanhsanpham")
public class hinhanhsanphamcontroller {

    private static final Logger logger = LoggerFactory.getLogger(hinhanhsanphamcontroller.class);

    private final HinhAnhSanPhamService hinhAnhSanPhamService;

    @Autowired
    public hinhanhsanphamcontroller(HinhAnhSanPhamService hinhAnhSanPhamService) {
        this.hinhAnhSanPhamService = hinhAnhSanPhamService;
    }

    @PostMapping
    public ResponseEntity<hinhanhsanphamdto> createHinhAnhSanPham(@Valid @RequestBody hinhanhsanphamdto hinhAnhSanPhamDto) {
        logger.info("API call: POST /api/hinhanhsanpham");
        hinhanhsanphamdto createdHinhAnhSanPham = hinhAnhSanPhamService.createHinhAnhSanPham(hinhAnhSanPhamDto);
        return new ResponseEntity<>(createdHinhAnhSanPham, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<hinhanhsanphamdto> getHinhAnhSanPhamById(@PathVariable Integer id) {
        logger.info("API call: GET /api/hinhanhsanpham/{}", id);
        hinhanhsanphamdto hinhAnhSanPhamDto = hinhAnhSanPhamService.getHinhAnhSanPhamById(id);
        return ResponseEntity.ok(hinhAnhSanPhamDto);
    }

    @GetMapping
    public ResponseEntity<List<hinhanhsanphamdto>> getAllHinhAnhSanPham() {
        logger.info("API call: GET /api/hinhanhsanpham");
        List<hinhanhsanphamdto> hinhAnhSanPhamList = hinhAnhSanPhamService.getAllHinhAnhSanPham();
        return ResponseEntity.ok(hinhAnhSanPhamList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<hinhanhsanphamdto> updateHinhAnhSanPham(@PathVariable Integer id, @Valid @RequestBody hinhanhsanphamdto hinhAnhSanPhamDto) {
        logger.info("API call: PUT /api/hinhanhsanpham/{}", id);
        hinhanhsanphamdto updatedHinhAnhSanPham = hinhAnhSanPhamService.updateHinhAnhSanPham(id, hinhAnhSanPhamDto);
        return ResponseEntity.ok(updatedHinhAnhSanPham);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHinhAnhSanPham(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/hinhanhsanpham/{}", id);
        hinhAnhSanPhamService.deleteHinhAnhSanPham(id);
        return ResponseEntity.noContent().build();
    }
}