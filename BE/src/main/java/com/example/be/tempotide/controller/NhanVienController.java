package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.NhanVienDTO;
import com.example.be.tempotide.service.NhanVienService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee API", description = "APIs for managing employees")
public class NhanVienController {
    private final NhanVienService nhanVienService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all active employees")
    public ResponseEntity<List<NhanVienDTO>> getAllEmployees() {
        return ResponseEntity.ok(nhanVienService.getAllActiveEmployees());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.manhanvien == #id")
    @Operation(summary = "Get employee by ID")
    public ResponseEntity<NhanVienDTO> getEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.ok(nhanVienService.getEmployeeById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new employee")
    public ResponseEntity<NhanVienDTO> createEmployee(@Valid @RequestBody NhanVienDTO nhanVienDTO) {
        return ResponseEntity.ok(nhanVienService.createEmployee(nhanVienDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an employee")
    public ResponseEntity<NhanVienDTO> updateEmployee(@PathVariable Integer id, @Valid @RequestBody NhanVienDTO nhanVienDTO) {
        return ResponseEntity.ok(nhanVienService.updateEmployee(id, nhanVienDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an employee (soft delete)")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        nhanVienService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}