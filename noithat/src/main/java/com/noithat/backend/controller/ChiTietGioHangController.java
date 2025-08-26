package com.noithat.backend.controller;

import com.noithat.backend.dto.ChiTietGioHangDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.ChiTietGioHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietgiohang")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Chi Tiết Giỏ Hàng", description = "APIs quản lý chi tiết giỏ hàng")
public class ChiTietGioHangController {

    private final ChiTietGioHangService service;

    @GetMapping("/giohang/{giohangId}")
    @Operation(summary = "Lấy chi tiết giỏ hàng theo ID giỏ hàng", description = "Trả về danh sách tất cả chi tiết giỏ hàng của một giỏ hàng")
    public ResponseEntity<ApiResponse<List<ChiTietGioHangDTO>>> getByGioHang(
            @Parameter(description = "ID của giỏ hàng") @PathVariable Integer giohangId) {
        log.info("Getting chi tiet gio hang by gio hang ID: {}", giohangId);
        List<ChiTietGioHangDTO> result = service.getByGioHangId(giohangId);
        return ResponseEntity.ok(ApiResponse.<List<ChiTietGioHangDTO>>builder()
                .success(true)
                .message("Lấy chi tiết giỏ hàng thành công")
                .data(result)
                .build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết giỏ hàng theo ID", description = "Trả về thông tin chi tiết của một mục trong giỏ hàng")
    public ResponseEntity<ApiResponse<ChiTietGioHangDTO>> getById(
            @Parameter(description = "ID của chi tiết giỏ hàng") @PathVariable Integer id) {
        log.info("Getting chi tiet gio hang by ID: {}", id);
        ChiTietGioHangDTO result = service.getById(id);
        return ResponseEntity.ok(ApiResponse.<ChiTietGioHangDTO>builder()
                .success(true)
                .message("Lấy chi tiết giỏ hàng thành công")
                .data(result)
                .build());
    }

    @PostMapping
    @Operation(summary = "Thêm sản phẩm vào giỏ hàng", description = "Tạo mới một chi tiết giỏ hàng (thêm sản phẩm vào giỏ)")
    public ResponseEntity<ApiResponse<ChiTietGioHangDTO>> create(
            @Parameter(description = "Thông tin chi tiết giỏ hàng") @Valid @RequestBody ChiTietGioHangDTO dto) {
        log.info("Creating new chi tiet gio hang");
        ChiTietGioHangDTO result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ChiTietGioHangDTO>builder()
                        .success(true)
                        .message("Thêm sản phẩm vào giỏ hàng thành công")
                        .data(result)
                        .build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật chi tiết giỏ hàng", description = "Cập nhật thông tin chi tiết giỏ hàng (số lượng, etc.)")
    public ResponseEntity<ApiResponse<ChiTietGioHangDTO>> update(
            @Parameter(description = "ID của chi tiết giỏ hàng") @PathVariable Integer id,
            @Parameter(description = "Thông tin cập nhật") @Valid @RequestBody ChiTietGioHangDTO dto) {
        log.info("Updating chi tiet gio hang with ID: {}", id);
        ChiTietGioHangDTO result = service.update(id, dto);
        return ResponseEntity.ok(ApiResponse.<ChiTietGioHangDTO>builder()
                .success(true)
                .message("Cập nhật chi tiết giỏ hàng thành công")
                .data(result)
                .build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa sản phẩm khỏi giỏ hàng", description = "Xóa một chi tiết giỏ hàng")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của chi tiết giỏ hàng") @PathVariable Integer id) {
        log.info("Deleting chi tiet gio hang with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Xóa sản phẩm khỏi giỏ hàng thành công")
                .build());
    }

    @PutMapping("/{id}/soluong")
    @Operation(summary = "Cập nhật số lượng sản phẩm", description = "Cập nhật số lượng của một sản phẩm trong giỏ hàng")
    public ResponseEntity<ApiResponse<ChiTietGioHangDTO>> updateSoLuong(
            @Parameter(description = "ID của chi tiết giỏ hàng") @PathVariable Integer id,
            @Parameter(description = "Số lượng mới") @RequestParam Integer soLuong) {
        log.info("Updating so luong for chi tiet gio hang ID: {} to {}", id, soLuong);
        ChiTietGioHangDTO result = service.updateSoLuong(id, soLuong);
        return ResponseEntity.ok(ApiResponse.<ChiTietGioHangDTO>builder()
                .success(true)
                .message("Cập nhật số lượng thành công")
                .data(result)
                .build());
    }

    @DeleteMapping("/giohang/{giohangId}/clear")
    @Operation(summary = "Xóa tất cả sản phẩm trong giỏ hàng", description = "Xóa toàn bộ chi tiết của một giỏ hàng")
    public ResponseEntity<ApiResponse<Void>> clearGioHang(
            @Parameter(description = "ID của giỏ hàng") @PathVariable Integer giohangId) {
        log.info("Clearing all items from gio hang ID: {}", giohangId);
        service.clearGioHang(giohangId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Xóa tất cả sản phẩm trong giỏ hàng thành công")
                .build());
    }
}
