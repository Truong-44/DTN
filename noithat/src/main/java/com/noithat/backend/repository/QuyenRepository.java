package com.noithat.backend.repository;

import com.noithat.backend.entity.Quyen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuyenRepository extends JpaRepository<Quyen, Integer> {
    Optional<Quyen> findByTen(String ten);
}
