package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.KhachHangDTO;
import com.example.be.tempotide.service.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khachhang")
@RequiredArgsConstructor
public class KhachHangController {
    private final KhachHangService khachHangService;

    @GetMapping
    public ResponseEntity<List<KhachHangDTO>> getAllKhachHangs() {
        return ResponseEntity.ok(khachHangService.getAllKhachHangs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhachHangDTO> getKhachHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(khachHangService.getKhachHangById(id));
    }

    @PostMapping
    public ResponseEntity<KhachHangDTO> createKhachHang(@RequestBody KhachHangDTO khachHangDTO) {
        return ResponseEntity.ok(khachHangService.createKhachHang(khachHangDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KhachHangDTO> updateKhachHang(@PathVariable Integer id, @RequestBody KhachHangDTO khachHangDTO) {
        return ResponseEntity.ok(khachHangService.updateKhachHang(id, khachHangDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKhachHang(@PathVariable Integer id) {
        khachHangService.deleteKhachHang(id);
        return ResponseEntity.noContent().build();
    }
}