package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.QuyenDTO;
import com.example.be.tempotide.dto.VaiTroQuyenRequestDTO;
import com.example.be.tempotide.service.VaiTroQuyenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role-permissions")
@RequiredArgsConstructor
@Tag(name = "Role-Permission API", description = "APIs for managing role-permission mappings")
public class VaiTroQuyenController {
    private final VaiTroQuyenService vaiTroQuyenService;

    @GetMapping("/role/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get permissions of a role by role ID")
    public ResponseEntity<List<QuyenDTO>> getPermissionsByRoleId(@PathVariable Integer roleId) {
        return ResponseEntity.ok(vaiTroQuyenService.getPermissionsByRoleId(roleId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Assign a permission to a role")
    public ResponseEntity<Void> assignPermissionToRole(@Valid @RequestBody VaiTroQuyenRequestDTO requestDTO) {
        vaiTroQuyenService.assignPermissionToRole(requestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/role/{roleId}/permission/{permissionId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove a permission from a role")
    public ResponseEntity<Void> removePermissionFromRole(@PathVariable Integer roleId, @PathVariable Integer permissionId) {
        vaiTroQuyenService.removePermissionFromRole(roleId, permissionId);
        return ResponseEntity.noContent().build();
    }
}