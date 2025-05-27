package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.danhmucdto;
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
@RequestMapping("/api/danhmuc")
public class danhmuccontroller {

    private static final Logger logger = LoggerFactory.getLogger(danhmuccontroller.class);

    private final com.example.be.tempotide.service.danhmucservice danhMucService;

    @Autowired
    public danhmuccontroller(com.example.be.tempotide.service.danhmucservice danhMucService) {
        this.danhMucService = danhMucService;
    }

    @PostMapping
    public ResponseEntity<danhmucdto> createDanhMuc(@Valid @RequestBody danhmucdto danhMucDto) {
        logger.info("API call: POST /api/danhmuc");
        danhmucdto createdDanhMuc = danhMucService.createDanhMuc(danhMucDto);
        return new ResponseEntity<>(createdDanhMuc, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<danhmucdto> getDanhMucById(@PathVariable Integer id) {
        logger.info("API call: GET /api/danhmuc/{}", id);
        danhmucdto danhMucDto = danhMucService.getDanhMucById(id);
        return ResponseEntity.ok(danhMucDto);
    }

    @GetMapping
    public ResponseEntity<List<danhmucdto>> getAllDanhMuc() {
        logger.info("API call: GET /api/danhmuc");
        List<danhmucdto> danhMucList = danhMucService.getAllDanhMuc();
        return ResponseEntity.ok(danhMucList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<danhmucdto> updateDanhMuc(@PathVariable Integer id, @Valid @RequestBody danhmucdto danhMucDto) {
        logger.info("API call: PUT /api/danhmuc/{}", id);
        danhmucdto updatedDanhMuc = danhMucService.updateDanhMuc(id, danhMucDto);
        return ResponseEntity.ok(updatedDanhMuc);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDanhMuc(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/danhmuc/{}", id);
        danhMucService.deleteDanhMuc(id);
        return ResponseEntity.noContent().build();
    }
}
