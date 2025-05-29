package com.example.tempotide.controller;

import com.example.tempotide.dto.GioHangDTO;
import com.example.tempotide.service.GioHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/giohang")
@RequiredArgsConstructor
public class GioHangController {
    private final GioHangService gioHangService;

    @GetMapping
    public ResponseEntity<List<GioHangDTO>> getAllGioHangs() {
        return ResponseEntity.ok(gioHangService.getAllGioHangs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GioHangDTO> getGioHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(gioHangService.getGioHangById(id));
    }

    @PostMapping
    public ResponseEntity<GioHangDTO> createGioHang(@RequestBody GioHangDTO gioHangDTO) {
        return ResponseEntity.ok(gioHangService.createGioHang(gioHangDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GioHangDTO> updateGioHang(@PathVariable Integer id, @RequestBody GioHangDTO gioHangDTO) {
        return ResponseEntity.ok(gioHangService.updateGioHang(id, gioHangDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGioHang(@PathVariable Integer id) {
        gioHangService.deleteGioHang(id);
        return ResponseEntity.noContent().build();
    }
}