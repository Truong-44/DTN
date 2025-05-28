package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.NguoiDungVaiTroDTO;
import com.example.be.tempotide.service.NguoiDungVaiTroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nguoidung-vaitro")
@RequiredArgsConstructor
@Tag(name = "NguoiDungVaiTro API", description = "APIs for managing nguoidung_vaitro")
public class NguoiDungVaiTroController {
    private final NguoiDungVaiTroService nguoiDungVaiTroService;

    @GetMapping
    @Operation(summary = "Get all nguoidung_vaitro")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NguoiDungVaiTroDTO>> getAllNguoiDungVaiTros() {
        return ResponseEntity.ok(nguoiDungVaiTroService.getAllNguoiDungVaiTros());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get nguoidung_vaitro by ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NguoiDungVaiTroDTO> getNguoiDungVaiTroById(@PathVariable Integer id) {
        return ResponseEntity.ok(nguoiDungVaiTroService.getNguoiDungVaiTroById(id));
    }

    @GetMapping("/manhanvien/{manhanvien}")
    @Operation(summary = "Get nguoidung_vaitro by manhanvien")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NguoiDungVaiTroDTO>> getNguoiDungVaiTroByManhanvien(@PathVariable Integer manhanvien) {
        return ResponseEntity.ok(nguoiDungVaiTroService.getNguoiDungVaiTroByManhanvien(manhanvien));
    }

    @GetMapping("/mavaitro/{mavaitro}")
    @Operation(summary = "Get nguoidung_vaitro by mavaitro")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NguoiDungVaiTroDTO>> getNguoiDungVaiTroByMavaitro(@PathVariable Integer mavaitro) {
        return ResponseEntity.ok(nguoiDungVaiTroService.getNguoiDungVaiTroByMavaitro(mavaitro));
    }

    @PostMapping
    @Operation(summary = "Create a new nguoidung_vaitro")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NguoiDungVaiTroDTO> createNguoiDungVaiTro(@Valid @RequestBody NguoiDungVaiTroDTO nguoiDungVaiTroDTO) {
        return ResponseEntity.ok(nguoiDungVaiTroService.createNguoiDungVaiTro(nguoiDungVaiTroDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a nguoidung_vaitro")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NguoiDungVaiTroDTO> updateNguoiDungVaiTro(@PathVariable Integer id, @Valid @RequestBody NguoiDungVaiTroDTO nguoiDungVaiTroDTO) {
        return ResponseEntity.ok(nguoiDungVaiTroService.updateNguoiDungVaiTro(id, nguoiDungVaiTroDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a nguoidung_vaitro (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNguoiDungVaiTro(@PathVariable Integer id) {
        nguoiDungVaiTroService.deleteNguoiDungVaiTro(id);
        return ResponseEntity.noContent().build();
    }
}