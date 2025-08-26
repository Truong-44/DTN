package com.noithat.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "otpcode")
@Data
public class OtpCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer taikhoanid; // nullable
    private String receiver;    // email hoặc số điện thoại
    private String otp;
    private String type;        // "EMAIL" hoặc "SMS"
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private Boolean verified = false;
}