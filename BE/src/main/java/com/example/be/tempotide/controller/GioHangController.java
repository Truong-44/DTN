package com.example.tempotide.controller;

import com.example.tempotide.dto.GioHangDTO;
import com.example.tempotide.service.GioHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@Tag(name = "Cart API", description = "APIs for managing shopping carts")
public class GioHangController {
    private final GioHangService gioHangService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all active carts")
    public ResponseEntity<List<GioHangDTO>> getAllCarts() {
        return ResponseEntity.ok(gioHangService.getAllActiveCarts());
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get current user's cart")
    public ResponseEntity<GioHangDTO> getUserCart() {
        return ResponseEntity.ok(gioHangService.getUserCart());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new cart")
    public ResponseEntity<GioHangDTO> createCart(@Valid @RequestBody GioHangDTO gioHangDTO) {
        return ResponseEntity.ok(gioHangService.createCart(gioHangDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update a cart")
    public ResponseEntity<GioHangDTO> updateCart(@PathVariable Integer id, @Valid @RequestBody GioHangDTO gioHangDTO) {
        return ResponseEntity.ok(gioHangService.updateCart(id, gioHangDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete a cart (soft delete)")
    public ResponseEntity<Void> deleteCart(@PathVariable Integer id) {
        gioHangService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
}