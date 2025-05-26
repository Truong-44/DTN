package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.NhaCungCapDto;
import com.example.be.TempoTide.service.NhaCungCapService;
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
@RequestMapping("/api/nhacungcap")
public class NhaCungCapController {

    private static final Logger logger = LoggerFactory.getLogger(NhaCungCapController.class);

    private final NhaCungCapService nhaCungCapService;

    @Autowired
    public NhaCungCapController(NhaCungCapService nhaCungCapService) {
        this.nhaCungCapService = nhaCungCapService;
    }

    @PostMapping
    public ResponseEntity<NhaCungCapDto> createNhaCungCap(@Valid @RequestBody NhaCungCapDto nhaCungCapDto) {
        logger.info("API call: POST /api/nhacungcap");
        NhaCungCapDto createdNhaCungCap = nhaCungCapService.createNhaCungCap(nhaCungCapDto);
        return new ResponseEntity<>(createdNhaCungCap, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhaCungCapDto> getNhaCungCapById(@PathVariable Integer id) {
        logger.info("API call: GET /api/nhacungcap/{}", id);
        NhaCungCapDto nhaCungCapDto = nhaCungCapService.getNhaCungCapById(id);
        return ResponseEntity.ok(nhaCungCapDto);
    }

    @GetMapping
    public ResponseEntity<List<NhaCungCapDto>> getAllNhaCungCap() {
        logger.info("API call: GET /api/nhacungcap");
        List<NhaCungCapDto> nhaCungCapList = nhaCungCapService.getAllNhaCungCap();
        return ResponseEntity.ok(nhaCungCapList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NhaCungCapDto> updateNhaCungCap(@PathVariable Integer id, @Valid @RequestBody NhaCungCapDto nhaCungCapDto) {
        logger.info("API call: PUT /api/nhacungcap/{}", id);
        NhaCungCapDto updatedNhaCungCap = nhaCungCapService.updateNhaCungCap(id, nhaCungCapDto);
        return ResponseEntity.ok(updatedNhaCungCap);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNhaCungCap(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/nhacungcap/{}", id);
        nhaCungCapService.deleteNhaCungCap(id);
        return ResponseEntity.noContent().build();
    }
}
