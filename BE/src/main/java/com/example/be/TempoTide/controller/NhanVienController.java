package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.NhanVienDto;
import com.example.be.TempoTide.service.NhanVienService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nhanvien")
public class NhanVienController {

    private static final Logger logger = LoggerFactory.getLogger(NhanVienController.class);

    private final NhanVienService nhanVienService;

    @Autowired
    public NhanVienController(NhanVienService nhanVienService) {
        this.nhanVienService = nhanVienService;
    }

    @PostMapping
    public ResponseEntity<NhanVienDto> createNhanVien(@Valid @RequestBody NhanVienDto nhanVienDto) {
        logger.info("API call: POST /api/nhanvien");
        NhanVienDto createdNhanVien = nhanVienService.createNhanVien(nhanVienDto);
        return new ResponseEntity<>(createdNhanVien, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhanVienDto> getNhanVienById(@PathVariable Integer id) {
        logger.info("API call: GET /api/nhanvien/{}", id);
        NhanVienDto nhanVienDto = nhanVienService.getNhanVienById(id);
        return ResponseEntity.ok(nhanVienDto);
    }

    @GetMapping
    public ResponseEntity<List<NhanVienDto>> getAllNhanVien() {
        logger.info("API call: GET /api/nhanvien");
        List<NhanVienDto> nhanVienList = nhanVienService.getAllNhanVien();
        return ResponseEntity.ok(nhanVienList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NhanVienDto> updateNhanVien(@PathVariable Integer id, @Valid @RequestBody NhanVienDto nhanVienDto) {
        logger.info("API call: PUT /api/nhanvien/{}", id);
        NhanVienDto updatedNhanVien = nhanVienService.updateNhanVien(id, nhanVienDto);
        return ResponseEntity.ok(updatedNhanVien);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNhanVien(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/nhanvien/{}", id);
        nhanVienService.deleteNhanVien(id);
        return ResponseEntity.noContent().build();
    }
}
