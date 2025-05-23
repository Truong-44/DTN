package com.example.be.TempoTideBE.controller;

import com.example.be.dto.PhieuXuatKhoDTO;
import com.example.be.service.PhieuXuatKhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phieuxuatkho")
@RequiredArgsConstructor
public class PhieuXuatKhoController {

    private final PhieuXuatKhoService phieuXuatKhoService;

    @GetMapping
    public ResponseEntity<List<PhieuXuatKhoDTO>> getAllPhieuXuatKho() {
        return ResponseEntity.ok(phieuXuatKhoService.getAllPhieuXuatKho());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhieuXuatKhoDTO> getPhieuXuatKhoById(@PathVariable Integer id) {
        return ResponseEntity.ok(phieuXuatKhoService.getPhieuXuatKhoById(id));
    }

    @PostMapping
    public ResponseEntity<PhieuXuatKhoDTO> createPhieuXuatKho(@RequestBody PhieuXuatKhoDTO dto) {
        return ResponseEntity.ok(phieuXuatKhoService.createPhieuXuatKho(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhieuXuatKhoDTO> updatePhieuXuatKho(@PathVariable Integer id, @RequestBody PhieuXuatKhoDTO dto) {
        return ResponseEntity.ok(phieuXuatKhoService.updatePhieuXuatKho(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhieuXuatKho(@PathVariable Integer id) {
        phieuXuatKhoService.deletePhieuXuatKho(id);
        return ResponseEntity.noContent().build();
    }
}