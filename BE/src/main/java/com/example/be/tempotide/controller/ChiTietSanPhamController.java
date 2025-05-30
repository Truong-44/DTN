package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.ChiTietSanPhamDTO;
import com.example.be.tempotide.service.ChiTietSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietsanpham")
@RequiredArgsConstructor
public class ChiTietSanPhamController {
    private final ChiTietSanPhamService chiTietSanPhamService;

    @GetMapping
    public ResponseEntity<List<ChiTietSanPhamDTO>> getAllChiTietSanPhams() {
        return ResponseEntity.ok(chiTietSanPhamService.getAllChiTietSanPhams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietSanPhamDTO> getChiTietSanPhamById(@PathVariable Integer id) {
        return ResponseEntity.ok(chiTietSanPhamService.getChiTietSanPhamById(id));
    }

    @PostMapping
    public ResponseEntity<ChiTietSanPhamDTO> createChiTietSanPham(@RequestBody ChiTietSanPhamDTO chiTietSanPhamDTO) {
        return ResponseEntity.ok(chiTietSanPhamService.createChiTietSanPham(chiTietSanPhamDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChiTietSanPhamDTO> updateChiTietSanPham(@PathVariable Integer id, @RequestBody ChiTietSanPhamDTO chiTietSanPhamDTO) {
        return ResponseEntity.ok(chiTietSanPhamService.updateChiTietSanPham(id, chiTietSanPhamDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChiTietSanPham(@PathVariable Integer id) {
        chiTietSanPhamService.deleteChiTietSanPham(id);
        return ResponseEntity.noContent().build();
    }
}