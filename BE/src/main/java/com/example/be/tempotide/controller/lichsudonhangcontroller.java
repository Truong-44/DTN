package com.example.be.tempotide.controller;


import com.example.be.tempotide.dto.lichsudonhangdto;
import com.example.be.tempotide.service.LichSuDonHangService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lichsudonhang")
public class lichsudonhangcontroller {

    private static final Logger logger = LoggerFactory.getLogger(lichsudonhangcontroller.class);

    private final LichSuDonHangService lichSuDonHangService;

    @Autowired
    public lichsudonhangcontroller(LichSuDonHangService lichSuDonHangService) {
        this.lichSuDonHangService = lichSuDonHangService;
    }

    @PostMapping
    public ResponseEntity<lichsudonhangdto> createLichSuDonHang(@Valid @RequestBody lichsudonhangdto lichSuDonHangDto) {
        logger.info("API call: POST /api/lichsudonhang");
        lichsudonhangdto createdLichSuDonHang = lichSuDonHangService.createLichSuDonHang(lichSuDonHangDto);
        return new ResponseEntity<>(createdLichSuDonHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<lichsudonhangdto> getLichSuDonHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/lichsudonhang/{}", id);
        lichsudonhangdto lichSuDonHangDto = lichSuDonHangService.getLichSuDonHangById(id);
        return ResponseEntity.ok(lichSuDonHangDto);
    }

    @GetMapping
    public ResponseEntity<List<lichsudonhangdto>> getAllLichSuDonHang() {
        logger.info("API call: GET /api/lichsudonhang");
        List<lichsudonhangdto> lichSuDonHangList = lichSuDonHangService.getAllLichSuDonHang();
        return ResponseEntity.ok(lichSuDonHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<lichsudonhangdto> updateLichSuDonHang(@PathVariable Integer id, @Valid @RequestBody lichsudonhangdto lichSuDonHangDto) {
        logger.info("API call: PUT /api/lichsudonhang/{}", id);
        lichsudonhangdto updatedLichSuDonHang = lichSuDonHangService.updateLichSuDonHang(id, lichSuDonHangDto);
        return ResponseEntity.ok(updatedLichSuDonHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLichSuDonHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/lichsudonhang/{}", id);
        lichSuDonHangService.deleteLichSuDonHang(id);
        return ResponseEntity.noContent().build();
    }
}