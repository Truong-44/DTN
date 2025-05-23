package com.example.be.TempoTideBE.controller;

import com.example.be.dto.PhuongThucThanhToanDTO;
import com.example.be.service.PhuongThucThanhToanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phuongthucthanhtoan")
@RequiredArgsConstructor
public class PhuongThucThanhToanController {

    private final PhuongThucThanhToanService phuongThucThanhToanService;

    @GetMapping
    public ResponseEntity<List<PhuongThucThanhToanDTO>> getAllPhuongThucThanhToan() {
        return ResponseEntity.ok(phuongThucThanhToanService.getAllPhuongThucThanhToan());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhuongThucThanhToanDTO> getPhuongThucThanhToanById(@PathVariable Integer id) {
        return ResponseEntity.ok(phuongThucThanhToanService.getPhuongThucThanhToanById(id));
    }

    @PostMapping
    public ResponseEntity<PhuongThucThanhToanDTO> createPhuongThucThanhToan(@RequestBody PhuongThucThanhToanDTO dto) {
        return ResponseEntity.ok(phuongThucThanhToanService.createPhuongThucThanhToan(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhuongThucThanhToanDTO> updatePhuongThucThanhToan(@PathVariable Integer id, @RequestBody PhuongThucThanhToanDTO dto) {
        return ResponseEntity.ok(phuongThucThanhToanService.updatePhuongThucThanhToan(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhuongThucThanhToan(@PathVariable Integer id) {
        phuongThucThanhToanService.deletePhuongThucThanhToan(id);
        return ResponseEntity.noContent().build();
    }
}