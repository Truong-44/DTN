package com.noithat.backend.service;

public interface OtpCodeService {
    void sendOtp(String receiver, String type); // type: "EMAIL" hoặc "SMS"
    boolean verifyOtp(String receiver, String type, String otp);
}