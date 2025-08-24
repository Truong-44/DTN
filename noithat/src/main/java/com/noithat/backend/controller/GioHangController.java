package com.noithat.backend.controller;

import com.noithat.backend.dto.GioHangDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.GioHangService;
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
@RequestMapping("/api/giohang")
@RequiredArgsConstructor
@Tag(name = "GioHang", description = "API quản lý giỏ hàng")
public class GioHangController {

    private final GioHangService service;

    @Operation(summary = "Lấy danh sách tất cả giỏ hàng")
    @GetMapping
    public ResponseEntity<ApiResponse<List<GioHangDTO>>> getAll() {
        log.info("Getting all giỏ hàng");
        List<GioHangDTO> result = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh sách giỏ hàng thành công"));
    }

    @Operation(summary = "Lấy giỏ hàng theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GioHangDTO>> getById(
            @Parameter(description = "ID của giỏ hàng") @PathVariable Integer id) {
        log.info("Getting giỏ hàng with id: {}", id);
        GioHangDTO result = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy giỏ hàng thành công"));
    }

    @Operation(summary = "Lấy giỏ hàng theo khách hàng")
    @GetMapping("/khachhang/{khachHangId}")
    public ResponseEntity<ApiResponse<GioHangDTO>> getByKhachHangId(
            @Parameter(description = "ID của khách hàng") @PathVariable Integer khachHangId) {
        log.info("Getting giỏ hàng by khách hàng id: {}", khachHangId);
        GioHangDTO result = service.getByKhachHangId(khachHangId);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy giỏ hàng theo khách hàng thành công"));
    }

    @Operation(summary = "Tạo mới giỏ hàng")
    @PostMapping
    public ResponseEntity<ApiResponse<GioHangDTO>> create(@Valid @RequestBody GioHangDTO dto) {
        log.info("Creating new giỏ hàng for khách hàng: {}", dto.getKhachhang());
        GioHangDTO result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Tạo giỏ hàng thành công"));
    }

    @Operation(summary = "Cập nhật giỏ hàng")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GioHangDTO>> update(
            @Parameter(description = "ID của giỏ hàng") @PathVariable Integer id,
            @Valid @RequestBody GioHangDTO dto) {
        log.info("Updating giỏ hàng with id: {}", id);
        GioHangDTO result = service.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật giỏ hàng thành công"));
    }

    @Operation(summary = "Xóa tất cả sản phẩm trong giỏ hàng")
    @PostMapping("/{id}/clear")
    public ResponseEntity<ApiResponse<Void>> clearGioHang(
            @Parameter(description = "ID của giỏ hàng") @PathVariable Integer id) {
        log.info("Clearing giỏ hàng with id: {}", id);
        service.clearGioHang(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa tất cả sản phẩm trong giỏ hàng thành công"));
    }

    @Operation(summary = "Xóa giỏ hàng theo khách hàng")
    @PostMapping("/khachhang/{khachHangId}/clear")
    public ResponseEntity<ApiResponse<Void>> clearGioHangByKhachHang(
            @Parameter(description = "ID của khách hàng") @PathVariable Integer khachHangId) {
        log.info("Clearing giỏ hàng by khách hàng id: {}", khachHangId);
        service.clearGioHangByKhachHang(khachHangId);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa giỏ hàng theo khách hàng thành công"));
    }

    @Operation(summary = "Xóa giỏ hàng")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của giỏ hàng") @PathVariable Integer id) {
        log.info("Deleting giỏ hàng with id: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa giỏ hàng thành công"));
    }
}
