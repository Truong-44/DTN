package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.DiaChiDto;
import com.example.be.TempoTide.service.DiaChiService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diachi")
public class DiaChiController {

    private static final Logger logger = LoggerFactory.getLogger(DiaChiController.class);

    private final DiaChiService diaChiService;

    @Autowired
    public DiaChiController(DiaChiService diaChiService) {
        this.diaChiService = diaChiService;
    }

    @PostMapping
    public ResponseEntity<DiaChiDto> createDiaChi(@Valid @RequestBody DiaChiDto diaChiDto) {
        logger.info("API call: POST /api/diachi");
        DiaChiDto createdDiaChi = diaChiService.createDiaChi(diaChiDto);
        return new ResponseEntity<>(createdDiaChi, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaChiDto> getDiaChiById(@PathVariable Integer id) {
        logger.info("API call: GET /api/diachi/{}", id);
        DiaChiDto diaChiDto = diaChiService.getDiaChiById(id);
        return ResponseEntity.ok(diaChiDto);
    }

    @GetMapping
    public ResponseEntity<List<DiaChiDto>> getAllDiaChi() {
        logger.info("API call: GET /api/diachi");
        List<DiaChiDto> diaChiList = diaChiService.getAllDiaChi();
        return ResponseEntity.ok(diaChiList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiaChiDto> updateDiaChi(@PathVariable Integer id, @Valid @RequestBody DiaChiDto diaChiDto) {
        logger.info("API call: PUT /api/diachi/{}", id);
        DiaChiDto updatedDiaChi = diaChiService.updateDiaChi(id, diaChiDto);
        return ResponseEntity.ok(updatedDiaChi);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiaChi(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/diachi/{}", id);
        diaChiService.deleteDiaChi(id);
        return ResponseEntity.noContent().build();
    }
}