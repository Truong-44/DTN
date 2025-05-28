package com.example.tempotide.controller;

import com.example.tempotide.dto.SanPhamDTO;
import com.example.tempotide.service.SanPhamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product API", description = "APIs for managing products")
public class SanPhamController {
    private final SanPhamService sanPhamService;

    @GetMapping
    @Operation(summary = "Get all active products")
    public ResponseEntity<List<SanPhamDTO>> getAllProducts() {
        return ResponseEntity.ok(sanPhamService.getAllActiveProducts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<SanPhamDTO> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(sanPhamService.getProductById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new product")
    public ResponseEntity<SanPhamDTO> createProduct(@Valid @RequestBody SanPhamDTO sanPhamDTO) {
        return ResponseEntity.ok(sanPhamService.createProduct(sanPhamDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a product")
    public ResponseEntity<SanPhamDTO> updateProduct(@PathVariable Integer id, @Valid @RequestBody SanPhamDTO sanPhamDTO) {
        return ResponseEntity.ok(sanPhamService.updateProduct(id, sanPhamDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a product (soft delete)")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        sanPhamService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}