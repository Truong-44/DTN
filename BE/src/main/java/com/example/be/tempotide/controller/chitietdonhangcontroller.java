package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.chitietdonhangdto;
import com.example.be.tempotide.service.chitietdonhangservice;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chitietdonhang")
public class chitietdonhangcontroller {

    private static final Logger logger = LoggerFactory.getLogger(chitietdonhangcontroller.class);

    private final chitietdonhangservice chitietdonhangservice;

    @Autowired
    public chitietdonhangcontroller(chitietdonhangservice chitietdonhangservice) {
        this.chitietdonhangservice = chitietdonhangservice;
    }

    @PostMapping
    public ResponseEntity<chitietdonhangdto> createchitietdonhang(@Valid @RequestBody chitietdonhangdto chitietdonhangdto) {
        logger.info("API call: POST /api/chitietdonhang");
        chitietdonhangdto createdchitietdonhang = chitietdonhangservice.createchitietdonhang(chitietdonhangdto);
        return new ResponseEntity<>(createdchitietdonhang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<chitietdonhangdto> getchitietdonhangbyid(@PathVariable Integer id) {
        logger.info("API call: GET /api/chitietdonhang/{}", id);
        chitietdonhangdto chitietdonhangdto = chitietdonhangservice.getchitietdonhangbyid(id);
        return ResponseEntity.ok(chitietdonhangdto);
    }

    @GetMapping
    public ResponseEntity<List<chitietdonhangdto>> getallchitietdonhang() {
        logger.info("API call: GET /api/chitietdonhang");
        List<chitietdonhangdto> chitietdonhanglist = chitietdonhangservice.getallchitietdonhang();
        return ResponseEntity.ok(chitietdonhanglist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<chitietdonhangdto> updatechitietdonhang(@PathVariable Integer id, @Valid @RequestBody chitietdonhangdto chitietdonhangdto) {
        logger.info("API call: PUT /api/chitietdonhang/{}", id);
        chitietdonhangdto updatedchitietdonhang = chitietdonhangservice.updatechitietdonhang(id, chitietdonhangdto);
        return ResponseEntity.ok(updatedchitietdonhang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletechitietdonhang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/chitietdonhang/{}", id);
        chitietdonhangservice.deletechitietdonhang(id);
        return ResponseEntity.noContent().build();
    }
}