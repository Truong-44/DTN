package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.giaodichtichdiemdto;
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
public class giaodichtichdiemcontroller {

    private static final Logger logger = LoggerFactory.getLogger(giaodichtichdiemcontroller.class);

    private final com.example.be.tempotide.service.giaodichtichdiemservice giaoDichTichDiemService;

    @Autowired
    public giaodichtichdiemcontroller(com.example.be.tempotide.service.giaodichtichdiemservice giaoDichTichDiemService) {
        this.giaoDichTichDiemService = giaoDichTichDiemService;
    }

    @PostMapping
    public ResponseEntity<giaodichtichdiemdto> createGiaoDichTichDiem(@Valid @RequestBody giaodichtichdiemdto giaoDichTichDiemDto) {
        logger.info("API call: POST /api/giaodichtichdiem");
        giaodichtichdiemdto createdGiaoDichTichDiem = giaoDichTichDiemService.createGiaoDichTichDiem(giaoDichTichDiemDto);
        return new ResponseEntity<>(createdGiaoDichTichDiem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<giaodichtichdiemdto> getGiaoDichTichDiemById(@PathVariable Integer id) {
        logger.info("API call: GET /api/giaodichtichdiem/{}", id);
        giaodichtichdiemdto giaoDichTichDiemDto = giaoDichTichDiemService.getGiaoDichTichDiemById(id);
        return ResponseEntity.ok(giaoDichTichDiemDto);
    }

    @GetMapping
    public ResponseEntity<List<giaodichtichdiemdto>> getAllGiaoDichTichDiem() {
        logger.info("API call: GET /api/giaodichtichdiem");
        List<giaodichtichdiemdto> giaoDichTichDiemList = giaoDichTichDiemService.getAllGiaoDichTichDiem();
        return ResponseEntity.ok(giaoDichTichDiemList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<giaodichtichdiemdto> updateGiaoDichTichDiem(@PathVariable Integer id, @Valid @RequestBody giaodichtichdiemdto giaoDichTichDiemDto) {
        logger.info("API call: PUT /api/giaodichtichdiem/{}", id);
        giaodichtichdiemdto updatedGiaoDichTichDiem = giaoDichTichDiemService.updateGiaoDichTichDiem(id, giaoDichTichDiemDto);
        return ResponseEntity.ok(updatedGiaoDichTichDiem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiaoDichTichDiem(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/giaodichtichdiem/{}", id);
        giaoDichTichDiemService.deleteGiaoDichTichDiem(id);
        return ResponseEntity.noContent().build();
    }
}
