package com.example.be.TempoTideBE.controller;


import com.example.be.TempoTideBE.dto.CauHinhHeThongDTO;
import com.example.be.TempoTideBE.service.CauHinhHeThongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cauhinhhethong")
@RequiredArgsConstructor
public class CauHinhHeThongController {

    private final CauHinhHeThongService cauHinhHeThongService;

    @GetMapping
    public ResponseEntity<List<CauHinhHeThongDTO>> getAllCauHinhHeThong() {
        return ResponseEntity.ok(cauHinhHeThongService.getAllCauHinhHeThong());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CauHinhHeThongDTO> getCauHinhHeThongById(@PathVariable Integer id) {
        return ResponseEntity.ok(cauHinhHeThongService.getCauHinhHeThongById(id));
    }

    @PostMapping
    public ResponseEntity<CauHinhHeThongDTO> createCauHinhHeThong(@RequestBody CauHinhHeThongDTO dto) {
        return ResponseEntity.ok(cauHinhHeThongService.createCauHinhHeThong(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CauHinhHeThongDTO> updateCauHinhHeThong(@PathVariable Integer id, @RequestBody CauHinhHeThongDTO dto) {
        return ResponseEntity.ok(cauHinhHeThongService.updateCauHinhHeThong(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCauHinhHeThong(@PathVariable Integer id) {
        cauHinhHeThongService.deleteCauHinhHeThong(id);
        return ResponseEntity.noContent().build();
    }
}