package com.noithat.backend.controller;

import com.noithat.backend.dto.DonHangDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.DonHangService;
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
@RequestMapping("/api/donhang")
@RequiredArgsConstructor
@Tag(name = "DonHang", description = "API quản lý đơn hàng")
public class DonHangController {

    private final DonHangService service;

    @Operation(summary = "Lấy danh sách tất cả đơn hàng")
    @GetMapping
    public ResponseEntity<ApiResponse<List<DonHangDTO>>> getAll() {
        log.info("Getting all đơn hàng");
        List<DonHangDTO> result = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh sách đơn hàng thành công"));
    }

    @Operation(summary = "Lấy đơn hàng theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DonHangDTO>> getById(
            @Parameter(description = "ID của đơn hàng") @PathVariable Integer id) {
        log.info("Getting đơn hàng with id: {}", id);
        DonHangDTO result = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy đơn hàng thành công"));
    }

    @Operation(summary = "Lấy đơn hàng theo khách hàng")
    @GetMapping("/khachhang/{khachHangId}")
    public ResponseEntity<ApiResponse<List<DonHangDTO>>> getByKhachHang(
            @Parameter(description = "ID của khách hàng") @PathVariable Integer khachHangId) {
        log.info("Getting đơn hàng by khách hàng id: {}", khachHangId);
        List<DonHangDTO> result = service.getByKhachHangId(khachHangId);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy đơn hàng theo khách hàng thành công"));
    }

    @Operation(summary = "Lấy đơn hàng theo trạng thái")
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<DonHangDTO>>> getByStatus(
            @Parameter(description = "Trạng thái đơn hàng") @PathVariable String status) {
        log.info("Getting đơn hàng by status: {}", status);
        List<DonHangDTO> result = service.getByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy đơn hàng theo trạng thái thành công"));
    }

    @Operation(summary = "Tạo mới đơn hàng")
    @PostMapping
    public ResponseEntity<ApiResponse<DonHangDTO>> create(@Valid @RequestBody DonHangDTO dto) {
        log.info("Creating new đơn hàng for khách hàng: {}", dto.getKhachhang());
        DonHangDTO result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Tạo đơn hàng thành công"));
    }

    @Operation(summary = "Cập nhật đơn hàng")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DonHangDTO>> update(
            @Parameter(description = "ID của đơn hàng") @PathVariable Integer id,
            @Valid @RequestBody DonHangDTO dto) {
        log.info("Updating đơn hàng with id: {}", id);
        DonHangDTO result = service.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật đơn hàng thành công"));
    }

    @Operation(summary = "Cập nhật trạng thái đơn hàng")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<DonHangDTO>> updateStatus(
            @Parameter(description = "ID của đơn hàng") @PathVariable Integer id,
            @Parameter(description = "Trạng thái mới") @RequestParam String status) {
        log.info("Updating status for đơn hàng id: {} to: {}", id, status);
        DonHangDTO result = service.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật trạng thái đơn hàng thành công"));
    }

    @Operation(summary = "Hủy đơn hàng")
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<DonHangDTO>> cancel(
            @Parameter(description = "ID của đơn hàng") @PathVariable Integer id,
            @Parameter(description = "Lý do hủy") @RequestParam(required = false) String reason) {
        log.info("Cancelling đơn hàng with id: {}, reason: {}", id, reason);
        DonHangDTO result = service.cancel(id, reason);
        return ResponseEntity.ok(ApiResponse.success(result, "Hủy đơn hàng thành công"));
    }

    @Operation(summary = "Xóa đơn hàng")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của đơn hàng") @PathVariable Integer id) {
        log.info("Deleting đơn hàng with id: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa đơn hàng thành công"));
    }
}
