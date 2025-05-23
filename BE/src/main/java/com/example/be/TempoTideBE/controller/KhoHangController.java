package com.example.be.TempoTideBE.controller;


import com.example.be.TempoTideBE.dto.KhoHangDTO;
import com.example.be.TempoTideBE.service.KhoHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kohang")
@RequiredArgsConstructor
public class KhoHangController {

    private final KhoHangService khoHangService;

    @GetMapping
    public ResponseEntity<List<KhoHangDTO>> getAllKhoHang() {
        return ResponseEntity.ok(khoHangService.getAllKhoHang());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhoHangDTO> getKhoHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(khoHangService.getKhoHangById(id));
    }

    @PostMapping
    public ResponseEntity<KhoHangDTO> createKhoHang(@RequestBody KhoHangDTO dto) {
        return ResponseEntity.ok(khoHangService.createKhoHang(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KhoHangDTO> updateKhoHang(@PathVariable Integer id, @RequestBody KhoHangDTO dto) {
        return ResponseEntity.ok(khoHangService.updateKhoHang(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKhoHang(@PathVariable Integer id) {
        khoHangService.deleteKhoHang(id);
        return ResponseEntity.noContent().build();
    }
}