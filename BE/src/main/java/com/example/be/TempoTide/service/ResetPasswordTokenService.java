package com.example.be.TempoTide.service;

import com.example.be.TempoTide.dto.ResetPasswordTokenDto;

import java.util.List;

public interface ResetPasswordTokenService {
    ResetPasswordTokenDto createResetPasswordToken(ResetPasswordTokenDto resetPasswordTokenDto);
    ResetPasswordTokenDto getResetPasswordTokenById(Integer id);
    List<ResetPasswordTokenDto> getAllResetPasswordTokens();
    ResetPasswordTokenDto updateResetPasswordToken(Integer id, ResetPasswordTokenDto resetPasswordTokenDto);
    void deleteResetPasswordToken(Integer id);
}