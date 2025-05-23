package com.example.be.TempoTideBE.controller;

import com.example.be.dto.LichSuDonHangDTO;
import com.example.be.service.LichSuDonHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lichsudonhang")
@RequiredArgsConstructor
public class LichSuDonHangController {

    private final LichSuDonHangService lichSuDonHangService;

    @GetMapping
    public ResponseEntity<List<LichSuDonHangDTO>> getAllLichSuDonHang() {
        return ResponseEntity.ok(lichSuDonHangService.getAllLichSuDonHang());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichSuDonHangDTO> getLichSuDonHangById(@PathVariable Integer id) {
        return ResponseEntity.okbundl(lichSuDonHangService.getLichSuDonHangById(id));
    }

    @PostMapping
    public ResponseEntity<LichSuDonHangDTO> createLichSuDonHang(@RequestBody LichSuDonHangDTO dto) {
        return ResponseEntity.ok(lichSuDonHangService.createLichSuDonHang(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LichSuDonHangDTO> updateLichSuDonHang(@PathVariable Integer id, @RequestBody LichSuDonHangDTO dto) {
        return ResponseEntity.ok(lichSuDonHangService.updateLichSuDonHang(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLichSuDonHang(@PathVariable Integer id) {
        lichSuDonHangService.deleteLichSuDonHang(id);
        return ResponseEntity.noContent().build();
    }
}