package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.capbackhachhangdto;
import com.example.be.tempotide.service.CapBacKhachHangService;
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
public class capbackhachhangcontroller {

    private static final Logger logger = LoggerFactory.getLogger(capbackhachhangcontroller.class);

    private final CapBacKhachHangService capBacKhachHangService;

    @Autowired
    public capbackhachhangcontroller(CapBacKhachHangService capBacKhachHangService) {
        this.capBacKhachHangService = capBacKhachHangService;
    }

    @PostMapping
    public ResponseEntity<capbackhachhangdto> createCapBacKhachHang(@Valid @RequestBody capbackhachhangdto capBacKhachHangDto) {
        logger.info("API call: POST /api/capbackhachhang");
        capbackhachhangdto createdCapBacKhachHang = capBacKhachHangService.createCapBacKhachHang(capBacKhachHangDto);
        return new ResponseEntity<>(createdCapBacKhachHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<capbackhachhangdto> getCapBacKhachHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/capbackhachhang/{}", id);
        capbackhachhangdto capBacKhachHangDto = capBacKhachHangService.getCapBacKhachHangById(id);
        return ResponseEntity.ok(capBacKhachHangDto);
    }

    @GetMapping
    public ResponseEntity<List<capbackhachhangdto>> getAllCapBacKhachHang() {
        logger.info("API call: GET /api/capbackhachhang");
        List<capbackhachhangdto> capBacKhachHangList = capBacKhachHangService.getAllCapBacKhachHang();
        return ResponseEntity.ok(capBacKhachHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<capbackhachhangdto> updateCapBacKhachHang(@PathVariable Integer id, @Valid @RequestBody capbackhachhangdto capBacKhachHangDto) {
        logger.info("API call: PUT /api/capbackhachhang/{}", id);
        capbackhachhangdto updatedCapBacKhachHang = capBacKhachHangService.updateCapBacKhachHang(id, capBacKhachHangDto);
        return ResponseEntity.ok(updatedCapBacKhachHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCapBacKhachHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/capbackhachhang/{}", id);
        capBacKhachHangService.deleteCapBacKhachHang(id);
        return ResponseEntity.noContent().build();
    }
}