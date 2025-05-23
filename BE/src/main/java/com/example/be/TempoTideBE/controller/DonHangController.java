package com.example.be.TempoTideBE.controller;

import com.example.be.TempoTideBE.dto.DonHangDTO;
import com.example.be.TempoTideBE.service.DonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donhang")
@RequiredArgsConstructor
public class DonHangController {

    private final DonHangService donHangService;

    @GetMapping
    public ResponseEntity<List<DonHangDTO>> getAllDonHang() {
        return ResponseEntity.ok(donHangService.getAllDonHang());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonHangDTO> getDonHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(donHangService.getDonHangById(id));
    }

    @PostMapping
    public ResponseEntity<DonHangDTO> createDonHang(@RequestBody DonHangDTO dto) {
        return ResponseEntity.ok(donHangService.createDonHang(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonHangDTO> updateDonHang(@PathVariable Integer id, @RequestBody DonHangDTO dto) {
        return ResponseEntity.ok(donHangService.updateDonHang(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonHang(@PathVariable Integer id) {
        donHangService.deleteDonHang(id);
        return ResponseEntity.noContent().build();
    }
}