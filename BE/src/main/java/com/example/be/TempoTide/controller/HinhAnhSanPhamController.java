package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.HinhAnhSanPhamDto;
import com.example.be.TempoTide.service.HinhAnhSanPhamService;
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
public class HinhAnhSanPhamController {

    private static final Logger logger = LoggerFactory.getLogger(HinhAnhSanPhamController.class);

    private final HinhAnhSanPhamService hinhAnhSanPhamService;

    @Autowired
    public HinhAnhSanPhamController(HinhAnhSanPhamService hinhAnhSanPhamService) {
        this.hinhAnhSanPhamService = hinhAnhSanPhamService;
    }

    @PostMapping
    public ResponseEntity<HinhAnhSanPhamDto> createHinhAnhSanPham(@Valid @RequestBody HinhAnhSanPhamDto hinhAnhSanPhamDto) {
        logger.info("API call: POST /api/hinhanhsanpham");
        HinhAnhSanPhamDto createdHinhAnhSanPham = hinhAnhSanPhamService.createHinhAnhSanPham(hinhAnhSanPhamDto);
        return new ResponseEntity<>(createdHinhAnhSanPham, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HinhAnhSanPhamDto> getHinhAnhSanPhamById(@PathVariable Integer id) {
        logger.info("API call: GET /api/hinhanhsanpham/{}", id);
        HinhAnhSanPhamDto hinhAnhSanPhamDto = hinhAnhSanPhamService.getHinhAnhSanPhamById(id);
        return ResponseEntity.ok(hinhAnhSanPhamDto);
    }

    @GetMapping
    public ResponseEntity<List<HinhAnhSanPhamDto>> getAllHinhAnhSanPham() {
        logger.info("API call: GET /api/hinhanhsanpham");
        List<HinhAnhSanPhamDto> hinhAnhSanPhamList = hinhAnhSanPhamService.getAllHinhAnhSanPham();
        return ResponseEntity.ok(hinhAnhSanPhamList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HinhAnhSanPhamDto> updateHinhAnhSanPham(@PathVariable Integer id, @Valid @RequestBody HinhAnhSanPhamDto hinhAnhSanPhamDto) {
        logger.info("API call: PUT /api/hinhanhsanpham/{}", id);
        HinhAnhSanPhamDto updatedHinhAnhSanPham = hinhAnhSanPhamService.updateHinhAnhSanPham(id, hinhAnhSanPhamDto);
        return ResponseEntity.ok(updatedHinhAnhSanPham);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHinhAnhSanPham(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/hinhanhsanpham/{}", id);
        hinhAnhSanPhamService.deleteHinhAnhSanPham(id);
        return ResponseEntity.noContent().build();
    }
}