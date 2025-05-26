package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.DonHangDto;
import com.example.be.TempoTide.service.DonHangService;
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
public class DonHangController {

    private static final Logger logger = LoggerFactory.getLogger(DonHangController.class);

    private final DonHangService donHangService;

    @Autowired
    public DonHangController(DonHangService donHangService) {
        this.donHangService = donHangService;
    }

    @PostMapping
    public ResponseEntity<DonHangDto> createDonHang(@Valid @RequestBody DonHangDto donHangDto) {
        logger.info("API call: POST /api/donhang");
        DonHangDto createdDonHang = donHangService.createDonHang(donHangDto);
        return new ResponseEntity<>(createdDonHang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonHangDto> getDonHangById(@PathVariable Integer id) {
        logger.info("API call: GET /api/donhang/{}", id);
        DonHangDto donHangDto = donHangService.getDonHangById(id);
        return ResponseEntity.ok(donHangDto);
    }

    @GetMapping
    public ResponseEntity<List<DonHangDto>> getAllDonHang() {
        logger.info("API call: GET /api/donhang");
        List<DonHangDto> donHangList = donHangService.getAllDonHang();
        return ResponseEntity.ok(donHangList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonHangDto> updateDonHang(@PathVariable Integer id, @Valid @RequestBody DonHangDto donHangDto) {
        logger.info("API call: PUT /api/donhang/{}", id);
        DonHangDto updatedDonHang = donHangService.updateDonHang(id, donHangDto);
        return ResponseEntity.ok(updatedDonHang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonHang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/donhang/{}", id);
        donHangService.deleteDonHang(id);
        return ResponseEntity.noContent().build();
    }
}
