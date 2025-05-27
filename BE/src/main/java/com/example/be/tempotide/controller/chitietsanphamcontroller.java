package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.chitietsanphamdto;
import com.example.be.tempotide.service.chitietsanphamservice;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietsanpham")
public class chitietsanphamcontroller {

    private static final Logger logger = LoggerFactory.getLogger(chitietsanphamcontroller.class);

    private final chitietsanphamservice chitietsanphamservice;

    @Autowired
    public chitietsanphamcontroller(chitietsanphamservice chitietsanphamservice) {
        this.chitietsanphamservice = chitietsanphamservice;
    }

    @PostMapping
    public ResponseEntity<chitietsanphamdto> createchitietsanpham(@Valid @RequestBody chitietsanphamdto chitietsanphamdto) {
        logger.info("API call: POST /api/chitietsanpham");
        chitietsanphamdto createdchitietsanpham = chitietsanphamservice.createchitietsanpham(chitietsanphamdto);
        return new ResponseEntity<>(createdchitietsanpham, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<chitietsanphamdto> getchitietsanphambyid(@PathVariable Integer id) {
        logger.info("API call: GET /api/chitietsanpham/{}", id);
        chitietsanphamdto chitietsanphamdto = chitietsanphamservice.getchitietsanphambyid(id);
        return ResponseEntity.ok(chitietsanphamdto);
    }

    @GetMapping
    public ResponseEntity<List<chitietsanphamdto>> getallchitietsanpham() {
        logger.info("API call: GET /api/chitietsanpham");
        List<chitietsanphamdto> chitietsanphamlist = chitietsanphamservice.getallchitietsanpham();
        return ResponseEntity.ok(chitietsanphamlist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<chitietsanphamdto> updatechitietsanpham(@PathVariable Integer id, @Valid @RequestBody chitietsanphamdto chitietsanphamdto) {
        logger.info("API call: PUT /api/chitietsanpham/{}", id);
        chitietsanphamdto updatedchitietsanpham = chitietsanphamservice.updatechitietsanpham(id, chitietsanphamdto);
        return ResponseEntity.ok(updatedchitietsanpham);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletechitietsanpham(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/chitietsanpham/{}", id);
        chitietsanphamservice.deletechitietsanpham(id);
        return ResponseEntity.noContent().build();
    }
}