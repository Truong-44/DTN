package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.DanhMucDto;
import com.example.be.TempoTide.service.DanhMucService;
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
public class DanhMucController {

    private static final Logger logger = LoggerFactory.getLogger(DanhMucController.class);

    private final DanhMucService danhMucService;

    @Autowired
    public DanhMucController(DanhMucService danhMucService) {
        this.danhMucService = danhMucService;
    }

    @PostMapping
    public ResponseEntity<DanhMucDto> createDanhMuc(@Valid @RequestBody DanhMucDto danhMucDto) {
        logger.info("API call: POST /api/danhmuc");
        DanhMucDto createdDanhMuc = danhMucService.createDanhMuc(danhMucDto);
        return new ResponseEntity<>(createdDanhMuc, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DanhMucDto> getDanhMucById(@PathVariable Integer id) {
        logger.info("API call: GET /api/danhmuc/{}", id);
        DanhMucDto danhMucDto = danhMucService.getDanhMucById(id);
        return ResponseEntity.ok(danhMucDto);
    }

    @GetMapping
    public ResponseEntity<List<DanhMucDto>> getAllDanhMuc() {
        logger.info("API call: GET /api/danhmuc");
        List<DanhMucDto> danhMucList = danhMucService.getAllDanhMuc();
        return ResponseEntity.ok(danhMucList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DanhMucDto> updateDanhMuc(@PathVariable Integer id, @Valid @RequestBody DanhMucDto danhMucDto) {
        logger.info("API call: PUT /api/danhmuc/{}", id);
        DanhMucDto updatedDanhMuc = danhMucService.updateDanhMuc(id, danhMucDto);
        return ResponseEntity.ok(updatedDanhMuc);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDanhMuc(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/danhmuc/{}", id);
        danhMucService.deleteDanhMuc(id);
        return ResponseEntity.noContent().build();
    }
}
