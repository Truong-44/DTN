package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.VaiTroQuyenDTO;
import com.example.be.tempotide.service.VaiTroQuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaitro-quyen")
@RequiredArgsConstructor
public class VaiTroQuyenController {
    private final VaiTroQuyenService vaiTroQuyenService;

    @GetMapping
    public ResponseEntity<List<VaiTroQuyenDTO>> getAllVaiTroQuyens() {
        return ResponseEntity.ok(vaiTroQuyenService.getAllVaiTroQuyens());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaiTroQuyenDTO> getVaiTroQuyenById(@PathVariable Integer id) {
        return ResponseEntity.ok(vaiTroQuyenService.getVaiTroQuyenById(id));
    }

    @PostMapping
    public ResponseEntity<VaiTroQuyenDTO> createVaiTroQuyen(@RequestBody VaiTroQuyenDTO vaiTroQuyenDTO) {
        return ResponseEntity.ok(vaiTroQuyenService.createVaiTroQuyen(vaiTroQuyenDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VaiTroQuyenDTO> updateVaiTroQuyen(@PathVariable Integer id, @RequestBody VaiTroQuyenDTO vaiTroQuyenDTO) {
        return ResponseEntity.ok(vaiTroQuyenService.updateVaiTroQuyen(id, vaiTroQuyenDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaiTroQuyen(@PathVariable Integer id) {
        vaiTroQuyenService.deleteVaiTroQuyen(id);
        return ResponseEntity.noContent().build();
    }
}