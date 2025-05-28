package com.example.tempotide.controller;

import com.example.tempotide.dto.ThuocTinhSanPhamDTO;
import com.example.tempotide.service.ThuocTinhSanPhamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attributes")
@RequiredArgsConstructor
@Tag(name = "Attribute API", description = "APIs for managing product attributes")
public class ThuocTinhSanPhamController {
    private final ThuocTinhSanPhamService thuocTinhSanPhamService;

    @GetMapping
    @Operation(summary = "Get all active attributes")
    public ResponseEntity<List<ThuocTinhSanPhamDTO>> getAllAttributes() {
        return ResponseEntity.ok(thuocTinhSanPhamService.getAllActiveAttributes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get attribute by ID")
    public ResponseEntity<ThuocTinhSanPhamDTO> getAttributeById(@PathVariable Integer id) {
        return ResponseEntity.ok(thuocTinhSanPhamService.getAttributeById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new attribute")
    public ResponseEntity<ThuocTinhSanPhamDTO> createAttribute(@Valid @RequestBody ThuocTinhSanPhamDTO thuocTinhSanPhamDTO) {
        return ResponseEntity.ok(thuocTinhSanPhamService.createAttribute(thuocTinhSanPhamDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an attribute")
    public ResponseEntity<ThuocTinhSanPhamDTO> updateAttribute(@PathVariable Integer id, @Valid @RequestBody ThuocTinhSanPhamDTO thuocTinhSanPhamDTO) {
        return ResponseEntity.ok(thuocTinhSanPhamService.updateAttribute(id, thuocTinhSanPhamDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an attribute (soft delete)")
    public ResponseEntity<Void> deleteAttribute(@PathVariable Integer id) {
        thuocTinhSanPhamService.deleteAttribute(id);
        return ResponseEntity.noContent().build();
    }
}