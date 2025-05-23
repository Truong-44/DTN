package com.example.be.TempoTideBE.controller;

import com.example.be.dto.PhieuNhapKhoDTO;
import com.example.be.service.PhieuNhapKhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phieunhapkho")
@RequiredArgsConstructor
public class PhieuNhapKhoController {

    private final PhieuNhapKhoService phieuNhapKhoService;

    @GetMapping
    public ResponseEntity<List<PhieuNhapKhoDTO>> getAllPhieuNhapKho() {
        return ResponseEntity.ok(phieuNhapKhoService.getAllPhieuNhapKho());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhieuNhapKhoDTO> getPhieuNhapKhoById(@PathVariable Integer id) {
        return ResponseEntity.ok(phieuNhapKhoService.getPhieuNhapKhoById(id));
    }

    @PostMapping
    public ResponseEntity<PhieuNhapKhoDTO> createPhieuNhapKho(@RequestBody PhieuNhapKhoDTO dto) {
        return ResponseEntity.ok(phieuNhapKhoService.createPhieuNhapKho(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhieuNhapKhoDTO> updatePhieuNhapKho(@PathVariable Integer id, @RequestBody PhieuNhapKhoDTO dto) {
        return ResponseEntity.ok(phieuNhapKhoService.updatePhieuNhapKho(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhieuNhapKho(@PathVariable Integer id) {
        phieuNhapKhoService.deletePhieuNhapKho(id);
        return ResponseEntity.noContent().build();
    }
}