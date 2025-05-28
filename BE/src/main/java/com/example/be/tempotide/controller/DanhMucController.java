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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category API", description = "APIs for managing categories")
public class DanhMucController {
    private final DanhMucService danhMucService;

    @GetMapping
    @Operation(summary = "Get all active categories")
    public ResponseEntity<List<DanhMucDTO>> getAllCategories() {
        return ResponseEntity.ok(danhMucService.getAllActiveCategories());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<DanhMucDTO> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(danhMucService.getCategoryById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new category")
    public ResponseEntity<DanhMucDTO> createCategory(@Valid @RequestBody DanhMucDTO danhMucDTO) {
        return ResponseEntity.ok(danhMucService.createCategory(danhMucDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a category")
    public ResponseEntity<DanhMucDTO> updateCategory(@PathVariable Integer id, @Valid @RequestBody DanhMucDTO danhMucDTO) {
        return ResponseEntity.ok(danhMucService.updateCategory(id, danhMucDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a category (soft delete)")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        danhMucService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}