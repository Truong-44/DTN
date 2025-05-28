package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.SanPhamDTO;
import com.example.be.tempotide.service.SanPhamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sanpham")
@RequiredArgsConstructor
@Tag(name = "SanPham API", description = "APIs for managing sanpham")
public class SanPhamController {
    private final SanPhamService sanPhamService;

    @GetMapping
    @Operation(summary = "Get all sanphams")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SanPhamDTO>> getAllSanPhams() {
        return ResponseEntity.ok(sanPhamService.getAllSanPhams());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sanpham by ID")
    public ResponseEntity<SanPhamDTO> getSanPhamById(@PathVariable Integer id) {
        return ResponseEntity.ok(sanPhamService.getSanPhamById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new sanpham")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SanPhamDTO> createSanPham(@Valid @RequestBody SanPhamDTO sanPhamDTO) {
        return ResponseEntity.ok(sanPhamService.createSanPham(sanPhamDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a sanpham")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SanPhamDTO> updateSanPham(@PathVariable Integer id, @Valid @RequestBody SanPhamDTO sanPhamDTO) {
        return ResponseEntity.ok(sanPhamService.updateSanPham(id, sanPhamDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a sanpham (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSanPham(@PathVariable Integer id) {
        sanPhamService.deleteSanPham(id);
        return ResponseEntity.noContent().build();
    }
}