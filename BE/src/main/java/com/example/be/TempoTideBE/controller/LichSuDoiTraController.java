package com.example.be.TempoTideBE.controller;

import com.example.be.dto.LichSuDoiTraDTO;
import com.example.be.service.LichSuDoiTraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lichsudoitra")
@RequiredArgsConstructor
public class LichSuDoiTraController {

    private final LichSuDoiTraService lichSuDoiTraService;

    @GetMapping
    public ResponseEntity<List<LichSuDoiTraDTO>> getAllLichSuDoiTra() {
        return ResponseEntity.ok(lichSuDoiTraService.getAllLichSuDoiTra());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichSuDoiTraDTO> getLichSuDoiTraById(@PathVariable Integer id) {
        return ResponseEntity.ok(lichSuDoiTraService.getLichSuDoiTraById(id));
    }

    @PostMapping
    public ResponseEntity<LichSuDoiTraDTO> createLichSuDoiTra(@RequestBody LichSuDoiTraDTO dto) {
        return ResponseEntity.ok(lichSuDoiTraService.createLichSuDoiTra(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LichSuDoiTraDTO> updateLichSuDoiTra(@PathVariable Integer id, @RequestBody LichSuDoiTraDTO dto) {
        return ResponseEntity.ok(lichSuDoiTraService.updateLichSuDoiTra(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLichSuDoiTra(@PathVariable Integer id) {
        lichSuDoiTraService.deleteLichSuDoiTra(id);
        return ResponseEntity.noContent().build();
    }
}