package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.QuyenDTO;
import com.example.be.tempotide.service.QuyenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@Tag(name = "Permission API", description = "APIs for managing permissions")
public class QuyenController {
    private final QuyenService quyenService;

    @GetMapping
    @Operation(summary = "Get all active permissions")
    public ResponseEntity<List<QuyenDTO>> getAllPermissions() {
        return ResponseEntity.ok(quyenService.getAllActivePermissions());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get permission by ID")
    public ResponseEntity<QuyenDTO> getPermissionById(@PathVariable Integer id) {
        return ResponseEntity.ok(quyenService.getPermissionById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new permission")
    public ResponseEntity<QuyenDTO> createPermission(@RequestBody QuyenDTO quyenDTO) {
        return ResponseEntity.ok(quyenService.createPermission(quyenDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a permission")
    public ResponseEntity<QuyenDTO> updatePermission(@PathVariable Integer id, @RequestBody QuyenDTO quyenDTO) {
        return ResponseEntity.ok(quyenService.updatePermission(id, quyenDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a permission (soft delete)")
    public ResponseEntity<Void> deletePermission(@PathVariable Integer id) {
        quyenService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}