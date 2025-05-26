package com.example.be.tempotide.controller;

import com.example.be.tempotide.dto.danhsachyeuthichdto;
import com.example.be.tempotide.service.DanhSachYeuThichService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danhsachyeuthich")
public class danhsachyeuthichcontroller {

    private static final Logger logger = LoggerFactory.getLogger(danhsachyeuthichcontroller.class);

    private final DanhSachYeuThichService danhSachYeuThichService;

    @Autowired
    public danhsachyeuthichcontroller(DanhSachYeuThichService danhSachYeuThichService) {
        this.danhSachYeuThichService = danhSachYeuThichService;
    }

    @PostMapping
    public ResponseEntity<danhsachyeuthichdto> createDanhSachYeuThich(@Valid @RequestBody danhsachyeuthichdto danhSachYeuThichDto) {
        logger.info("API call: POST /api/danhsachyeuthich");
        danhsachyeuthichdto createdDanhSachYeuThich = danhSachYeuThichService.createDanhSachYeuThich(danhSachYeuThichDto);
        return new ResponseEntity<>(createdDanhSachYeuThich, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<danhsachyeuthichdto> getDanhSachYeuThichById(@PathVariable Integer id) {
        logger.info("API call: GET /api/danhsachyeuthich/{}", id);
        danhsachyeuthichdto danhSachYeuThichDto = danhSachYeuThichService.getDanhSachYeuThichById(id);
        return ResponseEntity.ok(danhSachYeuThichDto);
    }

    @GetMapping
    public ResponseEntity<List<danhsachyeuthichdto>> getAllDanhSachYeuThich() {
        logger.info("API call: GET /api/danhsachyeuthich");
        List<danhsachyeuthichdto> danhSachYeuThichList = danhSachYeuThichService.getAllDanhSachYeuThich();
        return ResponseEntity.ok(danhSachYeuThichList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<danhsachyeuthichdto> updateDanhSachYeuThich(@PathVariable Integer id, @Valid @RequestBody danhsachyeuthichdto danhSachYeuThichDto) {
        logger.info("API call: PUT /api/danhsachyeuthich/{}", id);
        danhsachyeuthichdto updatedDanhSachYeuThich = danhSachYeuThichService.updateDanhSachYeuThich(id, danhSachYeuThichDto);
        return ResponseEntity.ok(updatedDanhSachYeuThich);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDanhSachYeuThich(@PathVariable Integer id) {
        logger.info("API call: DELETE /api/danhsachyeuthich/{}", id);
        danhSachYeuThichService.deleteDanhSachYeuThich(id);
        return ResponseEntity.noContent().build();
    }
}