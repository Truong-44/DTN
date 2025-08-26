package com.noithat.backend.service.impl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.noithat.backend.entity.OtpCode;
import com.noithat.backend.repository.OtpCodeRepository;
import com.noithat.backend.service.OtpCodeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpCodeServiceImpl implements OtpCodeService {

    private final OtpCodeRepository otpCodeRepository;

    @Override
    public void sendOtp(String receiver, String type) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000)); // 6 số
        OtpCode code = new OtpCode();
        code.setReceiver(receiver);
        code.setType(type.toUpperCase());
        code.setOtp(otp);
        code.setCreatedAt(LocalDateTime.now());
        code.setExpiredAt(LocalDateTime.now().plusMinutes(5));
        code.setVerified(false);
        otpCodeRepository.save(code);

        // Gửi OTP qua email hoặc SMS (giả lập)
        if ("EMAIL".equalsIgnoreCase(type)) {
            System.out.println("Gửi OTP " + otp + " tới email: " + receiver);
            // TODO: Tích hợp gửi email thực tế
        } else {
            System.out.println("Gửi OTP " + otp + " tới số điện thoại: " + receiver);
            // TODO: Tích hợp gửi SMS thực tế
        }
    }

    @Override
    public boolean verifyOtp(String receiver, String type, String otp) {
        var codeOpt = otpCodeRepository.findTopByReceiverAndTypeAndVerifiedFalseOrderByCreatedAtDesc(receiver, type.toUpperCase());
        if (codeOpt.isEmpty()) return false;
        OtpCode code = codeOpt.get();
        if (code.getExpiredAt().isBefore(LocalDateTime.now())) return false;
        if (!code.getOtp().equals(otp)) return false;
        code.setVerified(true);
        otpCodeRepository.save(code);
        return true;
    }
}