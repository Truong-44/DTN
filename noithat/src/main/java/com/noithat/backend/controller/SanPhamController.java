package com.noithat.backend.controller;

import com.noithat.backend.dto.SanPhamDTO;
import com.noithat.backend.dto.ChiTietSanPhamDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.SanPhamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/sanpham")
@RequiredArgsConstructor
@Tag(name = "SanPham", description = "API quản lý sản phẩm")
public class SanPhamController {

    private final SanPhamService service;

    @Operation(summary = "Lấy danh sách tất cả sản phẩm")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SanPhamDTO>>> getAll() {
        log.info("Getting all sản phẩm");
        List<SanPhamDTO> result = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh sách sản phẩm thành công"));
    }

    @Operation(summary = "Lấy danh sách sản phẩm có phân trang")
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<SanPhamDTO>>> getAllPaged(Pageable pageable) {
        log.info("Getting paged sản phẩm with page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<SanPhamDTO> result = service.getAllPaged(pageable);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh sách sản phẩm phân trang thành công"));
    }

    @Operation(summary = "Lấy sản phẩm theo danh mục")
    @GetMapping("/danhmuc/{danhMucId}")
    public ResponseEntity<ApiResponse<List<SanPhamDTO>>> getByDanhMuc(
            @Parameter(description = "ID của danh mục") @PathVariable Integer danhMucId) {
        log.info("Getting sản phẩm by danh mục id: {}", danhMucId);
        List<SanPhamDTO> result = service.getByDanhMucId(danhMucId);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy sản phẩm theo danh mục thành công"));
    }

    @Operation(summary = "Tìm kiếm sản phẩm theo tên")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SanPhamDTO>>> search(
            @Parameter(description = "Từ khóa tìm kiếm") @RequestParam String keyword) {
        log.info("Searching sản phẩm with keyword: {}", keyword);
        List<SanPhamDTO> result = service.searchByName(keyword);
        return ResponseEntity.ok(ApiResponse.success(result, "Tìm kiếm sản phẩm thành công"));
    }

    @Operation(summary = "Tìm kiếm sản phẩm có phân trang")
    @GetMapping("/search/page")
    public ResponseEntity<ApiResponse<Page<SanPhamDTO>>> searchPaged(
            @Parameter(description = "Từ khóa tìm kiếm") @RequestParam String keyword, 
            Pageable pageable) {
        log.info("Searching paged sản phẩm with keyword: {}", keyword);
        Page<SanPhamDTO> result = service.searchByNamePaged(keyword, pageable);
        return ResponseEntity.ok(ApiResponse.success(result, "Tìm kiếm sản phẩm phân trang thành công"));
    }

    @Operation(summary = "Lấy sản phẩm theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SanPhamDTO>> getById(
            @Parameter(description = "ID của sản phẩm") @PathVariable Integer id) {
        log.info("Getting sản phẩm with id: {}", id);
        SanPhamDTO result = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy sản phẩm thành công"));
    }

    @Operation(summary = "Lấy chi tiết sản phẩm")
    @GetMapping("/{id}/chitietsanpham")
    public ResponseEntity<ApiResponse<List<ChiTietSanPhamDTO>>> getChiTietSanPham(
            @Parameter(description = "ID của sản phẩm") @PathVariable Integer id) {
        log.info("Getting chi tiết sản phẩm for id: {}", id);
        List<ChiTietSanPhamDTO> result = service.getChiTietSanPhamById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy chi tiết sản phẩm thành công"));
    }

    @Operation(summary = "Lấy thông tin đầy đủ sản phẩm (bao gồm chi tiết)")
    @GetMapping("/{id}/full")
    public ResponseEntity<ApiResponse<SanPhamDTO>> getFullInfo(
            @Parameter(description = "ID của sản phẩm") @PathVariable Integer id) {
        log.info("Getting full info for sản phẩm id: {}", id);
        SanPhamDTO result = service.getFullInfoById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy thông tin đầy đủ sản phẩm thành công"));
    }

    @Operation(summary = "Lấy màu sắc có sẵn của sản phẩm")
    @GetMapping("/{id}/available-colors")
    public ResponseEntity<ApiResponse<List<String>>> getAvailableColors(
            @Parameter(description = "ID của sản phẩm") @PathVariable Integer id) {
        log.info("Getting available colors for sản phẩm id: {}", id);
        List<String> result = service.getAvailableColors(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy màu sắc có sẵn thành công"));
    }

    @Operation(summary = "Lấy kích thước có sẵn của sản phẩm")
    @GetMapping("/{id}/available-sizes")
    public ResponseEntity<ApiResponse<List<String>>> getAvailableSizes(
            @Parameter(description = "ID của sản phẩm") @PathVariable Integer id) {
        log.info("Getting available sizes for sản phẩm id: {}", id);
        List<String> result = service.getAvailableSizes(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy kích thước có sẵn thành công"));
    }

    @Operation(summary = "Tạo mới sản phẩm")
    @PostMapping
    public ResponseEntity<ApiResponse<SanPhamDTO>> create(@Valid @RequestBody SanPhamDTO dto) {
        log.info("Creating new sản phẩm: {}", dto.getTensanpham());
        SanPhamDTO result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Tạo sản phẩm thành công"));
    }

    @Operation(summary = "Cập nhật sản phẩm")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SanPhamDTO>> update(
            @Parameter(description = "ID của sản phẩm") @PathVariable Integer id,
            @Valid @RequestBody SanPhamDTO dto) {
        log.info("Updating sản phẩm with id: {}", id);
        SanPhamDTO result = service.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật sản phẩm thành công"));
    }

    @Operation(summary = "Xóa sản phẩm")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của sản phẩm") @PathVariable Integer id) {
        log.info("Deleting sản phẩm with id: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa sản phẩm thành công"));
    }

    @Operation(summary = "Thay đổi trạng thái sản phẩm")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<SanPhamDTO>> updateStatus(
            @Parameter(description = "ID của sản phẩm") @PathVariable Integer id,
            @Parameter(description = "Trạng thái mới") @RequestParam Boolean status) {
        log.info("Updating status for sản phẩm id: {} to: {}", id, status);
        SanPhamDTO result = service.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật trạng thái sản phẩm thành công"));
    }
}
