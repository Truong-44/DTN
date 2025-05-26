package com.example.be.tempotide.repository;

import com.example.be.tempotide.entity.lichsudonhang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface lichsudonhangrepository extends JpaRepository<lichsudonhang, Integer> {
}
