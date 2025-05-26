package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.nhanviendto;
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
public class nhanviencontroller {

    private static final Logger logger = LoggerFactory.getLogger(nhanviencontroller.class);

    private final com.example.be.tempotide.service.nhanvienservice nhanVienService;

    @Autowired
    public nhanviencontroller(com.example.be.tempotide.service.nhanvienservice nhanVienService) {
        this.nhanVienService = nhanVienService;
    }

    @PostMapping
    public ResponseEntity<nhanviendto> createNhanVien(@Valid @RequestBody nhanviendto nhanVienDto) {
        logger.info("API call: POST /api/nhanvien");
        nhanviendto createdNhanVien = nhanVienService.createNhanVien(nhanVienDto);
        return new ResponseEntity<>(createdNhanVien, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<nhanviendto> getNhanVienById(@PathVariable Integer id) {
        logger.info("API call: GET /api/nhanvien/{}", id);
        nhanviendto nhanVienDto = nhanVienService.getNhanVienById(id);
        return ResponseEntity.ok(nhanVienDto);
    }

    @GetMapping
    public ResponseEntity<List<nhanviendto>> getAllNhanVien() {
        logger.info("API call: GET /api/nhanvien");
        List<nhanviendto> nhanVienList = nhanVienService.getAllNhanVien();
        return ResponseEntity.ok(nhanVienList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<nhanviendto> updateNhanVien(@PathVariable Integer id, @Valid @RequestBody nhanviendto nhanVienDto) {
        logger.info("API call: PUT /api/nhanvien/{}", id);
        nhanviendto updatedNhanVien = nhanVienService.updateNhanVien(id, nhanVienDto);
        return ResponseEntity.ok(updatedNhanVien);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNhanVien(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/nhanvien/{}", id);
        nhanVienService.deleteNhanVien(id);
        return ResponseEntity.noContent().build();
    }
}