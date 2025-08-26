package com.noithat.backend.controller;

import com.noithat.backend.dto.TaiKhoanLienKetDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.TaiKhoanLienKetService;
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

@Slf4j
@RestController
@RequestMapping("/api/taikhoanlienket")
@RequiredArgsConstructor
@Tag(name = "TaiKhoanLienKet", description = "API quản lý tài khoản liên kết")
public class TaiKhoanLienKetController {

    private final TaiKhoanLienKetService service;

    @Operation(summary = "Lấy danh sách tất cả tài khoản liên kết")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TaiKhoanLienKetDTO>>> getAll() {
        log.info("Getting all tài khoản liên kết");
        List<TaiKhoanLienKetDTO> result = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh sách tài khoản liên kết thành công"));
    }

    @Operation(summary = "Lấy tài khoản liên kết theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaiKhoanLienKetDTO>> getById(
            @Parameter(description = "ID của tài khoản liên kết") @PathVariable Integer id) {
        log.info("Getting tài khoản liên kết with id: {}", id);
        TaiKhoanLienKetDTO result = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy tài khoản liên kết thành công"));
    }

    @Operation(summary = "Lấy tài khoản liên kết theo tài khoản ID")
    @GetMapping("/taikhoan/{taiKhoanId}")
    public ResponseEntity<ApiResponse<List<TaiKhoanLienKetDTO>>> getByTaiKhoanId(
            @Parameter(description = "ID của tài khoản") @PathVariable Integer taiKhoanId) {
        log.info("Getting tài khoản liên kết by tài khoản id: {}", taiKhoanId);
        List<TaiKhoanLienKetDTO> result = service.getByTaiKhoanId(taiKhoanId);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh sách tài khoản liên kết theo tài khoản thành công"));
    }

    @Operation(summary = "Lấy tài khoản liên kết theo loại provider")
    @GetMapping("/provider/{provider}")
    public ResponseEntity<ApiResponse<List<TaiKhoanLienKetDTO>>> getByProvider(
            @Parameter(description = "Loại provider (google, facebook, etc.)") @PathVariable String provider) {
        log.info("Getting tài khoản liên kết by provider: {}", provider);
        List<TaiKhoanLienKetDTO> result = service.getByProvider(provider);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh sách tài khoản liên kết theo provider thành công"));
    }

    @Operation(summary = "Tạo mới tài khoản liên kết")
    @PostMapping
    public ResponseEntity<ApiResponse<TaiKhoanLienKetDTO>> create(
            @Valid @RequestBody TaiKhoanLienKetDTO dto) {
        log.info("Creating new tài khoản liên kết for provider: {}", dto.getProvider());
        TaiKhoanLienKetDTO result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Tạo tài khoản liên kết thành công"));
    }

    @Operation(summary = "Cập nhật tài khoản liên kết")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaiKhoanLienKetDTO>> update(
            @Parameter(description = "ID của tài khoản liên kết") @PathVariable Integer id,
            @Valid @RequestBody TaiKhoanLienKetDTO dto) {
        log.info("Updating tài khoản liên kết with id: {}", id);
        TaiKhoanLienKetDTO result = service.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật tài khoản liên kết thành công"));
    }

    @Operation(summary = "Xóa tài khoản liên kết")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của tài khoản liên kết") @PathVariable Integer id) {
        log.info("Deleting tài khoản liên kết with id: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa tài khoản liên kết thành công"));
    }

    @Operation(summary = "Hủy liên kết tài khoản theo provider")
    @DeleteMapping("/taikhoan/{taiKhoanId}/provider/{provider}")
    public ResponseEntity<ApiResponse<Void>> unlinkByProvider(
            @Parameter(description = "ID của tài khoản") @PathVariable Integer taiKhoanId,
            @Parameter(description = "Loại provider") @PathVariable String provider) {
        log.info("Unlinking tài khoản {} from provider: {}", taiKhoanId, provider);
        service.unlinkByProvider(taiKhoanId, provider);
        return ResponseEntity.ok(ApiResponse.success(null, "Hủy liên kết tài khoản thành công"));
    }
}
