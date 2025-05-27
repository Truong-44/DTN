package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.baohanhdto;
import com.example.be.tempotide.service.baohanhservice;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baohanh")
public class baohanhcontroller {

    private static final Logger logger = LoggerFactory.getLogger(baohanhcontroller.class);

    private final baohanhservice baohanhservice;

    @Autowired
    public baohanhcontroller(baohanhservice baohanhservice) {
        this.baohanhservice = baohanhservice;
    }

    @PostMapping
    public ResponseEntity<baohanhdto> createbaohanh(@Valid @RequestBody baohanhdto baohanhdto) {
        logger.info("API call: POST /api/baohanh");
        baohanhdto createdbaohanh = baohanhservice.createbaohanh(baohanhdto);
        return new ResponseEntity<>(createdbaohanh, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<baohanhdto> getbaohanhbyid(@PathVariable Integer id) {
        logger.info("API call: GET /api/baohanh/{}", id);
        baohanhdto baohanhdto = baohanhservice.getbaohanhbyid(id);
        return ResponseEntity.ok(baohanhdto);
    }

    @GetMapping
    public ResponseEntity<List<baohanhdto>> getallbaohanh() {
        logger.info("API call: GET /api/baohanh");
        List<baohanhdto> baohanhlist = baohanhservice.getallbaohanh();
        return ResponseEntity.ok(baohanhlist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<baohanhdto> updatebaohanh(@PathVariable Integer id, @Valid @RequestBody baohanhdto baohanhdto) {
        logger.info("API call: PUT /api/baohanh/{}", id);
        baohanhdto updatedbaohanh = baohanhservice.updatebaohanh(id, baohanhdto);
        return ResponseEntity.ok(updatedbaohanh);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletebaohanh(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/baohanh/{}", id);
        baohanhservice.deletebaohanh(id);
        return ResponseEntity.noContent().build();
    }
}