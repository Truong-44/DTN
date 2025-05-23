package com.example.be.TempoTideBE.controller;

import com.example.be.dto.YeuCauBaoHanhDTO;
import com.example.be.service.YeuCauBaoHanhService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/yeucaubaohanh")
@RequiredArgsConstructor
public class YeuCauBaoHanhController {

    private final YeuCauBaoHanhService yeuCauBaoHanhService;

    @GetMapping
    public ResponseEntity<List<YeuCauBaoHanhDTO>> getAllYeuCauBaoHanh() {
        return ResponseEntity.ok(yeuCauBaoHanhService.getAllYeuCauBaoHanh());
    }

    @GetMapping("/{id}")
    public ResponseEntity<YeuCauBaoHanhDTO> getYeuCauBaoHanhById(@PathVariable Integer id) {
        return ResponseEntity.ok(yeuCauBaoHanhService.getYeuCauBaoHanhById(id));
    }

    @PostMapping
    public ResponseEntity<YeuCauBaoHanhDTO> createYeuCauBaoHanh(@RequestBody YeuCauBaoHanhDTO dto) {
        return ResponseEntity.ok(yeuCauBaoHanhService.createYeuCauBaoHanh(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<YeuCauBaoHanhDTO> updateYeuCauBaoHanh(@PathVariable Integer id, @RequestBody YeuCauBaoHanhDTO dto) {
        return ResponseEntity.ok(yeuCauBaoHanhService.updateYeuCauBaoHanh(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteYeuCauBaoHanh(@PathVariable Integer id) {
        yeuCauBaoHanhService.deleteYeuCauBaoHanh(id);
        return ResponseEntity.noContent().build();
    }
}