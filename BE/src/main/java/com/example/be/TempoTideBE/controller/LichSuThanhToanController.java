package com.example.be.TempoTideBE.controller;

import com.example.be.dto.LichSuThanhToanDTO;
import com.example.be.service.LichSuThanhToanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lichsuthanhtoan")
@RequiredArgsConstructor
public class LichSuThanhToanController {

    private final LichSuThanhToanService lichSuThanhToanService;

    @GetMapping
    public ResponseEntity<List<LichSuThanhToanDTO>> getAllLichSuThanhToan() {
        return ResponseEntity.ok(lichSuThanhToanService.getAllLichSuThanhToan());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichSuThanhToanDTO> getLichSuThanhToanById(@PathVariable Integer id) {
        return ResponseEntity.ok(lichSuThanhToanService.getLichSuThanhToanById(id));
    }

    @PostMapping
    public ResponseEntity<LichSuThanhToanDTO> createLichSuThanhToan(@RequestBody LichSuThanhToanDTO dto) {
        return ResponseEntity.ok(lichSuThanhToanService.createLichSuThanhToan(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LichSuThanhToanDTO> updateLichSuThanhToan(@PathVariable Integer id, @RequestBody LichSuThanhToanDTO dto) {
        return ResponseEntity.ok(lichSuThanhToanService.updateLichSuThanhToan(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLichSuThanhToan(@PathVariable Integer id) {
        lichSuThanhToanService.deleteLichSuThanhToan(id);
        return ResponseEntity.noContent().build();
    }
}