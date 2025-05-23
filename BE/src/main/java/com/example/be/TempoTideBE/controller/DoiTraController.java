package com.example.be.TempoTideBE.controller;

import com.example.be.TempoTideBE.dto.DoiTraDTO;
import com.example.be.TempoTideBE.service.DoiTraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doitra")
@RequiredArgsConstructor
public class DoiTraController {

    private final DoiTraService doiTraService;

    @GetMapping
    public ResponseEntity<List<DoiTraDTO>> getAllDoiTra() {
        return ResponseEntity.ok(doiTraService.getAllDoiTra());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoiTraDTO> getDoiTraById(@PathVariable Integer id) {
        return ResponseEntity.ok(doiTraService.getDoiTraById(id));
    }

    @PostMapping
    public ResponseEntity<DoiTraDTO> createDoiTra(@RequestBody DoiTraDTO dto) {
        return ResponseEntity.ok(doiTraService.createDoiTra(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoiTraDTO> updateDoiTra(@PathVariable Integer id, @RequestBody DoiTraDTO dto) {
        return ResponseEntity.ok(doiTraService.updateDoiTra(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoiTra(@PathVariable Integer id) {
        doiTraService.deleteDoiTra(id);
        return ResponseEntity.noContent().build();
    }
}