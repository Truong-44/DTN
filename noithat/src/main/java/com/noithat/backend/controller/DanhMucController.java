package com.noithat.backend.controller;

import com.noithat.backend.dto.DanhMucDTO;
import com.noithat.backend.dto.SanPhamDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.DanhMucService;
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
@RequestMapping("/api/danhmuc")
@RequiredArgsConstructor
@Tag(name = "DanhMuc", description = "API quản lý danh mục sản phẩm")
public class DanhMucController {

    private final DanhMucService danhMucService;

    @Operation(summary = "Lấy danh sách tất cả danh mục")
    @GetMapping
    public ResponseEntity<ApiResponse<List<DanhMucDTO>>> getAll() {
        log.info("Getting all danh mục");
        List<DanhMucDTO> result = danhMucService.getAll();
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh sách danh mục thành công"));
    }

    @Operation(summary = "Lấy danh mục theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DanhMucDTO>> getById(
            @Parameter(description = "ID của danh mục") @PathVariable Integer id) {
        log.info("Getting danh mục with id: {}", id);
        DanhMucDTO result = danhMucService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh mục thành công"));
    }

    @Operation(summary = "Lấy sản phẩm theo danh mục")
    @GetMapping("/{id}/sanpham")
    public ResponseEntity<ApiResponse<List<SanPhamDTO>>> getSanPhamByDanhMuc(
            @Parameter(description = "ID của danh mục") @PathVariable Integer id) {
        log.info("Getting sản phẩm by danh mục id: {}", id);
        List<SanPhamDTO> result = danhMucService.getSanPhamByDanhMucId(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy sản phẩm theo danh mục thành công"));
    }

    @Operation(summary = "Tạo mới danh mục")
    @PostMapping
    public ResponseEntity<ApiResponse<DanhMucDTO>> create(@Valid @RequestBody DanhMucDTO dto) {
        log.info("Creating new danh mục: {}", dto.getTendanhmuc());
        DanhMucDTO result = danhMucService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Tạo danh mục thành công"));
    }

    @Operation(summary = "Cập nhật danh mục")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DanhMucDTO>> update(
            @Parameter(description = "ID của danh mục") @PathVariable Integer id,
            @Valid @RequestBody DanhMucDTO dto) {
        log.info("Updating danh mục with id: {}", id);
        DanhMucDTO result = danhMucService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật danh mục thành công"));
    }

    @Operation(summary = "Xóa danh mục")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của danh mục") @PathVariable Integer id) {
        log.info("Deleting danh mục with id: {}", id);
        danhMucService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa danh mục thành công"));
    }
}
