package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.resetpasswordtoken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface resetpasswordtokenrepository extends JpaRepository<resetpasswordtoken, Integer> {
}