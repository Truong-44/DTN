package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.diachidto;
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
public class diachicontroller {

    private static final Logger logger = LoggerFactory.getLogger(diachicontroller.class);

    private final com.example.be.tempotide.service.diachiservice diaChiService;

    @Autowired
    public diachicontroller(com.example.be.tempotide.service.diachiservice diaChiService) {
        this.diaChiService = diaChiService;
    }

    @PostMapping
    public ResponseEntity<diachidto> createDiaChi(@Valid @RequestBody diachidto diaChiDto) {
        logger.info("API call: POST /api/diachi");
        diachidto createdDiaChi = diaChiService.createDiaChi(diaChiDto);
        return new ResponseEntity<>(createdDiaChi, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<diachidto> getDiaChiById(@PathVariable Integer id) {
        logger.info("API call: GET /api/diachi/{}", id);
        diachidto diaChiDto = diaChiService.getDiaChiById(id);
        return ResponseEntity.ok(diaChiDto);
    }

    @GetMapping
    public ResponseEntity<List<diachidto>> getAllDiaChi() {
        logger.info("API call: GET /api/diachi");
        List<diachidto> diaChiList = diaChiService.getAllDiaChi();
        return ResponseEntity.ok(diaChiList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<diachidto> updateDiaChi(@PathVariable Integer id, @Valid @RequestBody diachidto diaChiDto) {
        logger.info("API call: PUT /api/diachi/{}", id);
        diachidto updatedDiaChi = diaChiService.updateDiaChi(id, diaChiDto);
        return ResponseEntity.ok(updatedDiaChi);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiaChi(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/diachi/{}", id);
        diaChiService.deleteDiaChi(id);
        return ResponseEntity.noContent().build();
    }
}