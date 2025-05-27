package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.quyendto;
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
public class quyencontroller {

    private static final Logger logger = LoggerFactory.getLogger(quyencontroller.class);

    private final com.example.be.tempotide.service.quyenservice quyenService;

    @Autowired
    public quyencontroller(com.example.be.tempotide.service.quyenservice quyenService) {
        this.quyenService = quyenService;
    }

    @PostMapping
    public ResponseEntity<quyendto> createQuyen(@Valid @RequestBody quyendto quyenDto) {
        logger.info("API call: POST /api/quyen");
        quyendto createdQuyen = quyenService.createQuyen(quyenDto);
        return new ResponseEntity<>(createdQuyen, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<quyendto> getQuyenById(@PathVariable Integer id) {
        logger.info("API call: GET /api/quyen/{}", id);
        quyendto quyenDto = quyenService.getQuyenById(id);
        return ResponseEntity.ok(quyenDto);
    }

    @GetMapping
    public ResponseEntity<List<quyendto>> getAllQuyen() {
        logger.info("API call: GET /api/quyen");
        List<quyendto> quyenList = quyenService.getAllQuyen();
        return ResponseEntity.ok(quyenList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<quyendto> updateQuyen(@PathVariable Integer id, @Valid @RequestBody quyendto quyenDto) {
        logger.info("API call: PUT /api/quyen/{}", id);
        quyendto updatedQuyen = quyenService.updateQuyen(id, quyenDto);
        return ResponseEntity.ok(updatedQuyen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuyen(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/quyen/{}", id);
        quyenService.deleteQuyen(id);
        return ResponseEntity.noContent().build();
    }
}