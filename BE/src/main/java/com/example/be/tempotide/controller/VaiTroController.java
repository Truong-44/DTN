package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.VaiTroDTO;
import com.example.be.tempotide.service.VaiTroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Role API", description = "APIs for managing roles")
public class VaiTroController {
    private final VaiTroService vaiTroService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all active roles")
    public ResponseEntity<List<VaiTroDTO>> getAllRoles() {
        return ResponseEntity.ok(vaiTroService.getAllActiveRoles());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get role by ID")
    public ResponseEntity<VaiTroDTO> getRoleById(@PathVariable Integer id) {
        return ResponseEntity.ok(vaiTroService.getRoleById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new role")
    public ResponseEntity<VaiTroDTO> createRole(@Valid @RequestBody VaiTroDTO vaiTroDTO) {
        return ResponseEntity.ok(vaiTroService.createRole(vaiTroDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a role")
    public ResponseEntity<VaiTroDTO> updateRole(@PathVariable Integer id, @Valid @RequestBody VaiTroDTO vaiTroDTO) {
        return ResponseEntity.ok(vaiTroService.updateRole(id, vaiTroDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a role (soft delete)")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
        vaiTroService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}