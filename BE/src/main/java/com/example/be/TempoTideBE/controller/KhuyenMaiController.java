package com.example.be.TempoTideBE.controller;


import com.example.be.TempoTideBE.dto.KhuyenMaiDTO;
import com.example.be.TempoTideBE.service.KhuyenMaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khuyenmai")
@RequiredArgsConstructor
public class KhuyenMaiController {

    private final KhuyenMaiService khuyenMaiService;

    @GetMapping
    public ResponseEntity<List<KhuyenMaiDTO>> getAllKhuyenMai() {
        return ResponseEntity.ok(khuyenMaiService.getAllKhuyenMai());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhuyenMaiDTO> getKhuyenMaiById(@PathVariable Integer id) {
        return ResponseEntity.ok(khuyenMaiService.getKhuyenMaiById(id));
    }

    @PostMapping
    public ResponseEntity<KhuyenMaiDTO> createKhuyenMai(@RequestBody KhuyenMaiDTO dto) {
        return ResponseEntity.ok(khuyenMaiService.createKhuyenMai(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KhuyenMaiDTO> updateKhuyenMai(@PathVariable Integer id, @RequestBody KhuyenMaiDTO dto) {
        return ResponseEntity.ok(khuyenMaiService.updateKhuyenMai(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKhuyenMai(@PathVariable Integer id) {
        khuyenMaiService.deleteKhuyenMai(id);
        return ResponseEntity.noContent().build();
    }
}