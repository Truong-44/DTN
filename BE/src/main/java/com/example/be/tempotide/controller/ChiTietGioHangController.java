package com.example.tempotide.controller;

import com.example.tempotide.dto.ChiTietGioHangDTO;
import com.example.tempotide.service.ChiTietGioHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
@Tag(name = "Cart Item API", description = "APIs for managing cart items")
public class ChiTietGioHangController {
    private final ChiTietGioHangService chiTietGioHangService;

    @GetMapping("/{cartId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get cart items by cart ID")
    public ResponseEntity<List<ChiTietGioHangDTO>> getCartItems(@PathVariable Integer cartId) {
        return ResponseEntity.ok(chiTietGioHangService.getCartItems(cartId));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Add item to cart")
    public ResponseEntity<ChiTietGioHangDTO> addCartItem(@Valid @RequestBody ChiTietGioHangDTO chiTietGioHangDTO) {
        return ResponseEntity.ok(chiTietGioHangService.addCartItem(chiTietGioHangDTO));
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update cart item")
    public ResponseEntity<ChiTietGioHangDTO> updateCartItem(@Valid @RequestBody ChiTietGioHangDTO chiTietGioHangDTO) {
        return ResponseEntity.ok(chiTietGioHangService.updateCartItem(chiTietGioHangDTO));
    }

    @DeleteMapping("/{cartId}/{productDetailId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete cart item")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Integer cartId, @PathVariable Integer productDetailId) {
        chiTietGioHangService.deleteCartItem(cartId, productDetailId);
        return ResponseEntity.noContent().build();
    }
}