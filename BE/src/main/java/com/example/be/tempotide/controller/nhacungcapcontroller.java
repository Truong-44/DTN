package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.nhacungcapdto;
import com.example.be.tempotide.service.NhaCungCapService;
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
public class nhacungcapcontroller {

    private static final Logger logger = LoggerFactory.getLogger(nhacungcapcontroller.class);

    private final NhaCungCapService nhaCungCapService;

    @Autowired
    public nhacungcapcontroller(NhaCungCapService nhaCungCapService) {
        this.nhaCungCapService = nhaCungCapService;
    }

    @PostMapping
    public ResponseEntity<nhacungcapdto> createNhaCungCap(@Valid @RequestBody nhacungcapdto nhaCungCapDto) {
        logger.info("API call: POST /api/nhacungcap");
        nhacungcapdto createdNhaCungCap = nhaCungCapService.createNhaCungCap(nhaCungCapDto);
        return new ResponseEntity<>(createdNhaCungCap, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<nhacungcapdto> getNhaCungCapById(@PathVariable Integer id) {
        logger.info("API call: GET /api/nhacungcap/{}", id);
        nhacungcapdto nhaCungCapDto = nhaCungCapService.getNhaCungCapById(id);
        return ResponseEntity.ok(nhaCungCapDto);
    }

    @GetMapping
    public ResponseEntity<List<nhacungcapdto>> getAllNhaCungCap() {
        logger.info("API call: GET /api/nhacungcap");
        List<nhacungcapdto> nhaCungCapList = nhaCungCapService.getAllNhaCungCap();
        return ResponseEntity.ok(nhaCungCapList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<nhacungcapdto> updateNhaCungCap(@PathVariable Integer id, @Valid @RequestBody nhacungcapdto nhaCungCapDto) {
        logger.info("API call: PUT /api/nhacungcap/{}", id);
        nhacungcapdto updatedNhaCungCap = nhaCungCapService.updateNhaCungCap(id, nhaCungCapDto);
        return ResponseEntity.ok(updatedNhaCungCap);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNhaCungCap(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/nhacungcap/{}", id);
        nhaCungCapService.deleteNhaCungCap(id);
        return ResponseEntity.noContent().build();
    }
}