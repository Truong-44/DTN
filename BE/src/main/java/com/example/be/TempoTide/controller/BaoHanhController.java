package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.BaoHanhDto;
import com.example.be.TempoTide.service.BaoHanhService;
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
public class BaoHanhController {

    private static final Logger logger = LoggerFactory.getLogger(BaoHanhController.class);

    private final BaoHanhService baoHanhService;

    @Autowired
    public BaoHanhController(BaoHanhService baoHanhService) {
        this.baoHanhService = baoHanhService;
    }

    @PostMapping
    public ResponseEntity<BaoHanhDto> createBaoHanh(@Valid @RequestBody BaoHanhDto baoHanhDto) {
        logger.info("API call: POST /api/baohanh");
        BaoHanhDto createdBaoHanh = baoHanhService.createBaoHanh(baoHanhDto);
        return new ResponseEntity<>(createdBaoHanh, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaoHanhDto> getBaoHanhById(@PathVariable Integer id) {
        logger.info("API call: GET /api/baohanh/{}", id);
        BaoHanhDto baoHanhDto = baoHanhService.getBaoHanhById(id);
        return ResponseEntity.ok(baoHanhDto);
    }

    @GetMapping
    public ResponseEntity<List<BaoHanhDto>> getAllBaoHanh() {
        logger.info("API call: GET /api/baohanh");
        List<BaoHanhDto> baoHanhList = baoHanhService.getAllBaoHanh();
        return ResponseEntity.ok(baoHanhList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaoHanhDto> updateBaoHanh(@PathVariable Integer id, @Valid @RequestBody BaoHanhDto baoHanhDto) {
        logger.info("API call: PUT /api/baohanh/{}", id);
        BaoHanhDto updatedBaoHanh = baoHanhService.updateBaoHanh(id, baoHanhDto);
        return ResponseEntity.ok(updatedBaoHanh);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaoHanh(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/baohanh/{}", id);
        baoHanhService.deleteBaoHanh(id);
        return ResponseEntity.noContent().build();
    }
}