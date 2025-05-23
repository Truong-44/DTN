package com.example.be.TempoTideBE.controller;

import com.example.be.dto.ThuongHieuDTO;
import com.example.be.service.ThuongHieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thuonghieu")
@RequiredArgsConstructor
public class ThuongHieuController {

    private final ThuongHieuService thuongHieuService;

    @GetMapping
    public ResponseEntity<List<ThuongHieuDTO>> getAllThuongHieu() {
        return ResponseEntity.ok(thuongHieuService.getAllThuongHieu());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThuongHieuDTO> getThuongHieuById(@PathVariable Integer id) {
        return ResponseEntity.ok(thuongHieuService.getThuongHieuById(id));
    }

    @PostMapping
    public ResponseEntity<ThuongHieuDTO> createThuongHieu(@RequestBody ThuongHieuDTO thuongHieuDTO) {
        return ResponseEntity.ok(thuongHieuService.createThuongHieu(thuongHieuDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThuongHieuDTO> updateThuongHieu(@PathVariable Integer id, @RequestBody ThuongHieuDTO thuongHieuDTO) {
        return ResponseEntity.ok(thuongHieuService.updateThuongHieu(id, thuongHieuDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThuongHieu(@PathVariable Integer id) {
        thuongHieuService.deleteThuongHieu(id);
        return ResponseEntity.noContent().build();
    }
}