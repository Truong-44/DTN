package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.ChiTietGioHangDTO;
import com.example.be.tempotide.service.ChiTietGioHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietgiohang")
@RequiredArgsConstructor
@Tag(name = "ChiTietGioHang API", description = "APIs for managing chitietgiohang")
public class ChiTietGioHangController {
    private final ChiTietGioHangService chiTietGioHangService;

    @GetMapping
    @Operation(summary = "Get all chitietgiohangs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ChiTietGioHangDTO>> getAllChiTietGioHangs() {
        return ResponseEntity.ok(chiTietGioHangService.getAllChiTietGioHangs());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get chitietgiohang by ID")
    public ResponseEntity<ChiTietGioHangDTO> getChiTietGioHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(chiTietGioHangService.getChiTietGioHangById(id));
    }

    @GetMapping("/giohang/{magiohang}")
    @Operation(summary = "Get chitietgiohang by GioHang ID")
    public ResponseEntity<List<ChiTietGioHangDTO>> getChiTietGioHangByGioHangId(@PathVariable Integer magiohang) {
        return ResponseEntity.ok(chiTietGioHangService.getChiTietGioHangByGioHangId(magiohang));
    }

    @PostMapping
    @Operation(summary = "Create a new chitietgiohang")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChiTietGioHangDTO> createChiTietGioHang(@Valid @RequestBody ChiTietGioHangDTO chiTietGioHangDTO) {
        return ResponseEntity.ok(chiTietGioHangService.createChiTietGioHang(chiTietGioHangDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a chitietgiohang")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChiTietGioHangDTO> updateChiTietGioHang(@PathVariable Integer id, @Valid @RequestBody ChiTietGioHangDTO chiTietGioHangDTO) {
        return ResponseEntity.ok(chiTietGioHangService.updateChiTietGioHang(id, chiTietGioHangDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a chitietgiohang (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteChiTietGioHang(@PathVariable Integer id) {
        chiTietGioHangService.deleteChiTietGioHang(id);
        return ResponseEntity.noContent().build();
    }
}