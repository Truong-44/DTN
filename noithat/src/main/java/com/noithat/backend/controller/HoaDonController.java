package com.noithat.backend.controller;

import com.noithat.backend.dto.HoaDonDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.HoaDonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/hoadon")
@RequiredArgsConstructor
@Tag(name = "HoaDon", description = "API quản lý hóa đơn")
public class HoaDonController {

    private final HoaDonService service;

    @Operation(summary = "Lấy danh sách tất cả hóa đơn")
    @GetMapping
    public ResponseEntity<ApiResponse<List<HoaDonDTO>>> getAll() {
        log.info("Getting all hóa đơn");
        List<HoaDonDTO> result = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh sách hóa đơn thành công"));
    }

    @Operation(summary = "Lấy hóa đơn theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HoaDonDTO>> getById(
            @Parameter(description = "ID của hóa đơn") @PathVariable Integer id) {
        log.info("Getting hóa đơn with id: {}", id);
        HoaDonDTO result = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy hóa đơn thành công"));
    }

    @Operation(summary = "Lấy hóa đơn theo khách hàng")
    @GetMapping("/khachhang/{khachHangId}")
    public ResponseEntity<ApiResponse<List<HoaDonDTO>>> getByKhachHang(
            @Parameter(description = "ID của khách hàng") @PathVariable Integer khachHangId) {
        log.info("Getting hóa đơn by khách hàng id: {}", khachHangId);
        List<HoaDonDTO> result = service.getByKhachHangId(khachHangId);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy hóa đơn theo khách hàng thành công"));
    }

    @Operation(summary = "Lấy hóa đơn theo nhân viên")
    @GetMapping("/nhanvien/{nhanVienId}")
    public ResponseEntity<ApiResponse<List<HoaDonDTO>>> getByNhanVien(
            @Parameter(description = "ID của nhân viên") @PathVariable Integer nhanVienId) {
        log.info("Getting hóa đơn by nhân viên id: {}", nhanVienId);
        List<HoaDonDTO> result = service.getByNhanVienId(nhanVienId);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy hóa đơn theo nhân viên thành công"));
    }

    @Operation(summary = "Lấy hóa đơn theo khoảng thời gian")
    @GetMapping("/ngay")
    public ResponseEntity<ApiResponse<List<HoaDonDTO>>> getByNgay(
            @Parameter(description = "Ngày bắt đầu") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @Parameter(description = "Ngày kết thúc") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        log.info("Getting hóa đơn by date range: {} to {}", fromDate, toDate);
        List<HoaDonDTO> result = service.getByNgayTao(fromDate, toDate);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy hóa đơn theo khoảng thời gian thành công"));
    }

    @Operation(summary = "Tạo mới hóa đơn")
    @PostMapping
    public ResponseEntity<ApiResponse<HoaDonDTO>> create(@Valid @RequestBody HoaDonDTO dto) {
        log.info("Creating new hóa đơn for khách hàng: {}", dto.getKhachhang());
        HoaDonDTO result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Tạo hóa đơn thành công"));
    }

    @Operation(summary = "Cập nhật hóa đơn")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HoaDonDTO>> update(
            @Parameter(description = "ID của hóa đơn") @PathVariable Integer id,
            @Valid @RequestBody HoaDonDTO dto) {
        log.info("Updating hóa đơn with id: {}", id);
        HoaDonDTO result = service.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật hóa đơn thành công"));
    }

    @Operation(summary = "Xóa hóa đơn")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của hóa đơn") @PathVariable Integer id) {
        log.info("Deleting hóa đơn with id: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa hóa đơn thành công"));
    }
}
