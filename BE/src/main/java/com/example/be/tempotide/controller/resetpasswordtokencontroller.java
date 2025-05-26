package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.resetpasswordtokendto;
import com.example.be.tempotide.service.ResetPasswordTokenService;
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
public class resetpasswordtokencontroller {

    private static final Logger logger = LoggerFactory.getLogger(resetpasswordtokencontroller.class);

    private final ResetPasswordTokenService resetPasswordTokenService;

    @Autowired
    public resetpasswordtokencontroller(ResetPasswordTokenService resetPasswordTokenService) {
        this.resetPasswordTokenService = resetPasswordTokenService;
    }

    @PostMapping
    public ResponseEntity<resetpasswordtokendto> createResetPasswordToken(@Valid @RequestBody resetpasswordtokendto resetPasswordTokenDto) {
        logger.info("API call: POST /api/resetpasswordtoken");
        resetpasswordtokendto createdResetPasswordToken = resetPasswordTokenService.createResetPasswordToken(resetPasswordTokenDto);
        return new ResponseEntity<>(createdResetPasswordToken, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<resetpasswordtokendto> getResetPasswordTokenById(@PathVariable Integer id) {
        logger.info("API call: GET /api/resetpasswordtoken/{}", id);
        resetpasswordtokendto resetPasswordTokenDto = resetPasswordTokenService.getResetPasswordTokenById(id);
        return ResponseEntity.ok(resetPasswordTokenDto);
    }

    @GetMapping
    public ResponseEntity<List<resetpasswordtokendto>> getAllResetPasswordTokens() {
        logger.info("API call: GET /api/resetpasswordtoken");
        List<resetpasswordtokendto> resetPasswordTokenList = resetPasswordTokenService.getAllResetPasswordTokens();
        return ResponseEntity.ok(resetPasswordTokenList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<resetpasswordtokendto> updateResetPasswordToken(@PathVariable Integer id, @Valid @RequestBody resetpasswordtokendto resetPasswordTokenDto) {
        logger.info("API call: PUT /api/resetpasswordtoken/{}", id);
        resetpasswordtokendto updatedResetPasswordToken = resetPasswordTokenService.updateResetPasswordToken(id, resetPasswordTokenDto);
        return ResponseEntity.ok(updatedResetPasswordToken);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResetPasswordToken(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/resetpasswordtoken/{}", id);
        resetPasswordTokenService.deleteResetPasswordToken(id);
        return ResponseEntity.noContent().build();
    }
}