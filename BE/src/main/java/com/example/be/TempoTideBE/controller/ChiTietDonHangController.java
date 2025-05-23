package com.example.be.TempoTideBE.controller;

import com.example.be.TempoTideBE.service.ChiTietDonHangService;
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
    public ResponseEntity<List<ChiTietDonHangDTO>> getAllChiTietDonHang() {
        return ResponseEntity.ok(chiTietDonHangService.getAllChiTietDonHang());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietDonHangDTO> getChiTietDonHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(chiTietDonHangService.getChiTietDonHangById(id));
    }

    @PostMapping
    public ResponseEntity<ChiTietDonHangDTO> createChiTietDonHang(@RequestBody ChiTietDonHangDTO dto) {
        return ResponseEntity.ok(chiTietDonHangService.createChiTietDonHang(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChiTietDonHangDTO> updateChiTietDonHang(@PathVariable Integer id, @RequestBody ChiTietDonHangDTO dto) {
        return ResponseEntity.ok(chiTietDonHangService.updateChiTietDonHang(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChiTietDonHang(@PathVariable Integer id) {
        chiTietDonHangService.deleteChiTietDonHang(id);
        return ResponseEntity.noContent().build();
    }
}