package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.cauhinhhethongdto;
import com.example.be.tempotide.service.cauhinhhethongservice;
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

    private final cauhinhhethongservice cauhinhhethongservice;

    @Autowired
    public cauhinhhethongcontroller(cauhinhhethongservice cauhinhhethongservice) {
        this.cauhinhhethongservice = cauhinhhethongservice;
    }

    @PostMapping
    public ResponseEntity<cauhinhhethongdto> createcauhinhhethong(@Valid @RequestBody cauhinhhethongdto cauhinhhethongdto) {
        logger.info("API call: POST /api/cauhinhhethong");
        cauhinhhethongdto createdcauhinhhethong = cauhinhhethongservice.createcauhinhhethong(cauhinhhethongdto);
        return new ResponseEntity<>(createdcauhinhhethong, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<cauhinhhethongdto> getcauhinhhethongbyid(@PathVariable Integer id) {
        logger.info("API call: GET /api/cauhinhhethong/{}", id);
        cauhinhhethongdto cauhinhhethongdto = cauhinhhethongservice.getcauhinhhethongbyid(id);
        return ResponseEntity.ok(cauhinhhethongdto);
    }

    @GetMapping
    public ResponseEntity<List<cauhinhhethongdto>> getallcauhinhhethong() {
        logger.info("API call: GET /api/cauhinhhethong");
        List<cauhinhhethongdto> cauhinhhethonglist = cauhinhhethongservice.getallcauhinhhethong();
        return ResponseEntity.ok(cauhinhhethonglist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<cauhinhhethongdto> updatecauhinhhethong(@PathVariable Integer id, @Valid @RequestBody cauhinhhethongdto cauhinhhethongdto) {
        logger.info("API call: PUT /api/cauhinhhethong/{}", id);
        cauhinhhethongdto updatedcauhinhhethong = cauhinhhethongservice.updatecauhinhhethong(id, cauhinhhethongdto);
        return ResponseEntity.ok(updatedcauhinhhethong);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletecauhinhhethong(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/cauhinhhethong/{}", id);
        cauhinhhethongservice.deletecauhinhhethong(id);
        return ResponseEntity.noContent().build();
    }
}