package com.example.be.TempoTideBE.controller;

import com.example.be.dto.ThuocTinhSanPhamDTO;
import com.example.be.service.ThuocTinhSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thuoctinhsanpham")
@RequiredArgsConstructor
public class ThuocTinhSanPhamController {

    private final ThuocTinhSanPhamService thuocTinhSanPhamService;

    @GetMapping
    public ResponseEntity<List<ThuocTinhSanPhamDTO>> getAllThuocTinhSanPham() {
        return ResponseEntity.ok(thuocTinhSanPhamService.getAllThuocTinhSanPham());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThuocTinhSanPhamDTO> getThuocTinhSanPhamById(@PathVariable Integer id) {
        return ResponseEntity.ok(thuocTinhSanPhamService.getThuocTinhSanPhamById(id));
    }

    @PostMapping
    public ResponseEntity<ThuocTinhSanPhamDTO> createThuocTinhSanPham(@RequestBody ThuocTinhSanPhamDTO dto) {
        return ResponseEntity.ok(thuocTinhSanPhamService.createThuocTinhSanPham(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThuocTinhSanPhamDTO> updateThuocTinhSanPham(@PathVariable Integer id, @RequestBody ThuocTinhSanPhamDTO dto) {
        return ResponseEntity.ok(thuocTinhSanPhamService.updateThuocTinhSanPham(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThuocTinhSanPham(@PathVariable Integer id) {
        thuocTinhSanPhamService.deleteThuocTinhSanPham(id);
        return ResponseEntity.noContent().build();
    }
}