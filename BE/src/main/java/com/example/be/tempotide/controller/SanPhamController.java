package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.SanPhamDTO;
import com.example.be.tempotide.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sanpham")
@RequiredArgsConstructor
public class SanPhamController {
    private final SanPhamService sanPhamService;

    @GetMapping
    public ResponseEntity<List<SanPhamDTO>> getAllSanPhams() {
        return ResponseEntity.ok(sanPhamService.getAllSanPhams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPhamDTO> getSanPhamById(@PathVariable Integer id) {
        return ResponseEntity.ok(sanPhamService.getSanPhamById(id));
    }

    @PostMapping
    public ResponseEntity<SanPhamDTO> createSanPham(@RequestBody SanPhamDTO sanPhamDTO) {
        return ResponseEntity.ok(sanPhamService.createSanPham(sanPhamDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SanPhamDTO> updateSanPham(@PathVariable Integer id, @RequestBody SanPhamDTO sanPhamDTO) {
        return ResponseEntity.ok(sanPhamService.updateSanPham(id, sanPhamDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSanPham(@PathVariable Integer id) {
        sanPhamService.deleteSanPham(id);
        return ResponseEntity.noContent().build();
    }
}