package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.ChiTietSanPhamDTO;
import com.example.be.tempotide.service.ChiTietSanPhamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietsanpham")
@RequiredArgsConstructor
@Tag(name = "ChiTietSanPham API", description = "APIs for managing chitietsanpham")
public class ChiTietSanPhamController {
    private final ChiTietSanPhamService chiTietSanPhamService;

    @GetMapping
    @Operation(summary = "Get all chitietsanphams")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ChiTietSanPhamDTO>> getAllChiTietSanPhams() {
        return ResponseEntity.ok(chiTietSanPhamService.getAllChiTietSanPhams());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get chitietsanpham by ID")
    public ResponseEntity<ChiTietSanPhamDTO> getChiTietSanPhamById(@PathVariable Integer id) {
        return ResponseEntity.ok(chiTietSanPhamService.getChiTietSanPhamById(id));
    }

    @GetMapping("/sanpham/{masanpham}")
    @Operation(summary = "Get chitietsanpham by sanpham ID")
    public ResponseEntity<List<ChiTietSanPhamDTO>> getChiTietSanPhamBySanPhamId(@PathVariable Integer masanpham) {
        return ResponseEntity.ok(chiTietSanPhamService.getChiTietSanPhamBySanPhamId(masanpham));
    }

    @PostMapping
    @Operation(summary = "Create a new chitietsanpham")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChiTietSanPhamDTO> createChiTietSanPham(@Valid @RequestBody ChiTietSanPhamDTO chiTietSanPhamDTO) {
        return ResponseEntity.ok(chiTietSanPhamService.createChiTietSanPham(chiTietSanPhamDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a chitietsanpham")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChiTietSanPhamDTO> updateChiTietSanPham(@PathVariable Integer id, @Valid @RequestBody ChiTietSanPhamDTO chiTietSanPhamDTO) {
        return ResponseEntity.ok(chiTietSanPhamService.updateChiTietSanPham(id, chiTietSanPhamDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a chitietsanpham (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteChiTietSanPham(@PathVariable Integer id) {
        chiTietSanPhamService.deleteChiTietSanPham(id);
        return ResponseEntity.noContent().build();
    }
}