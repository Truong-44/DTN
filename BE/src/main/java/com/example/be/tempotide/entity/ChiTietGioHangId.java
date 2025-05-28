package com.example.be.tempotide.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ChiTietGioHangId implements Serializable {
    private Integer giohang;
    private Integer chitietsanpham;
}