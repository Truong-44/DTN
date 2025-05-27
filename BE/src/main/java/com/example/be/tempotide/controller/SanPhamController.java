package com.example.be.tempotide.controller;

import com.example.be.tempotide.service.SanPhamService;
import com.example.tempotide.backend.dto.SanPhamDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sanpham")
@RequiredArgsConstructor
@Tag(name = "Sản Phẩm API", description = "Quản lý thông tin sản phẩm")
public class SanPhamController {
    private final SanPhamService sanPhamService;

    @Operation(summary = "Lấy danh sách tất cả sản phẩm với phân trang")
    @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công")
    @GetMapping
    public ResponseEntity<Page<SanPhamDTO>> getAllSanPham(Pageable pageable) {
        return ResponseEntity.ok(sanPhamService.getAllSanPham(pageable));
    }

    @Operation(summary = "Tìm kiếm sản phẩm theo từ khóa với phân trang")
    @ApiResponse(responseCode = "200", description = "Tìm kiếm thành công")
    @GetMapping("/search")
    public ResponseEntity<Page<SanPhamDTO>> searchSanPham(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(sanPhamService.searchSanPham(keyword, pageable));
    }

    @Operation(summary = "Lấy thông tin sản phẩm theo ID")
    @ApiResponse(responseCode = "200", description = "Lấy thành công")
    @ApiResponse(responseCode = "404", description = "Sản phẩm không tồn tại")
    @GetMapping("/{id}")
    public ResponseEntity<SanPhamDTO> getSanPhamById(@PathVariable Integer id) {
        return ResponseEntity.ok(sanPhamService.getSanPhamById(id));
    }

    @Operation(summary = "Tạo mới sản phẩm")
    @ApiResponse(responseCode = "201", description = "Tạo thành công")
    @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ")
    @PostMapping
    public ResponseEntity<SanPhamDTO> createSanPham(@Valid @RequestBody SanPhamDTO dto) {
        return new ResponseEntity<>(sanPhamService.createSanPham(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Cập nhật thông tin sản phẩm")
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    @ApiResponse(responseCode = "404", description = "Sản phẩm không tồn tại")
    @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ")
    @PutMapping("/{id}")
    public ResponseEntity<SanPhamDTO> updateSanPham(@PathVariable Integer id, @Valid @RequestBody SanPhamDTO dto) {
        return ResponseEntity.ok(sanPhamService.updateSanPham(id, dto));
    }

    @Operation(summary = "Xóa sản phẩm (chuyển trạng thái thành false)")
    @ApiResponse(responseCode = "204", description = "Xóa thành công")
    @ApiResponse(responseCode = "404", description = "Sản phẩm không tồn tại")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSanPham(@PathVariable Integer id) {
        sanPhamService.deleteSanPham(id);
        return ResponseEntity.noContent().build();
    }
}