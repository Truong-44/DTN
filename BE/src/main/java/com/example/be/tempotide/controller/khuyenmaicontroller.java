package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.khuyenmaidto;
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
public class khuyenmaicontroller {

    private static final Logger logger = LoggerFactory.getLogger(khuyenmaicontroller.class);

    private final com.example.be.tempotide.service.khuyenmaiservice khuyenMaiService;

    @Autowired
    public khuyenmaicontroller(com.example.be.tempotide.service.khuyenmaiservice khuyenMaiService) {
        this.khuyenMaiService = khuyenMaiService;
    }

    @PostMapping
    public ResponseEntity<khuyenmaidto> createKhuyenMai(@Valid @RequestBody khuyenmaidto khuyenMaiDto) {
        logger.info("API call: POST /api/khuyenmai");
        khuyenmaidto createdKhuyenMai = khuyenMaiService.createKhuyenMai(khuyenMaiDto);
        return new ResponseEntity<>(createdKhuyenMai, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<khuyenmaidto> getKhuyenMaiById(@PathVariable Integer id) {
        logger.info("API call: GET /api/khuyenmai/{}", id);
        khuyenmaidto khuyenMaiDto = khuyenMaiService.getKhuyenMaiById(id);
        return ResponseEntity.ok(khuyenMaiDto);
    }

    @GetMapping
    public ResponseEntity<List<khuyenmaidto>> getAllKhuyenMai() {
        logger.info("API call: GET /api/khuyenmai");
        List<khuyenmaidto> khuyenMaiList = khuyenMaiService.getAllKhuyenMai();
        return ResponseEntity.ok(khuyenMaiList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<khuyenmaidto> updateKhuyenMai(@PathVariable Integer id, @Valid @RequestBody khuyenmaidto khuyenMaiDto) {
        logger.info("API call: PUT /api/khuyenmai/{}", id);
        khuyenmaidto updatedKhuyenMai = khuyenMaiService.updateKhuyenMai(id, khuyenMaiDto);
        return ResponseEntity.ok(updatedKhuyenMai);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKhuyenMai(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/khuyenmai/{}", id);
        khuyenMaiService.deleteKhuyenMai(id);
        return ResponseEntity.noContent().build();
    }
}