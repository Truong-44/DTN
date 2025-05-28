package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.ChiTietDonHangDTO;
import com.example.be.tempotide.service.ChiTietDonHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietdonhang")
@RequiredArgsConstructor
@Tag(name = "ChiTietDonHang API", description = "APIs for managing chitietdonhang")
public class ChiTietDonHangController {
    private final ChiTietDonHangService chiTietDonHangService;

    @GetMapping
    @Operation(summary = "Get all chitietdonhangs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ChiTietDonHangDTO>> getAllChiTietDonHangs() {
        return ResponseEntity.ok(chiTietDonHangService.getAllChiTietDonHangs());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get chitietdonhang by ID")
    public ResponseEntity<ChiTietDonHangDTO> getChiTietDonHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(chiTietDonHangService.getChiTietDonHangById(id));
    }

    @GetMapping("/donhang/{madonhang}")
    @Operation(summary = "Get chitietdonhang by DonHang ID")
    public ResponseEntity<List<ChiTietDonHangDTO>> getChiTietDonHangByDonHangId(@PathVariable Integer madonhang) {
        return ResponseEntity.ok(chiTietDonHangService.getChiTietDonHangByDonHangId(madonhang));
    }

    @PostMapping
    @Operation(summary = "Create a new chitietdonhang")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChiTietDonHangDTO> createChiTietDonHang(@Valid @RequestBody ChiTietDonHangDTO chiTietDonHangDTO) {
        return ResponseEntity.ok(chiTietDonHangService.createChiTietDonHang(chiTietDonHangDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a chitietdonhang")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChiTietDonHangDTO> updateChiTietDonHang(@PathVariable Integer id, @Valid @RequestBody ChiTietDonHangDTO chiTietDonHangDTO) {
        return ResponseEntity.ok(chiTietDonHangService.updateChiTietDonHang(id, chiTietDonHangDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a chitietdonhang (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteChiTietDonHang(@PathVariable Integer id) {
        chiTietDonHangService.deleteChiTietDonHang(id);
        return ResponseEntity.noContent().build();
    }
}