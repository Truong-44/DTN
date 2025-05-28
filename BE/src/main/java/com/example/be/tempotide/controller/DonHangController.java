package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.DonHangDTO;
import com.example.be.tempotide.service.DonHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/donhang")
@RequiredArgsConstructor
@Tag(name = "DonHang API", description = "APIs for managing donhang")
public class DonHangController {
    private final DonHangService donHangService;

    @GetMapping
    @Operation(summary = "Get all donhangs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DonHangDTO>> getAllDonHangs() {
        return ResponseEntity.ok(donHangService.getAllDonHangs());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get donhang by ID")
    public ResponseEntity<DonHangDTO> getDonHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(donHangService.getDonHangById(id));
    }

    @GetMapping("/khachhang/{makhachhang}")
    @Operation(summary = "Get donhang by KhachHang ID")
    public ResponseEntity<List<DonHangDTO>> getDonHangByKhachHangId(@PathVariable Integer makhachhang) {
        return ResponseEntity.ok(donHangService.getDonHangByKhachHangId(makhachhang));
    }

    @GetMapping("/daterange")
    @Operation(summary = "Get donhang by date range")
    public ResponseEntity<List<DonHangDTO>> getDonHangByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(donHangService.getDonHangByDateRange(start, end));
    }

    @PostMapping
    @Operation(summary = "Create a new donhang")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DonHangDTO> createDonHang(@Valid @RequestBody DonHangDTO donHangDTO) {
        return ResponseEntity.ok(donHangService.createDonHang(donHangDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a donhang")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DonHangDTO> updateDonHang(@PathVariable Integer id, @Valid @RequestBody DonHangDTO donHangDTO) {
        return ResponseEntity.ok(donHangService.updateDonHang(id, donHangDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a donhang (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDonHang(@PathVariable Integer id) {
        donHangService.deleteDonHang(id);
        return ResponseEntity.noContent().build();
    }
}