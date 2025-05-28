package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.DanhMucDTO;
import com.example.be.tempotide.service.DanhMucService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danhmuc")
@RequiredArgsConstructor
@Tag(name = "DanhMuc API", description = "APIs for managing danhmuc")
public class DanhMucController {
    private final DanhMucService danhMucService;

    @GetMapping
    @Operation(summary = "Get all danhmucs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DanhMucDTO>> getAllDanhMucs() {
        return ResponseEntity.ok(danhMucService.getAllDanhMucs());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get danhmuc by ID")
    public ResponseEntity<DanhMucDTO> getDanhMucById(@PathVariable Integer id) {
        return ResponseEntity.ok(danhMucService.getDanhMucById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new danhmuc")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DanhMucDTO> createDanhMuc(@Valid @RequestBody DanhMucDTO danhMucDTO) {
        return ResponseEntity.ok(danhMucService.createDanhMuc(danhMucDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a danhmuc")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DanhMucDTO> updateDanhMuc(@PathVariable Integer id, @Valid @RequestBody DanhMucDTO danhMucDTO) {
        return ResponseEntity.ok(danhMucService.updateDanhMuc(id, danhMucDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a danhmuc (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDanhMuc(@PathVariable Integer id) {
        danhMucService.deleteDanhMuc(id);
        return ResponseEntity.noContent().build();
    }
}