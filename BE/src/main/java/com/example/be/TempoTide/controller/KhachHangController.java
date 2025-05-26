package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.KhachHangDto;
import com.example.be.TempoTide.service.KhachHangService;
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
@RequestMapping("/api/khachhang")
public class KhachHangController {

    private static final Logger logger = LoggerFactory.getLogger(KhachHangController.class);

    private final KhachHangService khachHangService;

    @Autowired
    public KhachHangController(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    @PostMapping
    public ResponseEntity<KhachHangDto> createKhachHang(@Valid @RequestBody KhachHangDto khachHangDto) {
        logger.info("API call: POST /api/khachhang");
        KhachHangDto createdKhachHang = khachHangService.createKhachHang(khachHangDto);
        return new ResponseEntity<>(createdKhachHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhachHangDto> getKhachHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/khachhang/{}", id);
        KhachHangDto khachHangDto = khachHangService.getKhachHangById(id);
        return ResponseEntity.ok(khachHangDto);
    }

    @GetMapping
    public ResponseEntity<List<KhachHangDto>> getAllKhachHang() {
        logger.info("API call: GET /api/khachhang");
        List<KhachHangDto> khachHangList = khachHangService.getAllKhachHang();
        return ResponseEntity.ok(khachHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KhachHangDto> updateKhachHang(@PathVariable Integer id, @Valid @RequestBody KhachHangDto khachHangDto) {
        logger.info("API call: PUT /api/khachhang/{}", id);
        KhachHangDto updatedKhachHang = khachHangService.updateKhachHang(id, khachHangDto);
        return ResponseEntity.ok(updatedKhachHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKhachHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/khachhang/{}", id);
        khachHangService.deleteKhachHang(id);
        return ResponseEntity.noContent().build();
    }
}