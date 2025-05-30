package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.DonHangDTO;
import com.example.be.tempotide.service.DonHangService;
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
    public ResponseEntity<List<DonHangDTO>> getAllDonHangs() {
        return ResponseEntity.ok(donHangService.getAllDonHangs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonHangDTO> getDonHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(donHangService.getDonHangById(id));
    }

    @PostMapping
    public ResponseEntity<DonHangDTO> createDonHang(@RequestBody DonHangDTO donHangDTO) {
        return ResponseEntity.ok(donHangService.createDonHang(donHangDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonHangDTO> updateDonHang(@PathVariable Integer id, @RequestBody DonHangDTO donHangDTO) {
        return ResponseEntity.ok(donHangService.updateDonHang(id, donHangDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonHang(@PathVariable Integer id) {
        donHangService.deleteDonHang(id);
        return ResponseEntity.noContent().build();
    }
}