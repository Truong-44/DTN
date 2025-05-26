package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.cauhinhhethongdto;
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
public class cauhinhhethongcontroller {

    private static final Logger logger = LoggerFactory.getLogger(cauhinhhethongcontroller.class);

    private final com.example.be.tempotide.service.cauhinhhethongservice cauHinhHeThongService;

    @Autowired
    public cauhinhhethongcontroller(com.example.be.tempotide.service.cauhinhhethongservice cauHinhHeThongService) {
        this.cauHinhHeThongService = cauHinhHeThongService;
    }

    @PostMapping
    public ResponseEntity<cauhinhhethongdto> createCauHinhHeThong(@Valid @RequestBody cauhinhhethongdto cauHinhHeThongDto) {
        logger.info("API call: POST /api/cauhinhhethong");
        cauhinhhethongdto createdCauHinhHeThong = cauHinhHeThongService.createCauHinhHeThong(cauHinhHeThongDto);
        return new ResponseEntity<>(createdCauHinhHeThong, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<cauhinhhethongdto> getCauHinhHeThongById(@PathVariable Integer id) {
        logger.info("API call: GET /api/cauhinhhethong/{}", id);
        cauhinhhethongdto cauHinhHeThongDto = cauHinhHeThongService.getCauHinhHeThongById(id);
        return ResponseEntity.ok(cauHinhHeThongDto);
    }

    @GetMapping
    public ResponseEntity<List<cauhinhhethongdto>> getAllCauHinhHeThong() {
        logger.info("API call: GET /api/cauhinhhethong");
        List<cauhinhhethongdto> cauHinhHeThongList = cauHinhHeThongService.getAllCauHinhHeThong();
        return ResponseEntity.ok(cauHinhHeThongList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<cauhinhhethongdto> updateCauHinhHeThong(@PathVariable Integer id, @Valid @RequestBody cauhinhhethongdto cauHinhHeThongDto) {
        logger.info("API call: PUT /api/cauhinhhethong/{}", id);
        cauhinhhethongdto updatedCauHinhHeThong = cauHinhHeThongService.updateCauHinhHeThong(id, cauHinhHeThongDto);
        return ResponseEntity.ok(updatedCauHinhHeThong);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCauHinhHeThong(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/cauhinhhethong/{}", id);
        cauHinhHeThongService.deleteCauHinhHeThong(id);
        return ResponseEntity.noContent().build();
    }
}