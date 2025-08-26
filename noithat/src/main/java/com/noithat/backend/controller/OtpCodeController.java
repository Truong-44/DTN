package com.noithat.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.OtpCodeService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpCodeController {

    private final OtpCodeService otpCodeService;

    @Operation(summary = "Gửi OTP tới email hoặc số điện thoại")
    @PostMapping("/send")
    public ResponseEntity<ApiResponse<Void>> sendOtp(
            @RequestParam String receiver,
            @RequestParam String type
    ) {
        otpCodeService.sendOtp(receiver, type);
        return ResponseEntity.ok(ApiResponse.success(null, "OTP đã được gửi"));
    }

    @Operation(summary = "Xác thực OTP")
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Boolean>> verifyOtp(
            @RequestParam String receiver,
            @RequestParam String type,
            @RequestParam String otp
    ) {
        boolean result = otpCodeService.verifyOtp(receiver, type, otp);
        return ResponseEntity.ok(ApiResponse.success(result, result ? "Xác thực thành công" : "OTP không hợp lệ hoặc đã hết hạn"));
    }
}