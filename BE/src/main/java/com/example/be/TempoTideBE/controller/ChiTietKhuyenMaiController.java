package com.example.be.TempoTideBE.controller;


import com.example.be.TempoTideBE.dto.ChiTietKhuyenMaiDTO;
import com.example.be.TempoTideBE.service.ChiTietKhuyenMaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietkhuyenmai")
@RequiredArgsConstructor
public class ChiTietKhuyenMaiController {

    private final ChiTietKhuyenMaiService chiTietKhuyenMaiService;

    @GetMapping
    public ResponseEntity<List<ChiTietKhuyenMaiDTO>> getAllChiTietKhuyenMai() {
        return ResponseEntity.ok(chiTietKhuyenMaiService.getAllChiTietKhuyenMai());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChiTietKhuyenMaiDTO> getChiTietKhuyenMaiById(@PathVariable Integer id) {
        return ResponseEntity.ok(chiTietKhuyenMaiService.getChiTietKhuyenMaiById(id));
    }

    @PostMapping
    public ResponseEntity<ChiTietKhuyenMaiDTO> createChiTietKhuyenMai(@RequestBody ChiTietKhuyenMaiDTO dto) {
        return ResponseEntity.ok(chiTietKhuyenMaiService.createChiTietKhuyenMai(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChiTietKhuyenMaiDTO> updateChiTietKhuyenMai(@PathVariable Integer id, @RequestBody ChiTietKhuyenMaiDTO dto) {
        return ResponseEntity.ok(chiTietKhuyenMaiService.updateChiTietKhuyenMai(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChiTietKhuyenMai(@PathVariable Integer id) {
        chiTietKhuyenMaiService.deleteChiTietKhuyenMai(id);
        return ResponseEntity.noContent().build();
    }
}