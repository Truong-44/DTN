package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.VaiTroDTO;
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
@RequestMapping("/api/user-roles")
@RequiredArgsConstructor
@Tag(name = "User-Role API", description = "APIs for managing user-role mappings")
public class NguoiDungVaiTroController {
    private final NguoiDungVaiTroService nguoiDungVaiTroService;

    @GetMapping("/user/{userId}/type/{userType}")
    @PreAuthorize("hasRole('ADMIN') or (authentication.principal.manhanvien == #userId and #userType == 'NhanVien')")
    @Operation(summary = "Get roles of a user by user ID and type")
    public ResponseEntity<List<VaiTroDTO>> getRolesByUserIdAndType(
            @PathVariable Integer userId, @PathVariable String userType) {
        return ResponseEntity.ok(nguoiDungVaiTroService.getRolesByUserIdAndType(userId, userType));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Assign a role to a user")
    public ResponseEntity<Void> assignRoleToUser(@Valid @RequestBody NguoiDungVaiTroRequestDTO requestDTO) {
        nguoiDungVaiTroService.assignRoleToUser(requestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/user/{userId}/type/{userType}/role/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove a role from a user")
    public ResponseEntity<Void> removeRoleFromUser(
            @PathVariable Integer userId, @PathVariable String userType, @PathVariable Integer roleId) {
        nguoiDungVaiTroService.removeRoleFromUser(userId, roleId, userType);
        return ResponseEntity.noContent().build();
    }
}