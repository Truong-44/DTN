package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.ChiTietDonHangDTO;
import com.example.be.tempotide.service.ChiTietDonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietdonhang")
@RequiredArgsConstructor
public class ChiTietDonHangController {
    private final ChiTietDonHangService chiTietDonHangService;

    @GetMapping
    public ResponseEntity<List<ChiTietDonHangDTO>> getAllChiTietDonHangs() {
        return ResponseEntity.ok(chiTietDonHangService.getAllChiTietDonHangs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietDonHangDTO> getChiTietDonHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(chiTietDonHangService.getChiTietDonHangById(id));
    }

    @PostMapping
    public ResponseEntity<ChiTietDonHangDTO> createChiTietDonHang(@RequestBody ChiTietDonHangDTO chiTietDonHangDTO) {
        return ResponseEntity.ok(chiTietDonHangService.createChiTietDonHang(chiTietDonHangDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChiTietDonHangDTO> updateChiTietDonHang(@PathVariable Integer id, @RequestBody ChiTietDonHangDTO chiTietDonHangDTO) {
        return ResponseEntity.ok(chiTietDonHangService.updateChiTietDonHang(id, chiTietDonHangDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChiTietDonHang(@PathVariable Integer id) {
        chiTietDonHangService.deleteChiTietDonHang(id);
        return ResponseEntity.noContent().build();
    }
}