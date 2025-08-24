package com.noithat.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quyen")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String ten;

    @Column(length = 255)
    private String mota;
}
