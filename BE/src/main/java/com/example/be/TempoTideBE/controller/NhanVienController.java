package com.example.be.TempoTideBE.controller;

import com.example.be.dto.NhanVienDTO;
import com.example.be.service.NhanVienService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nhanvien")
@RequiredArgsConstructor
public class NhanVienController {

    private final NhanVienService nhanVienService;

    @GetMapping
    public ResponseEntity<List<NhanVienDTO>> getAllNhanVien() {
        return ResponseEntity.ok(nhanVienService.getAllNhanVien());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhanVienDTO> getNhanVienById(@PathVariable Integer id) {
        return ResponseEntity.ok(nhanVienService.getNhanVienById(id));
    }

    @PostMapping
    public ResponseEntity<NhanVienDTO> createNhanVien(@RequestBody NhanVienDTO nhanVienDTO) {
        return ResponseEntity.ok(nhanVienService.createNhanVien(nhanVienDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NhanVienDTO> updateNhanVien(@PathVariable Integer id, @RequestBody NhanVienDTO nhanVienDTO) {
        return ResponseEntity.ok(nhanVienService.updateNhanVien(id, nhanVienDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNhanVien(@PathVariable Integer id) {
        nhanVienService.deleteNhanVien(id);
        return ResponseEntity.noContent().build();
    }
}