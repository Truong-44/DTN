package com.example.be.TempoTideBE.controller;


import com.example.be.TempoTideBE.dto.DanhGiaDTO;
import com.example.be.TempoTideBE.service.DanhGiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danhgia")
@RequiredArgsConstructor
public class DanhGiaController {

    private final DanhGiaService danhGiaService;

    @GetMapping
    public ResponseEntity<List<DanhGiaDTO>> getAllDanhGia() {
        return ResponseEntity.ok(danhGiaService.getAllDanhGia());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DanhGiaDTO> getDanhGiaById(@PathVariable Integer id) {
        return ResponseEntity.ok(danhGiaService.getDanhGiaById(id));
    }

    @PostMapping
    public ResponseEntity<DanhGiaDTO> createDanhGia(@RequestBody DanhGiaDTO dto) {
        return ResponseEntity.ok(danhGiaService.createDanhGia(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DanhGiaDTO> updateDanhGia(@PathVariable Integer id, @RequestBody DanhGiaDTO dto) {
        return ResponseEntity.ok(danhGiaService.updateDanhGia(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDanhGia(@PathVariable Integer id) {
        danhGiaService.deleteDanhGia(id);
        return ResponseEntity.noContent().build();
    }
}