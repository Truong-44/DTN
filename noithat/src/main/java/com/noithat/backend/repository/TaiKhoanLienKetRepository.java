package com.noithat.backend.repository;

import com.noithat.backend.entity.TaiKhoanLienKet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaiKhoanLienKetRepository extends JpaRepository<TaiKhoanLienKet, Integer> {
    Optional<TaiKhoanLienKet> findByLoaidangnhapAndGiatridangnhap(String loaidangnhap, String giatridangnhap);
    List<TaiKhoanLienKet> findByLoaidangnhap(String loaidangnhap);
    List<TaiKhoanLienKet> findByTaikhoan_Id(Integer taikhoandId);
    void deleteByTaikhoan_IdAndLoaidangnhap(Integer taikhoanId, String loaidangnhap);
}
