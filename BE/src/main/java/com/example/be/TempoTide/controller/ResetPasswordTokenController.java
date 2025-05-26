package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.ResetPasswordTokenDto;
import com.example.be.TempoTide.service.ResetPasswordTokenService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resetpasswordtoken")
public class ResetPasswordTokenController {

    private static final Logger logger = LoggerFactory.getLogger(ResetPasswordTokenController.class);

    private final ResetPasswordTokenService resetPasswordTokenService;

    @Autowired
    public ResetPasswordTokenController(ResetPasswordTokenService resetPasswordTokenService) {
        this.resetPasswordTokenService = resetPasswordTokenService;
    }

    @PostMapping
    public ResponseEntity<ResetPasswordTokenDto> createResetPasswordToken(@Valid @RequestBody ResetPasswordTokenDto resetPasswordTokenDto) {
        logger.info("API call: POST /api/resetpasswordtoken");
        ResetPasswordTokenDto createdResetPasswordToken = resetPasswordTokenService.createResetPasswordToken(resetPasswordTokenDto);
        return new ResponseEntity<>(createdResetPasswordToken, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResetPasswordTokenDto> getResetPasswordTokenById(@PathVariable Integer id) {
        logger.info("API call: GET /api/resetpasswordtoken/{}", id);
        ResetPasswordTokenDto resetPasswordTokenDto = resetPasswordTokenService.getResetPasswordTokenById(id);
        return ResponseEntity.ok(resetPasswordTokenDto);
    }

    @GetMapping
    public ResponseEntity<List<ResetPasswordTokenDto>> getAllResetPasswordTokens() {
        logger.info("API call: GET /api/resetpasswordtoken");
        List<ResetPasswordTokenDto> resetPasswordTokenList = resetPasswordTokenService.getAllResetPasswordTokens();
        return ResponseEntity.ok(resetPasswordTokenList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResetPasswordTokenDto> updateResetPasswordToken(@PathVariable Integer id, @Valid @RequestBody ResetPasswordTokenDto resetPasswordTokenDto) {
        logger.info("API call: PUT /api/resetpasswordtoken/{}", id);
        ResetPasswordTokenDto updatedResetPasswordToken = resetPasswordTokenService.updateResetPasswordToken(id, resetPasswordTokenDto);
        return ResponseEntity.ok(updatedResetPasswordToken);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResetPasswordToken(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/resetpasswordtoken/{}", id);
        resetPasswordTokenService.deleteResetPasswordToken(id);
        return ResponseEntity.noContent().build();
    }
}