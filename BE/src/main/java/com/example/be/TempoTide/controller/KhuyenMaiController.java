package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.KhuyenMaiDto;
import com.example.be.TempoTide.service.KhuyenMaiService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khuyenmai")
public class KhuyenMaiController {

    private static final Logger logger = LoggerFactory.getLogger(KhuyenMaiController.class);

    private final KhuyenMaiService khuyenMaiService;

    @Autowired
    public KhuyenMaiController(KhuyenMaiService khuyenMaiService) {
        this.khuyenMaiService = khuyenMaiService;
    }

    @PostMapping
    public ResponseEntity<KhuyenMaiDto> createKhuyenMai(@Valid @RequestBody KhuyenMaiDto khuyenMaiDto) {
        logger.info("API call: POST /api/khuyenmai");
        KhuyenMaiDto createdKhuyenMai = khuyenMaiService.createKhuyenMai(khuyenMaiDto);
        return new ResponseEntity<>(createdKhuyenMai, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhuyenMaiDto> getKhuyenMaiById(@PathVariable Integer id) {
        logger.info("API call: GET /api/khuyenmai/{}", id);
        KhuyenMaiDto khuyenMaiDto = khuyenMaiService.getKhuyenMaiById(id);
        return ResponseEntity.ok(khuyenMaiDto);
    }

    @GetMapping
    public ResponseEntity<List<KhuyenMaiDto>> getAllKhuyenMai() {
        logger.info("API call: GET /api/khuyenmai");
        List<KhuyenMaiDto> khuyenMaiList = khuyenMaiService.getAllKhuyenMai();
        return ResponseEntity.ok(khuyenMaiList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KhuyenMaiDto> updateKhuyenMai(@PathVariable Integer id, @Valid @RequestBody KhuyenMaiDto khuyenMaiDto) {
        logger.info("API call: PUT /api/khuyenmai/{}", id);
        KhuyenMaiDto updatedKhuyenMai = khuyenMaiService.updateKhuyenMai(id, khuyenMaiDto);
        return ResponseEntity.ok(updatedKhuyenMai);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKhuyenMai(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/khuyenmai/{}", id);
        khuyenMaiService.deleteKhuyenMai(id);
        return ResponseEntity.noContent().build();
    }
}