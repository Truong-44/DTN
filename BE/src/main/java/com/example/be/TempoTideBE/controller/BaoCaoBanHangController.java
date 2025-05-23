package com.example.be.TempoTideBE.controller;


import com.example.be.TempoTideBE.dto.BaoCaoBanHangDTO;
import com.example.be.TempoTideBE.service.BaoCaoBanHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baocaobanhang")
@RequiredArgsConstructor
public class BaoCaoBanHangController {

    private final BaoCaoBanHangService baoCaoBanHangService;

    @GetMapping
    public ResponseEntity<List<BaoCaoBanHangDTO>> getAllBaoCaoBanHang() {
        return ResponseEntity.ok(baoCaoBanHangService.getAllBaoCaoBanHang());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaoCaoBanHangDTO> getBaoCaoBanHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(baoCaoBanHangService.getBaoCaoBanHangById(id));
    }

    @PostMapping
    public ResponseEntity<BaoCaoBanHangDTO> createBaoCaoBanHang(@RequestBody BaoCaoBanHangDTO dto) {
        return ResponseEntity.ok(baoCaoBanHangService.createBaoCaoBanHang(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaoCaoBanHangDTO> updateBaoCaoBanHang(@PathVariable Integer id, @RequestBody BaoCaoBanHangDTO dto) {
        return ResponseEntity.ok(baoCaoBanHangService.updateBaoCaoBanHang(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaoCaoBanHang(@PathVariable Integer id) {
        baoCaoBanHangService.deleteBaoCaoBanHang(id);
        return ResponseEntity.noContent().build();
    }
}