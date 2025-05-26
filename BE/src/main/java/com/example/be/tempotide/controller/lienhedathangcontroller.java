package com.example.be.tempotide.controller;


import com.example.be.tempotide.dto.lienhedathangdto;
import com.example.be.tempotide.service.LienHeDatHangService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lienhedathang")
public class lienhedathangcontroller {

    private static final Logger logger = LoggerFactory.getLogger(lienhedathangcontroller.class);

    private final LienHeDatHangService lienHeDatHangService;

    @Autowired
    public lienhedathangcontroller(LienHeDatHangService lienHeDatHangService) {
        this.lienHeDatHangService = lienHeDatHangService;
    }

    @PostMapping
    public ResponseEntity<lienhedathangdto> createLienHeDatHang(@Valid @RequestBody lienhedathangdto lienHeDatHangDto) {
        logger.info("API call: POST /api/lienhedathang");
        lienhedathangdto createdLienHeDatHang = lienHeDatHangService.createLienHeDatHang(lienHeDatHangDto);
        return new ResponseEntity<>(createdLienHeDatHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<lienhedathangdto> getLienHeDatHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/lienhedathang/{}", id);
        lienhedathangdto lienHeDatHangDto = lienHeDatHangService.getLienHeDatHangById(id);
        return ResponseEntity.ok(lienHeDatHangDto);
    }

    @GetMapping
    public ResponseEntity<List<lienhedathangdto>> getAllLienHeDatHang() {
        logger.info("API call: GET /api/lienhedathang");
        List<lienhedathangdto> lienHeDatHangList = lienHeDatHangService.getAllLienHeDatHang();
        return ResponseEntity.ok(lienHeDatHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<lienhedathangdto> updateLienHeDatHang(@PathVariable Integer id, @Valid @RequestBody lienhedathangdto lienHeDatHangDto) {
        logger.info("API call: PUT /api/lienhedathang/{}", id);
        lienhedathangdto updatedLienHeDatHang = lienHeDatHangService.updateLienHeDatHang(id, lienHeDatHangDto);
        return ResponseEntity.ok(updatedLienHeDatHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLienHeDatHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/lienhedathang/{}", id);
        lienHeDatHangService.deleteLienHeDatHang(id);
        return ResponseEntity.noContent().build();
    }
}