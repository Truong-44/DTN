package com.example.be.TempoTideBE.controller;

import com.example.be.TempoTideBE.dto.DanhMucDTO;
import com.example.be.TempoTideBE.service.DanhMucService;
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
    public ResponseEntity<List<DanhMucDTO>> getAllDanhMuc() {
        return ResponseEntity.ok(danhMucService.getAllDanhMuc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DanhMucDTO> getDanhMucById(@PathVariable Integer id) {
        return ResponseEntity.ok(danhMucService.getDanhMucById(id));
    }

    @PostMapping
    public ResponseEntity<DanhMucDTO> createDanhMuc(@RequestBody DanhMucDTO dto) {
        return ResponseEntity.ok(danhMucService.createDanhMuc(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DanhMucDTO> updateDanhMuc(@PathVariable Integer id, @RequestBody DanhMucDTO dto) {
        return ResponseEntity.ok(danhMucService.updateDanhMuc(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDanhMuc(@PathVariable Integer id) {
        danhMucService.deleteDanhMuc(id);
        return ResponseEntity.noContent().build();
    }
}