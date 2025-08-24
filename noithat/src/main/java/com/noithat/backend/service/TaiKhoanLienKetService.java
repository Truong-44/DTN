package com.noithat.backend.service;

import com.noithat.backend.dto.TaiKhoanLienKetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaiKhoanLienKetService {
    List<TaiKhoanLienKetDTO> getAll();
    TaiKhoanLienKetDTO getById(Integer id);
    List<TaiKhoanLienKetDTO> getByTaiKhoanId(Integer taiKhoanId);
    List<TaiKhoanLienKetDTO> getByProvider(String provider);
    TaiKhoanLienKetDTO create(TaiKhoanLienKetDTO dto);
    TaiKhoanLienKetDTO update(Integer id, TaiKhoanLienKetDTO dto);
    void delete(Integer id);
    void unlinkByProvider(Integer taiKhoanId, String provider);
    
    // Legacy methods for compatibility
    List<TaiKhoanLienKetDTO> findAll();
    TaiKhoanLienKetDTO findById(Integer id);
    TaiKhoanLienKetDTO save(TaiKhoanLienKetDTO dto);
    void deleteById(Integer id);
}
