package com.example.tempotide.controller;

import com.example.tempotide.dto.ChiTietDonHangDTO;
import com.example.tempotide.service.ChiTietDonHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
@Tag(name = "Order Item API", description = "APIs for managing order items")
public class ChiTietDonHangController {
    private final ChiTietDonHangService chiTietDonHangService;

    @GetMapping("/{orderId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get order items by order ID")
    public ResponseEntity<List<ChiTietDonHangDTO>> getOrderItems(@PathVariable Integer orderId) {
        return ResponseEntity.ok(chiTietDonHangService.getOrderItems(orderId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Add item to order")
    public ResponseEntity<ChiTietDonHangDTO> addOrderItem(@Valid @RequestBody ChiTietDonHangDTO chiTietDonHangDTO) {
        return ResponseEntity.ok(chiTietDonHangService.addOrderItem(chiTietDonHangDTO));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Update order item")
    public ResponseEntity<ChiTietDonHangDTO> updateOrderItem(@Valid @RequestBody ChiTietDonHangDTO chiTietDonHangDTO) {
        return ResponseEntity.ok(chiTietDonHangService.updateOrderItem(chiTietDonHangDTO));
    }

    @DeleteMapping("/{orderId}/{productDetailId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Delete order item")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Integer orderId, @PathVariable Integer productDetailId) {
        chiTietDonHangService.deleteOrderItem(orderId, productDetailId);
        return ResponseEntity.noContent().build();
    }
}