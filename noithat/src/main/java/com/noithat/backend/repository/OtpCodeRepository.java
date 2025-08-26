package com.noithat.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noithat.backend.entity.OtpCode;

public interface OtpCodeRepository extends JpaRepository<OtpCode, Integer> {
    Optional<OtpCode> findTopByReceiverAndTypeAndVerifiedFalseOrderByCreatedAtDesc(String receiver, String type);
}