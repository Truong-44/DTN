package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.KhachHangDTO;
import com.example.be.tempotide.service.KhachHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customer API", description = "APIs for managing customers")
public class KhachHangController {
    private final KhachHangService khachHangService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all active customers")
    public ResponseEntity<List<KhachHangDTO>> getAllCustomers() {
        return ResponseEntity.ok(khachHangService.getAllActiveCustomers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<KhachHangDTO> getCustomerById(@PathVariable Integer id) {
        return ResponseEntity.ok(khachHangService.getCustomerById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Create a new customer")
    public ResponseEntity<KhachHangDTO> createCustomer(@Valid @RequestBody KhachHangDTO khachHangDTO) {
        return ResponseEntity.ok(khachHangService.createCustomer(khachHangDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a customer")
    public ResponseEntity<KhachHangDTO> updateCustomer(@PathVariable Integer id, @Valid @RequestBody KhachHangDTO khachHangDTO) {
        return ResponseEntity.ok(khachHangService.updateCustomer(id, khachHangDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a customer (soft delete)")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        khachHangService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}