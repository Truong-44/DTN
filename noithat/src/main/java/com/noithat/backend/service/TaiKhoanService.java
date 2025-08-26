package com.noithat.backend.service;

import com.noithat.backend.dto.TaiKhoanDTO;
import com.noithat.backend.dto.request.ChangePasswordRequest;
import com.noithat.backend.dto.request.LoginRequest;
import com.noithat.backend.dto.request.ResetPasswordRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TaiKhoanService {
    TaiKhoanDTO create(TaiKhoanDTO dto);
    TaiKhoanDTO update(Integer id, TaiKhoanDTO dto);
    void delete(Integer id);
    TaiKhoanDTO getById(Integer id);
    TaiKhoanDTO getByUsername(String username);
    List<TaiKhoanDTO> getAll();
    Page<TaiKhoanDTO> getAllPaged(Pageable pageable);
    
    // Authentication methods
    TaiKhoanDTO register(TaiKhoanDTO dto);
    Map<String, Object> login(String username, String password);
    void logout(String token);
    void changePassword(String username, String oldPassword, String newPassword);
    void resetPassword(String email);
    TaiKhoanDTO updateStatus(Integer id, Boolean active);
    boolean validateToken(String token);
}
