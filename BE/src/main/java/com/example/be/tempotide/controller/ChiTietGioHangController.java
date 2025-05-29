package com.example.tempotide.controller;

import com.example.tempotide.dto.ChiTietGioHangDTO;
import com.example.tempotide.service.ChiTietGioHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietgiohang")
@RequiredArgsConstructor
public class ChiTietGioHangController {
    private final ChiTietGioHangService chiTietGioHangService;

    @GetMapping
    public ResponseEntity<List<ChiTietGioHangDTO>> getAllChiTietGioHangs() {
        return ResponseEntity.ok(chiTietGioHangService.getAllChiTietGioHangs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietGioHangDTO> getChiTietGioHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(chiTietGioHangService.getChiTietGioHangById(id));
    }

    @PostMapping
    public ResponseEntity<ChiTietGioHangDTO> createChiTietGioHang(@RequestBody ChiTietGioHangDTO chiTietGioHangDTO) {
        return ResponseEntity.ok(chiTietGioHangService.createChiTietGioHang(chiTietGioHangDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChiTietGioHangDTO> updateChiTietGioHang(@PathVariable Integer id, @RequestBody ChiTietGioHangDTO chiTietGioHangDTO) {
        return ResponseEntity.ok(chiTietGioHangService.updateChiTietGioHang(id, chiTietGioHangDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChiTietGioHang(@PathVariable Integer id) {
        chiTietGioHangService.deleteChiTietGioHang(id);
        return ResponseEntity.noContent().build();
    }
}