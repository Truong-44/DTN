package com.example.be.TempoTideBE.controller;


import com.example.be.TempoTideBE.dto.CapBacKhachHangDTO;
import com.example.be.TempoTideBE.service.CapBacKhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capbackhachhang")
@RequiredArgsConstructor
public class CapBacKhachHangController {

    private final CapBacKhachHangService capBacKhachHangService;

    @GetMapping
    public ResponseEntity<List<CapBacKhachHangDTO>> getAllCapBacKhachHang() {
        return ResponseEntity.ok(capBacKhachHangService.getAllCapBacKhachHang());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapBacKhachHangDTO> getCapBacKhachHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(capBacKhachHangService.getCapBacKhachHangById(id));
    }

    @PostMapping
    public ResponseEntity<CapBacKhachHangDTO> createCapBacKhachHang(@RequestBody CapBacKhachHangDTO dto) {
        return ResponseEntity.ok(capBacKhachHangService.createCapBacKhachHang(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CapBacKhachHangDTO> updateCapBacKhachHang(@PathVariable Integer id, @RequestBody CapBacKhachHangDTO dto) {
        return ResponseEntity.ok(capBacKhachHangService.updateCapBacKhachHang(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCapBacKhachHang(@PathVariable Integer id) {
        capBacKhachHangService.deleteCapBacKhachHang(id);
        return ResponseEntity.noContent().build();
    }
}