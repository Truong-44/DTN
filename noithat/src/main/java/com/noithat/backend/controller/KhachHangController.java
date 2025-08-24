package com.noithat.backend.controller;

import com.noithat.backend.dto.KhachHangDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.KhachHangService;
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
@RequestMapping("/api/khachhang")
@RequiredArgsConstructor
@Tag(name = "KhachHang", description = "API quản lý khách hàng")
public class KhachHangController {

    private final KhachHangService khachHangService;

    @Operation(summary = "Lấy danh sách tất cả khách hàng")
    @GetMapping
    public ResponseEntity<ApiResponse<List<KhachHangDTO>>> getAll() {
        log.info("Getting all khách hàng");
        List<KhachHangDTO> result = khachHangService.getAll();
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh sách khách hàng thành công"));
    }

    @Operation(summary = "Lấy khách hàng theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KhachHangDTO>> getById(
            @Parameter(description = "ID của khách hàng") @PathVariable Integer id) {
        log.info("Getting khách hàng with id: {}", id);
        KhachHangDTO result = khachHangService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy khách hàng thành công"));
    }

    @Operation(summary = "Tìm kiếm khách hàng theo tên")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<KhachHangDTO>>> searchByName(
            @Parameter(description = "Tên khách hàng") @RequestParam String name) {
        log.info("Searching khách hàng with name: {}", name);
        List<KhachHangDTO> result = khachHangService.searchByName(name);
        return ResponseEntity.ok(ApiResponse.success(result, "Tìm kiếm khách hàng thành công"));
    }

    @Operation(summary = "Tạo mới khách hàng")
    @PostMapping
    public ResponseEntity<ApiResponse<KhachHangDTO>> create(@Valid @RequestBody KhachHangDTO dto) {
        log.info("Creating new khách hàng: {}", dto.getHoten());
        KhachHangDTO result = khachHangService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Tạo khách hàng thành công"));
    }

    @Operation(summary = "Cập nhật khách hàng")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<KhachHangDTO>> update(
            @Parameter(description = "ID của khách hàng") @PathVariable Integer id,
            @Valid @RequestBody KhachHangDTO dto) {
        log.info("Updating khách hàng with id: {}", id);
        KhachHangDTO result = khachHangService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật khách hàng thành công"));
    }

    @Operation(summary = "Xóa khách hàng")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của khách hàng") @PathVariable Integer id) {
        log.info("Deleting khách hàng with id: {}", id);
        khachHangService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa khách hàng thành công"));
    }
}
