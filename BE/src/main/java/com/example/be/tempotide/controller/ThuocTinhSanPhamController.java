package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.ThuocTinhSanPhamDTO;
import com.example.be.tempotide.service.ThuocTinhSanPhamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thuoctinhsanpham")
@RequiredArgsConstructor
@Tag(name = "ThuocTinhSanPham API", description = "APIs for managing thuoctinhsanpham")
public class ThuocTinhSanPhamController {
    private final ThuocTinhSanPhamService thuocTinhSanPhamService;

    @GetMapping
    @Operation(summary = "Get all thuoctinhsanphams")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ThuocTinhSanPhamDTO>> getAllThuocTinhSanPhams() {
        return ResponseEntity.ok(thuocTinhSanPhamService.getAllThuocTinhSanPhams());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get thuoctinhsanpham by ID")
    public ResponseEntity<ThuocTinhSanPhamDTO> getThuocTinhSanPhamById(@PathVariable Integer id) {
        return ResponseEntity.ok(thuocTinhSanPhamService.getThuocTinhSanPhamById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new thuoctinhsanpham")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ThuocTinhSanPhamDTO> createThuocTinhSanPham(@Valid @RequestBody ThuocTinhSanPhamDTO thuocTinhSanPhamDTO) {
        return ResponseEntity.ok(thuocTinhSanPhamService.createThuocTinhSanPham(thuocTinhSanPhamDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a thuoctinhsanpham")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ThuocTinhSanPhamDTO> updateThuocTinhSanPham(@PathVariable Integer id, @Valid @RequestBody ThuocTinhSanPhamDTO thuocTinhSanPhamDTO) {
        return ResponseEntity.ok(thuocTinhSanPhamService.updateThuocTinhSanPham(id, thuocTinhSanPhamDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a thuoctinhsanpham (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteThuocTinhSanPham(@PathVariable Integer id) {
        thuocTinhSanPhamService.deleteThuocTinhSanPham(id);
        return ResponseEntity.noContent().build();
    }
}