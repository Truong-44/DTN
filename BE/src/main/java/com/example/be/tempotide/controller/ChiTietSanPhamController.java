package com.example.temp;

import com.example.temp.dto.ChiTietSanPhamDTO;
import com.example.temp.service.ChiTietSanPhamService;
import io.swagger.v3.oas.annotations.OpenAPIOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "Product Detail API", description = "APIs for managing product details")
public class ChiTietSanPhamController {
    private final ChiTietSanPhamService chiTietSanPhamService;

    @GetMapping
    @Operation(summary = "Get all active product details")
    public ResponseEntity<List<ChiTietSanPhamDTO>> getAllProductDetails() {
        return ResponseEntity.ok(chiTietSanPhamService.getAllActiveProductDetails());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product detail by ID")
    public ResponseEntity<ChiTietSanPhamDTO> getProductDetailById(@PathVariable long id) {
        return ResponseEntity.ok(chiTietSanPhamService.getProductDetailById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new product detail")
    public ResponseEntity<ChiTietSanPhamDTO> createProductDetail(@Valid @RequestBody ChiTietSanPhamDTO chiTietSanPhamDTO) {
        return ResponseEntity.ok(chiTietSanPhamService.createProductDetail(chiTietSanPhamDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a product detail")
    public ResponseEntity<ChiTietSanPhamDTO> updateProductDetail(@PathVariable long id, @Valid @RequestBody ChiTietSanPhamDTO chiTietSanPhamDTO) {
        return ResponseEntity.ok(chiTietSanPhamService.updateProductDetail(id, chiTietSanPhamDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a product detail (soft delete)")
    public ResponseEntity<Void> deleteProductDetail(@PathVariable long id) {
        chiTietSanPhamService.deleteProductDetail(id);
        return ResponseEntity.noContent().build();
    }
}