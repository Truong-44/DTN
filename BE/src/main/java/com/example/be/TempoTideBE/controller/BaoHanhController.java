package com.example.be.TempoTideBE.controller;


import com.example.be.TempoTideBE.dto.BaoHanhDTO;
import com.example.be.TempoTideBE.service.BaoHanhService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baohanh")
@RequiredArgsConstructor
public class BaoHanhController {

    private final BaoHanhService baoHanhService;

    @GetMapping
    public ResponseEntity<List<BaoHanhDTO>> getAllBaoHanh() {
        return ResponseEntity.ok(baoHanhService.getAllBaoHanh());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaoHanhDTO> getBaoHanhById(@PathVariable Integer id) {
        return ResponseEntity.ok(baoHanhService.getBaoHanhById(id));
    }

    @PostMapping
    public ResponseEntity<BaoHanhDTO> createBaoHanh(@RequestBody BaoHanhDTO dto) {
        return ResponseEntity.ok(baoHanhService.createBaoHanh(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaoHanhDTO> updateBaoHanh(@PathVariable Integer id, @RequestBody BaoHanhDTO dto) {
        return ResponseEntity.ok(baoHanhService.updateBaoHanh(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaoHanh(@PathVariable Integer id) {
        baoHanhService.deleteBaoHanh(id);
        return ResponseEntity.noContent().build();
    }
}