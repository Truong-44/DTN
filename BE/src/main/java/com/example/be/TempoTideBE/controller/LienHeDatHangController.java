package com.example.be.TempoTideBE.controller;

import com.example.be.dto.LienHeDatHangDTO;
import com.example.be.service.LienHeDatHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lienhedathang")
@RequiredArgsConstructor
public class LienHeDatHangController {

    private final LienHeDatHangService lienHeDatHangService;

    @GetMapping
    public ResponseEntity<List<LienHeDatHangDTO>> getAllLienHeDatHang() {
        return ResponseEntity.ok(lienHeDatHangService.getAllLienHeDatHang());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LienHeDatHangDTO> getLienHeDatHangById(@PathVariable Integer id) {
        return ResponseEntity.ok(lienHeDatHangService.getLienHeDatHangById(id));
    }

    @PostMapping
    public ResponseEntity<LienHeDatHangDTO> createLienHeDatHang(@RequestBody LienHeDatHangDTO dto) {
        return ResponseEntity.ok(lienHeDatHangService.createLienHeDatHang(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LienHeDatHangDTO> updateLienHeDatHang(@PathVariable Integer id, @RequestBody LienHeDatHangDTO dto) {
        return ResponseEntity.ok(lienHeDatHangService.updateLienHeDatHang(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLienHeDatHang(@PathVariable Integer id) {
        lienHeDatHangService.deleteLienHeDatHang(id);
        return ResponseEntity.noContent().build();
    }
}