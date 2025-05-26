package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.khachhangdto;
import com.example.be.tempotide.service.KhachHangService;
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
public class khachhangcontroller {

    private static final Logger logger = LoggerFactory.getLogger(khachhangcontroller.class);

    private final KhachHangService khachHangService;

    @Autowired
    public khachhangcontroller(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    @PostMapping
    public ResponseEntity<khachhangdto> createKhachHang(@Valid @RequestBody khachhangdto khachHangDto) {
        logger.info("API call: POST /api/khachhang");
        khachhangdto createdKhachHang = khachHangService.createKhachHang(khachHangDto);
        return new ResponseEntity<>(createdKhachHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<khachhangdto> getKhachHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/khachhang/{}", id);
        khachhangdto khachHangDto = khachHangService.getKhachHangById(id);
        return ResponseEntity.ok(khachHangDto);
    }

    @GetMapping
    public ResponseEntity<List<khachhangdto>> getAllKhachHang() {
        logger.info("API call: GET /api/khachhang");
        List<khachhangdto> khachHangList = khachHangService.getAllKhachHang();
        return ResponseEntity.ok(khachHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<khachhangdto> updateKhachHang(@PathVariable Integer id, @Valid @RequestBody khachhangdto khachHangDto) {
        logger.info("API call: PUT /api/khachhang/{}", id);
        khachhangdto updatedKhachHang = khachHangService.updateKhachHang(id, khachHangDto);
        return ResponseEntity.ok(updatedKhachHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKhachHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/khachhang/{}", id);
        khachHangService.deleteKhachHang(id);
        return ResponseEntity.noContent().build();
    }
}