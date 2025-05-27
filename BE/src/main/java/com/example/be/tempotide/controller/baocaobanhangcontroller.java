package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.baocaobanhangdto;
import com.example.be.tempotide.service.baocaobanhangservice;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baocaobanhang")
public class baocaobanhangcontroller {

    private static final Logger logger = LoggerFactory.getLogger(baocaobanhangcontroller.class);

    private final baocaobanhangservice baocaobanhangservice;

    @Autowired
    public baocaobanhangcontroller(baocaobanhangservice baocaobanhangservice) {
        this.baocaobanhangservice = baocaobanhangservice;
    }

    @PostMapping
    public ResponseEntity<baocaobanhangdto> create_baocaobanhang(@Valid @RequestBody baocaobanhangdto baocaobanhangdto) {
        logger.info("API call: POST /api/baocaobanhang");
        baocaobanhangdto created_baocaobanhang = baocaobanhangservice.create_baocaobanhang(baocaobanhangdto);
        return new ResponseEntity<>(created_baocaobanhang, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<baocaobanhangdto> get_baocaobanhang_by_id(@PathVariable Integer id) {
        logger.info("API call: GET /api/baocaobanhang/{}", id);
        baocaobanhangdto baocaobanhangdto = baocaobanhangservice.get_baocaobanhang_by_id(id);
        return ResponseEntity.ok(baocaobanhangdto);
    }

    @GetMapping
    public ResponseEntity<List<baocaobanhangdto>> get_all_baocaobanhang() {
        logger.info("API call: GET /api/baocaobanhang");
        List<baocaobanhangdto> baocaobanhang_list = baocaobanhangservice.get_all_baocaobanhang();
        return ResponseEntity.ok(baocaobanhang_list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<baocaobanhangdto> update_baocaobanhang(@PathVariable Integer id, @Valid @RequestBody baocaobanhangdto baocaobanhangdto) {
        logger.info("API call: PUT /api/baocaobanhang/{}", id);
        baocaobanhangdto updated_baocaobanhang = baocaobanhangservice.update_baocaobanhang(id, baocaobanhangdto);
        return ResponseEntity.ok(updated_baocaobanhang);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete_baocaobanhang(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/baocaobanhang/{}", id);
        baocaobanhangservice.delete_baocaobanhang(id);
        return ResponseEntity.noContent().build();
    }
}