package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.ThuongHieuDto;
import com.example.be.TempoTide.service.ThuongHieuService;
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
@RequestMapping("/api/thuonghieu")
public class ThuongHieuController {

    private static final Logger logger = LoggerFactory.getLogger(ThuongHieuController.class);

    private final ThuongHieuService thuongHieuService;

    @Autowired
    public ThuongHieuController(ThuongHieuService thuongHieuService) {
        this.thuongHieuService = thuongHieuService;
    }

    @PostMapping
    public ResponseEntity<ThuongHieuDto> createThuongHieu(@Valid @RequestBody ThuongHieuDto thuongHieuDto) {
        logger.info("API call: POST /api/thuonghieu");
        ThuongHieuDto createdThuongHieu = thuongHieuService.createThuongHieu(thuongHieuDto);
        return new ResponseEntity<>(createdThuongHieu, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThuongHieuDto> getThuongHieuById(@PathVariable Integer id) {
        logger.info("API call: GET /api/thuonghieu/{}", id);
        ThuongHieuDto thuongHieuDto = thuongHieuService.getThuongHieuById(id);
        return ResponseEntity.ok(thuongHieuDto);
    }

    @GetMapping
    public ResponseEntity<List<ThuongHieuDto>> getAllThuongHieu() {
        logger.info("API call: GET /api/thuonghieu");
        List<ThuongHieuDto> thuongHieuList = thuongHieuService.getAllThuongHieu();
        return ResponseEntity.ok(thuongHieuList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThuongHieuDto> updateThuongHieu(@PathVariable Integer id, @Valid @RequestBody ThuongHieuDto thuongHieuDto) {
        logger.info("API call: PUT /api/thuonghieu/{}", id);
        ThuongHieuDto updatedThuongHieu = thuongHieuService.updateThuongHieu(id, thuongHieuDto);
        return ResponseEntity.ok(updatedThuongHieu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThuongHieu(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/thuonghieu/{}", id);
        thuongHieuService.deleteThuongHieu(id);
        return ResponseEntity.noContent().build();
    }
}