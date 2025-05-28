package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.GioHangDTO;
import com.example.be.tempotide.service.GioHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/giohang")
@RequiredArgsConstructor
@Tag(name = "GioHang API", description = "APIs for managing giohang")
public class GioHangController {
    private final GioHangService gioHangService;

    @GetMapping
    @Operation(summary = "Get all giohangs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GioHangDTO>> getAllGioHangs() {
        return ResponseEntity.ok(gioHangService.getAllGioHangs());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get giohang by ID")
    public ResponseEntity<GioHangDTO> getGioHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(gioHangService.getGioHangById(id));
    }

    @GetMapping("/khachhang/{makhachhang}")
    @Operation(summary = "Get giohang by KhachHang ID")
    public ResponseEntity<GioHangDTO> getGioHangByKhachHangId(@PathVariable Integer makhachhang) {
        return ResponseEntity.ok(gioHangService.getGioHangByKhachHangId(makhachhang));
    }

    @GetMapping("/sodienthoai/{sodienthoai}")
    @Operation(summary = "Get giohang by sodienthoai")
    public ResponseEntity<GioHangDTO> getGioHangBySodienthoai(@PathVariable String sodienthoai) {
        return ResponseEntity.ok(gioHangService.getGioHangBySodienthoai(sodienthoai));
    }

    @PostMapping
    @Operation(summary = "Create a new giohang")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GioHangDTO> createGioHang(@Valid @RequestBody GioHangDTO gioHangDTO) {
        return ResponseEntity.ok(gioHangService.createGioHang(gioHangDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a giohang")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GioHangDTO> updateGioHang(@PathVariable Integer id, @Valid @RequestBody GioHangDTO gioHangDTO) {
        return ResponseEntity.ok(gioHangService.updateGioHang(id, gioHangDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a giohang (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteGioHang(@PathVariable Integer id) {
        gioHangService.deleteGioHang(id);
        return ResponseEntity.noContent().build();
    }
}