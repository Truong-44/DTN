package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.CapBacKhachHangDto;
import com.example.be.TempoTide.service.CapBacKhachHangService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capbackhachhang")
public class CapBacKhachHangController {

    private static final Logger logger = LoggerFactory.getLogger(CapBacKhachHangController.class);

    private final CapBacKhachHangService capBacKhachHangService;

    @Autowired
    public CapBacKhachHangController(CapBacKhachHangService capBacKhachHangService) {
        this.capBacKhachHangService = capBacKhachHangService;
    }

    @PostMapping
    public ResponseEntity<CapBacKhachHangDto> createCapBacKhachHang(@Valid @RequestBody CapBacKhachHangDto capBacKhachHangDto) {
        logger.info("API call: POST /api/capbackhachhang");
        CapBacKhachHangDto createdCapBacKhachHang = capBacKhachHangService.createCapBacKhachHang(capBacKhachHangDto);
        return new ResponseEntity<>(createdCapBacKhachHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapBacKhachHangDto> getCapBacKhachHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/capbackhachhang/{}", id);
        CapBacKhachHangDto capBacKhachHangDto = capBacKhachHangService.getCapBacKhachHangById(id);
        return ResponseEntity.ok(capBacKhachHangDto);
    }

    @GetMapping
    public ResponseEntity<List<CapBacKhachHangDto>> getAllCapBacKhachHang() {
        logger.info("API call: GET /api/capbackhachhang");
        List<CapBacKhachHangDto> capBacKhachHangList = capBacKhachHangService.getAllCapBacKhachHang();
        return ResponseEntity.ok(capBacKhachHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CapBacKhachHangDto> updateCapBacKhachHang(@PathVariable Integer id, @Valid @RequestBody CapBacKhachHangDto capBacKhachHangDto) {
        logger.info("API call: PUT /api/capbackhachhang/{}", id);
        CapBacKhachHangDto updatedCapBacKhachHang = capBacKhachHangService.updateCapBacKhachHang(id, capBacKhachHangDto);
        return ResponseEntity.ok(updatedCapBacKhachHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCapBacKhachHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/capbackhachhang/{}", id);
        capBacKhachHangService.deleteCapBacKhachHang(id);
        return ResponseEntity.noContent().build();
    }
}