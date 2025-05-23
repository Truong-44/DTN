package com.example.be.TempoTideBE.controller;

import com.example.be.dto.LichSuBaoHanhDTO;
import com.example.be.service.LichSuBaoHanhService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lichsubaohanh")
@RequiredArgsConstructor
public class LichSuBaoHanhController {

    private final LichSuBaoHanhService lichSuBaoHanhService;

    @GetMapping
    public ResponseEntity<List<LichSuBaoHanhDTO>> getAllLichSuBaoHanh() {
        return ResponseEntity.ok(lichSuBaoHanhService.getAllLichSuBaoHanh());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichSuBaoHanhDTO> getLichSuBaoHanhById(@PathVariable Integer id) {
        return ResponseEntity.ok(lichSuBaoHanhService.getLichSuBaoHanhById(id));
    }

    @PostMapping
    public ResponseEntity<LichSuBaoHanhDTO> createLichSuBaoHanh(@RequestBody LichSuBaoHanhDTO dto) {
        return ResponseEntity.ok(lichSuBaoHanhService.createLichSuBaoHanh(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LichSuBaoHanhDTO> updateLichSuBaoHanh(@PathVariable Integer id, @RequestBody LichSuBaoHanhDTO dto) {
        return ResponseEntity.ok(lichSuBaoHanhService.updateLichSuBaoHanh(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLichSuBaoHanh(@PathVariable Integer id) {
        lichSuBaoHanhService.deleteLichSuBaoHanh(id);
        return ResponseEntity.noContent().build();
    }
}