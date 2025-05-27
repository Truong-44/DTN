package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.khohangdto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khohang")
public class khohangcontroller {

    private static final Logger logger = LoggerFactory.getLogger(khohangcontroller.class);

    private final com.example.be.tempotide.service.khohangservice khoHangService;

    @Autowired
    public khohangcontroller(com.example.be.tempotide.service.khohangservice khoHangService) {
        this.khoHangService = khoHangService;
    }

    @PostMapping
    public ResponseEntity<khohangdto> createKhoHang(@Valid @RequestBody khohangdto khoHangDto) {
        logger.info("API call: POST /api/kohang");
        khohangdto createdKhoHang = khoHangService.createKhoHang(khoHangDto);
        return new ResponseEntity<>(createdKhoHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<khohangdto> getKhoHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/kohang/{}", id);
        khohangdto khoHangDto = khoHangService.getKhoHangById(id);
        return ResponseEntity.ok(khoHangDto);
    }

    @GetMapping
    public ResponseEntity<List<khohangdto>> getAllKhoHang() {
        logger.info("API call: GET /api/kohang");
        List<khohangdto> khoHangList = khoHangService.getAllKhoHang();
        return ResponseEntity.ok(khoHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<khohangdto> updateKhoHang(@PathVariable Integer id, @Valid @RequestBody khohangdto khoHangDto) {
        logger.info("API call: PUT /api/kohang/{}", id);
        khohangdto updatedKhoHang = khoHangService.updateKhoHang(id, khoHangDto);
        return ResponseEntity.ok(updatedKhoHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKhoHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/kohang/{}", id);
        khoHangService.deleteKhoHang(id);
        return ResponseEntity.noContent().build();
    }
}