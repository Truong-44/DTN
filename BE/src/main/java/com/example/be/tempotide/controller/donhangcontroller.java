package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.donhangdto;
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
@RequestMapping("/api/donhang")
public class donhangcontroller {

    private static final Logger logger = LoggerFactory.getLogger(donhangcontroller.class);

    private final com.example.be.tempotide.service.donhangservice donHangService;

    @Autowired
    public donhangcontroller(com.example.be.tempotide.service.donhangservice donHangService) {
        this.donHangService = donHangService;
    }

    @PostMapping
    public ResponseEntity<donhangdto> createDonHang(@Valid @RequestBody donhangdto donHangDto) {
        logger.info("API call: POST /api/donhang");
        donhangdto createdDonHang = donHangService.createDonHang(donHangDto);
        return new ResponseEntity<>(createdDonHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<donhangdto> getDonHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/donhang/{}", id);
        donhangdto donHangDto = donHangService.getDonHangById(id);
        return ResponseEntity.ok(donHangDto);
    }

    @GetMapping
    public ResponseEntity<List<donhangdto>> getAllDonHang() {
        logger.info("API call: GET /api/donhang");
        List<donhangdto> donHangList = donHangService.getAllDonHang();
        return ResponseEntity.ok(donHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<donhangdto> updateDonHang(@PathVariable Integer id, @Valid @RequestBody donhangdto donHangDto) {
        logger.info("API call: PUT /api/donhang/{}", id);
        donhangdto updatedDonHang = donHangService.updateDonHang(id, donHangDto);
        return ResponseEntity.ok(updatedDonHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/donhang/{}", id);
        donHangService.deleteDonHang(id);
        return ResponseEntity.noContent().build();
    }
}