package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hoadon")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "donhangid", nullable = false, unique = true)
    private DonHang donHang;

    @CreationTimestamp
    private LocalDateTime ngayxuathoadon;

    private String nguoixuathoadon;
    private String tenkhachhang;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String diachinguoinhan;

    private BigDecimal tongtien;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String ghichu;
}
