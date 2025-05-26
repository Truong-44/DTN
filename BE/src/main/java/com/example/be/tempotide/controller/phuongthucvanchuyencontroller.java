package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.phuongthucvanchuyendto;
import com.example.be.tempotide.service.PhuongThucVanChuyenService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phuongthucvanchuyen")
public class phuongthucvanchuyencontroller {

    private static final Logger logger = LoggerFactory.getLogger(phuongthucvanchuyencontroller.class);

    private final PhuongThucVanChuyenService phuongThucVanChuyenService;

    @Autowired
    public phuongthucvanchuyencontroller(PhuongThucVanChuyenService phuongThucVanChuyenService) {
        this.phuongThucVanChuyenService = phuongThucVanChuyenService;
    }

    @PostMapping
    public ResponseEntity<phuongthucvanchuyendto> createPhuongThucVanChuyen(@Valid @RequestBody phuongthucvanchuyendto phuongThucVanChuyenDto) {
        logger.info("API call: POST /api/phuongthucvanchuyen");
        phuongthucvanchuyendto createdPhuongThucVanChuyen = phuongThucVanChuyenService.createPhuongThucVanChuyen(phuongThucVanChuyenDto);
        return new ResponseEntity<>(createdPhuongThucVanChuyen, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<phuongthucvanchuyendto> getPhuongThucVanChuyenById(@PathVariable Integer id) {
        logger.info("API call: GET /api/phuongthucvanchuyen/{}", id);
        phuongthucvanchuyendto phuongThucVanChuyenDto = phuongThucVanChuyenService.getPhuongThucVanChuyenById(id);
        return ResponseEntity.ok(phuongThucVanChuyenDto);
    }

    @GetMapping
    public ResponseEntity<List<phuongthucvanchuyendto>> getAllPhuongThucVanChuyen() {
        logger.info("API call: GET /api/phuongthucvanchuyen");
        List<phuongthucvanchuyendto> phuongThucVanChuyenList = phuongThucVanChuyenService.getAllPhuongThucVanChuyen();
        return ResponseEntity.ok(phuongThucVanChuyenList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<phuongthucvanchuyendto> updatePhuongThucVanChuyen(@PathVariable Integer id, @Valid @RequestBody phuongthucvanchuyendto phuongThucVanChuyenDto) {
        logger.info("API call: PUT /api/phuongthucvanchuyen/{}", id);
        phuongthucvanchuyendto updatedPhuongThucVanChuyen = phuongThucVanChuyenService.updatePhuongThucVanChuyen(id, phuongThucVanChuyenDto);
        return ResponseEntity.ok(updatedPhuongThucVanChuyen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhuongThucVanChuyen(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/phuongthucvanchuyen/{}", id);
        phuongThucVanChuyenService.deletePhuongThucVanChuyen(id);
        return ResponseEntity.noContent().build();
    }
}