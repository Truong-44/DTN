package com.example.tempotide.controller;

import com.example.tempotide.dto.QuyenDTO;
import com.example.tempotide.service.QuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quyen")
@RequiredArgsConstructor
public class QuyenController {
    private final QuyenService quyenService;

    @GetMapping
    public ResponseEntity<List<QuyenDTO>> getAllQuyens() {
        return ResponseEntity.ok(quyenService.getAllQuyens());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuyenDTO> getQuyenById(@PathVariable Integer id) {
        return ResponseEntity.ok(quyenService.getQuyenById(id));
    }

    @PostMapping
    public ResponseEntity<QuyenDTO> createQuyen(@RequestBody QuyenDTO quyenDTO) {
        return ResponseEntity.ok(quyenService.createQuyen(quyenDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuyenDTO> updateQuyen(@PathVariable Integer id, @RequestBody QuyenDTO quyenDTO) {
        return ResponseEntity.ok(quyenService.updateQuyen(id, quyenDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuyen(@PathVariable Integer id) {
        quyenService.deleteQuyen(id);
        return ResponseEntity.noContent().build();
    }
}