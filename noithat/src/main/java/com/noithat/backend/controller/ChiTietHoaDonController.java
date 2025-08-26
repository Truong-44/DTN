package com.noithat.backend.controller;

import com.noithat.backend.dto.ChiTietHoaDonDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.ChiTietHoaDonService;
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
@RequestMapping("/api/chitiethoadon")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Chi Tiết Hóa Đơn", description = "APIs quản lý chi tiết hóa đơn")
public class ChiTietHoaDonController {

    private final ChiTietHoaDonService service;

    @GetMapping("/hoadon/{hoadonId}")
    @Operation(summary = "Lấy chi tiết hóa đơn theo ID hóa đơn", description = "Trả về danh sách tất cả chi tiết của một hóa đơn")
    public ResponseEntity<ApiResponse<List<ChiTietHoaDonDTO>>> getByHoaDonId(
            @Parameter(description = "ID của hóa đơn") @PathVariable Integer hoadonId) {
        log.info("Getting chi tiet hoa don by hoa don ID: {}", hoadonId);
        List<ChiTietHoaDonDTO> result = service.getByHoaDonId(hoadonId);
        return ResponseEntity.ok(ApiResponse.<List<ChiTietHoaDonDTO>>builder()
                .success(true)
                .message("Lấy chi tiết hóa đơn thành công")
                .data(result)
                .build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết hóa đơn theo ID", description = "Trả về thông tin chi tiết của một mục trong hóa đơn")
    public ResponseEntity<ApiResponse<ChiTietHoaDonDTO>> getById(
            @Parameter(description = "ID của chi tiết hóa đơn") @PathVariable Integer id) {
        log.info("Getting chi tiet hoa don by ID: {}", id);
        ChiTietHoaDonDTO result = service.getById(id);
        return ResponseEntity.ok(ApiResponse.<ChiTietHoaDonDTO>builder()
                .success(true)
                .message("Lấy chi tiết hóa đơn thành công")
                .data(result)
                .build());
    }

    @PostMapping
    @Operation(summary = "Tạo chi tiết hóa đơn", description = "Tạo mới một chi tiết hóa đơn")
    public ResponseEntity<ApiResponse<ChiTietHoaDonDTO>> create(
            @Parameter(description = "Thông tin chi tiết hóa đơn") @Valid @RequestBody ChiTietHoaDonDTO dto) {
        log.info("Creating new chi tiet hoa don");
        ChiTietHoaDonDTO result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ChiTietHoaDonDTO>builder()
                        .success(true)
                        .message("Tạo chi tiết hóa đơn thành công")
                        .data(result)
                        .build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật chi tiết hóa đơn", description = "Cập nhật thông tin chi tiết hóa đơn")
    public ResponseEntity<ApiResponse<ChiTietHoaDonDTO>> update(
            @Parameter(description = "ID của chi tiết hóa đơn") @PathVariable Integer id,
            @Parameter(description = "Thông tin cập nhật") @Valid @RequestBody ChiTietHoaDonDTO dto) {
        log.info("Updating chi tiet hoa don with ID: {}", id);
        ChiTietHoaDonDTO result = service.update(id, dto);
        return ResponseEntity.ok(ApiResponse.<ChiTietHoaDonDTO>builder()
                .success(true)
                .message("Cập nhật chi tiết hóa đơn thành công")
                .data(result)
                .build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa chi tiết hóa đơn", description = "Xóa một chi tiết hóa đơn")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của chi tiết hóa đơn") @PathVariable Integer id) {
        log.info("Deleting chi tiet hoa don with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Xóa chi tiết hóa đơn thành công")
                .build());
    }

    @GetMapping("/hoadon/{hoadonId}/total")
    @Operation(summary = "Tính tổng tiền hóa đơn", description = "Tính tổng giá trị của tất cả chi tiết trong hóa đơn")
    public ResponseEntity<ApiResponse<BigDecimal>> calculateTotal(
            @Parameter(description = "ID của hóa đơn") @PathVariable Integer hoadonId) {
        log.info("Calculating total for hoa don ID: {}", hoadonId);
        BigDecimal total = service.calculateTotal(hoadonId);
        return ResponseEntity.ok(ApiResponse.<BigDecimal>builder()
                .success(true)
                .message("Tính tổng tiền hóa đơn thành công")
                .data(total)
                .build());
    }

    @GetMapping("/hoadon/{hoadonId}/summary")
    @Operation(summary = "Tóm tắt hóa đơn", description = "Trả về thông tin tóm tắt hóa đơn (tổng tiền, số lượng sản phẩm)")
    public ResponseEntity<ApiResponse<Object>> getInvoiceSummary(
            @Parameter(description = "ID của hóa đơn") @PathVariable Integer hoadonId) {
        log.info("Getting invoice summary for hoa don ID: {}", hoadonId);
        Object summary = service.getInvoiceSummary(hoadonId);
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message("Lấy tóm tắt hóa đơn thành công")
                .data(summary)
                .build());
    }
}
