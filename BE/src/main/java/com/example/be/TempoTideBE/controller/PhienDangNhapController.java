package com.example.be.TempoTideBE.controller;

import com.example.be.dto.PhienDangNhapDTO;
import com.example.be.service.PhienDangNhapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phiendangnhap")
@RequiredArgsConstructor
public class PhienDangNhapController {

    private final PhienDangNhapService phienDangNhapService;

    @GetMapping
    public ResponseEntity<List<PhienDangNhapDTO>> getAllPhienDangNhap() {
        return ResponseEntity.ok(phienDangNhapService.getAllPhienDangNhap());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhienDangNhapDTO> getPhienDangNhapById(@PathVariable Integer id) {
        return ResponseEntity.ok(phienDangNhapService.getPhienDangNhapById(id));
    }

    @PostMapping
    public ResponseEntity<PhienDangNhapDTO> createPhienDangNhap(@RequestBody PhienDangNhapDTO dto) {
        return ResponseEntity.ok(phienDangNhapService.createPhienDangNhap(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhienDangNhapDTO> updatePhienDangNhap(@PathVariable Integer id, @RequestBody PhienDangNhapDTO dto) {
        return ResponseEntity.ok(phienDangNhapService.updatePhienDangNhap(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhienDangNhap(@PathVariable Integer id) {
        phienDangNhapService.deletePhienDangNhap(id);
        return ResponseEntity.noContent().build();
    }
}