package com.noithat.backend.controller;

import com.noithat.backend.dto.PhuongThucVanChuyenDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.PhuongThucVanChuyenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/vanchuyen")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Phương Thức Vận Chuyển", description = "APIs quản lý phương thức vận chuyển")
public class PhuongThucVanChuyenController {

    private final PhuongThucVanChuyenService service;

    @GetMapping
    @Operation(summary = "Lấy danh sách phương thức vận chuyển", description = "Trả về danh sách tất cả phương thức vận chuyển")
    public ResponseEntity<ApiResponse<List<PhuongThucVanChuyenDTO>>> getAll() {
        log.info("Getting all phuong thuc van chuyen");
        List<PhuongThucVanChuyenDTO> result = service.getAll();
        return ResponseEntity.ok(ApiResponse.<List<PhuongThucVanChuyenDTO>>builder()
                .success(true)
                .message("Lấy danh sách phương thức vận chuyển thành công")
                .data(result)
                .build());
    }

    @GetMapping("/paged")
    @Operation(summary = "Lấy danh sách phương thức vận chuyển có phân trang", description = "Trả về danh sách phương thức vận chuyển với phân trang")
    public ResponseEntity<ApiResponse<Page<PhuongThucVanChuyenDTO>>> getAllPaged(Pageable pageable) {
        log.info("Getting paged phuong thuc van chuyen, page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<PhuongThucVanChuyenDTO> result = service.getAllPaged(pageable);
        return ResponseEntity.ok(ApiResponse.<Page<PhuongThucVanChuyenDTO>>builder()
                .success(true)
                .message("Lấy danh sách phương thức vận chuyển thành công")
                .data(result)
                .build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy phương thức vận chuyển theo ID", description = "Trả về thông tin chi tiết của một phương thức vận chuyển")
    public ResponseEntity<ApiResponse<PhuongThucVanChuyenDTO>> getById(
            @Parameter(description = "ID của phương thức vận chuyển") @PathVariable Integer id) {
        log.info("Getting phuong thuc van chuyen by ID: {}", id);
        PhuongThucVanChuyenDTO result = service.getById(id);
        return ResponseEntity.ok(ApiResponse.<PhuongThucVanChuyenDTO>builder()
                .success(true)
                .message("Lấy thông tin phương thức vận chuyển thành công")
                .data(result)
                .build());
    }

    @GetMapping("/active")
    @Operation(summary = "Lấy phương thức vận chuyển đang hoạt động", description = "Trả về danh sách phương thức vận chuyển đang hoạt động")
    public ResponseEntity<ApiResponse<List<PhuongThucVanChuyenDTO>>> getActiveShippingMethods() {
        log.info("Getting active shipping methods");
        List<PhuongThucVanChuyenDTO> result = service.getActiveShippingMethods();
        return ResponseEntity.ok(ApiResponse.<List<PhuongThucVanChuyenDTO>>builder()
                .success(true)
                .message("Lấy danh sách phương thức vận chuyển hoạt động thành công")
                .data(result)
                .build());
    }

    @GetMapping("/price-range")
    @Operation(summary = "Tìm phương thức vận chuyển theo khoảng giá", description = "Tìm phương thức vận chuyển trong khoảng giá cụ thể")
    public ResponseEntity<ApiResponse<List<PhuongThucVanChuyenDTO>>> getByPriceRange(
            @Parameter(description = "Giá tối thiểu") @RequestParam BigDecimal minPrice,
            @Parameter(description = "Giá tối đa") @RequestParam BigDecimal maxPrice) {
        log.info("Getting shipping methods by price range: {} - {}", minPrice, maxPrice);
        List<PhuongThucVanChuyenDTO> result = service.getByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(ApiResponse.<List<PhuongThucVanChuyenDTO>>builder()
                .success(true)
                .message("Tìm phương thức vận chuyển theo khoảng giá thành công")
                .data(result)
                .build());
    }

    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm phương thức vận chuyển", description = "Tìm kiếm phương thức vận chuyển theo tên")
    public ResponseEntity<ApiResponse<List<PhuongThucVanChuyenDTO>>> search(
            @Parameter(description = "Từ khóa tìm kiếm") @RequestParam String keyword) {
        log.info("Searching shipping methods with keyword: {}", keyword);
        List<PhuongThucVanChuyenDTO> result = service.search(keyword);
        return ResponseEntity.ok(ApiResponse.<List<PhuongThucVanChuyenDTO>>builder()
                .success(true)
                .message("Tìm kiếm phương thức vận chuyển thành công")
                .data(result)
                .build());
    }

    @PostMapping
    @Operation(summary = "Tạo phương thức vận chuyển mới", description = "Tạo mới một phương thức vận chuyển")
    public ResponseEntity<ApiResponse<PhuongThucVanChuyenDTO>> create(
            @Parameter(description = "Thông tin phương thức vận chuyển") @Valid @RequestBody PhuongThucVanChuyenDTO dto) {
        log.info("Creating new phuong thuc van chuyen");
        PhuongThucVanChuyenDTO result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PhuongThucVanChuyenDTO>builder()
                        .success(true)
                        .message("Tạo phương thức vận chuyển thành công")
                        .data(result)
                        .build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật phương thức vận chuyển", description = "Cập nhật thông tin phương thức vận chuyển")
    public ResponseEntity<ApiResponse<PhuongThucVanChuyenDTO>> update(
            @Parameter(description = "ID của phương thức vận chuyển") @PathVariable Integer id,
            @Parameter(description = "Thông tin cập nhật") @Valid @RequestBody PhuongThucVanChuyenDTO dto) {
        log.info("Updating phuong thuc van chuyen with ID: {}", id);
        PhuongThucVanChuyenDTO result = service.update(id, dto);
        return ResponseEntity.ok(ApiResponse.<PhuongThucVanChuyenDTO>builder()
                .success(true)
                .message("Cập nhật phương thức vận chuyển thành công")
                .data(result)
                .build());
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Cập nhật trạng thái phương thức vận chuyển", description = "Kích hoạt hoặc vô hiệu hóa phương thức vận chuyển")
    public ResponseEntity<ApiResponse<PhuongThucVanChuyenDTO>> updateStatus(
            @Parameter(description = "ID của phương thức vận chuyển") @PathVariable Integer id,
            @Parameter(description = "Trạng thái mới (true: hoạt động, false: không hoạt động)") @RequestParam Boolean active) {
        log.info("Updating status for phuong thuc van chuyen ID: {} to {}", id, active);
        PhuongThucVanChuyenDTO result = service.updateStatus(id, active);
        return ResponseEntity.ok(ApiResponse.<PhuongThucVanChuyenDTO>builder()
                .success(true)
                .message("Cập nhật trạng thái phương thức vận chuyển thành công")
                .data(result)
                .build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa phương thức vận chuyển", description = "Xóa một phương thức vận chuyển")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của phương thức vận chuyển") @PathVariable Integer id) {
        log.info("Deleting phuong thuc van chuyen with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Xóa phương thức vận chuyển thành công")
                .build());
    }
}
