package com.noithat.backend.controller;

import com.noithat.backend.dto.QuyenDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.QuyenService;
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
@RequestMapping("/api/quyen")
@RequiredArgsConstructor
@Tag(name = "Quyen", description = "API quản lý quyền")
public class QuyenController {

    private final QuyenService quyenService;

    @Operation(summary = "Lấy danh sách tất cả quyền")
    @GetMapping
    public ResponseEntity<ApiResponse<List<QuyenDTO>>> getAll() {
        log.info("Getting all quyền");
        List<QuyenDTO> result = quyenService.getAll();
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh sách quyền thành công"));
    }

    @Operation(summary = "Lấy quyền theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<QuyenDTO>> getById(
            @Parameter(description = "ID của quyền") @PathVariable Integer id) {
        log.info("Getting quyền with id: {}", id);
        QuyenDTO result = quyenService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy quyền thành công"));
    }

    @Operation(summary = "Tạo mới quyền")
    @PostMapping
    public ResponseEntity<ApiResponse<QuyenDTO>> create(@Valid @RequestBody QuyenDTO dto) {
        log.info("Creating new quyền: {}", dto.getTenquyen());
        QuyenDTO result = quyenService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Tạo quyền thành công"));
    }

    @Operation(summary = "Cập nhật quyền")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<QuyenDTO>> update(
            @Parameter(description = "ID của quyền") @PathVariable Integer id,
            @Valid @RequestBody QuyenDTO dto) {
        log.info("Updating quyền with id: {}", id);
        QuyenDTO result = quyenService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật quyền thành công"));
    }

    @Operation(summary = "Xóa quyền")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của quyền") @PathVariable Integer id) {
        log.info("Deleting quyền with id: {}", id);
        quyenService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa quyền thành công"));
    }
}
