package com.example.be.TempoTideBE.controller;

import com.example.be.dto.NguoiDungVaiTroDTO;
import com.example.be.service.NguoiDungVaiTroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nguoidungvaitro")
@RequiredArgsConstructor
public class NguoiDungVaiTroController {

    private final NguoiDungVaiTroService nguoiDungVaiTroService;

    @GetMapping
    public ResponseEntity<List<NguoiDungVaiTroDTO>> getAllNguoiDungVaiTro() {
        return ResponseEntity.ok(nguoiDungVaiTroService.getAllNguoiDungVaiTro());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NguoiDungVaiTroDTO> getNguoiDungVaiTroById(@PathVariable Integer id) {
        return ResponseEntity.ok(nguoiDungVaiTroService.getNguoiDungVaiTroById(id));
    }

    @PostMapping
    public ResponseEntity<NguoiDungVaiTroDTO> createNguoiDungVaiTro(@RequestBody NguoiDungVaiTroDTO dto) {
        return ResponseEntity.ok(nguoiDungVaiTroService.createNguoiDungVaiTro(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NguoiDungVaiTroDTO> updateNguoiDungVaiTro(@PathVariable Integer id, @RequestBody NguoiDungVaiTroDTO dto) {
        return ResponseEntity.ok(nguoiDungVaiTroService.updateNguoiDungVaiTro(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNguoiDungVaiTro(@PathVariable Integer id) {
        nguoiDungVaiTroService.deleteNguoiDungVaiTro(id);
        return ResponseEntity.noContent().build();
    }
}