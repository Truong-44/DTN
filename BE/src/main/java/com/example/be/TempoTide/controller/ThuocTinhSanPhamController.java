package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.ThuocTinhSanPhamDto;
import com.example.be.TempoTide.service.ThuocTinhSanPhamService;
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
public class ThuocTinhSanPhamController {

    private static final Logger logger = LoggerFactory.getLogger(ThuocTinhSanPhamController.class);

    private final ThuocTinhSanPhamService thuocTinhSanPhamService;

    @Autowired
    public ThuocTinhSanPhamController(ThuocTinhSanPhamService thuocTinhSanPhamService) {
        this.thuocTinhSanPhamService = thuocTinhSanPhamService;
    }

    @PostMapping
    public ResponseEntity<ThuocTinhSanPhamDto> createThuocTinhSanPham(@Valid @RequestBody ThuocTinhSanPhamDto thuocTinhSanPhamDto) {
        logger.info("API call: POST /api/thuoctinhsanpham");
        ThuocTinhSanPhamDto createdThuocTinhSanPham = thuocTinhSanPhamService.createThuocTinhSanPham(thuocTinhSanPhamDto);
        return new ResponseEntity<>(createdThuocTinhSanPham, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThuocTinhSanPhamDto> getThuocTinhSanPhamById(@PathVariable Integer id) {
        logger.info("API call: GET /api/thuoctinhsanpham/{}", id);
        ThuocTinhSanPhamDto thuocTinhSanPhamDto = thuocTinhSanPhamService.getThuocTinhSanPhamById(id);
        return ResponseEntity.ok(thuocTinhSanPhamDto);
    }

    @GetMapping
    public ResponseEntity<List<ThuocTinhSanPhamDto>> getAllThuocTinhSanPham() {
        logger.info("API call: GET /api/thuoctinhsanpham");
        List<ThuocTinhSanPhamDto> thuocTinhSanPhamList = thuocTinhSanPhamService.getAllThuocTinhSanPham();
        return ResponseEntity.ok(thuocTinhSanPhamList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThuocTinhSanPhamDto> updateThuocTinhSanPham(@PathVariable Integer id, @Valid @RequestBody ThuocTinhSanPhamDto thuocTinhSanPhamDto) {
        logger.info("API call: PUT /api/thuoctinhsanpham/{}", id);
        ThuocTinhSanPhamDto updatedThuocTinhSanPham = thuocTinhSanPhamService.updateThuocTinhSanPham(id, thuocTinhSanPhamDto);
        return ResponseEntity.ok(updatedThuocTinhSanPham);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThuocTinhSanPham(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/thuoctinhsanpham/{}", id);
        thuocTinhSanPhamService.deleteThuocTinhSanPham(id);
        return ResponseEntity.noContent().build();
    }
}