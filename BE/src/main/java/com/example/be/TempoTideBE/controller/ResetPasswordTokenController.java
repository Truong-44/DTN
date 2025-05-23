package com.example.be.TempoTideBE.controller;

import com.example.be.dto.ResetPasswordTokenDTO;
import com.example.be.service.ResetPasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resetpasswordtoken")
@RequiredArgsConstructor
public class ResetPasswordTokenController {

    private final ResetPasswordTokenService resetPasswordTokenService;

    @GetMapping
    public ResponseEntity<List<ResetPasswordTokenDTO>> getAllResetPasswordToken() {
        return ResponseEntity.ok(resetPasswordTokenService.getAllResetPasswordToken());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResetPasswordTokenDTO> getResetPasswordTokenById(@PathVariable Integer id) {
        return ResponseEntity.ok(resetPasswordTokenService.getResetPasswordTokenById(id));
    }

    @PostMapping
    public ResponseEntity<ResetPasswordTokenDTO> createResetPasswordToken(@RequestBody ResetPasswordTokenDTO dto) {
        return ResponseEntity.ok(resetPasswordTokenService.createResetPasswordToken(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResetPasswordTokenDTO> updateResetPasswordToken(@PathVariable Integer id, @RequestBody ResetPasswordTokenDTO dto) {
        return ResponseEntity.ok(resetPasswordTokenService.updateResetPasswordToken(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResetPasswordToken(@PathVariable Integer id) {
        resetPasswordTokenService.deleteResetPasswordToken(id);
        return ResponseEntity.noContent().build();
    }
}