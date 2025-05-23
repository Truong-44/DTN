package com.example.be.TempoTideBE.controller;

import com.example.be.dto.ThanhToanDTO;
import com.example.be.service.ThanhToanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thanhtoan")
@RequiredArgsConstructor
public class ThanhToanController {

    private final ThanhToanService thanhToanService;

    @GetMapping
    public ResponseEntity<List<ThanhToanDTO>> getAllThanhToan() {
        return ResponseEntity.ok(thanhToanService.getAllThanhToan());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThanhToanDTO> getThanhToanById(@PathVariable Integer id) {
        return ResponseEntity.ok(thanhToanService.getThanhToanById(id));
    }

    @PostMapping
    public ResponseEntity<ThanhToanDTO> createThanhToan(@RequestBody ThanhToanDTO dto) {
        return ResponseEntity.ok(thanhToanService.createThanhToan(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThanhToanDTO> updateThanhToan(@PathVariable Integer id, @RequestBody ThanhToanDTO dto) {
        return ResponseEntity.ok(thanhToanService.updateThanhToan(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThanhToan(@PathVariable Integer id) {
        thanhToanService.deleteThanhToan(id);
        return ResponseEntity.noContent().build();
    }
}