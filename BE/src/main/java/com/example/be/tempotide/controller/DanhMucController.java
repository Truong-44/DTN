package com.example.tempotide.controller;

import com.example.tempotide.dto.DanhMucDTO;
import com.example.tempotide.service.DanhMucService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danhmuc")
@RequiredArgsConstructor
public class DanhMucController {
    private final DanhMucService danhMucService;

    @GetMapping
    public ResponseEntity<List<DanhMucDTO>> getAllDanhMucs() {
        return ResponseEntity.ok(danhMucService.getAllDanhMucs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DanhMucDTO> getDanhMucById(@PathVariable Integer id) {
        return ResponseEntity.ok(danhMucService.getDanhMucById(id));
    }

    @PostMapping
    public ResponseEntity<DanhMucDTO> createDanhMuc(@RequestBody DanhMucDTO danhMucDTO) {
        return ResponseEntity.ok(danhMucService.createDanhMuc(danhMucDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DanhMucDTO> updateDanhMuc(@PathVariable Integer id, @RequestBody DanhMucDTO danhMucDTO) {
        return ResponseEntity.ok(danhMucService.updateDanhMuc(id, danhMucDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDanhMuc(@PathVariable Integer id) {
        danhMucService.deleteDanhMuc(id);
        return ResponseEntity.noContent().build();
    }
}