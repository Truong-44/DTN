package com.noithat.backend.controller;

import com.noithat.backend.dto.TaiKhoanDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.dto.request.LoginRequest;
import com.noithat.backend.dto.request.ChangePasswordRequest;
import com.noithat.backend.dto.request.ResetPasswordRequest;
import com.noithat.backend.service.TaiKhoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/taikhoan")
@RequiredArgsConstructor
@Tag(name = "TaiKhoan", description = "API quản lý tài khoản và xác thực")
public class TaiKhoanController {

    private final TaiKhoanService taiKhoanService;

    @Operation(summary = "Lấy danh sách tất cả tài khoản")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TaiKhoanDTO>>> getAll() {
        log.info("Getting all tài khoản");
        List<TaiKhoanDTO> result = taiKhoanService.getAll();
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh sách tài khoản thành công"));
    }

    @Operation(summary = "Lấy tài khoản theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaiKhoanDTO>> getById(
            @Parameter(description = "ID của tài khoản") @PathVariable Integer id) {
        log.info("Getting tài khoản with id: {}", id);
        TaiKhoanDTO result = taiKhoanService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy tài khoản thành công"));
    }

    @Operation(summary = "Lấy tài khoản theo username")
    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse<TaiKhoanDTO>> getByUsername(
            @Parameter(description = "Tên đăng nhập") @PathVariable String username) {
        log.info("Getting tài khoản with username: {}", username);
        TaiKhoanDTO result = taiKhoanService.getByUsername(username);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy tài khoản theo username thành công"));
    }

    @Operation(summary = "Đăng ký tài khoản mới")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<TaiKhoanDTO>> register(@Valid @RequestBody TaiKhoanDTO dto) {
        log.info("Registering new account with username: {}", dto.getUsername());
        TaiKhoanDTO result = taiKhoanService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Đăng ký tài khoản thành công"));
    }

    @Operation(summary = "Đăng nhập")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login attempt for username: {}", loginRequest.getUsername());
        Map<String, Object> result = taiKhoanService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(ApiResponse.success(result, "Đăng nhập thành công"));
    }

    @Operation(summary = "Đăng xuất")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(
            @Parameter(description = "Bearer token") @RequestHeader("Authorization") String token) {
        log.info("Logout request");
        taiKhoanService.logout(token);
        return ResponseEntity.ok(ApiResponse.success("OK", "Đăng xuất thành công"));
    }

    @Operation(summary = "Đổi mật khẩu")
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @Valid @RequestBody ChangePasswordRequest passwordRequest,
            @Parameter(description = "Bearer token") @RequestHeader("Authorization") String token) {
        log.info("Change password request");
        taiKhoanService.changePassword(token, passwordRequest.getOldPassword(), passwordRequest.getNewPassword());
        return ResponseEntity.ok(ApiResponse.success("OK", "Đổi mật khẩu thành công"));
    }

    @Operation(summary = "Reset mật khẩu")
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@Valid @RequestBody ResetPasswordRequest resetRequest) {
        log.info("Reset password request for email: {}", resetRequest.getEmail());
        taiKhoanService.resetPassword(resetRequest.getEmail());
        return ResponseEntity.ok(ApiResponse.success("OK", "Mật khẩu mới đã được gửi qua email"));
    }

    @Operation(summary = "Tạo tài khoản mới (admin)")
    @PostMapping
    public ResponseEntity<ApiResponse<TaiKhoanDTO>> create(@Valid @RequestBody TaiKhoanDTO dto) {
        log.info("Creating new tài khoản with username: {}", dto.getUsername());
        TaiKhoanDTO result = taiKhoanService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Tạo tài khoản thành công"));
    }

    @Operation(summary = "Cập nhật tài khoản")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaiKhoanDTO>> update(
            @Parameter(description = "ID của tài khoản") @PathVariable Integer id,
            @Valid @RequestBody TaiKhoanDTO dto) {
        log.info("Updating tài khoản with id: {}", id);
        TaiKhoanDTO result = taiKhoanService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật tài khoản thành công"));
    }

    @Operation(summary = "Khóa/Mở khóa tài khoản")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<TaiKhoanDTO>> updateStatus(
            @Parameter(description = "ID của tài khoản") @PathVariable Integer id,
            @Parameter(description = "Trạng thái tài khoản") @RequestParam Boolean active) {
        log.info("Updating status for tài khoản id: {} to: {}", id, active);
        TaiKhoanDTO result = taiKhoanService.updateStatus(id, active);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật trạng thái tài khoản thành công"));
    }

    @Operation(summary = "Xóa tài khoản")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của tài khoản") @PathVariable Integer id) {
        log.info("Deleting tài khoản with id: {}", id);
        taiKhoanService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa tài khoản thành công"));
    }

    @Operation(summary = "Xác thực token")
    @PostMapping("/validate-token")
    public ResponseEntity<ApiResponse<Map<String, Object>>> validateToken(
            @Parameter(description = "Bearer token") @RequestHeader("Authorization") String token) {
        log.info("Token validation request");
        boolean isValid = taiKhoanService.validateToken(token);
        Map<String, Object> result = new java.util.HashMap<>();
        result.put("valid", isValid);
        return ResponseEntity.ok(ApiResponse.success(result, "Token hợp lệ"));
    }
}
