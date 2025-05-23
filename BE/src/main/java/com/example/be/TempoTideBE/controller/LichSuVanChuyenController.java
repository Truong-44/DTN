package com.example.be.TempoTideBE.controller;

import com.example.be.dto.LichSuVanChuyenDTO;
import com.example.be.service.LichSuVanChuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lichsuvanchuyen")
@RequiredArgsConstructor
public class LichSuVanChuyenController {

    private final LichSuVanChuyenService lichSuVanChuyenService;

    @GetMapping
    public ResponseEntity<List<LichSuVanChuyenDTO>> getAllLichSuVanChuyen() {
        return ResponseEntity.ok(lichSuVanChuyenService.getAllLichSuVanChuyen());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichSuVanChuyenDTO> getLichSuVanChuyenById(@PathVariable Integer id) {
        return ResponseEntity.ok(lichSuVanChuyenService.getLichSuVanChuyenById(id));
    }

    @PostMapping
    public ResponseEntity<LichSuVanChuyenDTO> createLichSuVanChuyen(@RequestBody LichSuVanChuyenDTO dto) {
        return ResponseEntity.ok(lichSuVanChuyenService.createLichSuVanChuyen(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LichSuVanChuyenDTO> updateLichSuVanChuyen(@PathVariable Integer id, @RequestBody LichSuVanChuyenDTO dto) {
        return ResponseEntity.ok(lichSuVanChuyenService.updateLichSuVanChuyen(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLichSuVanChuyen(@PathVariable Integer id) {
        lichSuVanChuyenService.deleteLichSuVanChuyen(id);
        return ResponseEntity.noContent().build();
    }
}