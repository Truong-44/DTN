package com.example.be.TempoTideBE.controller;

import com.example.be.TempoTideBE.dto.DanhGiaSanPhamDTO;
import com.example.be.TempoTideBE.service.DanhGiaSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danhgiasanpham")
@RequiredArgsConstructor
public class DanhGiaSanPhamController {

    private final DanhGiaSanPhamService danhGiaSanPhamService;

    @GetMapping
    public ResponseEntity<List<DanhGiaSanPhamDTO>> getAllDanhGiaSanPham() {
        return ResponseEntity.ok(danhGiaSanPhamService.getAllDanhGiaSanPham());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DanhGiaSanPhamDTO> getDanhGiaSanPhamById(@PathVariable Integer id) {
        return ResponseEntity.ok(danhGiaSanPhamService.getDanhGiaSanPhamById(id));
    }

    @PostMapping
    public ResponseEntity<DanhGiaSanPhamDTO> createDanhGiaSanPham(@RequestBody DanhGiaSanPhamDTO dto) {
        return ResponseEntity.ok(danhGiaSanPhamService.createDanhGiaSanPham(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DanhGiaSanPhamDTO> updateDanhGiaSanPham(@PathVariable Integer id, @RequestBody DanhGiaSanPhamDTO dto) {
        return ResponseEntity.ok(danhGiaSanPhamService.updateDanhGiaSanPham(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDanhGiaSanPham(@PathVariable Integer id) {
        danhGiaSanPhamService.deleteDanhGiaSanPham(id);
        return ResponseEntity.noContent().build();
    }
}