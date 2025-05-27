package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.capbackhachhangdto;
import com.example.be.tempotide.service.capbackhachhangservice;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/capbackhachhang")
public class capbackhachhangcontroller {

    private static final Logger logger = LoggerFactory.getLogger(capbackhachhangcontroller.class);

    private final capbackhachhangservice capbackhachhangservice;

    @Autowired
    public capbackhachhangcontroller(capbackhachhangservice capbackhachhangservice) {
        this.capbackhachhangservice = capbackhachhangservice;
    }

    @PostMapping
    public ResponseEntity<capbackhachhangdto> createcapbackhachhang(@Valid @RequestBody capbackhachhangdto capbackhachhangdto) {
        logger.info("API call: POST /api/capbackhachhang");
        capbackhachhangdto createdcapbackhachhang = capbackhachhangservice.createcapbackhachhang(capbackhachhangdto);
        return new ResponseEntity<>(createdcapbackhachhang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<capbackhachhangdto> getcapbackhachhangbyid(@PathVariable Integer id) {
        logger.info("API call: GET /api/capbackhachhang/{}", id);
        capbackhachhangdto capbackhachhangdto = capbackhachhangservice.getcapbackhachhangbyid(id);
        return ResponseEntity.ok(capbackhachhangdto);
    }

    @GetMapping
    public ResponseEntity<List<capbackhachhangdto>> getallcapbackhachhang() {
        logger.info("API call: GET /api/capbackhachhang");
        List<capbackhachhangdto> capbackhachhanglist = capbackhachhangservice.getallcapbackhachhang();
        return ResponseEntity.ok(capbackhachhanglist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<capbackhachhangdto> updatecapbackhachhang(@PathVariable Integer id, @Valid @RequestBody capbackhachhangdto capbackhachhangdto) {
        logger.info("API call: PUT /api/capbackhachhang/{}", id);
        capbackhachhangdto updatedcapbackhachhang = capbackhachhangservice.updatecapbackhachhang(id, capbackhachhangdto);
        return ResponseEntity.ok(updatedcapbackhachhang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletecapbackhachhang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/capbackhachhang/{}", id);
        capbackhachhangservice.deletecapbackhachhang(id);
        return ResponseEntity.noContent().build();
    }
}