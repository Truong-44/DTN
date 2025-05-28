package com.example.tempotide.controller;

import com.example.tempotide.dto.DonHangDTO;
import com.example.tempotide.service.DonHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "APIs for managing orders")
public class DonHangController {
    private final DonHangService donHangService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all orders")
    public ResponseEntity<List<DonHangDTO>> getAllOrders() {
        return ResponseEntity.ok(donHangService.getAllOrders());
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get current user's orders")
    public ResponseEntity<List<DonHangDTO>> getUserOrders() {
        return ResponseEntity.ok(donHangService.getUserOrders());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<DonHangDTO> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(donHangService.getOrderById(id));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new order from cart")
    public ResponseEntity<DonHangDTO> createOrder(@Valid @RequestBody DonHangDTO donHangDTO, @RequestParam Integer cartId) {
        return ResponseEntity.ok(donHangService.createOrder(donHangDTO, cartId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Update an order")
    public ResponseEntity<DonHangDTO> updateOrder(@PathVariable Integer id, @Valid @RequestBody DonHangDTO donHangDTO) {
        return ResponseEntity.ok(donHangService.updateOrder(id, donHangDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Cancel an order")
    public ResponseEntity<Void> cancelOrder(@PathVariable Integer id) {
        donHangService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }
}