package com.example.be.TempoTideBE.controller;

import com.example.be.dto.NhaCungCapDTO;
import com.example.be.service.NhaCungCapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nhacungcap")
@RequiredArgsConstructor
public class NhaCungCapController {

    private final NhaCungCapService nhaCungCapService;

    @GetMapping
    public ResponseEntity<List<NhaCungCapDTO>> getAllNhaCungCap() {
        return ResponseEntity.ok(nhaCungCapService.getAllNhaCungCap());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhaCungCapDTO> getNhaCungCapById(@PathVariable Integer id) {
        return ResponseEntity.ok(nhaCungCapService.getNhaCungCapById(id));
    }

    @PostMapping
    public ResponseEntity<NhaCungCapDTO> createNhaCungCap(@RequestBody NhaCungCapDTO dto) {
        return ResponseEntity.ok(nhaCungCapService.createNhaCungCap(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NhaCungCapDTO> updateNhaCungCap(@PathVariable Integer id, @RequestBody NhaCungCapDTO dto) {
        return ResponseEntity.ok(nhaCungCapService.updateNhaCungCap(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNhaCungCap(@PathVariable Integer id) {
        nhaCungCapService.deleteNhaCungCap(id);
        return ResponseEntity.noContent().build();
    }
}