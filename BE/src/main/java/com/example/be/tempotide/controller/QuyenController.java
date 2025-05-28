package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.QuyenDTO;
import com.example.be.tempotide.service.QuyenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quyen")
@RequiredArgsConstructor
@Tag(name = "Quyen API", description = "APIs for managing quyen")
public class QuyenController {
    private final QuyenService quyenService;

    @GetMapping
    @Operation(summary = "Get all quyens")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<QuyenDTO>> getAllQuyens() {
        return ResponseEntity.ok(quyenService.getAllQuyens());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get quyen by ID")
    public ResponseEntity<QuyenDTO> getQuyenById(@PathVariable Integer id) {
        return ResponseEntity.ok(quyenService.getQuyenById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new quyen")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuyenDTO> createQuyen(@Valid @RequestBody QuyenDTO quyenDTO) {
        return ResponseEntity.ok(quyenService.createQuyen(quyenDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a quyen")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuyenDTO> updateQuyen(@PathVariable Integer id, @Valid @RequestBody QuyenDTO quyenDTO) {
        return ResponseEntity.ok(quyenService.updateQuyen(id, quyenDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a quyen (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteQuyen(@PathVariable Integer id) {
        quyenService.deleteQuyen(id);
        return ResponseEntity.noContent().build();
    }
}