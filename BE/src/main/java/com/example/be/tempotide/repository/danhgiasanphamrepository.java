package com.example.be.tempotide.repository;
import com.example.be.tempotide.entity.danhgiasanpham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface danhgiasanphamrepository extends JpaRepository<danhgiasanpham, Integer> {
}