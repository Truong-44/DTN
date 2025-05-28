package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.NhanVienDTO;
import com.example.be.tempotide.service.NhanVienService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nhanvien")
@RequiredArgsConstructor
@Tag(name = "NhanVien API", description = "APIs for managing nhanvien")
public class NhanVienController {
    private final NhanVienService nhanVienService;

    @GetMapping
    @Operation(summary = "Get all nhanviens")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NhanVienDTO>> getAllNhanViens() {
        return ResponseEntity.ok(nhanVienService.getAllNhanViens());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get nhanvien by ID")
    public ResponseEntity<NhanVienDTO> getNhanVienById(@PathVariable Integer id) {
        return ResponseEntity.ok(nhanVienService.getNhanVienById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new nhanvien")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NhanVienDTO> createNhanVien(@Valid @RequestBody NhanVienDTO nhanVienDTO) {
        return ResponseEntity.ok(nhanVienService.createNhanVien(nhanVienDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a nhanvien")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NhanVienDTO> updateNhanVien(@PathVariable Integer id, @Valid @RequestBody NhanVienDTO nhanVienDTO) {
        return ResponseEntity.ok(nhanVienService.updateNhanVien(id, nhanVienDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a nhanvien (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNhanVien(@PathVariable Integer id) {
        nhanVienService.deleteNhanVien(id);
        return ResponseEntity.noContent().build();
    }
}