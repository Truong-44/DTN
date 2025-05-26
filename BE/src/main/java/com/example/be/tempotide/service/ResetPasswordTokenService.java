package com.example.be.tempotide.service;

import com.example.be.tempotide.dto.resetpasswordtokendto;

import java.util.List;

public interface resetpasswordtokenservice {
    resetpasswordtokendto createResetPasswordToken(resetpasswordtokendto resetPasswordTokenDto);
    resetpasswordtokendto getResetPasswordTokenById(Integer id);
    List<resetpasswordtokendto> getAllResetPasswordTokens();
    resetpasswordtokendto updateResetPasswordToken(Integer id, resetpasswordtokendto resetPasswordTokenDto);
    void deleteResetPasswordToken(Integer id);
}