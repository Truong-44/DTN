package com.noithat.backend.controller;

import com.noithat.backend.dto.ChiTietSanPhamDTO;
import com.noithat.backend.dto.response.ApiResponse;
import com.noithat.backend.service.ChiTietSanPhamService;
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
@RequestMapping("/api/chitietsanpham")
@RequiredArgsConstructor
@Tag(name = "ChiTietSanPham", description = "API quản lý chi tiết sản phẩm")
public class ChiTietSanPhamController {

    private final ChiTietSanPhamService service;

    @Operation(summary = "Lấy danh sách tất cả chi tiết sản phẩm")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ChiTietSanPhamDTO>>> getAll() {
        log.info("Getting all chi tiết sản phẩm");
        List<ChiTietSanPhamDTO> result = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy danh sách chi tiết sản phẩm thành công"));
    }

    @Operation(summary = "Lấy chi tiết sản phẩm theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ChiTietSanPhamDTO>> getById(
            @Parameter(description = "ID của chi tiết sản phẩm") @PathVariable Integer id) {
        log.info("Getting chi tiết sản phẩm with id: {}", id);
        ChiTietSanPhamDTO result = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy chi tiết sản phẩm thành công"));
    }

    @Operation(summary = "Lấy chi tiết sản phẩm theo sản phẩm")
    @GetMapping("/sanpham/{sanPhamId}")
    public ResponseEntity<ApiResponse<List<ChiTietSanPhamDTO>>> getBySanPham(
            @Parameter(description = "ID của sản phẩm") @PathVariable Integer sanPhamId) {
        log.info("Getting chi tiết sản phẩm by sản phẩm id: {}", sanPhamId);
        List<ChiTietSanPhamDTO> result = service.getBySanPhamId(sanPhamId);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy chi tiết sản phẩm theo sản phẩm thành công"));
    }

    @Operation(summary = "Lấy chi tiết sản phẩm theo sản phẩm và màu sắc")
    @GetMapping("/sanpham/{sanPhamId}/mau/{mauSac}")
    public ResponseEntity<ApiResponse<List<ChiTietSanPhamDTO>>> getBySanPhamAndMau(
            @Parameter(description = "ID của sản phẩm") @PathVariable Integer sanPhamId,
            @Parameter(description = "Màu sắc") @PathVariable String mauSac) {
        log.info("Getting chi tiết sản phẩm by sản phẩm id: {} and màu: {}", sanPhamId, mauSac);
        List<ChiTietSanPhamDTO> result = service.getBySanPhamIdAndMau(sanPhamId, mauSac);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy chi tiết sản phẩm theo màu sắc thành công"));
    }

    @Operation(summary = "Lấy chi tiết sản phẩm theo sản phẩm và kích thước")
    @GetMapping("/sanpham/{sanPhamId}/size/{kichThuoc}")
    public ResponseEntity<ApiResponse<List<ChiTietSanPhamDTO>>> getBySanPhamAndSize(
            @Parameter(description = "ID của sản phẩm") @PathVariable Integer sanPhamId,
            @Parameter(description = "Kích thước") @PathVariable String kichThuoc) {
        log.info("Getting chi tiết sản phẩm by sản phẩm id: {} and size: {}", sanPhamId, kichThuoc);
        List<ChiTietSanPhamDTO> result = service.getBySanPhamIdAndKichThuoc(sanPhamId, kichThuoc);
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy chi tiết sản phẩm theo kích thước thành công"));
    }

    @Operation(summary = "Lọc chi tiết sản phẩm theo nhiều tiêu chí")
    @GetMapping("/sanpham/{sanPhamId}/filter")
    public ResponseEntity<ApiResponse<List<ChiTietSanPhamDTO>>> getBySanPhamWithFilter(
            @Parameter(description = "ID của sản phẩm") @PathVariable Integer sanPhamId,
            @Parameter(description = "Màu sắc") @RequestParam(required = false) String mauSac,
            @Parameter(description = "Kích thước") @RequestParam(required = false) String kichThuoc,
            @Parameter(description = "Chất liệu") @RequestParam(required = false) String chatLieu) {
        log.info("Filtering chi tiết sản phẩm by sản phẩm id: {} with filters - màu: {}, size: {}, chất liệu: {}", 
                sanPhamId, mauSac, kichThuoc, chatLieu);
        List<ChiTietSanPhamDTO> result = service.getBySanPhamIdWithFilter(sanPhamId, mauSac, kichThuoc, chatLieu);
        return ResponseEntity.ok(ApiResponse.success(result, "Lọc chi tiết sản phẩm thành công"));
    }

    @Operation(summary = "Lấy chi tiết sản phẩm còn hàng")
    @GetMapping("/available-stock")
    public ResponseEntity<ApiResponse<List<ChiTietSanPhamDTO>>> getAvailableStock() {
        log.info("Getting available stock chi tiết sản phẩm");
        List<ChiTietSanPhamDTO> result = service.getAvailableStock();
        return ResponseEntity.ok(ApiResponse.success(result, "Lấy chi tiết sản phẩm còn hàng thành công"));
    }

    @Operation(summary = "Tạo mới chi tiết sản phẩm")
    @PostMapping
    public ResponseEntity<ApiResponse<ChiTietSanPhamDTO>> create(@Valid @RequestBody ChiTietSanPhamDTO dto) {
        log.info("Creating new chi tiết sản phẩm for sản phẩm: {}", dto.getSanphamId());
        ChiTietSanPhamDTO result = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(result, "Tạo chi tiết sản phẩm thành công"));
    }

    @Operation(summary = "Cập nhật chi tiết sản phẩm")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ChiTietSanPhamDTO>> update(
            @Parameter(description = "ID của chi tiết sản phẩm") @PathVariable Integer id,
            @Valid @RequestBody ChiTietSanPhamDTO dto) {
        log.info("Updating chi tiết sản phẩm with id: {}", id);
        ChiTietSanPhamDTO result = service.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success(result, "Cập nhật chi tiết sản phẩm thành công"));
    }

    @Operation(summary = "Xóa chi tiết sản phẩm")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @Parameter(description = "ID của chi tiết sản phẩm") @PathVariable Integer id) {
        log.info("Deleting chi tiết sản phẩm with id: {}", id);
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa chi tiết sản phẩm thành công"));
    }
}
