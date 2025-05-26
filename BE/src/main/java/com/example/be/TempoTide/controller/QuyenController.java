package com.example.be.TempoTide.controller;


import com.example.be.TempoTide.dto.QuyenDto;
import com.example.be.TempoTide.service.QuyenService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quyen")
public class QuyenController {

    private static final Logger logger = LoggerFactory.getLogger(QuyenController.class);

    private final QuyenService quyenService;

    @Autowired
    public QuyenController(QuyenService quyenService) {
        this.quyenService = quyenService;
    }

    @PostMapping
    public ResponseEntity<QuyenDto> createQuyen(@Valid @RequestBody QuyenDto quyenDto) {
        logger.info("API call: POST /api/quyen");
        QuyenDto createdQuyen = quyenService.createQuyen(quyenDto);
        return new ResponseEntity<>(createdQuyen, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuyenDto> getQuyenById(@PathVariable Integer id) {
        logger.info("API call: GET /api/quyen/{}", id);
        QuyenDto quyenDto = quyenService.getQuyenById(id);
        return ResponseEntity.ok(quyenDto);
    }

    @GetMapping
    public ResponseEntity<List<QuyenDto>> getAllQuyen() {
        logger.info("API call: GET /api/quyen");
        List<QuyenDto> quyenList = quyenService.getAllQuyen();
        return ResponseEntity.ok(quyenList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuyenDto> updateQuyen(@PathVariable Integer id, @Valid @RequestBody QuyenDto quyenDto) {
        logger.info("API call: PUT /api/quyen/{}", id);
        QuyenDto updatedQuyen = quyenService.updateQuyen(id, quyenDto);
        return ResponseEntity.ok(updatedQuyen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuyen(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/quyen/{}", id);
        quyenService.deleteQuyen(id);
        return ResponseEntity.noContent().build();
    }
}