package com.example.be.TempoTide.controller;

import com.example.be.TempoTide.dto.CauHinhHeThongDto;
import com.example.be.TempoTide.service.CauHinhHeThongService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cauhinhhethong")
public class CauHinhHeThongController {

    private static final Logger logger = LoggerFactory.getLogger(CauHinhHeThongController.class);

    private final CauHinhHeThongService cauHinhHeThongService;

    @Autowired
    public CauHinhHeThongController(CauHinhHeThongService cauHinhHeThongService) {
        this.cauHinhHeThongService = cauHinhHeThongService;
    }

    @PostMapping
    public ResponseEntity<CauHinhHeThongDto> createCauHinhHeThong(@Valid @RequestBody CauHinhHeThongDto cauHinhHeThongDto) {
        logger.info("API call: POST /api/cauhinhhethong");
        CauHinhHeThongDto createdCauHinhHeThong = cauHinhHeThongService.createCauHinhHeThong(cauHinhHeThongDto);
        return new ResponseEntity<>(createdCauHinhHeThong, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CauHinhHeThongDto> getCauHinhHeThongById(@PathVariable Integer id) {
        logger.info("API call: GET /api/cauhinhhethong/{}", id);
        CauHinhHeThongDto cauHinhHeThongDto = cauHinhHeThongService.getCauHinhHeThongById(id);
        return ResponseEntity.ok(cauHinhHeThongDto);
    }

    @GetMapping
    public ResponseEntity<List<CauHinhHeThongDto>> getAllCauHinhHeThong() {
        logger.info("API call: GET /api/cauhinhhethong");
        List<CauHinhHeThongDto> cauHinhHeThongList = cauHinhHeThongService.getAllCauHinhHeThong();
        return ResponseEntity.ok(cauHinhHeThongList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CauHinhHeThongDto> updateCauHinhHeThong(@PathVariable Integer id, @Valid @RequestBody CauHinhHeThongDto cauHinhHeThongDto) {
        logger.info("API call: PUT /api/cauhinhhethong/{}", id);
        CauHinhHeThongDto updatedCauHinhHeThong = cauHinhHeThongService.updateCauHinhHeThong(id, cauHinhHeThongDto);
        return ResponseEntity.ok(updatedCauHinhHeThong);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCauHinhHeThong(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/cauhinhhethong/{}", id);
        cauHinhHeThongService.deleteCauHinhHeThong(id);
        return ResponseEntity.noContent().build();
    }
}