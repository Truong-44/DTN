package com.example.be.TempoTideBE.controller;

import com.example.be.dto.GiaoDichTichDiemDTO;
import com.example.be.service.GiaoDichTichDiemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/giaodichtichdiem")
@RequiredArgsConstructor
public class GiaoDichTichDiemController {

    private final GiaoDichTichDiemService giaoDichTichDiemService;

    @GetMapping
    public ResponseEntity<List<GiaoDichTichDiemDTO>> getAllGiaoDichTichDiem() {
        return ResponseEntity.ok(giaoDichTichDiemService.getAllGiaoDichTichDiem());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiaoDichTichDiemDTO> getGiaoDichTichDiemById(@PathVariable Integer id) {
        return ResponseEntity.ok(giaoDichTichDiemService.getGiaoDichTichDiemById(id));
    }

    @PostMapping
    public ResponseEntity<GiaoDichTichDiemDTO> createGiaoDichTichDiem(@RequestBody GiaoDichTichDiemDTO dto) {
        return ResponseEntity.ok(giaoDichTichDiemService.createGiaoDichTichDiem(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiaoDichTichDiemDTO> updateGiaoDichTichDiem(@PathVariable Integer id, @RequestBody GiaoDichTichDiemDTO dto) {
        return ResponseEntity.ok(giaoDichTichDiemService.updateGiaoDichTichDiem(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiaoDichTichDiem(@PathVariable Integer id) {
        giaoDichTichDiemService.deleteGiaoDichTichDiem(id);
        return ResponseEntity.noContent().build();
    }
}