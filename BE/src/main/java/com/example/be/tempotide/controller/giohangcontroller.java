package com.example.be.tempotide.controller;


import com.example.be.tempotide.dto.giohangdto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/giohang")
public class giohangcontroller {

    private static final Logger logger = LoggerFactory.getLogger(giohangcontroller.class);

    private final com.example.be.tempotide.service.giohangservice gioHangService;

    @Autowired
    public giohangcontroller(com.example.be.tempotide.service.giohangservice gioHangService) {
        this.gioHangService = gioHangService;
    }

    @PostMapping
    public ResponseEntity<giohangdto> createGioHang(@Valid @RequestBody giohangdto gioHangDto) {
        logger.info("API call: POST /api/giohang");
        giohangdto createdGioHang = gioHangService.createGioHang(gioHangDto);
        return new ResponseEntity<>(createdGioHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<giohangdto> getGioHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/giohang/{}", id);
        giohangdto gioHangDto = gioHangService.getGioHangById(id);
        return ResponseEntity.ok(gioHangDto);
    }

    @GetMapping
    public ResponseEntity<List<giohangdto>> getAllGioHang() {
        logger.info("API call: GET /api/giohang");
        List<giohangdto> gioHangList = gioHangService.getAllGioHang();
        return ResponseEntity.ok(gioHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<giohangdto> updateGioHang(@PathVariable Integer id, @Valid @RequestBody giohangdto gioHangDto) {
        logger.info("API call: PUT /api/giohang/{}", id);
        giohangdto updatedGioHang = gioHangService.updateGioHang(id, gioHangDto);
        return ResponseEntity.ok(updatedGioHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGioHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/giohang/{}", id);
        gioHangService.deleteGioHang(id);
        return ResponseEntity.noContent().build();
    }
}