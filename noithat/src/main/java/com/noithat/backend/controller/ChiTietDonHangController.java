package com.noithat.backend.controller;

import com.noithat.backend.dto.ChiTietDonHangDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.ChiTietDonHangService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/chitietdonhang")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Chi Tiết Đơn Hàng", description = "APIs quản lý chi tiết đơn hàng")
public class ChiTietDonHangController {

    private final ChiTietDonHangService service;

    @GetMapping("/donhang/{donhangId}")
    @Operation(summary = "Lấy chi tiết đơn hàng theo ID đơn hàng", description = "Trả về danh sách tất cả chi tiết của một đơn hàng")
    public ResponseEntity<ApiResponse<List<ChiTietDonHangDTO>>> getByDonHangId(
            @Parameter(description = "ID của đơn hàng") @PathVariable Integer donhangId) {
        log.info("Getting chi tiet don hang by don hang ID: {}", donhangId);
        List<ChiTietDonHangDTO> result = service.getByDonHangId(donhangId);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy chi tiết đơn hàng thành công"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết đơn hàng theo ID", description = "Trả về thông tin chi tiết của một mục trong đơn hàng")
    public ResponseEntity<ApiResponse<ChiTietDonHangDTO>> getById(
            @Parameter(description = "ID của chi tiết đơn hàng") @PathVariable Integer id) {
        log.info("Getting chi tiet don hang by ID: {}", id);
        ChiTietDonHangDTO result = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy chi tiết đơn hàng thành công"));
    }

    @PostMapping
    @Operation(summary = "Tạo chi tiết đơn hàng", description = "Tạo mới một chi tiết đơn hàng")
    public ResponseEntity<ApiResponse<ChiTietDonHangDTO>> create(
            @Parameter(description = "Thông tin chi tiết đơn hàng") @Valid @RequestBody ChiTietDonHangDTO dto) {
        log.info("Creating new chi tiet don hang");
        ChiTietDonHangDTO result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Tạo chi tiết đơn hàng thành công"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật chi tiết đơn hàng", description = "Cập nhật thông tin chi tiết đơn hàng")
    public ResponseEntity<ApiResponse<ChiTietDonHangDTO>> update(
            @Parameter(description = "ID của chi tiết đơn hàng") @PathVariable Integer id,
            @Parameter(description = "Thông tin cập nhật") @Valid @RequestBody ChiTietDonHangDTO dto) {
        log.info("Updating chi tiet don hang with ID: {}", id);
        ChiTietDonHangDTO result = service.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật chi tiết đơn hàng thành công"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa chi tiết đơn hàng", description = "Xóa một chi tiết đơn hàng")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của chi tiết đơn hàng") @PathVariable Integer id) {
        log.info("Deleting chi tiet don hang with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa chi tiết đơn hàng thành công"));
    }

    @GetMapping("/donhang/{donhangId}/total")
    @Operation(summary = "Tính tổng tiền đơn hàng", description = "Tính tổng giá trị của tất cả chi tiết trong đơn hàng")
    public ResponseEntity<ApiResponse<BigDecimal>> calculateTotal(
            @Parameter(description = "ID của đơn hàng") @PathVariable Integer donhangId) {
        log.info("Calculating total for don hang ID: {}", donhangId);
        BigDecimal total = service.calculateTotal(donhangId);
        return ResponseEntity.ok(ApiResponse.success(total, "Tính tổng tiền đơn hàng thành công"));
    }

    @GetMapping("/donhang/{donhangId}/count")
    @Operation(summary = "Đếm số lượng sản phẩm", description = "Đếm tổng số lượng sản phẩm trong đơn hàng")
    public ResponseEntity<ApiResponse<Integer>> countItems(
            @Parameter(description = "ID của đơn hàng") @PathVariable Integer donhangId) {
        log.info("Counting items for don hang ID: {}", donhangId);
        Integer count = service.countItems(donhangId);
        return ResponseEntity.ok(ApiResponse.success(count, "Đếm số lượng sản phẩm thành công"));
    }
}
