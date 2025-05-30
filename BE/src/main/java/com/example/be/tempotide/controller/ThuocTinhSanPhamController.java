package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.ThuocTinhSanPhamDTO;
import com.example.be.tempotide.service.ThuocTinhSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thuoctinh-sanpham")
@RequiredArgsConstructor
public class ThuocTinhSanPhamController {
    private final ThuocTinhSanPhamService thuocTinhSanPhamService;

    @GetMapping
    public ResponseEntity<List<ThuocTinhSanPhamDTO>> getAllThuocTinhSanPhams() {
        return ResponseEntity.ok(thuocTinhSanPhamService.getAllThuocTinhSanPhams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThuocTinhSanPhamDTO> getThuocTinhSanPhamById(@PathVariable Integer id) {
        return ResponseEntity.ok(thuocTinhSanPhamService.getThuocTinhSanPhamById(id));
    }

    @PostMapping
    public ResponseEntity<ThuocTinhSanPhamDTO> createThuocTinhSanPham(@RequestBody ThuocTinhSanPhamDTO thuocTinhSanPhamDTO) {
        return ResponseEntity.ok(thuocTinhSanPhamService.createThuocTinhSanPham(thuocTinhSanPhamDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThuocTinhSanPhamDTO> updateThuocTinhSanPham(@PathVariable Integer id, @RequestBody ThuocTinhSanPhamDTO thuocTinhSanPhamDTO) {
        return ResponseEntity.ok(thuocTinhSanPhamService.updateThuocTinhSanPham(id, thuocTinhSanPhamDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThuocTinhSanPham(@PathVariable Integer id) {
        thuocTinhSanPhamService.deleteThuocTinhSanPham(id);
        return ResponseEntity.noContent().build();
    }
}