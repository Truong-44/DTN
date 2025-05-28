package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.KhachHangDTO;
import com.example.be.tempotide.service.KhachHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khachhang")
@RequiredArgsConstructor
@Tag(name = "KhachHang API", description = "APIs for managing khachhang")
public class KhachHangController {
    private final KhachHangService khachHangService;

    @GetMapping
    @Operation(summary = "Get all khachhangs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<KhachHangDTO>> getAllKhachHangs() {
        return ResponseEntity.ok(khachHangService.getAllKhachHangs());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get khachhang by ID")
    public ResponseEntity<KhachHangDTO> getKhachHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(khachHangService.getKhachHangById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new khachhang")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<KhachHangDTO> createKhachHang(@Valid @RequestBody KhachHangDTO khachHangDTO) {
        return ResponseEntity.ok(khachHangService.createKhachHang(khachHangDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a khachhang")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<KhachHangDTO> updateKhachHang(@PathVariable Integer id, @Valid @RequestBody KhachHangDTO khachHangDTO) {
        return ResponseEntity.ok(khachHangService.updateKhachHang(id, khachHangDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a khachhang (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteKhachHang(@PathVariable Integer id) {
        khachHangService.deleteKhachHang(id);
        return ResponseEntity.noContent().build();
    }
}