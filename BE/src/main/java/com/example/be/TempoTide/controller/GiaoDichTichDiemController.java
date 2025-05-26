package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.GiaoDichTichDiemDto;
import com.example.be.TempoTide.service.GiaoDichTichDiemService;
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
@RequestMapping("/api/giaodichtichdiem")
public class GiaoDichTichDiemController {

    private static final Logger logger = LoggerFactory.getLogger(GiaoDichTichDiemController.class);

    private final GiaoDichTichDiemService giaoDichTichDiemService;

    @Autowired
    public GiaoDichTichDiemController(GiaoDichTichDiemService giaoDichTichDiemService) {
        this.giaoDichTichDiemService = giaoDichTichDiemService;
    }

    @PostMapping
    public ResponseEntity<GiaoDichTichDiemDto> createGiaoDichTichDiem(@Valid @RequestBody GiaoDichTichDiemDto giaoDichTichDiemDto) {
        logger.info("API call: POST /api/giaodichtichdiem");
        GiaoDichTichDiemDto createdGiaoDichTichDiem = giaoDichTichDiemService.createGiaoDichTichDiem(giaoDichTichDiemDto);
        return new ResponseEntity<>(createdGiaoDichTichDiem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiaoDichTichDiemDto> getGiaoDichTichDiemById(@PathVariable Integer id) {
        logger.info("API call: GET /api/giaodichtichdiem/{}", id);
        GiaoDichTichDiemDto giaoDichTichDiemDto = giaoDichTichDiemService.getGiaoDichTichDiemById(id);
        return ResponseEntity.ok(giaoDichTichDiemDto);
    }

    @GetMapping
    public ResponseEntity<List<GiaoDichTichDiemDto>> getAllGiaoDichTichDiem() {
        logger.info("API call: GET /api/giaodichtichdiem");
        List<GiaoDichTichDiemDto> giaoDichTichDiemList = giaoDichTichDiemService.getAllGiaoDichTichDiem();
        return ResponseEntity.ok(giaoDichTichDiemList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiaoDichTichDiemDto> updateGiaoDichTichDiem(@PathVariable Integer id, @Valid @RequestBody GiaoDichTichDiemDto giaoDichTichDiemDto) {
        logger.info("API call: PUT /api/giaodichtichdiem/{}", id);
        GiaoDichTichDiemDto updatedGiaoDichTichDiem = giaoDichTichDiemService.updateGiaoDichTichDiem(id, giaoDichTichDiemDto);
        return ResponseEntity.ok(updatedGiaoDichTichDiem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiaoDichTichDiem(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/giaodichtichdiem/{}", id);
        giaoDichTichDiemService.deleteGiaoDichTichDiem(id);
        return ResponseEntity.noContent().build();
    }
}
