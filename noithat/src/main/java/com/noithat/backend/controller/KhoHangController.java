package com.noithat.backend.controller;

import com.noithat.backend.dto.KhoHangDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.KhoHangService;
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

import java.util.List;

@RestController
@RequestMapping("/api/khohang")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Kho Hàng", description = "APIs quản lý kho hàng và tồn kho")
public class KhoHangController {

    private final KhoHangService service;

    @GetMapping
    @Operation(summary = "Lấy danh sách kho hàng", description = "Trả về danh sách tất cả kho hàng")
    public ResponseEntity<ApiResponse<List<KhoHangDTO>>> getAll() {
        log.info("Getting all kho hang");
        List<KhoHangDTO> result = service.getAll();
        return ResponseEntity.ok(ApiResponse.<List<KhoHangDTO>>builder()
                .success(true)
                .message("Lấy danh sách kho hàng thành công")
                .data(result)
                .build());
    }

    @GetMapping("/paged")
    @Operation(summary = "Lấy danh sách kho hàng có phân trang", description = "Trả về danh sách kho hàng với phân trang")
    public ResponseEntity<ApiResponse<Page<KhoHangDTO>>> getAllPaged(Pageable pageable) {
        log.info("Getting paged kho hang, page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<KhoHangDTO> result = service.getAllPaged(pageable);
        return ResponseEntity.ok(ApiResponse.<Page<KhoHangDTO>>builder()
                .success(true)
                .message("Lấy danh sách kho hàng thành công")
                .data(result)
                .build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy kho hàng theo ID", description = "Trả về thông tin chi tiết của một kho hàng")
    public ResponseEntity<ApiResponse<KhoHangDTO>> getById(
            @Parameter(description = "ID của kho hàng") @PathVariable Integer id) {
        log.info("Getting kho hang by ID: {}", id);
        KhoHangDTO result = service.getById(id);
        return ResponseEntity.ok(ApiResponse.<KhoHangDTO>builder()
                .success(true)
                .message("Lấy thông tin kho hàng thành công")
                .data(result)
                .build());
    }

    @GetMapping("/sanpham/{ctspId}")
    @Operation(summary = "Lấy kho hàng theo ID chi tiết sản phẩm", description = "Trả về thông tin kho hàng của một chi tiết sản phẩm")
    public ResponseEntity<ApiResponse<KhoHangDTO>> getByChiTietSanPhamId(
            @Parameter(description = "ID của chi tiết sản phẩm") @PathVariable Integer ctspId) {
        log.info("Getting kho hang by chi tiet san pham ID: {}", ctspId);
        KhoHangDTO result = service.getByChiTietSanPhamId(ctspId);
        return ResponseEntity.ok(ApiResponse.<KhoHangDTO>builder()
                .success(true)
                .message("Lấy thông tin kho hàng thành công")
                .data(result)
                .build());
    }

    @PostMapping
    @Operation(summary = "Tạo kho hàng mới", description = "Tạo mới một bản ghi kho hàng")
    public ResponseEntity<ApiResponse<KhoHangDTO>> create(
            @Parameter(description = "Thông tin kho hàng") @Valid @RequestBody KhoHangDTO dto) {
        log.info("Creating new kho hang");
        KhoHangDTO result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<KhoHangDTO>builder()
                        .success(true)
                        .message("Tạo kho hàng thành công")
                        .data(result)
                        .build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật kho hàng", description = "Cập nhật thông tin kho hàng")
    public ResponseEntity<ApiResponse<KhoHangDTO>> update(
            @Parameter(description = "ID của kho hàng") @PathVariable Integer id,
            @Parameter(description = "Thông tin cập nhật") @Valid @RequestBody KhoHangDTO dto) {
        log.info("Updating kho hang with ID: {}", id);
        KhoHangDTO result = service.update(id, dto);
        return ResponseEntity.ok(ApiResponse.<KhoHangDTO>builder()
                .success(true)
                .message("Cập nhật kho hàng thành công")
                .data(result)
                .build());
    }

    @PostMapping("/create-or-update")
    @Operation(summary = "Tạo hoặc cập nhật kho hàng", description = "Tạo mới hoặc cập nhật kho hàng tùy theo tồn tại")
    public ResponseEntity<ApiResponse<KhoHangDTO>> createOrUpdate(
            @Parameter(description = "Thông tin kho hàng") @Valid @RequestBody KhoHangDTO dto) {
        log.info("Creating or updating kho hang");
        KhoHangDTO result = service.createOrUpdate(dto);
        return ResponseEntity.ok(ApiResponse.<KhoHangDTO>builder()
                .success(true)
                .message("Xử lý kho hàng thành công")
                .data(result)
                .build());
    }

    @PutMapping("/{id}/soluong")
    @Operation(summary = "Cập nhật số lượng tồn kho", description = "Cập nhật số lượng tồn kho của một sản phẩm")
    public ResponseEntity<ApiResponse<KhoHangDTO>> updateSoLuong(
            @Parameter(description = "ID của kho hàng") @PathVariable Integer id,
            @Parameter(description = "Số lượng mới") @RequestParam Integer soLuong) {
        log.info("Updating so luong for kho hang ID: {} to {}", id, soLuong);
        KhoHangDTO result = service.updateSoLuong(id, soLuong);
        return ResponseEntity.ok(ApiResponse.<KhoHangDTO>builder()
                .success(true)
                .message("Cập nhật số lượng tồn kho thành công")
                .data(result)
                .build());
    }

    @PutMapping("/{id}/increase")
    @Operation(summary = "Nhập kho", description = "Tăng số lượng tồn kho (nhập hàng)")
    public ResponseEntity<ApiResponse<KhoHangDTO>> increaseStock(
            @Parameter(description = "ID của kho hàng") @PathVariable Integer id,
            @Parameter(description = "Số lượng nhập") @RequestParam Integer soLuong) {
        log.info("Increasing stock for kho hang ID: {} by {}", id, soLuong);
        KhoHangDTO result = service.increaseStock(id, soLuong);
        return ResponseEntity.ok(ApiResponse.<KhoHangDTO>builder()
                .success(true)
                .message("Nhập kho thành công")
                .data(result)
                .build());
    }

    @PutMapping("/{id}/decrease")
    @Operation(summary = "Xuất kho", description = "Giảm số lượng tồn kho (xuất hàng)")
    public ResponseEntity<ApiResponse<KhoHangDTO>> decreaseStock(
            @Parameter(description = "ID của kho hàng") @PathVariable Integer id,
            @Parameter(description = "Số lượng xuất") @RequestParam Integer soLuong) {
        log.info("Decreasing stock for kho hang ID: {} by {}", id, soLuong);
        KhoHangDTO result = service.decreaseStock(id, soLuong);
        return ResponseEntity.ok(ApiResponse.<KhoHangDTO>builder()
                .success(true)
                .message("Xuất kho thành công")
                .data(result)
                .build());
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Sản phẩm sắp hết hàng", description = "Lấy danh sách sản phẩm có số lượng tồn kho thấp")
    public ResponseEntity<ApiResponse<List<KhoHangDTO>>> getLowStockItems(
            @Parameter(description = "Ngưỡng số lượng tối thiểu") @RequestParam(defaultValue = "10") Integer threshold) {
        log.info("Getting low stock items with threshold: {}", threshold);
        List<KhoHangDTO> result = service.getLowStockItems(threshold);
        return ResponseEntity.ok(ApiResponse.<List<KhoHangDTO>>builder()
                .success(true)
                .message("Lấy danh sách sản phẩm sắp hết hàng thành công")
                .data(result)
                .build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa kho hàng", description = "Xóa một bản ghi kho hàng")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của kho hàng") @PathVariable Integer id) {
        log.info("Deleting kho hang with ID: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Xóa kho hàng thành công")
                .build());
    }
}
