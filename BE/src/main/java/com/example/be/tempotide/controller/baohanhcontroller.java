package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.baohanhdto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baohanh")
public class baohanhcontroller {

    private static final Logger logger = LoggerFactory.getLogger(baohanhcontroller.class);

    private final com.example.be.tempotide.service.baohanhservice baoHanhService;

    @Autowired
    public baohanhcontroller(com.example.be.tempotide.service.baohanhservice baoHanhService) {
        this.baoHanhService = baoHanhService;
    }

    @PostMapping
    public ResponseEntity<baohanhdto> createBaoHanh(@Valid @RequestBody baohanhdto baoHanhDto) {
        logger.info("API call: POST /api/baohanh");
        baohanhdto createdBaoHanh = baoHanhService.createBaoHanh(baoHanhDto);
        return new ResponseEntity<>(createdBaoHanh, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<baohanhdto> getBaoHanhById(@PathVariable Integer id) {
        logger.info("API call: GET /api/baohanh/{}", id);
        baohanhdto baoHanhDto = baoHanhService.getBaoHanhById(id);
        return ResponseEntity.ok(baoHanhDto);
    }

    @GetMapping
    public ResponseEntity<List<baohanhdto>> getAllBaoHanh() {
        logger.info("API call: GET /api/baohanh");
        List<baohanhdto> baoHanhList = baoHanhService.getAllBaoHanh();
        return ResponseEntity.ok(baoHanhList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<baohanhdto> updateBaoHanh(@PathVariable Integer id, @Valid @RequestBody baohanhdto baoHanhDto) {
        logger.info("API call: PUT /api/baohanh/{}", id);
        baohanhdto updatedBaoHanh = baoHanhService.updateBaoHanh(id, baoHanhDto);
        return ResponseEntity.ok(updatedBaoHanh);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaoHanh(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/baohanh/{}", id);
        baoHanhService.deleteBaoHanh(id);
        return ResponseEntity.noContent().build();
    }
}