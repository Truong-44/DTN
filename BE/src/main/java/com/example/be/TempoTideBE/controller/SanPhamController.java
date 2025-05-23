package com.example.be.TempoTideBE.controller;

import com.example.be.TempoTideBE.dto.SanPhamDTO;
import com.example.be.TempoTideBE.exception.ResourceNotFoundException;
import com.example.be.TempoTideBE.service.SanPhamService;
import com.example.be.TempoTideBE.util.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sanpham")
@Tag(name = "SanPham", description = "API quản lý sản phẩm")
@RequiredArgsConstructor
public class SanPhamController {

    private final SanPhamService sanPhamService;

    @Operation(summary = "Tạo mới sản phẩm", description = "Thêm một sản phẩm mới vào hệ thống")
    @ApiResponse(responseCode = "201", description = "Sản phẩm được tạo thành công")
    @ApiResponse(responseCode = "400", description = "Yêu cầu không hợp lệ")
    @PostMapping
    public ResponseEntity<Object> createSanPham(@RequestBody SanPhamDTO sanPhamDTO) {
        try {
            SanPhamDTO createdSanPham = sanPhamService.createSanPham(sanPhamDTO);
            return ResponseHandler.generateResponse("Sản phẩm được tạo thành công", HttpStatus.CREATED, createdSanPham);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Lỗi khi tạo sản phẩm", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "Cập nhật sản phẩm", description = "Cập nhật thông tin sản phẩm theo mã sản phẩm")
    @ApiResponse(responseCode = "200", description = "Sản phẩm được cập nhật thành công")
    @ApiResponse(responseCode = "404", description = "Sản phẩm không tồn tại")
    @ApiResponse(responseCode = "400", description = "Yêu cầu không hợp lệ")
    @PutMapping("/{maSanPham}")
    public ResponseEntity<Object> updateSanPham(@PathVariable Integer maSanPham, @RequestBody SanPhamDTO sanPhamDTO) {
        try {
            SanPhamDTO updatedSanPham = sanPhamService.updateSanPham(maSanPham, sanPhamDTO);
            return ResponseHandler.generateResponse("Sản phẩm được cập nhật thành công", HttpStatus.OK, updatedSanPham);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Lỗi khi cập nhật sản phẩm", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "Xóa sản phẩm", description = "Xóa sản phẩm theo mã sản phẩm")
    @ApiResponse(responseCode = "200", description = "Sản phẩm được xóa thành công")
    @ApiResponse(responseCode = "404", description = "Sản phẩm không tồn tại")
    @DeleteMapping("/{maSanPham}")
    public ResponseEntity<Object> deleteSanPham(@PathVariable Integer maSanPham) {
        try {
            sanPhamService.deleteSanPham(maSanPham);
            return ResponseHandler.generateResponse("Sản phẩm được xóa thành công", HttpStatus.OK, null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Lỗi khi xóa sản phẩm", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "Lấy thông tin sản phẩm", description = "Lấy thông tin sản phẩm theo mã sản phẩm")
    @ApiResponse(responseCode = "200", description = "Thành công")
    @ApiResponse(responseCode = "404", description = "Sản phẩm không tồn tại")
    @GetMapping("/{maSanPham}")
    public ResponseEntity<Object> getSanPhamById(@PathVariable Integer maSanPham) {
        try {
            SanPhamDTO sanPhamDTO = sanPhamService.getSanPhamById(maSanPham);
            return ResponseHandler.generateResponse("Thành công", HttpStatus.OK, sanPhamDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Lỗi khi lấy sản phẩm", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Operation(summary = "Lấy danh sách tất cả sản phẩm", description = "Lấy danh sách tất cả sản phẩm trong hệ thống")
    @ApiResponse(responseCode = "200", description = "Thành công")
    @GetMapping
    public ResponseEntity<Object> getAllSanPhams() {
        try {
            List<SanPhamDTO> sanPhamDTOs = sanPhamService.getAllSanPham();
            return ResponseHandler.generateResponse("Thành công", HttpStatus.OK, sanPhamDTOs);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Lỗi khi lấy danh sách sản phẩm", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}