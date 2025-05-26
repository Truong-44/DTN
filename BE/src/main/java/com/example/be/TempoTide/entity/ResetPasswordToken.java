package com.example.be.TempoTide.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ResetPasswordToken")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaToken")
    private Integer maToken;

    @NotNull(message = "MaNguoiDung is required")
    @Column(name = "MaNguoiDung", nullable = false)
    private Integer maNguoiDung;

    @NotBlank(message = "Token is required")
    @Size(max = 500, message = "Token must not exceed 500 characters")
    @Column(name = "Token", nullable = false)
    private String token;

    @Column(name = "NgayTao", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    private LocalDateTime ngayTao;

    @Column(name = "NgayHetHan", nullable = false)
    private LocalDateTime ngayHetHan;

    @Column(name = "DaSuDung", columnDefinition = "BIT DEFAULT 0")
    private Boolean daSuDung;

    @ManyToOne
    @JoinColumn(name = "MaNguoiDung", insertable = false, updatable = false)
    private NhanVien nhanVien;
}