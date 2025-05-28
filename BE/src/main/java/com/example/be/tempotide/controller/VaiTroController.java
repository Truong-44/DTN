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
@RequestMapping("/api/vaitro")
@RequiredArgsConstructor
@Tag(name = "VaiTro API", description = "APIs for managing vaitro")
public class VaiTroController {
    private final VaiTroService vaiTroService;

    @GetMapping
    @Operation(summary = "Get all vaitros")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VaiTroDTO>> getAllVaiTros() {
        return ResponseEntity.ok(vaiTroService.getAllVaiTros());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get vaitro by ID")
    public ResponseEntity<VaiTroDTO> getVaiTroById(@PathVariable Integer id) {
        return ResponseEntity.ok(vaiTroService.getVaiTroById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new vaitro")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VaiTroDTO> createVaiTro(@Valid @RequestBody VaiTroDTO vaiTroDTO) {
        return ResponseEntity.ok(vaiTroService.createVaiTro(vaiTroDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a vaitro")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VaiTroDTO> updateVaiTro(@PathVariable Integer id, @Valid @RequestBody VaiTroDTO vaiTroDTO) {
        return ResponseEntity.ok(vaiTroService.updateVaiTro(id, vaiTroDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a vaitro (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVaiTro(@PathVariable Integer id) {
        vaiTroService.deleteVaiTro(id);
        return ResponseEntity.noContent().build();
    }
}