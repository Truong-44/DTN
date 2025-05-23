package com.example.be.TempoTideBE.repository;

import com.example.be.TempoTideBE.entity.KhoHang;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhoHangRepository extends CrudRepository<KhoHang, Integer> {
}
