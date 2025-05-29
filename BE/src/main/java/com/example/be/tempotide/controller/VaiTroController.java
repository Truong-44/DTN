package com.example.tempotide.controller;

import com.example.tempotide.dto.VaiTroDTO;
import com.example.tempotide.service.VaiTroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaitro")
@RequiredArgsConstructor
public class VaiTroController {
    private final VaiTroService vaiTroService;

    @GetMapping
    public ResponseEntity<List<VaiTroDTO>> getAllVaiTros() {
        return ResponseEntity.ok(vaiTroService.getAllVaiTros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaiTroDTO> getVaiTroById(@PathVariable Integer id) {
        return ResponseEntity.ok(vaiTroService.getVaiTroById(id));
    }

    @PostMapping
    public ResponseEntity<VaiTroDTO> createVaiTro(@RequestBody VaiTroDTO vaiTroDTO) {
        return ResponseEntity.ok(vaiTroService.createVaiTro(vaiTroDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VaiTroDTO> updateVaiTro(@PathVariable Integer id, @RequestBody VaiTroDTO vaiTroDTO) {
        return ResponseEntity.ok(vaiTroService.updateVaiTro(id, vaiTroDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaiTro(@PathVariable Integer id) {
        vaiTroService.deleteVaiTro(id);
        return ResponseEntity.noContent().build();
    }
}