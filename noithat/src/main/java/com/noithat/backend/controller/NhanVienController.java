package com.noithat.backend.controller;

import com.noithat.backend.dto.NhanVienDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.NhanVienService;
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
@RequestMapping("/api/nhanvien")
@RequiredArgsConstructor
@Tag(name = "NhanVien", description = "API quản lý nhân viên")
public class NhanVienController {

    private final NhanVienService nhanVienService;

    @Operation(summary = "Lấy danh sách tất cả nhân viên")
    @GetMapping
    public ResponseEntity<ApiResponse<List<NhanVienDTO>>> getAll() {
        log.info("Getting all nhân viên");
        List<NhanVienDTO> result = nhanVienService.getAll();
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh sách nhân viên thành công"));
    }

    @Operation(summary = "Lấy nhân viên theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NhanVienDTO>> getById(
            @Parameter(description = "ID của nhân viên") @PathVariable Integer id) {
        log.info("Getting nhân viên with id: {}", id);
        NhanVienDTO result = nhanVienService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy nhân viên thành công"));
    }

    @Operation(summary = "Tìm kiếm nhân viên theo tên")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<NhanVienDTO>>> searchByName(
            @Parameter(description = "Tên nhân viên") @RequestParam String name) {
        log.info("Searching nhân viên with name: {}", name);
        List<NhanVienDTO> result = nhanVienService.searchByName(name);
        return ResponseEntity.ok(ApiResponse.success(result, "Tìm kiếm nhân viên thành công"));
    }

    @Operation(summary = "Tạo mới nhân viên")
    @PostMapping
    public ResponseEntity<ApiResponse<NhanVienDTO>> create(@Valid @RequestBody NhanVienDTO dto) {
        log.info("Creating new nhân viên: {}", dto.getHoten());
        NhanVienDTO result = nhanVienService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Tạo nhân viên thành công"));
    }

    @Operation(summary = "Cập nhật nhân viên")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NhanVienDTO>> update(
            @Parameter(description = "ID của nhân viên") @PathVariable Integer id,
            @Valid @RequestBody NhanVienDTO dto) {
        log.info("Updating nhân viên with id: {}", id);
        NhanVienDTO result = nhanVienService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật nhân viên thành công"));
    }

    @Operation(summary = "Xóa nhân viên")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của nhân viên") @PathVariable Integer id) {
        log.info("Deleting nhân viên with id: {}", id);
        nhanVienService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa nhân viên thành công"));
    }
}
