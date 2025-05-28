package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.VaiTroQuyenDTO;
import com.example.be.tempotide.service.VaiTroQuyenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaitro-quyen")
@RequiredArgsConstructor
@Tag(name = "VaiTroQuyen API", description = "APIs for managing vaitro_quyen")
public class VaiTroQuyenController {
    private final VaiTroQuyenService vaiTroQuyenService;

    @GetMapping
    @Operation(summary = "Get all vaitro_quyens")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VaiTroQuyenDTO>> getAllVaiTroQuyens() {
        return ResponseEntity.ok(vaiTroQuyenService.getAllVaiTroQuyens());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get vaitro_quyen by ID")
    public ResponseEntity<VaiTroQuyenDTO> getVaiTroQuyenById(@PathVariable Integer id) {
        return ResponseEntity.ok(vaiTroQuyenService.getVaiTroQuyenById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new vaitro_quyen")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VaiTroQuyenDTO> createVaiTroQuyen(@Valid @RequestBody VaiTroQuyenDTO vaiTroQuyenDTO) {
        return ResponseEntity.ok(vaiTroQuyenService.createVaiTroQuyen(vaiTroQuyenDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a vaitro_quyen")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VaiTroQuyenDTO> updateVaiTroQuyen(@PathVariable Integer id, @Valid @RequestBody VaiTroQuyenDTO vaiTroQuyenDTO) {
        return ResponseEntity.ok(vaiTroQuyenService.updateVaiTroQuyen(id, vaiTroQuyenDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a vaitro_quyen (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVaiTroQuyen(@PathVariable Integer id) {
        vaiTroQuyenService.deleteVaiTroQuyen(id);
        return ResponseEntity.noContent().build();
    }
}