package com.example.tempotide.controller;

import com.example.tempotide.dto.NguoiDungVaiTroDTO;
import com.example.tempotide.service.NguoiDungVaiTroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nguoidung-vaitro")
@RequiredArgsConstructor
public class NguoiDungVaiTroController {
    private final NguoiDungVaiTroService nguoiDungVaiTroService;

    @GetMapping
    public ResponseEntity<List<NguoiDungVaiTroDTO>> getAllNguoiDungVaiTros() {
        return ResponseEntity.ok(nguoiDungVaiTroService.getAllNguoiDungVaiTros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NguoiDungVaiTroDTO> getNguoiDungVaiTroById(@PathVariable Integer id) {
        return ResponseEntity.ok(nguoiDungVaiTroService.getNguoiDungVaiTroById(id));
    }

    @PostMapping
    public ResponseEntity<NguoiDungVaiTroDTO> createNguoiDungVaiTro(@RequestBody NguoiDungVaiTroDTO nguoiDungVaiTroDTO) {
        return ResponseEntity.ok(nguoiDungVaiTroService.createNguoiDungVaiTro(nguoiDungVaiTroDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NguoiDungVaiTroDTO> updateNguoiDungVaiTro(@PathVariable Integer id, @RequestBody NguoiDungVaiTroDTO nguoiDungVaiTroDTO) {
        return ResponseEntity.ok(nguoiDungVaiTroService.updateNguoiDungVaiTro(id, nguoiDungVaiTroDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNguoiDungVaiTro(@PathVariable Integer id) {
        nguoiDungVaiTroService.deleteNguoiDungVaiTro(id);
        return ResponseEntity.noContent().build();
    }
}