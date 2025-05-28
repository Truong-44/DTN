package com.example.tempotide.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ChiTietDonHangId implements Serializable {
    private Integer donhang;
    private Integer chitietsanpham;
}