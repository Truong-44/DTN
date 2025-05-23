package com.example.be.TempoTideBE.controller;

import com.example.be.dto.PhuongThucVanChuyenDTO;
import com.example.be.service.PhuongThucVanChuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phuongthucvanchuyen")
@RequiredArgsConstructor
public class PhuongThucVanChuyenController {

    private final PhuongThucVanChuyenService phuongThucVanChuyenService;

    @GetMapping
    public ResponseEntity<List<PhuongThucVanChuyenDTO>> getAllPhuongThucVanChuyen() {
        return ResponseEntity.ok(phuongThucVanChuyenService.getAllPhuongThucVanChuyen());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhuongThucVanChuyenDTO> getPhuongThucVanChuyenById(@PathVariable Integer id) {
        return ResponseEntity.ok(phuongThucVanChuyenService.getPhuongThucVanChuyenById(id));
    }

    @PostMapping
    public ResponseEntity<PhuongThucVanChuyenDTO> createPhuongThucVanChuyen(@RequestBody PhuongThucVanChuyenDTO dto) {
        return ResponseEntity.ok(phuongThucVanChuyenService.createPhuongThucVanChuyen(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhuongThucVanChuyenDTO> updatePhuongThucVanChuyen(@PathVariable Integer id, @RequestBody PhuongThucVanChuyenDTO dto) {
        return ResponseEntity.ok(phuongThucVanChuyenService.updatePhuongThucVanChuyen(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhuongThucVanChuyen(@PathVariable Integer id) {
        phuongThucVanChuyenService.deletePhuongThucVanChuyen(id);
        return ResponseEntity.noContent().build();
    }
}